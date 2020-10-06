#include <stdlib.h>
#include <sys/types.h>
#include <iostream>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <stdio.h>

// ce fichier contient des bouts de codes du client pour l'exercice 1 du TP IPC.

int main (int argc, char * argv[]){

  if(argc != 4){
    printf("lancement : ./client operande1 operande2 chemin_fichier_cle\n");
    exit(1);
  }

  // récuperer l'identifiant de la file de message qu'on souhaite
  // utiliser. La clé est une paire : chemin vers un fichier existant
  // et un caractère (ou entier en fonction de l'OS). La même paire
  // permet d'identifier une seule file de message. Donc tous les
  // processus qui utiliseront la même paire, partageront la même
  // file de message (s'ils en ont les droits aussi)
  key_t cle = ftok(argv[3], 'z');
  if (cle ==-1) {
    perror("erreur ftok");
    exit(1);
  }
  printf("ftok ok\n");
  // la clé numérique calculée, je réupère l'identifiant de la file (ici je suppose que la file existe.
  int msgid = msgget(cle, 0666);
  if(msgid == -1) {
    perror("erreur msgget");
    exit(1);
  }

  printf("msgget ok\n");

  // structure des requetes 
  struct req{
    long etiqReq; // 1
    struct contenu{
      int idop;  // 1: +, 2 : -, 3: *, 4: /
      int op1;
      int op2;
    } contenu;
  } requete;

 .....
  
 

      // initialiser un message avant de l'envoyer.
  requete.etiqReq = ...;
  requete.contenu.op1 = atoi(argv[1]);
  requete.contenu.op2 = atoi(argv[2]);
  requete.contenu.idop = ...;
  
    // envoi requete 
    if(msgsnd(msgid, (void *)&requete, sizeof(requete.contenu), 0) ==-1){
      perror("erreur send");
      exit(1);
    }

   .....
  return 0;
}
