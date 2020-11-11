#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>


union semun {
    int              val;   /* Value for SETVAL */
    struct semid_ds *buf;   /* Buffer for IPC_STAT, IPC_SET */
    unsigned short  *array; /* Array for GETALL, SETALL */
    struct seminfo  *__buf; /* Buffer for IPC_INFO */
};



int main() {
    int nbProgs, nbZones, idSem;
    union semun arg;
    struct sembuf buf;

    // saisie de valeurs
    do {printf("Saisir le nombre de programmes : ");
        scanf("%d", &nbProgs);
    } while(nbProgs <= 0);

    do {printf("Saisir le nombre de zones : ");
        scanf("%d", &nbZones);
    } while(nbZones <= 0);

    // création du tableau de sémaphores
    //key_t key = ftok("chemin/fichier", n);
    idSem = semget(1,               // clé
                   nbZones,         // nombre de sémaphores
                   IPC_CREAT|0666); // droits

    if(idSem < 0) {
        perror("Erreur création sémaphore ");
        return 1;
    }

    // initialisation
    arg.array = calloc(nbZones, sizeof(unsigned short));

    if(arg.array == NULL) {
        perror("Erreur allocation ");
        semctl(idSem, 0, IPC_RMID);
        return 1;
    }

    if(semctl(idSem, 0, SETALL, arg) == -1) {
        perror("Erreur initialisation ");
        if(semctl(idSem, 0, IPC_RMID) == -1) {
            perror("Erreur rm sem ");
        }
        free(arg.array);
        return 1;
    }

    free(arg.array);

    // attente de la terminaison des traitements
    printf("Attente du traitement par les %d programmes sur %d zones...\n", nbProgs, nbZones);
    
    // on attend que la dernière zone soit traitée par le dernier programme
    buf.sem_num = nbZones-1; // semaphore
    buf.sem_op = -nbProgs;   // opération P
    buf.sem_flg = 0;         // aucun flag

    if(semop(idSem, &buf, 1) == -1) {
        perror("Erreur terminaison op P ");
        if(semctl(idSem, 0, IPC_RMID) == -1) {
            perror("Erreur rm sem ");
        }
        return 1;
    }

    // libération des ressources
    if(semctl(idSem, 0, IPC_RMID) == -1) {
        perror("Erreur rm sem ");
        return 1;
    }

    return 0;
}
