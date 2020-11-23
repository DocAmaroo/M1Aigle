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

/* colors define */
#define RED   "\x1B[31m"
#define GRN   "\x1B[32m"
#define YEL   "\x1B[33m"
#define BLU   "\x1B[34m"
#define MAG   "\x1B[35m"
#define CYN   "\x1B[36m"
#define WHT   "\x1B[37m"
#define RESET "\x1B[0m"

int DEFAULT_BUFFER_SIZE = 128;
const int MAX_RESSOURCES = 10;

struct message {
	int service;
	long int quantity;
} message;

struct ressource {
	char* country;
	int length;
	int CPU;
	int storage;
} ressource;

struct data {
	struct ressource* ressources;
	int length;
}data;

void printData(struct data* data) {
	for (int i=0; i < data->length; i++) {
		printf(RESET "{\n");
		printf("\tCountry: %s\n", data->ressources[i].country);
		printf("\tCPU: %i\n", data->ressources[i].CPU);
		printf("\tStorage: %i\n", data->ressources[i].storage);
		printf("}\n");
	}
}

void printUsage(char const* name) {
	printf("Usage: %s adress port", name);
	exit(0);
}

int recvTCP(int socket, char *buffer, size_t length){
	int _recv = 0;

	size_t remaining=length; while(remaining){
		_recv = recv(socket, buffer, remaining, 0);
		if (_recv <= 0) return _recv;

		buffer += _recv;
		remaining -= _recv;
	}

	return 1;
}

int recvRessource(int socket, char* buffer, struct ressource* ressources, int index) {
	int recv = 0;
	int _intBuffer = 0;
	struct ressource new;

	// get country size
	recv = recvTCP(socket, (char *) &_intBuffer, sizeof(int));
	if (recv <= 0) return recv;
	new.length = _intBuffer;
	printf("j'ai reçu: %i\n", new.length);

	// country
	recv = recvTCP(socket, buffer, 4);
	if (recv <= 0) return recv;
	new.country = buffer;
	printf("\tcountry: %s\n", buffer);

	// CPU
	// recv = recvTCP(socket, (char *) &_intBuffer, sizeof(size_t));
	// if (recv <= 0) return recv;
	// new.CPU = _intBuffer;
	// printf("\tCPU: %i\n", _intBuffer);


	// Storage
	// recv = recvTCP(socket, (char *) &_intBuffer, sizeof(size_t));
	// if (recv <= 0) return recv;
	// new.storage = _intBuffer;
	// printf("\tStorage: %i\n", _intBuffer);

	// ressources[index] = new;
	return 1;
}

int recvRessources(int socket, char* buffer, struct data* data, struct ressource* ressources) {
	
	int recv = 0;
	int _intBuffer = 0;

	recv = recvTCP(socket, (char *) &_intBuffer, sizeof(size_t));
	if (recv <= 0) return recv;
	data->length = _intBuffer;
	printf("Element to receive: %i\n", data->length);

	for (int i=0; i < data->length; i++) {
		printf(RESET "{\n");
		
		recv = recvRessource(socket, buffer, ressources, i);
		if (recv <= 0) return recv;

		printf("}\n");
	}

	data->ressources = ressources;

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

int sendMessage(int socket, struct message msg){
	int send = 0;

	send = sendTCP(socket, (char *) &msg.service, sizeof(int));
	if (send <= 0) return send;

	send = sendTCP(socket, (char *) &msg.quantity, sizeof(long int));
	if (send <= 0) return send;

	return 1;
}

int main(int argc, char const *argv[]){
	
	if (argc != 3) printUsage(argv[0]);

	//------- SOCKET INITIALISATION ------- //

	//try to create a socket
	int _socket = socket(PF_INET, SOCK_STREAM, 0);
	if (_socket == -1) exit(1);
	printf(GRN "[+]Client Socket created\n");

	//create server structure
	struct sockaddr_in server;
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = inet_addr(argv[1]);
	server.sin_port = htons(atoi(argv[2])); 
	socklen_t lgAdrServer = sizeof(struct sockaddr_in);

	//try to connect to the server
	if (connect(_socket, (struct sockaddr *) &server, lgAdrServer) == -1) exit(1);
	printf(GRN "[+]Client is connected with the server\n");
	
	// init ressources structure
	char buffer[DEFAULT_BUFFER_SIZE];
	struct data* data = malloc(sizeof(struct data));
	struct ressource* ressources = malloc(MAX_RESSOURCES*sizeof(struct ressource));
	struct message msg;

	// receive available ressources
	int recv = recvRessources(_socket, buffer, data, ressources);
	if (recv < 0) { perror(RED "[-]Error while receiving\n"); close(_socket); exit(1); }
	else if (recv == 0) { printf(YEL "[~]The server is disconnected\n"); }

	/**
	 * fork comprehension :
	 * -- parent process send messages
	 * -- child process receive messages
	*/
	int id = fork();
	if (id == 0) {
		while(1) {
			
			// ask which services
			printf(RESET "What do you want ? (CPU: 0/ STORAGE: 1)");
			msg.service = fgetc(stdin) - 48; 
			while(fgetc(stdin) != '\n');

			// ask the value
			//if message = ":exit" then close the socket
			printf("How many ? ");
			fgets(buffer, DEFAULT_BUFFER_SIZE, stdin);
			if (strncmp(buffer, ":exit", 5) == 0) { 
				close(_socket); 
				printf(RED "[-]Disconnected from server.\n"); 
				exit(0); 
			}
			msg.quantity = atoi(buffer);
			printf("test: %li", msg.quantity);

			//send it to the server
			int send = sendMessage(_socket, msg);
			if (send < 0) { perror(RED "[-]Error while sending\n"); close(_socket); exit(1); }
			else if (send == 0) { printf(YEL "[~]The server is disconnected\n"); break;}
		}
	} else {
		while(1) {

			//receive and print the message
			// int recv = recvTCP(_socket, buffer, sizeof(size_t));
			// if (recv < 0){ perror (RED "[-] Error while receiving\n"); close(_socket); break; }
			// if (recv == 0) { printf(YEL "[~] Socket is closed\n"); close(_socket); break; }
			// printf("%s", buffer);
		}
	}

	//close the socket
	close(_socket);
	return 0;
}
