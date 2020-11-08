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


void printUsage(char const* name) {
	printf("Usage: %s adress port", name);
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
	
	if (argc != 3) { printUsage(argv[0]); }

	//------- SOCKET INITIALISATION ------- //

	//try to create a socket
	int ds = socket(PF_INET, SOCK_STREAM, 0);
	if (ds == -1) { exit(1); }
	printf("[+]Client Socket created\n");

	//create server structure
	struct sockaddr_in server;
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = inet_addr(argv[1]);
	server.sin_port = htons(atoi(argv[2])); 
	socklen_t lgAdrServer = sizeof(struct sockaddr_in);

	//try to connect to the server
	if (connect(ds, (struct sockaddr *) &server, lgAdrServer) == -1) { exit(1); }
	printf("[+]Client is connected with the server\n");

	/**
	 * shareFlag: define if the message will be transmit (default: 0 => not transmit)
	 * buffer: contain the message
	*/
	int shareFlag = 0;
	char* buffer = malloc(DEFAULT_BUFFER_SIZE * sizeof(char));
	
	//start sending message
	printf("Send messages: \n");
	
	/**
	 * fork comprehension :
	 * -- parent process send messages
	 * -- child process receive messages
	*/
	int id = fork();
	if (id == 0) {
		while(1) {
		
			//ask for sharing
			printf("share ? (yes: 1/ no: 0)");
			shareFlag = fgetc(stdin) - 48; while(fgetc(stdin) != '\n');

			//send it to the server
			int send = sendTCP(ds, (char *) &shareFlag, sizeof(int));
			if (send < 0) { perror("[-]Error while sending\n"); close(ds); exit(1); }
			else if (send == 0) { printf("[-]The server is disconnected\n"); break;}

			//wait for the message
			//if message = ":exit" then close the socket
			fgets(buffer, DEFAULT_BUFFER_SIZE, stdin);
			if (strncmp(buffer, ":exit", 5) == 0) { 
				close(ds); 
				printf("[-]Disconnected from server.\n"); 
				exit(0); 
			}

			//send it to the server
			send = sendTCP(ds, buffer, sizeof(buffer));
			if (send < 0) { perror("[-]Error while sending\n"); close(ds); exit(1); }
			else if (send == 0) { printf("[-]The server is disconnected\n"); break;}
		}
	} else {
		while(1) {

			//receive and print the message
			int receive = recvTCP(ds, buffer, sizeof(buffer));
			if (receive < 0){ perror ("[-] Error while receiving\n"); close(ds); break; }
			if (receive == 0) { printf("[-] Socket is closed\n"); close(ds); break; }
			printf("%s", buffer);
		}
	}

	//close the socket
	close(ds);
	return 0;
}
