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
            } contenu;
        } requete;


        int rcv_res = (msgrcv(msgid, &requete, sizeof(requete.contenu), requete.etiqReq, 0));
        printf("calculatrice - nb octect reçu : %i\n", rcv_res);

        struct response {
            long etiq;
            double result;
        } response;
        response.etiq = 2;

        double op1 = requete.contenu.op1;
        double op2 = requete.contenu.op2;
        char op;
        switch (requete.contenu.idop) {
            case 1 :
                response.result = op1 + op2;
                op = '+';
                break;
            case 2 :
                response.result = op1 - op2;
                op = '-';
                break;
            case 3 :
                response.result = op1 * op2;
                op = 'x';
                break;
            case 4 :
                response.result = op1 / op2;
                op = '/';
                break;
            default :
                response.result = op1 + op2;
                op = '+';
        }

        printf("Envoie du message au client...\n");

        // envoi requete
        if (msgsnd(msgid, (void *) &response, sizeof(double), 0) == -1) {
            perror("erreur send");
            exit(1);
        }
    }

    // suppression de la file
    printf("Suppression de la file");
    msgctl(msgid, IPC_RMID, NULL);

    return 0;

}