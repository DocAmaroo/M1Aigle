#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <unistd.h>
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


#define DEFAULT_BUFFER_SIZE 127 // nombre de carateres max d'une ligne
#define SHM_KEY 1 // cle du segment de memoire partage
#define SEM_KEY 2 // cle du tableau de semaphores
#define MAX_CLIENT 10
#define MAX_STORES 10

//------- STRUCTURES ------- //

typedef struct user {
    pid_t *id;
    char name[20];
    char firstname[20];
} user;

typedef struct order {
    int city;
    int cpu;
    int go;
} order;

typedef struct message {
    user user;
    int size;
    order *orders;
    int flag; // 1 pour mode exclusif, 0 pour mode partage
} message;

typedef struct store {
    char city[20];
    int cpu;
    int go;
} store;

typedef struct log {
    pid_t id;
    char name[20];
    char firstname[20];
    int city;
    int cpu;
    int go;
    int nb_user;
    int flag;
} log;

typedef struct shm_data {
    int nb_client_connected;
    int nb_stores;
    store stores[MAX_STORES];
    int nb_logs;
    log logs[MAX_CLIENT * MAX_STORES];
} shm_data;


//------- THREADS ------- //
typedef struct thread_param {
    int idThread;
    pid_t *idClient;
    message *msg;
    int semID;
    int sem_length;
    int *loop;
    pthread_mutex_t *verrou1;
    pthread_cond_t *cond1;
    pthread_mutex_t *verrou2;
    pthread_cond_t *cond2;
    pthread_mutex_t *verrou3;
    pthread_cond_t *cond3;
    pthread_mutex_t *verrou4;
    pthread_cond_t *cond4;
} thread_param;


//------- SEMAPHORES ------- //
typedef union semun {
    int val;
    struct semid_ds *buf;
    unsigned short *array;
    struct seminfo *__buf;
} semun;

typedef struct {
    unsigned short sem_num;
    short sem_op;
    short sem_flg;
} sembuf;


void print_system(int semid, int sem_length, store *stores, int store_length) {
    int sem_cpt = 0;

    semun semun;
    semun.array = malloc(sem_length * sizeof(short));
    if (semctl(semid, 0, GETALL, semun.array) == -1) {
        perror("[-]Error occurred while reading semaphores");
        return;
    }


    printf(CYN "\n\t--- Etat du systeme ---\n");
    printf("index: { ville | cpu | cpu partages | go | go partages }\n");
    for (int i = 0; i < store_length; i++) {
        sem_cpt = i * 4;
        printf("%i: { %s | %i | %i | %i | %i }\n", i, stores[i].city, semun.array[sem_cpt], semun.array[sem_cpt + 1],
               semun.array[sem_cpt + 2], semun.array[sem_cpt + 3]);
    }
}


/**
 * @brief display logs
 */
void print_log(log log) {
    printf(RESET "{ id:%i, %s, %s, city:%i, cpu:%i, go:%i, flag:%i}\n", log.id, log.name, log.firstname, log.city,
           log.cpu, log.go, log.flag);
}


/**
 * @brief display logs
 */
void print_logs(log *logs, int length) {
    printf(CYN "\n\t--- Logs ---\n");
    for (int i = 0; i < length; i++) {
        print_log(logs[i]);
    }
}


void loguser(message *msg, user *user) {
    char buffer[DEFAULT_BUFFER_SIZE];

    printf(YEL "\n\t--- Connexion ---\n");
    // --- Ask name
    printf(YEL "Quel est votre nom ?");
    fgets(buffer, 20, stdin);
    buffer[strcspn(buffer, "\n")] = 0;
    memcpy(user->name, buffer, 20);

    // --- Ask firstname
    printf("Quel est votre prénom ?");
    fgets(buffer, 20, stdin);
    buffer[strcspn(buffer, "\n")] = 0;
    memcpy(user->firstname, buffer, 20);

    msg->user = *user;
}


void decrRessources(shm_data *ptr_shm, thread_param *args) {
    for (int i = 0; i < args->msg->size; i++){
		ptr_shm->stores[args->msg->orders[i].city].cpu = ptr_shm->stores[args->msg->orders[i].city].cpu - args->msg->orders[i].cpu;
		ptr_shm->stores[args->msg->orders[i].city].go = ptr_shm->stores[args->msg->orders[i].city].go - args->msg->orders[i].go;

	}
}


void incrRessources(shm_data *ptr_shm, thread_param *args) {
    for (int i = 0; i < args->msg->size; i++) {
        ptr_shm->stores[args->msg->orders[i].city].cpu += args->msg->orders[i].cpu;
        ptr_shm->stores[args->msg->orders[i].city].go += args->msg->orders[i].go;
    }
}


