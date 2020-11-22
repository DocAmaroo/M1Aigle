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

struct thread_param {
	int* socket;
	int* socketStore;
	int* nbClient;
	struct ressources* ressources;
    pthread_mutex_t* mutex;
}param;

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
	printf("Usage: %s port", name);
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

int sendTCP(int socket, const char *buffer, size_t length){
	int sent = 1;

	size_t bytes=length; while(bytes != 0){
		sent = send(socket, buffer, bytes, 0);
		if (sent <= 0) return sent;

		bytes -= sent;
	}
	return 1;
}

int recvMessage(int socket, char* buffer, struct message *ms) {
	if (recvTCP(socket, buffer, DEFAULT_BUFFER_SIZE) <= 0) return -1;
	ms->buffer = buffer;
	bzero(buffer, DEFAULT_BUFFER_SIZE);

	if (recvTCP(socket, buffer, DEFAULT_BUFFER_SIZE <= 0)) return -1;
	ms->service = atoi(buffer);
	bzero(buffer, DEFAULT_BUFFER_SIZE);

	return 1;
}

int sendStruct(int socket, struct ressources _ressources) {
	
	int send = 0;

	send = sendTCP(socket, _ressources.country, sizeof(DEFAULT_BUFFER_SIZE));
	if (send <= 0) return send;
	printf(MAG "[+] country\n");

	// sprintf(buffer, "%d", _ressources.CPU);
	send = sendTCP(socket, (char *) &_ressources.CPU, sizeof(long int));
	if (send <= 0) return send;
	//bzero(buffer, DEFAULT_BUFFER_SIZE);
	printf(MAG "[+] CPU\n");
	
	//sprintf(buffer, "%d", _ressources.storage);
	send = sendTCP(socket, (char *) &_ressources.storage, sizeof(long int));
	if (send <= 0) return send;
	//bzero(buffer, DEFAULT_BUFFER_SIZE);
	printf(MAG "[+] Storage\n");

	return 1;
}

void initRessources( struct ressources* ressources) {
	struct ressources _ressources;
	_ressources.country = "Lyon";
	_ressources.CPU = 10;
	_ressources.storage = 50000;
	ressources[0] = _ressources;
}

void* handleClient(void* param) {

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

	// send available ressources to the client
	int send = sendStruct(socket, *p->ressources);
	if (send < 0) { perror("[-]Error while sending\n"); close(socket); exit(1); }
	else if (send == 0) { printf("[-]The client is disconnected\n"); close(socket);}

	int service = 0;
	char* buffer = malloc(DEFAULT_BUFFER_SIZE * sizeof(char));
	while(1) {

		//1st receive => get the service
		int receive = recvTCP(socket, (char *) &service, sizeof(int));
		if (receive < 0){ perror ("[-]Receive error"); close(socket); exit(1);}
		if (receive == 0) { printf("[-]The socket is closed\n"); close(socket); break;}

		//2nd receive => get the buffer (value)
		receive = recvTCP(socket, buffer, sizeof(buffer));
		if (receive < 0){ perror ("[-]Receive error"); close(socket); exit(1);}
		if (receive == 0) { printf("[-]The socket is closed\n"); close(socket); break;}
		
		int _toIntBuffer = atoi(buffer);
		if (service == 0) { // on CPU
			printf("[+]Demand CPU usage: %i\n", _toIntBuffer);
		} else { // on Storage
			printf("[+]Demand Storage usage: %i\n", _toIntBuffer);
		}

		//send it the message
		int send = sendTCP(socket, buffer, sizeof(buffer));
		if (send < 0) { perror("[-]Sendind error\n"); close(socket); exit(1); }
		else if (send == 0) { printf("[-]The client is disconnected\n"); close(socket); break;}
	}

	pthread_exit(NULL);
}


int main(int argc, char const *argv[]) {
	
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

	// all available ressources
	struct ressources* ressources = malloc(MAX_RESSOURCES*sizeof(struct ressources));
	initRessources(ressources);

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
		param->ressources = ressources;
		param->mutex = &mutex;

		//create a new thread for the client
		if (pthread_create(&threads[nbClient], NULL, handleClient, param) != 0) { perror(RED "erreur creation thread\n"); exit(1); }
	}
	
	//close the socket
	close(_socket);
	return 0;
}
