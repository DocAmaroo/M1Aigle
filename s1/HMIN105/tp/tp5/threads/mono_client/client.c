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


int sendTCP(int socket, const char * buffer, size_t length){

	int sent = 1;
	size_t nbBytes = length;

	while(nbBytes != 0){

		sent = send(socket, buffer, nbBytes, 0);

		if (sent <= 0){
			return sent;
		}

		buffer += sent;
		nbBytes -= sent;
	}

	return 1;
}

int main(int argc, char const *argv[])
{
	
	if (argc != 3) {
		printf("Utilisation: %s adress port", argv[0]);
		exit(0);
	}

	int ds = socket(PF_INET, SOCK_STREAM, 0);
	if (ds == -1) { printf("client : problem creating socket"); exit(1); }

	struct sockaddr_in server;
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = inet_addr(argv[1]);
	server.sin_port = htons(atoi(argv[2])); 
	socklen_t lgAdrServer = sizeof(struct sockaddr_in);

	if (connect(ds, (struct sockaddr *) &server, lgAdrServer) == -1) { printf("client : problem connection"); close(ds); exit(1); }

	int maxMessage = 5;
	while (1) {

		char buffer[DEFAULT_BUFFER_SIZE];
		fgets(buffer, DEFAULT_BUFFER_SIZE, stdin);

		int sent = sendTCP(ds, &buffer, sizeof(buffer));
		if (sent < 0) { perror("client : error send"); close(ds); exit(1); }
		else if (sent == 0) { printf("client: server disconnected \n"); break;}
	}

	close(ds);
	return 0;
}
