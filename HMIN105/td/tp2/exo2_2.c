// ligne de compil : gcc -Wall exo1.c -o exo1 -lpthread
#include <string.h>
#include <stdio.h>//perror
#include <sys/types.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>


struct thread_param {
	int i1;
	int i2;
	int* res;
	pthread_mutex_t* mutex;
};

void* scalaire (void* ptr){

	struct thread_param *param = (struct thread_param*) ptr;
	int a = param->i1;
	int b = param->i2;


    pthread_t thread = pthread_self();
	printf("\nid : %ld | proc : %i\n", thread, getpid());
	printf("multiplication entre %i et %i : %i\n", a, b, a*b);

	pthread_mutex_lock(param->mutex);
	*param->res += a*b;
	pthread_mutex_unlock(param->mutex);

    pthread_exit(NULL);
}


int main(int argc, char * argv[]){

	if (argc < 2){
		printf("utilisation: %s  nombres_de_dimensions  \n", argv[0]);
		return 1;
	}     

	if ( atoi(argv[1]) < 1) {
		printf("Donner une valeur > 1");
	}

	// define mutex
	pthread_mutex_t mutex;
	pthread_mutex_init(&mutex, NULL);

	// init des vecteurs à la main
	int v1[3] = {2,3,4};
	int v2[3] = {3,2,5};
	int res = 0;

	pthread_t threads[atoi(argv[1])];

	// création des threads -------------------------
	for (int i = 0; i < atoi(argv[1]); i++){
		
		// def struct for the thread param
		struct thread_param *param = malloc(sizeof(struct thread_param));
		param->i1 = v1[i];
		param->i2 = v2[i];
		param->res = &res;
		param->mutex = &mutex;
		if (pthread_create(&threads[i], NULL, scalaire, (void*) param) != 0){
			perror("erreur creation thread");
			exit(1);
		}
	}

	for (int i=0; i < atoi(argv[1]); i++) {
		pthread_join(threads[i], NULL);
	}

	printf("----------- \n\nTout les threads sont terminées !\n");

	printf("Résultat du produit scalaire : %i\n", res);
	return 0;
}

