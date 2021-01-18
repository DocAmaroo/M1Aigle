// ligne de compil : gcc -Wall exo2_2.c -o exo2_2 -lpthread
#include <string.h>
#include <stdio.h>//perror
#include <sys/types.h>
#include <stdlib.h>
#include <pthread.h>


struct thread_param {
	int arg1;
	int arg2;
	int index;
	pthread_mutex_t* mutex;
};

void* scalaire (void* ptr){
	
	struct thread_param *param = (struct thread_param*) ptr;

    int mult = param->arg1*param->arg2;
    printf("scalaire() - [THREAD %i] : %i*%i = %i\n", param->index, param->arg1, param->arg2, mult);

	printf("scalaire() - [THREAD %i] : asking mutex\n", param->index);
	pthread_mutex_lock(param->mutex);

	int* result = malloc(sizeof(int));
	*result = mult;

	pthread_mutex_unlock(param->mutex);
	printf("scalaire() - [THREAD %i] : unlock mutex\n", param->index);
	printf("FIN DU THREAD %i\n", param->index);
	pthread_exit(result);
}


int main(int argc, char * argv[]){

	if (argc < 2){
		printf("utilisation: %s  nombres_de_dimensions  \n", argv[0]);
		return 1;
	}     

	if ( atoi(argv[1]) < 1) {
		printf("Donner une valeur > 1");
	}

	// création du mutex
	pthread_mutex_t mutex;
	pthread_mutex_init(&mutex, NULL);

	// initialisation
	int* v1 = malloc(sizeof(int)*atoi(argv[1]));
	int* v2 = malloc(sizeof(int)*atoi(argv[1]));
	int* response = 0;
	int cpt = 0;
	int result = 0;

	printf("Donner les valeurs du 1er vecteur :\n");
	while(cpt < atoi(argv[1])){
		printf("arg %i ? ", cpt);
		scanf("%i", &v1[cpt]);
		cpt++;
	}

	printf("Donner les valeurs du 2nd vecteur :\n");
	cpt = 0; while(cpt < atoi(argv[1])){
		printf("arg %i ? ", cpt);
		scanf("%i", &v2[cpt]);
		cpt++;
	}

	
	printf("\n----- START -----\n");
	
	// tableau de threads
	pthread_t* threads = malloc(sizeof(pthread_t)*atoi(argv[1]));

	// création des threads -------------------------
	for (int i = 0; i < atoi(argv[1]); i++){
		// definition des param de la structure
		struct thread_param *param = malloc(sizeof(struct thread_param));
		param->arg1 = v1[i];
		param->arg2 = v2[i];
		param->index = i;
		param->mutex = &mutex;

		// création d'un thread
		if (pthread_create(&threads[i], NULL, scalaire, (void*) param) != 0){
			perror("erreur creation thread\n");
			exit(1);
		}
	}

	for (int i=0; i < atoi(argv[1]); i++) {
		pthread_join(threads[i], (void**) &response);
		result += *response;
	}

	printf("\nmain() : tout les threads sont terminés\n");

	// affichage du résultat
	printf("Résultat du produit scalaire : %i\n", result);

	// free memory
	free(v1);
	free(v2);
	free(threads);
	printf("\n----- END -----\n");
	return 0;
}

