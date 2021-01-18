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

const int DEFAULT_BUFFER_SIZE = 16;
const int MAX_CLIENT = 2;
		
struct thread_param {
	int* socket;
	int* socketStore;
	int* nbClient;
    pthread_mutex_t* mutex;
    pthread_cond_t* cond;
}param;

struct message {
	char* buffer;
	int shareFlag;
} message;

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


void* handleClient(void* param) {

	//get params
	struct thread_param *p = (struct thread_param*) param;
	int socket = *p->socket;
	int id = *p->nbClient;
	printf("Client %i\n", id+1);

	//lock mutex
	pthread_mutex_lock(p->mutex);

	//store the socket and update nbClient
	p->socketStore[*p->nbClient] = socket;
	*p->nbClient += 1;

	//check if each client is here
	if (*p->nbClient == MAX_CLIENT) {
		printf("[+]Tous les clients sont connectés\n");
		pthread_cond_broadcast(p->cond);
	}

	//wait for more user
	while (*p->nbClient < MAX_CLIENT) {
		pthread_cond_wait(p->cond, p->mutex);
	}

	//unlock mutex
	pthread_mutex_unlock(p->mutex);

	//All client are here, we can start to receive message from client
	int shareFlag = 0;
	char* buffer = malloc(DEFAULT_BUFFER_SIZE * sizeof(char));
	while(1) {

		//1st receive => get the shareFlag
		int receive = recvTCP(socket, (char *) &shareFlag, sizeof(int));
		if (receive < 0){ perror ("[-]Receive error"); close(socket); exit(1);}
		if (receive == 0) { printf("[-]The socket is closed\n"); close(socket); break;}

		//2nd receive => get the message
		receive = recvTCP(socket, buffer, sizeof(buffer));
		if (receive < 0){ perror ("[-]Receive error"); close(socket); exit(1);}
		if (receive == 0) { printf("[-]The socket is closed\n"); close(socket); break;}
		printf("%s", buffer);

		//if we can transmit the message to the other client then
		if (shareFlag) {

			//get the other client 
			int socketToSend = (id == 0)? p->socketStore[1] : p->socketStore[0];

			//send it the message
			int send = sendTCP(socketToSend, buffer, sizeof(buffer));
			if (send < 0) { perror("[-]Sendind error\n"); close(socketToSend); exit(1); }
			else if (send == 0) { printf("[-]The client is disconnected\n"); close(socketToSend); break;}
		}
	}

	pthread_exit(NULL);
}


int main(int argc, char const *argv[])
{
	
	if (argc != 2) {
		printf("Usage: %s port", argv[0]);
	}


	// ------- SOCKET INITIALISATION ------- //

	//try to create a socket
	int ds; if ( (ds = socket(PF_INET, SOCK_STREAM, 0)) == -1) { exit(1); }
	printf("[+]Server Socket created\n");

	//create server structure
	struct sockaddr_in server;
	server.sin_family = AF_INET;
	server.sin_addr.s_addr = INADDR_ANY;
	server.sin_port = htons(atoi(argv[1]));

	//try to bind the port
	if (bind(ds, (struct sockaddr*) &server, sizeof(server)) == -1) { exit(1); }
	printf("[+]Bind to port %i\n", atoi(argv[1]));

	//try to listen
	if (listen(ds, 10) == -1) { exit(1);}
	printf("[+]Listening...\n");

	//store all socket
	int* socketStore = malloc(MAX_CLIENT * sizeof(int));

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

	//start the program
	int nbClient = 0; while(1) {

		//waiting for client
		printf("[+]Waiting for %i client...\n", MAX_CLIENT-nbClient);

		client = accept(ds, (struct sockaddr *) &clientAddr, &addr_size);
		if (client == -1) {exit(1);}
		printf("Connection accepted from %s:%d\n", inet_ntoa(clientAddr.sin_addr), ntohs(clientAddr.sin_port));

		//create param to send with the thread of the client
		struct thread_param *param = (struct thread_param*) malloc(sizeof(struct thread_param));
		param->socket = &client;
		param->socketStore = socketStore;
		param->nbClient = &nbClient;
		param->mutex = &mutex;
		param->cond = &cond;

		//create a new thread for the client
		if (pthread_create(&threads[nbClient], NULL, handleClient, param) != 0) { perror("erreur creation thread\n"); exit(1); }
	}
	
	//close the socket
	close(ds);
	return 0;
}
