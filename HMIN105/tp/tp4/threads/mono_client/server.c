#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>

int DEFAULT_BUFFER_SIZE = 8;


int recvTCP(int socket, char *buffer, size_t length){

	int received = 1;

	while(received == 1){
		
		received = recv(socket, buffer, length, 0);

		if (received <= 0){
			return received;
		}
	}

	buffer += received;
	return 1;
}

int main(int argc, char const *argv[])
{
    
    if (argc != 2) {
        printf("Utilisation: %s port", argv[0]);
    }

    int ds = socket(PF_INET, SOCK_STREAM, 0);
    if (ds == -1) { printf("server : problem creating socket"); exit(1); }

    struct sockaddr_in server;
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(atoi(argv[1]));

    // bind and listen
    if (bind(ds, (struct sockaddr*) &server, sizeof(server)) == -1) {printf("server: error bind"); exit(0);}
    if (listen(ds, 10) == -1) {printf("server: error listen"); close(ds); exit(0);}

    while(1) {

        // get client adress
        struct sockaddr_in client;
        socklen_t lgAdrClient = sizeof (struct sockaddr_in);
        
        // accept
        int dsClient = accept(ds, (struct sockaddr *) &client, &lgAdrClient);
        if (dsClient == -1) {printf("server: error accept"); close(ds); exit(0);}

        printf("server : nouveau client %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));

        char buffer[DEFAULT_BUFFER_SIZE];

        while(1) {
            int rcv = recvTCP(dsClient, &buffer, DEFAULT_BUFFER_SIZE);
			if (rcv < 0){ perror ("Serveur : probleme reception :"); close(dsClient); break; }
			if (rcv == 0) { printf("Serveur : la socket a été fermée\n"); close(dsClient); break; }
			printf("%s", buffer);
        }
    }

    close(ds);
    return 0;
}
