#include "commun.h"

// lire le fichier commun.h pour la documentation et la trsucture utilisée

int jetonsEtape_init(JetonsEtape * v, int k){
	int res = 0;

	v->max = k;
	v->nbJetonPris = 0;
	if ( pthread_cond_init(&v->cond, NULL) != 0 ) {
		res = -1;
	}

	if ( pthread_mutex_init(&v->mut, NULL) != 0 ) {
		res = -1;
	}
	
	return res;
}


int demande_jeton(JetonsEtape * v){

	int res = 0;
	
	pthread_mutex_lock(&v->mut);
	
	while(v->nbJetonPris >= v->max) {
		if ( pthread_cond_wait(&v->cond, &v->mut) != 0 ) {
			res = -1;
		}
	}

	v->nbJetonPris++;

	if ( pthread_mutex_unlock(&v->mut) != 0 ){
		res = -1;
	}
	return res;
}

int liberer_jeton(JetonsEtape * v){

	int res = 0;
	
	if ( pthread_mutex_lock(&v->mut) != 0 ) {
		res = -1;
	}

	v->nbJetonPris--;
	if ( v->nbJetonPris < v->max) {
		if ( pthread_cond_broadcast(&v->cond) != 0 ) {
			res = -1;
		}
	}
	
	if ( pthread_mutex_unlock(&v->mut) != 0 ) {
		res = -1;
	}

	return res;
}

int jetonsEtape_destroy(JetonsEtape * v){

	int res = 0;
	
	v->max = 0;
	v->nbJetonPris = 0;
	if ( pthread_mutex_destroy(&v->mut) != 0 ) {
		res = -1;
	}
	if ( pthread_cond_destroy(&v->cond) != 0 ) {
		res = -1;
	}

	return res;
}