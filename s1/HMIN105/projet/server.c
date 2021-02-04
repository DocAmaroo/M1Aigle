#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <pthread.h>

//------- COLORS ------- //
#define RED   "\x1B[31m"
#define GRN   "\x1B[32m"
#define YEL   "\x1B[33m"
#define BLU   "\x1B[34m"
#define MAG   "\x1B[35m"
#define CYN   "\x1B[36m"
#define WHT   "\x1B[37m"
#define RESET "\x1B[0m"

//------- GLOBAL VAR ------- //
#define SHM_KEY 1 // cle du segment de memoire partage
#define SEM_KEY 2 // cle du tableau de semaphores
#define DEFAULT_BUFFER_SIZE 100 // nombre de carateres max d'une ligne
#define MAX_CLIENT 10
#define MAX_STORES 10

//------- STRUCTURES ------- //
typedef struct store {
	char city[20];
	int cpu;
	int go;
} store;

typedef struct log {
    char name[20];
    char firstname[20];
    int city;
    int cpu;
    int go;
    int max_user;
    int nb_user;
    int flag;
} log;

typedef struct{
	int nb_client_connected;
	int nb_stores;
	store stores[MAX_STORES];
	int nb_logs;
	log logs[MAX_CLIENT*MAX_STORES];
}shm_data;

//------- SEMAPHORES ------- //
typedef union{
	int val;
	struct semid_ds *buf;
	unsigned short *array;
	struct seminfo *__buf;
} semun;

void print_system(int semid, int sem_length, store* stores, int store_length) {
    int sem_cpt = 0;

    semun semun;
    semun.array = malloc(sem_length * sizeof(short));
    if (semctl(semid, 0, GETALL, semun.array) == -1) {
        perror("[-]Error occurred while reading semaphores");
        return;
    }


    printf(CYN "\n\t--- Etat du systeme ---\n");
    printf("index: { ville | cpu | cpu partages | go | go partages }\n");
    for (int i=0; i < store_length; i++) {
        sem_cpt = i*4;
        printf("%i: { %s | %i | %i | %i | %i }\n", i, stores[i].city, semun.array[sem_cpt], semun.array[sem_cpt+1], semun.array[sem_cpt+2], semun.array[sem_cpt+3]);
    }

    printf("%i", semun.array[sem_length-1]);
}


