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

int DEFAULT_BUFFER_SIZE = 128;

struct message {
	char* buffer;
	int service;
} message;

struct ressources {
	char* country;
	int CPU;
	int storage;
} ressources;

void printUsage(char const* name) {
	printf("Usage: %s adress port", name);
	exit(0);
}

int recvTCP(int socket, char *buffer, size_t length){
	
	size_t remaining=length; while(remaining){
		int _recv = recv(socket, buffer, remaining, 0);
		if (_recv <= 0) return _recv;		
		buffer += _recv;
		remaining -= _recv;
	}

	return 1;
}

int recvRessources(int socket, char *buffer, struct ressources* _ressources) {
	
	int recv = 0;
	int _intBuffer = 0;
	printf("{\n");

	// country
	recv = recvTCP(socket, buffer, 4);
	if (recv <= 0) return recv;
	//_ressources->country = buffer;
	printf("\tcountry: %s\n", buffer);
	bzero(buffer, DEFAULT_BUFFER_SIZE);

	// CPU
	recv = recvTCP(socket, (char *) &_intBuffer, 4);
	if (recv <= 0) return recv;
	//_ressources->CPU = atoi(buffer);
	printf("\tCPU: %i\n", _intBuffer);
	bzero(buffer, DEFAULT_BUFFER_SIZE);


	// Storage
	recv = recvTCP(socket, (char *) &_intBuffer, 4);
	if (recv <= 0) return recv;
	//_ressources->storage = atoi(buffer);
	printf("\tStorage: %i\n", _intBuffer);
	bzero(buffer, DEFAULT_BUFFER_SIZE);


	printf("}\n");

	return 1;
}

int sendTCP(int socket, const char *buffer, size_t length){
	int sent = 1;

	size_t bytes=length; while(bytes != 0){
		sent = send(socket, buffer, bytes, 0);
		if (sent <= 0) return sent;

		bytes -= sent;
	}
	return 1;
}

int sendMessage(int socket, struct message *ms){
	if (sendTCP(socket, ms->buffer, DEFAULT_BUFFER_SIZE) <= 0)  return -1;
	
	char* _service = malloc(8*sizeof(int));
	sprintf(_service, "%d", ms->service);
	if (sendTCP(socket, _service, DEFAULT_BUFFER_SIZE <= 0)) return -1;
	return 1;
}

int main(int argc, char const *argv[]){
	
	if (argc != 3) printUsage(argv[0]);

	//------- SOCKET INITIALISATION ------- //

	//try to create a socket
	int _socket = socket(PF_INET, SOCK_STREAM, 0);
	if (_socket == -1) exit(1);
	printf("[+]Client Socket created\n");

	//create server structure
	struct sockaddr_in server;
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = inet_addr(argv[1]);
	server.sin_port = htons(atoi(argv[2])); 
	socklen_t lgAdrServer = sizeof(struct sockaddr_in);

	//try to connect to the server
	if (connect(_socket, (struct sockaddr *) &server, lgAdrServer) == -1) exit(1);
	printf("[+]Client is connected with the server\n");

	/**
	 * service: defin which service the client choose for (0:CPU | 1:Storage)
	 * buffer: containe the value needed
	*/
	int service = 0;
	// char* buffer = malloc(DEFAULT_BUFFER_SIZE * sizeof(int));
	
	//start sending message
	//printf("Send messages: \n");
	
	// init ressources structure
	char buffer[DEFAULT_BUFFER_SIZE];
	struct ressources* ressources;
	int recv = recvRessources(_socket, buffer, ressources);
	if (recv < 0) { perror("[-]Error while receiving\n"); close(_socket); exit(1); }
	else if (recv == 0) { printf("[-]The server is disconnected\n"); }

	/**
	 * fork comprehension :
	 * -- parent process send messages
	 * -- child process receive messages
	*/
	int id = fork();
	if (id == 0) {
		while(1) {
			
			// ask which services
			printf("What do you want ? (CPU: 0/ STORAGE: 1)");
			service = fgetc(stdin) - 48; while(fgetc(stdin) != '\n');

			//send it to the server
			int send = sendTCP(_socket, (char *) &service, sizeof(int));
			if (send < 0) { perror("[-]Error while sending\n"); close(_socket); exit(1); }
			else if (send == 0) { printf("[-]The server is disconnected\n"); break;}

			// ask the value
			//if message = ":exit" then close the socket
			printf("How many ? ");
			fgets(buffer, DEFAULT_BUFFER_SIZE, stdin);
			if (strncmp(buffer, ":exit", 5) == 0) { 
				close(_socket); 
				printf("[-]Disconnected from server.\n"); 
				exit(0); 
			}

			//send it to the server
			send = sendTCP(_socket, buffer, sizeof(buffer));
			if (send < 0) { perror("[-]Error while sending\n"); close(_socket); exit(1); }
			else if (send == 0) { printf("[-]The server is disconnected\n"); break;}
		}
	} else {
		while(1) {

			//receive and print the message
			int recv = recvTCP(_socket, buffer, sizeof(buffer));
			if (recv < 0){ perror ("[-] Error while receiving\n"); close(_socket); break; }
			if (recv == 0) { printf("[-] Socket is closed\n"); close(_socket); break; }
			printf("%s", buffer);
		}
	}

	//close the socket
	close(_socket);
	return 0;
}
