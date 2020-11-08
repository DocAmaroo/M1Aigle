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
    
    if (argc != 3) {
        printf("utilisation : %s ip_serveur port_serveur", argv[0]);
        exit(0);
    }

    // création de la socket
    int ds = socket(PF_INET, SOCK_DGRAM, 0);
    if (ds == -1) { printf("Client : error while creating socket"); exit(0); }
    
    // création structure server
    struct sockaddr_in server;
    server.sin_addr.s_addr = inet_addr(argv[1]);
    server.sin_family = AF_INET;
    server.sin_port = htons(atoi(argv[2]));
    socklen_t lgAdrServer = sizeof(struct sockaddr_in);

    // connection
    int connection = connect(ds, (struct sockaddr *) &server, sizeof(server));
    if (connection < 0) {
        perror("Client : error occur while connecting");
        close(ds);
        exit(1);
    }

    // demande du message
    char message[255];
    printf("Quel est votre message ? ");
    scanf("%s", &message);

    int sent = sendto(ds, &message, sizeof(char*), 0, (struct sockaddr *) &server, lgAdrServer);    
    if (sent < 0) {
      perror("Client : erreur lors de l'envoi :");
      close(ds);
      exit(1);
    }

    printf("Message envoyé au serveur !");

    struct response {
        int i1;
        int i2;
        char message[8];
    } response;

    int rcv = recvfrom(ds, &response, sizeof(response), 0, (struct sockaddr *) &server, &lgAdrServer);
    if (rcv < 0){ 
        perror ("Client : probleme reception :");
        close(ds);
        exit (1);
    }

    printf("\nLecture de la réponse:\n i1: %i\n i2: %i\n message: %s\n", response.i1, response.i2, response.message);

    close(ds);
    return 0;
}