int main(int argc, char const *argv[]) {
	
	if (argc < 2) {
        printf("Usage: %s filename", argv[0]);
        printf("\t[filename] : database file");
        exit(0);
    }

    // OUVERTURE DU FICHIER CONTENANT LES STORES
    FILE *f;
	if ((f = fopen(argv[1], "r")) == NULL){
		perror("seveur : erreur fopen");
		exit(1);
	}

	int nbstores = 0;
	char ligne[DEFAULT_BUFFER_SIZE];
	// COMPTE LE NOMBE DE STORES
	while (fgets(ligne, DEFAULT_BUFFER_SIZE, f) != NULL){
		nbstores++;
	}

	if (nbstores > MAX_STORES){
		printf(RED "[-]Nombre de données trop important! Le max est %i, vous en avez donné(s) %i", MAX_STORES, nbstores);
		exit(1);
	}

	shm_data data;
	data.nb_stores = nbstores;

	rewind(f); // REPLACE LE CUSEUR AU DEBUT DU FICHIER
	char * token;
	
	// LIT LE FICHIER LIGNE PAR LIGNE ET COPIE LES INFORMATIONS DANS LE TABLEAU DE STORES
	for (int i = 0; i < data.nb_stores; i++){
		fgets(ligne, DEFAULT_BUFFER_SIZE, f);
		// RECUPERE ET COPIE CITY
		token = strtok(ligne, " ");
		memcpy(data.stores[i].city, token, 20);
		// RECUPERE ET COPIE CPU
		token = strtok(NULL, " ");
		data.stores[i].cpu = atoi(token);
		// RECUPERE ET COPIE GO
		token = strtok(NULL, " ");
		data.stores[i].go = atoi(token);
	}
	
	int shmID; // identificateur de la memoire partagee
	void* ptr_shm; // pointeur de la memoire partagee

	// CREATION DU SEGMENT MEMOIRE PARTAGEE
	if ((shmID = shmget(SHM_KEY, sizeof(data), IPC_CREAT | 0666)) == -1) {
		perror("serveur : erreur shmget");
		exit(1);
	}


	// ATTACHEMENT AU SEGMENT MEMOIRE PARTAGEE
	if ((ptr_shm = shmat(shmID, NULL, 0)) == (void *) -1) {
		perror("serveur : erreur shmat");
		exit(1);
	}

	((shm_data*)ptr_shm)->nb_client_connected = 0;
	((shm_data*)ptr_shm)->nb_stores = data.nb_stores;
	for (int i = 0; i < data.nb_stores; i++)
	{
		memcpy(((shm_data*)ptr_shm)->stores[i].city, data.stores[i].city, 20);
		((shm_data*)ptr_shm)->stores[i].cpu = data.stores[i].cpu;
		((shm_data*)ptr_shm)->stores[i].go = data.stores[i].go;
	}
	((shm_data*)ptr_shm)->nb_logs = 0;


	/* ------------ SEMAPHORES ------------ */
	int semID;
	int sem_size = 4*nbstores+1;
	if ((semID = semget(SEM_KEY, sem_size, IPC_CREAT | 0666)) < 0){
        perror(RED "[-]Erreur creation semaphore");
		exit(1);
	}

	// INITIALISATION DU TABLEAU DE SEMAPHORES
    semun semun;

    for (int i = 0; i < nbstores; i++) {
        semun.val = 0;

        // CPU PARTAGE
        if (semctl(semID, i*nbstores+1, SETVAL, semun) == -1) {
            perror(RED "[-]Erreur init semaphores");
            exit(1);
        }

        // STOCKAGE PARTAGE
        if (semctl(semID, i*nbstores+3, SETVAL, semun) == -1) {
            perror(RED "[-]Erreur init semaphores");
            exit(1);
        }

        // CPU DISPONIBLE
        semun.val = data.stores[i].cpu;
        if (semctl(semID, i*nbstores, SETVAL, semun) == -1) {
            perror(RED "[-]Erreur init semaphores");
            exit(1);
        }

        // STOCKAGE DISPONIBLE
        semun.val = data.stores[i].go;
        if (semctl(semID, i*nbstores+2, SETVAL, semun) == -1) {
            perror(RED "[-]Erreur init semaphores");
            exit(1);
        }
	}

	semun.val = 0;
	if (semctl(semID, sem_size-1, SETVAL, semun) == -1) {
		perror("serveur : erreur initialisation semaphore on_update");
		exit(1);
	}

    print_system(semID, sem_size, data.stores, data.nb_stores);


//    semun.array = malloc(sem_size*sizeof(short));
//	if (semctl(semID, 0, GETALL, semun.array) == -1){
//		perror("serveur : erreur semctl GETALL");
//		exit(1);
//	}
//
//	for (int i = 0; i < sem_size; i++){
//		// sem[i] = semun.array[i];
//		printf("sem[%d] = %d\n", i, semun.array[i]);
//	}

    printf(GRN "\n\n[+]Serveur en attente de clients...\n");
    printf(RESET "Appuyez sur une entrée pour l'arrêter !\n");
    fgetc(stdin);

	// DETACHEMENT AU SEGMENT MEMOIRE PARTAGEE
	if (shmdt(ptr_shm) == -1){
        perror("[-]Erreur shmdt");
		exit(1);
	}

	// SUPPRESSION DU SEGMENT DE MEMOIRE PARTAGEE
	if (shmctl(shmID, IPC_RMID, NULL) == -1){
        perror("[-]Erreur shmdt");
		exit(1);
	}

	if (semctl(semID, 0, IPC_RMID) == -1){
        perror("[-]Erreur semctl");
		exit(1);
	}

	return 0;
}