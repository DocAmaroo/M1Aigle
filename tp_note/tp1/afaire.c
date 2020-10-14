#include "commun.h"

// lire le fichier commun.h pour la documentation et la trsucture utilisée


int jetonsEtape_init(JetonsEtape * v, int k){
  int res = 0;
  int* max = k;
  int* nbJetonPris = 0;
  pthread_mutex_t* mut;
  pthread_cond_t* cond;
  v->max = &max;
  v->nbJetonPris = &nbJetonPris;
  v->mut = &mut;
  v->cond = &cond;
  pthread_mutex_init(&mut, NULL);
  pthread_cond_init(&cond, NULL);

  if(&max < k){
		  	res = -1;
		  	return res;
  		}
  return res;
}


int demande_jeton(JetonsEtape * v){

  int res = 0;

  if (v->nbJetonPris > 0){

  	v->nbJetonPris =+ 1;
  }
  else {
  	pthread_mutex_lock(&mut);
  	while(v->nbJetonPris == 0){
  		printf("En attente de jetons");
  		pthread_cond_wait(&cond, &mut);
  	}
   if (v->nbJetonPris < 0)
   {
   		res = -1;
   		return res;
   }

  }
 
  return res;
}

int liberer_jeton(JetonsEtape * v){

  int res = 0;
  v->nbJetonPris =+ 1 ;
  printf("Libération de jeton \n");
  pthread_mutex_unlock(&mut);
  pthread_cond_broadcast(&mut);
  if(v->nbJetonPris = 0){
  	res = -1;
  	return res;
  }
  return res;
}

int jetonsEtape_destroy(JetonsEtape * v){

  int res = 0;
  
  free(v);
  if(v=!0){
  	res =-1;
  	return res;
  }
 
  return res;
}


