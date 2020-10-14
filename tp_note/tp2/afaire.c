#include "commun.h"


// lire le fichier commun.h pour la documentation et la trsucture utilisée


int jetonsEtape_init(JetonsEtape * v, int k){
  int res = 0;
  
  v->max = k;
  v->nbJetonPris = 0;

  res = pthread_mutex_init(&v->mut,NULL);
  if (res != 0) return res;
  res = pthread_cond_init(&v->cond,NULL);
  return res;
}


int demande_jeton(JetonsEtape * v){

  int res = 0;
  
  res = pthread_mutex_lock(&v->mut);
  if (res != 0) return res;

  while ((v->nbJetonPris) >= (v->max)) {
    res = pthread_cond_wait(&v->cond,&v->mut);
    if (res != 0) return res;
  }
  v->nbJetonPris++;
 
  return pthread_mutex_unlock(&v->mut);
}

int liberer_jeton(JetonsEtape * v){

  int res = 0;
  
  res = pthread_mutex_lock(&v->mut);
  if (res != 0) return res;

  if ((v->nbJetonPris) == (v->max)) {
    res = pthread_cond_broadcast(&v->cond);
    if (res != 0) return res;
  }
  v->nbJetonPris--;

  return pthread_mutex_unlock(&v->mut);
}

int jetonsEtape_destroy(JetonsEtape * v){

  int res = 0;
  
  res = pthread_cond_destroy(&v->cond);
  if (res != 0) return res;
  res = pthread_mutex_destroy(&v->mut);
  if (res != 0) return res;
  return res;
}


