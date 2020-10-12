#include <string.h>
#include <stdio.h>//perror
#include <sys/types.h>
#include <stdlib.h>
#include <pthread.h>


struct thread_param {
    int* count;
    int* nbThread;
    pthread_mutex_t* mutex;
    pthread_cond_t* cond;
};

void* hello(void* param) {

    pthread_t thread = pthread_self();
    printf("\nNew thread | id : %ld\n", thread);

    struct thread_param *p = (struct thread_param*) param;
    printf("[ %ld ] Présent !\n", thread);

    pthread_mutex_lock(p->mutex);
    *p->count += 1;


    if (*p->count == *p->nbThread){
        printf("[BROADCAST] : Tous le monde est au rendez-vous !\n");
        pthread_cond_broadcast(p->cond);
    }

    while (*p->count < *p->nbThread) {
        printf("[ %ld ] Je me met en attente !\n", thread);
        pthread_cond_wait(p->cond, p->mutex);
    }

    pthread_mutex_unlock(p->mutex);

    printf("do something\n");

    pthread_exit(NULL);
}

int main(int argc, char * argv[]){

    int size = atoi(argv[1]);
    if (argc < 2){
        printf("utilisation: %s  nombres_de_threads  \n", argv[0]);
        return 1;
    }

    if ( size < 1) {
        printf("Donner une valeur > 1");
    }

    // tableau de threads
    pthread_t threads[size];

    // initialisation du mutex
    pthread_mutex_t mutex;
    pthread_mutex_init(&mutex, NULL);

    // initialisation de la variable conditionnelle
    pthread_cond_t cond;
    pthread_cond_init(&cond, NULL);


    // definition des param de la structure
    int count = 0;
    int nbThread = size;

    struct thread_param *param = (struct thread_param*) malloc(sizeof(struct thread_param));
    param->count = &count;
    param->nbThread = &nbThread;
    param->mutex = &mutex;
    param->cond = &cond;

    // création des threads -------------------------
    for (int i = 0; i < size; i++){

        // création d'un thread
        if (pthread_create(&threads[i], NULL, hello, param) != 0){
            perror("erreur creation thread\n");
            exit(1);
        }
    }


    for (int i=0; i < size; i++) {
        pthread_join(threads[i], NULL);
    }

    printf("\nmain() : tout les threads sont terminés\n");


    // free memory
    free(param->count);
    free(param->nbThread);
    free(param->mutex);
    free(param->cond);
    free(param);
    return 0;
}