#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <stdio.h>

// ce fichier contient des bouts de codes du client pour l'exercice 1 du TP IPC.

int main(int argc, char *argv[]) {

    if (argc != 5) {
        printf("lancement : ./client operande1 id_operande operande2 chemin_fichier_cle\n");
        exit(1);
    }

    // récuperer l'identifiant de la file de message qu'on souhaite
    // utiliser. La clé est une paire : chemin vers un fichier existant
    // et un caractère (ou entier en fonction de l'OS). La même paire
    // permet d'identifier une seule file de message. Donc tous les
    // processus qui utiliseront la même paire, partageront la même
    // file de message (s'ils en ont les droits aussi)
    key_t cle = ftok(argv[4], 'z');
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

    // structure des requetes
    struct req {
        long etiqReq; // 1
        struct contenu {
            int idop;  // 1: +, 2 : -, 3: *, 4: /
            int op1;
            int op2;
        } contenu;
    } requete;


    // initialiser un message avant de l'envoyer.
    requete.etiqReq = 1;
    requete.contenu.op1 = atoi(argv[1]);
    requete.contenu.idop = 0;
    requete.contenu.op2 = atoi(argv[3]);

    switch (argv[2][0]) {
        case '+' :
            requete.contenu.idop = 1;
            break;
        case '-' :
            requete.contenu.idop = 2;
            break;
        case 'x' :
            requete.contenu.idop = 3;
            break;
        case '/' :
            requete.contenu.idop = 4;
            break;
        default :
            requete.contenu.idop = 1;
    }

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

    int rcv_res = (msgrcv(msgid, &response, sizeof(double), response.etiq, 0));
    printf("main - Nb octect reçu : %i\n", rcv_res);
    printf("resultat obtenu : %f\n", response.result);

    // suppression de la file
    msgctl(msgid, IPC_RMID, NULL);

    return 0;
}