void ajoutLog(shm_data *ptr_shm, thread_param *args) {
    int y;
    for (int i = ptr_shm->nb_logs;
         i < ptr_shm->nb_logs + args->msg->size; i++) {
        y = i - ptr_shm->nb_logs;
        ptr_shm->logs[i].id = *args->idClient;
        ptr_shm->logs[i].city = args->msg->orders[y].city;
        memcpy(ptr_shm->logs[i].name, args->msg->user.name, 20);
        memcpy(ptr_shm->logs[i].firstname, args->msg->user.firstname, 20);
        ptr_shm->logs[i].cpu = args->msg->orders[y].cpu;
        ptr_shm->logs[i].go = args->msg->orders[y].go;
        ptr_shm->logs[i].flag = args->msg->flag;
        ptr_shm->logs[i].nb_user = 0;
    }

    ptr_shm->nb_logs += args->msg->size;
}


void supprLog(shm_data *ptr_shm, thread_param *args) {
    int index = 0;
    int cpt = 0;
    for (int i = 0; i < ptr_shm->nb_logs; i++) {
        if (ptr_shm->logs[i].id == *args->idClient) {
            cpt++;
            index = i;
        }
    }
    for (int i = index + 1; i < ptr_shm->nb_logs; i++) {
        ptr_shm->logs[i - cpt].id = ptr_shm->logs[i].id;
        memcpy(ptr_shm->logs[i - cpt].name, ptr_shm->logs[i].name, 20);
        memcpy(ptr_shm->logs[i - cpt].firstname, ptr_shm->logs[i].firstname,
               20);
        ptr_shm->logs[i - cpt].city = ptr_shm->logs[i].city;
        ptr_shm->logs[i - cpt].cpu = ptr_shm->logs[i].cpu;
        ptr_shm->logs[i - cpt].go = ptr_shm->logs[i].go;
        ptr_shm->logs[i - cpt].flag = ptr_shm->logs[i].flag;
        ptr_shm->logs[i - cpt].nb_user = ptr_shm->logs[i].nb_user;
    }
    ptr_shm->nb_logs -= cpt;
}


void *on_update(void *params) {

    thread_param *args = (thread_param *) params;

    int shmID; // identificateur de la memoire partagee
    void *ptr_shm; // pointeur de la memoire partagee

    if ((shmID = shmget(SHM_KEY, sizeof(0), 0666)) == -1) {
        perror("client : erreur shmget");
        exit(1);
    }

    if ((ptr_shm = shmat(shmID, NULL, 0)) == (void *) -1) {
        perror("client : erreur shmat");
        exit(1);
    }

    /* ------------ SEMAPHORES ------------ */
    while (args->loop) {
        struct sembuf acp = {args->sem_length - 1, -(1), SEM_UNDO};
        if (semop(args->semID, &acp, 1) == -1) {
            perror("[-]Error semop (on_update)");
            exit(1);
        }

        printf(GRN "[+]Nouvelle mise à jour reçu!");
        print_system(args->semID, args->sem_length, ((shm_data *)ptr_shm)->stores,
                     ((shm_data *)ptr_shm)->nb_stores);
    }

    // DETACHEMENT AU SEGMENT MEMOIRE PARTAGEE
    if (shmdt(ptr_shm) == -1) {
        perror("client : erreur shmdt");
        exit(1);
    }

    pthread_exit(NULL);

}


