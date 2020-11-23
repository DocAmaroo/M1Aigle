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

/**
 * @brief structure used to send to the server
 * 
 */
struct message 
{
	int service;
	long int quantity;
} message;

/**
 * @brief structure used to kept ressource send by the server
 * 
 */
struct ressource 
{
	char* country;
	int length;
	int CPU;
	int storage;
} ressource;

/**
 * @brief structure used to store all ressources send by the server
 * 
 */
struct data 
{
	struct ressource* ressources;
	int length;
}data;

/**
 * @brief print on the terminal how to use client program
 * 
 * @param name 
 */
void printUsage(char const* name) 
{
	printf("Usage: %s adress port", name);
	exit(0);
}

/**
 * @brief print all data available
 * 
 * @param data 
 */
void printData(struct data* data) 
{
	for (int i=0; i < data->length; i++) {
		printf(RESET "{\n");
		printf("\tCountry: %s\n", data->ressources[i].country);
		printf("\tCPU: %i\n", data->ressources[i].CPU);
		printf("\tStorage: %i\n", data->ressources[i].storage);
		printf("}\n");
	}
}

/**
 * @brief Receive TCP message
 * 
 * @param socket 
 * @param buffer 
 * @param length 
 * @return int 
 */
int recvTCP(int socket, char *buffer, size_t length) 
{

	size_t toread = length; 
	
	while(toread > 0){
		size_t _recv = recv(socket, buffer, toread, 0);
		if (_recv <= 0) return _recv;		
		buffer += _recv;
		toread -= _recv;
	}

	return length;
}

/**
 * @brief Receive ressource structure
 * 
 * @param socket 
 * @param buffer 
 * @param ressources 
 * @param index 
 * @return int 
 */
int recvRessource(int socket, char* buffer, struct ressource* ressources, int index) 
{
	int recv = 0;
	struct ressource new;

	// get country size
	recv = recvTCP(socket, (char *) buffer, sizeof(int));
	if (recv <= 0) return recv;
	new.length = atoi(buffer);
	bzero(buffer, DEFAULT_BUFFER_SIZE);

	// get country
	recv = recvTCP(socket, (char *) buffer, new.length);
	if (recv <= 0) return recv;
	new.country = malloc(new.length + 1);
	strncpy(new.country, buffer , new.length);
	bzero(buffer, DEFAULT_BUFFER_SIZE);
	// printf("\tCountry: %s\n", buffer);

	// get CPU
	recv = recvTCP(socket, (char *) buffer, sizeof(size_t));
	if (recv <= 0) return recv;
	new.CPU = atoi(buffer);
	bzero(buffer, DEFAULT_BUFFER_SIZE);
	// printf("\tCPU: %i\n", atoi(buffer));


	// get Storage
	recv = recvTCP(socket, (char *) buffer, sizeof(size_t));
	if (recv <= 0) return recv;
	new.storage = atoi(buffer);
	// printf("\tStorage: %i\n", atoi(buffer));

	ressources[index] = new;
	
	return 1;
}

/**
 * @brief Receive all ressource structure
 * 
 * @param socket 
 * @param buffer 
 * @param data 
 * @param ressources 
 * @return int 
 */
int recvRessources(int socket, char* buffer, struct data* data, struct ressource* ressources) 
{	
	int recv = 0;

	recv = recvTCP(socket, (char *) buffer, sizeof(int));
	if (recv <= 0) return recv;
	data->length = atoi(buffer);
	
	for (int i=0; i < data->length; i++) {
		recv = recvRessource(socket, buffer, ressources, i);
		if (recv <= 0) return recv;
	}

	data->ressources = ressources;
	printData(data);
	return 1;
}

/**
 * @brief Send TCP request
 * 
 * @param socket 
 * @param buffer 
 * @param length 
 * @return int 
 */
int sendTCP(int socket, const char* buffer, size_t length)
{
	size_t bytes=length;
	
	while(bytes > 0){
		size_t _send = send(socket, buffer, bytes, 0);
		if (_send <= 0) return _send;

		buffer += _send;
		bytes -= _send;
	}

	return length;
}

/**
 * @brief Send message structure
 * 
 * @param socket 
 * @param msg 
 * @return int 
 */
int sendMessage(int socket, struct message msg)
{
	int send = 0;

	send = sendTCP(socket, (char *) &msg.service, sizeof(int));
	if (send <= 0) return send;

	send = sendTCP(socket, (char *) &msg.quantity, sizeof(long int));
	if (send <= 0) return send;

	return 1;
}


int main(int argc, char const *argv[])
{
	
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
