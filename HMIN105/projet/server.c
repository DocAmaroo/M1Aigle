#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <pthread.h>

/* colors define */
#define RED   "\x1B[31m"
#define GRN   "\x1B[32m"
#define YEL   "\x1B[33m"
#define BLU   "\x1B[34m"
#define MAG   "\x1B[35m"
#define CYN   "\x1B[36m"
#define WHT   "\x1B[37m"
#define RESET "\x1B[0m"

const int DEFAULT_BUFFER_SIZE = 127;
const int MAX_RESSOURCES = 10;
const int MAX_CLIENT = 10;

/**
 * @brief structure used to get message sent by a client
 * 
 */
struct message 
{
	int service;
	long int quantity;
} message;

/**
 * @brief structure used to store ressource
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
 * @brief structure used to store all ressources
 * 
 */
struct data 
{
	struct ressource* ressources;
	int length;
}data;

/**
 * @brief struct used to send param to thread
 * 
 */
struct thread_param 
{
	int* socket;
	int* socketStore;
	int* nbClient;
	struct data* data;
    pthread_mutex_t* mutex;
}param;

/**
 * @brief print on the terminal how to use server program
 * 
 * @param name 
 */
void printUsage(char const* name) 
{
	printf("Usage: %s port", name);
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
 * @brief Receive message from the client
 * 
 * @param socket 
 * @param msg 
 * @return int 
 */
int recvMessage(int socket, char* buffer, struct message *ms) 
{
	int recv = 0;
	int _intBuffer = 0;

	recv = recvTCP(socket, (char *) &_intBuffer, sizeof(int));
	if (recv <= 0) return recv;
	ms->service = _intBuffer;

	recv = recvTCP(socket, (char *) &_intBuffer, sizeof(long int));
	if (recv <= 0) return recv;
	ms->quantity = _intBuffer;

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
 * @brief Send ressource structure
 * 
 * @param socket 
 * @param buffer 
 * @param length 
 * @return int 
 */
int sendRessource(int socket, char* buffer, struct ressource ressource) 
{
	int send = 0;

	// send country size
	sprintf(buffer, "%d", ressource.length);
	send = sendTCP(socket, (char *) buffer, sizeof(int));
	if (send <= 0) return send;
	bzero(buffer, DEFAULT_BUFFER_SIZE);

	// send country
	send = sendTCP(socket, ressource.country, ressource.length*sizeof(char));
	if (send <= 0) return send;
	bzero(buffer, DEFAULT_BUFFER_SIZE);

	// send CPU
	sprintf(buffer, "%d", ressource.CPU);
	send = sendTCP(socket, (char *) buffer, sizeof(size_t));
	if (send <= 0) return send;
	bzero(buffer, DEFAULT_BUFFER_SIZE);
	
	// send storage
	sprintf(buffer, "%d", ressource.storage);
	send = sendTCP(socket, (char *) buffer, sizeof(size_t));
	if (send <= 0) return send;
	bzero(buffer, DEFAULT_BUFFER_SIZE);
	return 1;
}

/**
 * @brief Send all ressource structure
 * 
 * @param socket 
 * @param buffer 
 * @param length 
 * @return int 
 */
int sendRessources(int socket, char* buffer, struct ressource* ressources, int length) 
{
	int send = 0;

	sprintf(buffer, "%d", length);
	send = sendTCP(socket, (char *) buffer, sizeof(int));
	if (send <= 0) return send;

	for (int i=0; i < length; i++){
		send = sendRessource(socket, buffer, ressources[i]);
		if (send <= 0) return send;
	}

	return 1;
}

/**
 * @brief initialize data
 * 
 * @param data 
 * @param ressources 
 */
void initData(struct data* data, struct ressource* ressources) 
{
	struct ressource new;
	int res = 0;

	new.country = (char *) "Lyon";
	new.length = strlen(new.country);
	new.CPU = 10;
	new.storage = 50000;
	ressources[0] = new;
	res++;

	new.country = (char *) "Montpellier";
	new.length = strlen(new.country);
	new.CPU = 50;
	new.storage = 500000;
	ressources[1] = new;
	res++;

	data->length = res;
	data->ressources = ressources;
	printData(data);
}

/**
 * @brief client handler
 * 
 * @param param 
 * @return void* 
 */
void* handleClient(void* param) 
{

	//get params
	struct thread_param *p = (struct thread_param*) param;
	int socket = *p->socket;

	//lock mutex
	pthread_mutex_lock(p->mutex);

	//store the socket and update nbClient
	p->socketStore[*p->nbClient] = socket;
	*p->nbClient += 1;

	//unlock mutex
	pthread_mutex_unlock(p->mutex);
	
	//struct message msg;
	char buffer[DEFAULT_BUFFER_SIZE];

	// send available ressources to the client
	int send = sendRessources(socket, buffer, p->data->ressources, p->data->length);
	if (send < 0) { perror(RED "[-]Error while sending\n"); close(socket);}
	else if (send == 0) { printf(YEL "[~]The client is disconnected\n"); close(socket);}	

	while(1) {

		// int recv = recvMessage(socket, buffer, &msg);
		// if (recv < 0){ perror (RED "[-]Receive error"); close(socket);}
		// if (recv == 0) { printf(YEL "[~]The socket is closed\n"); close(socket);}

		// if (msg.service == 0) {
		// 	printf(MAG "[+]Demand CPU usage: %li\n", msg.quantity);
		// } else {
		// 	printf(MAG "[+]Demand Storage usage: %li\n", msg.quantity);
		// }

		//send it the message
		// int send = sendTCP(socket, buffer, sizeof(buffer));
		// if (send < 0) { perror("[-]Sendind error\n"); close(socket); exit(1); }
		// else if (send == 0) { printf("[-]The client is disconnected\n"); close(socket); break;}
	}

	pthread_exit(NULL);
}

int main(int argc, char const *argv[]) 
{
	
	if (argc != 2) 
		printUsage(argv[0]);

	// ------- SOCKET INITIALISATION ------- //

	//try to create a socket
	int _socket; if ( (_socket = socket(PF_INET, SOCK_STREAM, 0)) == -1) { exit(1); }
	printf(GRN "[+]Server Socket created\n");

	//create server structure
	struct sockaddr_in server;
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons(atoi(argv[1]));

	//try to bind the port
	if (bind(_socket, (struct sockaddr*) &server, sizeof(server)) == -1) { exit(1); }
	printf(GRN "[+]Bind to port %i\n", atoi(argv[1]));

	//try to listen
	if (listen(_socket, MAX_CLIENT) == -1) { exit(1);}
	printf(GRN "[+]Listening...\n");

	//new client
	int client;
	struct sockaddr_in clientAddr;
	socklen_t addr_size;


	// ------- THREAD INITIALISATION ------- //

	//thread store
	pthread_t threads[MAX_CLIENT];

	//thread mutex
	pthread_mutex_t mutex;
	pthread_mutex_init(&mutex, NULL);

	//thread cond
	pthread_cond_t cond;
	pthread_cond_init(&cond, NULL);


	// socket storage
	int* socketStore = malloc(MAX_CLIENT * sizeof(int));

	// init data
	printf(CYN "\n------------------------------\n");
	printf(CYN "----- * AVAILABLE DATA * -----\n");
	printf(CYN "------------------------------\n");
	struct data* data = malloc(sizeof(struct data));
	struct ressource ressources[MAX_RESSOURCES];
	initData(data, ressources);

	//start the program
	int nbClient=0; while(1) {
		client = accept(_socket, (struct sockaddr *) &clientAddr, &addr_size);
		if (client == -1) {exit(1);}
		printf(YEL "[+]Connection accepted from %s:%d\n", inet_ntoa(clientAddr.sin_addr), ntohs(clientAddr.sin_port));

		//create param to send with the thread of the client
		struct thread_param *param = (struct thread_param*) malloc(sizeof(struct thread_param));
		param->socket = &client;
		param->socketStore = socketStore;
		param->nbClient = &nbClient;
		param->data = data;
		param->mutex = &mutex;

		//create a new thread for the client
		if (pthread_create(&threads[nbClient], NULL, handleClient, param) != 0) { perror(RED "erreur creation thread\n"); exit(1); }
	}
	
	//close the socket
	close(_socket);
	return 0;
}
