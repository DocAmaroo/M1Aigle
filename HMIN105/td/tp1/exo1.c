// ligne de compil : gcc -Wall exo1.c -o exo1 -lpthread

#include <string.h>
#include <stdio.h>//perror
#include <sys/types.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

struct paramsFonctionThread {

	int idThread;
	// si d'autres paramétres, les ajouter ici.

};


void* fonctionThread (){

	//struct paramsFonctionThread * args = (struct paramsFonctionThread *) params;
    pthread_t thread = pthread_self();
	printf("Création d'un thread");
    
	printf("\nid : %ld\nproc : %i\n", thread, getpid());

	printf("Fin du thread ----\n\n");
    pthread_exit(NULL);
}


int main(int argc, char * argv[]){

	if (argc < 2 ){
		printf("utilisation: %s  nombre_threads  \n", argv[0]);
		return 1;
	}     

	// tab contenant les id des threads
	// l'id servira pour attendre la fin d'un thread et/ou son résultat
	pthread_t threads[atoi(argv[1])];

	
	// création des threads -------------------------
	for (int i = 0; i < atoi(argv[1]); i++){
		if (pthread_create(&threads[i], NULL, fonctionThread, NULL) != 0){
			perror("erreur creation thread");
			exit(1);
		}

		// permet d'attendre la fin de l'éxécution du précédent thread
		pthread_join(threads[i], NULL);
	}

	//
	//... compléter

	return 0;
}