void *fonctionThread(void *params) {
    thread_param *args = (thread_param *) params;

    int shmID; // identificateur de la memoire partagee
    void *ptr_shm; // pointeur de la memoire partagee

    if ((shmID = shmget(SHM_KEY, sizeof(0), 0666)) == -1) {
        perror("client : erreur shmget");
        exit(1);
    }

    if ((ptr_shm = shmat(shmID, NULL, 0)) == (void *) -1) {
        perror("client : erreur shmat");
        exit(1);
    }

    int sem_size = ((shm_data *) ptr_shm)->nb_stores * 4 + 1;

    while(args->loop) {
        if (pthread_cond_wait(args->cond1, args->verrou1) != 0) {
            perror("[-]Error on cond2");
            exit(1);
        }

        int nbmsg = args->msg->size;
        struct sembuf opp[2 * nbmsg];
        for (int i = 0; i < 2 * nbmsg; i++) {
            if (i % 2 == 0) {
                opp[i].sem_num = 4 * (args->msg->orders[i / 2].city);
                opp[i].sem_op = -(args->msg->orders[i / 2].cpu);
                opp[i].sem_flg = SEM_UNDO;
            } else {
                opp[i].sem_num = 4 * (args->msg->orders[i / 2].city) + 2;
                opp[i].sem_op = -(args->msg->orders[i / 2].go);
                opp[i].sem_flg = SEM_UNDO;
            }
        }
        
        if (semop(args->semID, opp, 2 * nbmsg) == -1) {
            perror("[-]Erreur semop (fonctionThread)");
            exit(1);
        }

        decrRessources(((shm_data *) ptr_shm), args);

        ajoutLog(((shm_data *) ptr_shm), args);

        struct sembuf acv = {sem_size - 1, (short) ((shm_data *) ptr_shm)->nb_client_connected, SEM_UNDO};
        if (semop(args->semID, &acv, 1) == -1) {
            perror("[-]Error on semop (avc1)");
            exit(1);
        }

        if (pthread_cond_signal(args->cond2) != 0) {
            perror("[-]Error on cond2)");
            exit(1);
        }

        if (pthread_cond_wait(args->cond3, args->verrou3) != 0) {
            perror("[-]Error on cond3)");
            exit(1);
        }

        struct sembuf opv[2 * nbmsg];
        for (int i = 0; i < 2 * nbmsg; i++) {
            if (i % 2 == 0) {
                opv[i].sem_num = 4 * (args->msg->orders[i / 2].city);
                opv[i].sem_op = (args->msg->orders[i / 2].cpu);
                opv[i].sem_flg = SEM_UNDO;
            } else {
                opv[i].sem_num = 4 * (args->msg->orders[i / 2].city) + 2;
                opv[i].sem_op = (args->msg->orders[i / 2].go);
                opv[i].sem_flg = SEM_UNDO;
            }
        }

        if (semop(args->semID, opv, 2 * nbmsg) == -1) {
            perror("[-]Error on semop (opv)");
            exit(1);
        }

        incrRessources(((shm_data *) ptr_shm), args);
        supprLog(((shm_data *) ptr_shm), args);

        if (semop(args->semID, &acv, 1) == -1) {
            perror("[-]Error on semop (acv2)");
            exit(1);
        }


        if (pthread_cond_signal(args->cond4) != 0) {
            perror("[-]Error on cond4)");
            exit(1);
        }

    }

     // DETACHEMENT AU SEGMENT MEMOIRE PARTAGEE
    if (shmdt(ptr_shm) == -1) {
        perror("[-]Error shmdt");
        exit(1);
    }

    pthread_exit(NULL);
}


void *demande(void *params) {

    thread_param *args = (thread_param *) params;
    int nbs = 0;
    int flag = 1;

    while(args->loop) {
        printf(RESET "Combien de de commande allez-vous effectuer ? ");
        scanf("%d", &nbs);
        args->msg->size = nbs;

        args->msg->orders = malloc(nbs * sizeof(order));

        for (int i = 0; i < nbs; i++) {
            printf("Dans quel magasin voulez-vous faire votre demande ?\n(utilisez l'indice correspondant à gauche) ");
            scanf("%d", &args->msg->orders[i].city);
            printf("Combien de CPU voulez-vous ? ");
            scanf("%d", &args->msg->orders[i].cpu);
            printf("Combien de Go voulez-vous ? ");
            scanf("%d", &args->msg->orders[i].go);
        }

        if (pthread_cond_signal(args->cond1) != 0) {
            perror("[-]Error on cond1");
            exit(1);
        }

        if (pthread_cond_wait(args->cond2, args->verrou2) != 0) {
            perror("[-]Error on cond2");
            exit(1);
        }

        int att = 0;
        while (att != 1) {
            printf(YEL "\n[~]En attente de libération...\n");
            printf(RESET "Appuyer sur \"1\" pour rendre les ressources\n");
            scanf("%d", &att);
        }

        if (pthread_cond_signal(args->cond3) != 0) {
            perror("[-]Error cond3 signal");
            exit(1);
        }

        if (pthread_cond_wait(args->cond4, args->verrou4) != 0){
            perror("[-]Error wait 4");
            exit(1);
        }


        printf(RESET "Voulez-vous prendre une nouvelle commande ? (0: non | 1:oui)");
        if (scanf("%d", &flag) == 0) {
            args->loop = 0;
        }

        free(args->msg->orders);
    }

    pthread_exit(NULL);
}


