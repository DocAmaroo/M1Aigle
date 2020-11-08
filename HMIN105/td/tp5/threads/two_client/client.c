#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <string.h>

int DEFAULT_BUFFER_SIZE = 16;

struct message {
	char* buffer;
	int shareFlag;
} message;

void printUsage(char const* name) {
	printf("Usage: %s adress port sharing", name);
	printf("<sharing> : 1 => Message will we shared, 0 instead");
	exit(0);
}

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

int sendTCP(int socket, const char *buffer, size_t length){

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
	
	if (argc != 4) { printUsage(argv[0]); }
	if (atoi(argv[3]) > 1 || atoi(argv[3]) < 0) { printUsage(argv[0]); }

	int ds = socket(PF_INET, SOCK_STREAM, 0);
	if (ds == -1) { exit(1); }
	printf("[+]Client Socket created\n");

	struct sockaddr_in server;
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = inet_addr(argv[1]);
	server.sin_port = htons(atoi(argv[2])); 
	socklen_t lgAdrServer = sizeof(struct sockaddr_in);

	if (connect(ds, (struct sockaddr *) &server, lgAdrServer) == -1) { exit(1); }
	printf("[+]Client is connected with the server\n");

	struct message newMessage;
	newMessage.shareFlag = atoi(argv[3]);

	char* buffer = malloc(DEFAULT_BUFFER_SIZE * sizeof(char));
	printf("Send messages: \n");

	int id = fork();
	if (id == 0) {
		while(1) {
		
			fgets(buffer, DEFAULT_BUFFER_SIZE, stdin);
			newMessage.buffer = buffer;

			if (strncmp(buffer, ":exit", 5) == 0) {
				close(ds);
				printf("[-]Disconnected from server.\n");
				exit(1);
			}

			int send = sendTCP(ds, buffer, sizeof(buffer));
			if (send < 0) { perror("[-]Error while sending\n"); close(ds); exit(1); }
			else if (send == 0) { printf("[-]The server is disconnected\n"); break;}
		}
	} else {
		while(1) {
			int receive = recvTCP(ds, buffer, sizeof(buffer));
			if (receive < 0){ perror ("[-] Error while receiving\n"); close(ds); break; }
			if (receive == 0) { printf("[-] Socket is closed\n"); close(ds); break; }
			printf("%s", buffer);
		}
	}

	close(ds);
	return 0;
}
