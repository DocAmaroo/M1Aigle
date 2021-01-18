#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <stdio.h>


int main(int argc, char *argv[]) {


    if (argc != 2) {
        printf("lancement : ./calc chemin_fichier_cle\n");
        exit(1);
    }

    key_t cle = ftok(argv[1], 'z');
    if (cle == -1) {
        perror("erreur ftok");
        exit(1);
    }

    printf("ftok ok\n");

    // la clé numérique calculée, je réupère l'identifiant de la file (ici je suppose que la file existe.
    int msgid = msgget(cle, IPC_CREAT | 0666);
    if (msgid == -1) {
        perror("erreur msgget");
        exit(1);
    }

    printf("msgget ok\n");

    while(1) {
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


        int rcv_res = (msgrcv(msgid, &requete, sizeof(requete.contenu), 4, 0));
        printf("calcMul - nb octect reçu : %i\n", rcv_res);

        struct response {
            long etiq;
            double result;
        } response;
        response.etiq = 1;

        response.result = requete.contenu.op1 * requete.contenu.op2;

        if (requete.contenu.flag == 1){

            printf("Envoie du message au client...\n");
            if (msgsnd(msgid, (void *) &response, sizeof(double), 0) == -1) {
                perror("erreur send");
                exit(1);
            }

            // suppression de la file
            printf("Suppression de la file...\n");
            msgctl(msgid, IPC_RMID, NULL);
            return EXIT_SUCCESS;
        }

        // envoi requete
        printf("Envoie du message au client...\n");
        if (msgsnd(msgid, (void *) &response, sizeof(double), 0) == -1) {
            perror("erreur send");
            exit(1);
        }
    }

    return 0;

}