int main(int argc, char const *argv[]) {

    // --- User connection
    user user;
    message *msg = malloc(sizeof(message));
    pid_t id = getpid();
    loguser(msg, &user);
    msg->user.id = &id;
    printf(YEL "\n\t--- Welcome %s %s ! ---\n" RESET, msg->user.name, msg->user.firstname);


    int shmID; // identificateur de la memoire partagee
    void *ptr_shm; // pointeur de la memoire partagee

    if ((shmID = shmget(SHM_KEY, sizeof(0), 0666)) == -1) {
        perror("client : erreur shmget");
        exit(1);
    }

    if ((ptr_shm = shmat(shmID, NULL, 0)) == (void *) -1) {
        perror("client : erreur shmat");
        exit(1);
    }

    ((shm_data *) ptr_shm)->nb_client_connected++;


    /* ------------ SEMAPHORES ------------ */
    int semID;
    int sem_length = 4 * ((shm_data *) ptr_shm)->nb_stores + 1;
    if ((semID = semget(SEM_KEY, sem_length, 0)) < 0) {
        perror(RED "[-]Erreur semget");
        exit(1);
    }

    print_system(semID, sem_length, ((shm_data *) ptr_shm)->stores, ((shm_data *) ptr_shm)->nb_stores);

    /* ------------ SEMAPHORES ------------ */
    thread_param *params = malloc(3 * sizeof(thread_param));
    pthread_t threads[3];

    pthread_mutex_t verrou1;
    if (pthread_mutex_init(&verrou1, NULL) != 0) {
        perror(RED "[-]Error pthread mutex1 init\n");
        exit(1);
    }

    pthread_cond_t cond1;
    if (pthread_cond_init(&cond1, NULL) != 0) {
        perror(RED "[-]Error pthread cond1 init\n");
        exit(1);
    }

    pthread_mutex_t verrou2;
    if (pthread_mutex_init(&verrou2, NULL) != 0) {
        perror(RED "[-]Error pthread mutex2 init\n");
        exit(1);
    }

    pthread_cond_t cond2;
    if (pthread_cond_init(&cond2, NULL) != 0) {
        perror(RED "[-]Error pthread cond1 init\n");
        exit(1);
    }

    pthread_mutex_t verrou3;
    if (pthread_mutex_init(&verrou3, NULL) != 0) {
        perror(RED "[-]Error pthread mutex3 init\n");
        exit(1);
    }

    pthread_cond_t cond3;
    if (pthread_cond_init(&cond3, NULL) != 0) {
        perror(RED "[-]Error pthread cond1 init\n");
        exit(1);
    }

    pthread_cond_t cond4;
    if (pthread_cond_init(&cond4, NULL) != 0) {
        perror(RED "[-]Error pthread cond1 init\n");
        exit(1);
    }

    pthread_mutex_t verrou4;
    if (pthread_mutex_init(&verrou4, NULL) != 0) {
        perror(RED "[-]Error pthread mutex3 init\n");
        exit(1);
    }

    int loop = 1;
    for (int i = 0; i < 3; i++) {

        params[i].idThread = i;
        params[i].idClient = &id;
        params[i].msg = msg;
        params[i].semID = semID;
        params[i].sem_length = sem_length;
        params[i].loop = &loop;
        params[i].verrou1 = &verrou1;
        params[i].cond1 = &cond1;
        params[i].verrou2 = &verrou2;
        params[i].cond2 = &cond2;
        params[i].verrou3 = &verrou3;
        params[i].cond3 = &cond3;
        params[i].verrou4 = &verrou4;
        params[i].cond4 = &cond4;

        if (i == 0) {
            if (pthread_create(&threads[0], NULL, on_update, &params[i]) != 0) {
                perror(RED "[-]Error creation thread (on_update)");
                exit(1);
            }
        }

        if (i == 1) {
            if (pthread_create(&threads[0], NULL, fonctionThread, &params[i]) != 0) {
                perror(RED "[-]Error creation thread (fonctionThread)");
                exit(1);
            }
        }

        if (i == 2) {
            if (pthread_create(&threads[1], NULL, demande, &params[i]) != 0) {
                perror(RED "[-]Error creation thread (demande)");
                exit(1);
            }
        }

    }

    for (int i=0; i < 3; i++) {
        pthread_join(threads[i], NULL);
    }

    // DETACHEMENT AU SEGMENT MEMOIRE PARTAGEE
    ((shm_data *) ptr_shm)->nb_client_connected--;

    // DETACHEMENT AU SEGMENT MEMOIRE PARTAGEE
    if (shmdt(ptr_shm) == -1) {
        perror("client : erreur shmdt");
        exit(1);
    }

    pthread_exit(NULL);

    return 0;
}