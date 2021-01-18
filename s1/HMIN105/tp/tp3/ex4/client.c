#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

// ce fichier contient des bouts de codes du client pour l'exercice 1 du TP IPC.

int main(int argc, char *argv[]) {

    if (argc < 5) {
        printf("lancement : ./client operande1 id_operande operande2 chemin_fichier_cle [options]\n");
        printf("[options] :\n\tk : permet de fermer la file\n");
        exit(1);
    }

    key_t cle = ftok(argv[4], 'z');
    if (cle == -1) {
        perror("erreur ftok");
        exit(1);
    }
    printf("ftok ok\n");


    int msgid = msgget(cle, IPC_CREAT | 0666);
    if (msgid == -1) {
        perror("erreur msgget");
        exit(1);
    }

    printf("msgget ok\n");


    // structure des requetes
    struct req {
        long etiqReq; // 1
        struct contenu {
            int idop;  // 1: +, 2 : -, 3: *, 4: /
            int op1;
            int op2;
            int flag;
        } contenu;
    } requete;


    // initialiser un message avant de l'envoyer.
    requete.etiqReq = 1;
    requete.contenu.op1 = atoi(argv[1]);
    requete.contenu.op2 = atoi(argv[3]);
    if ( (argv[5] != NULL) && (argv[5][0] == 'k') ) {
        requete.contenu.flag = 1;
    } else {
        requete.contenu.flag = 0;
    }

    switch (argv[2][0]) {
        case '+' :
            requete.etiqReq = 2;
            break;
        case '-' :
            requete.etiqReq = 3;
            break;
        case 'x' :
            requete.etiqReq = 4;
            break;
        case '/' :
            requete.etiqReq = 5;
            break;
        default :
            requete.etiqReq = 1;
    }

    printf("Calculatrice demandée : %li\n", requete.etiqReq);
    printf("Demande à la calculatrice : %i %c %i\n", requete.contenu.op1, argv[2][0], requete.contenu.op2);

    // envoi requete
    if (msgsnd(msgid, (void *) &requete, sizeof(requete.contenu), 0) == -1) {
        perror("erreur send");
        exit(1);
    }

    struct response {
        long etiq;
        double result;
    } response;
    response.etiq = 2;

    int rcv_res = (msgrcv(msgid, &response, sizeof(double), 1, 0));
    printf("main - Nb octect reçu : %i\n", rcv_res);
    printf("resultat obtenu : %f\n", response.result);

    return 0;
}