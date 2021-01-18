#include <string.h>
#include <stdio.h>//perror
#include <sys/types.h>
#include <stdlib.h>
#include <pthread.h>


void* activity1 (void* param) {

}

void* activity2 (void* param) {

}

int main(int argc, char* argv[]){

    int size = atoi(argv[1]);
    if (argc < 2){
        printf("utilisation: %s  nombres_de_threads  \n", argv[0]);
        return 1;
    }

    if ( size < 1) {
        printf("Donner une valeur > 1");
    }

    int* zones = (int*) calloc(0,sizeof(int)*5);

    pthread_t activities[size];

    if ( pthread_create(&activities[0], NULL, activity1, NULL)) {
        perror("erreur creation thread\n");
        exit(1);
    }

    if ( pthread_create(&activities[1], NULL, activity2, NULL)) {
        perror("erreur creation thread\n");
        exit(1);
    }


    if ( pthread_join(activities[0], NULL) ) {
        perror("erreur creation thread\n");
        exit(1);
    }

    if ( pthread_join(activities[1], NULL) ) {
        perror("erreur creation thread\n");
        exit(1);
    }
    return 0;
}