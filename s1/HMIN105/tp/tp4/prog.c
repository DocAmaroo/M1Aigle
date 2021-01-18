#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/sem.h>


union semun {
    int              val;   /* Value for SETVAL */
    struct semid_ds *buf;   /* Buffer for IPC_STAT, IPC_SET */
    unsigned short  *array; /* Array for GETALL, SETALL */
    struct seminfo  *__buf; /* Buffer for IPC_INFO */
};


/*
    Les zones sont traitées successivement, en ayant l'exclusivité
    Pour chaque zone, le programme attend qu'elle soit traitée par les programmes
    ayant un numéro inférieur, sauf pour le premier programme (numéroté 0)
*/

int main(int argc, char** argv) {
    int idSem, nbZones;
    union semun arg;
    struct sembuf buf;


    if(argc != 2) {
        printf("Utilisation : %s <prog>\n\
        - prog : numéro du programme dans [0-nbProgs[\n",
        argv[0]);

        return 1;
    }

    int prog = atoi(argv[1]); // numéro du programme
    

    // récupération de l'id du sémaphore
    /*
    Normalement on génère une clé avec ftok :
        key_t key = ftok("chemin/fichier", n);
    mais on va se contenter d'utiliser la clé 1
    /!\ Ne pas utiliser la clé 0 /!\
    -> elle correspond à IPC_PRIVATE, voir manuel
    Les autres paramètres ne sont pas importants
    car on récupère un sémaphore déjà existant
    */
    idSem = semget(1,  // clé
                   0,  // nombre de sémaphores
                   0); // flags

    if(idSem == -1) {
        perror("Erreur récupération sémaphore ");
        return 1;
    }


    // récupération du nombre de zones
    arg.buf = malloc(sizeof(struct semid_ds));

    if(arg.buf == NULL) {
        perror("Erreur allocation ");
        return 1;
    }

    if(semctl(idSem, 0, IPC_STAT, arg) == -1) {
        perror("Erreur récupération nbZones ");
        free(arg.buf);
        return 1;
    }

    nbZones = arg.buf->sem_nsems;
    free(arg.buf);


    // traitement
    buf.sem_flg = 0; // aucun flag

    for(int i=0; i<nbZones; i++) {
        // décrémentation du sémaphore
        buf.sem_num = i;    // semaphore
        buf.sem_op = -prog; // opération P

        printf("Prog %d attend zone %d (P=%d)\n", prog, i, buf.sem_op);

        if(semop(idSem, &buf, 1) == -1) {
            perror("Erreur semop P ");
            return 1;
        }

        // travail sur la zone
        printf("Prog %d travaille sur zone %d\n", prog, i);
        sleep(5);

        // incrémentation du sémaphore
        buf.sem_op = prog + 1; // opération V

        printf("Prog %d libère zone %d (V=%d)\n", prog, i, buf.sem_op);

        if(semop(idSem, &buf, 1) == -1) {
            perror("Erreur semop V ");
            return 1;
        }
    }

    return 0;
}