#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <string.h>

int main(int argc, char const *argv[])
{
    if (argc < 2){
        printf("utilisation: %s numero_port\n", argv[0]);
        exit(1);
    }

    // création de la socket
    int ds = socket(PF_INET, SOCK_DGRAM, 0);
    if (ds == -1){
        perror("server : error while creating a socket");
        exit(1);
    }

    struct sockaddr_in server;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_family = AF_INET;
    server.sin_port = htons(atoi(argv[1]));


    struct sockaddr_in client;
    socklen_t lgAdrClient = sizeof(struct sockaddr_in);

    char message[255];

    int rcv = recvfrom(ds, &message, sizeof(char*), 0, (struct sockaddr*) &client, &lgAdrClient); 
    if (rcv < 0){ 
        perror ("server : probleme reception :");
        close(ds);
        exit (1);
    }

    printf("server : réception d'un client %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));
    printf("Message reçu: %s\n", message);
    printf("Tentative de réponse...\n");

    struct response {
        int i1;
        int i2;
        char message[8];
    } response;
    
    response.i1 = 3;
    response.i2 = 1;
    strcpy(response.message, "hello !");

    int snd = sendto(ds, &response, sizeof(response), 0, (struct sockaddr *) &client, lgAdrClient);
    if (snd < 0){ 
        perror ("server : probleme send :");
        close(ds);
        exit (1);
    }
    printf("message: %s\n", response.message);
    printf("réponse envoyé !\n");

    return 0;
}
