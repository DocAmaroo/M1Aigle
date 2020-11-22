#include <stdio.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <pthread.h>
#include <fcntl.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <ctype.h>
#include <netinet/in.h>
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
/* end of color define */

#define BUFF_SIZE 256
#define SA struct sockaddr
char* words[16]= {"ANGLE","ARMOIRE","BANC","BUREAU","CABINET","CARREAU","CHAISE","CLASSE","CLEF","COIN","COULOIR","DOSSIER","EAU","ECOLE","ENTRER","ESCALIER"};


struct gamestate {
        int error;
        int win;
        char* word_to_find;
        int* alreadyFound;
        int size;
        char word_found[];

};

char* getRandomWord(){
        srand(time(NULL));
        char * wordRet = words[(rand() % 15)];
        //char * wordRet = words[11];
        return wordRet;
}

void initGame(struct gamestate* gs){
        gs->error = 0;
        gs->word_to_find = (char*) getRandomWord();
        gs->size = strlen(gs->word_to_find);
        gs->alreadyFound = malloc(gs->size*sizeof(int));
        for (size_t i = 0; i <= strlen(gs->word_to_find); i++) {
                gs->alreadyFound[i] = 0;
                gs->word_found[i] ='_';
                if ( i == 0) { gs->word_found[i] = gs->word_to_find[i];}
                if ( i == strlen(gs->word_to_find) ) { gs->word_found[i] = '\0';}
        }

        gs->win = 0;
}

int sendall(int sock, const char* data, int data_length){
        int bytessend = 0;
        while ( bytessend < data_length) {
                int result = send(sock, data + bytessend, data_length - bytessend, 0);
                if ( result == -1) { perror("send error"); exit(1);} // not exit if errno == EAGAIN
                bytessend += result;
        }
        return bytessend;
}

int sendWithSize(int sock, const char* data, int data_length){
        char sizeToSend[4];
        *((int*) sizeToSend ) = data_length;
        sendall(sock, sizeToSend,sizeof(sizeToSend) );
        sendall(sock, data,data_length);
        return 1;
}

int recvAll(int socket, char *buf, int len, char* ip, int port) { // recvAll function
        int remaining = len;

        while (remaining) {
                int received = recv(socket, buf, remaining, 0);
                if (received <= 0) { printf(MAG "[Quit] Client %s:%d disconnected !\n", ip, port); return 1;}
                buf += received;
                remaining -= received;
        }
        return 0;
}

int recvWithSize(int sock, char* data, char* ip, int port){
        char sizeToRecv[4];
        if(recvAll(sock, sizeToRecv, sizeof(sizeToRecv), ip, port) == 1) {return -1;};
        if(recvAll(sock, data, *((int*) sizeToRecv ), ip, port) == 1) {return -1;};
        return *((int*) sizeToRecv );
}

// Driver function

void sendStruct(int connfd, struct gamestate *gs){

        char intToSend[4];
        sprintf(intToSend, "%d", gs->error);
        printf("%s\n", intToSend);
        sendWithSize(connfd, intToSend, strlen(intToSend));
        bzero(intToSend, 4);

        sprintf(intToSend, "%d", gs->win);
        printf("%s\n", intToSend);
        sendWithSize(connfd, intToSend, strlen(intToSend));
        bzero(intToSend, 4);

        sprintf(intToSend, "%d", gs->size);
        printf("%s\n", intToSend);
        sendWithSize(connfd, intToSend, strlen(intToSend));
        bzero(intToSend, 4);

        for (size_t i = 0; i < gs->size; i++) {
                sprintf(intToSend, "%d", gs->alreadyFound[i]);
                printf("%s - ", intToSend);
                sendWithSize(connfd, intToSend, strlen(intToSend));
                bzero(intToSend, 4);
        }

        printf("%s\n", gs->word_found);
        char *wf = &gs->word_found[0];
        sendWithSize(connfd, wf, strlen(gs->word_found));


        printf("%s\n", gs->word_to_find);
        sendWithSize(connfd, gs->word_to_find, strlen(gs->word_to_find));

}
int containsAtPos( char c, char* s, struct gamestate *gs){
        int pos = -1;
        for (size_t i = 0; i < strlen(s); i++) {
                if ( ( c == s[i] || toupper(c) == s[i] ) && gs->alreadyFound[i] == 0 ) { pos = i;}
        }
        return pos;
}

void executePlay(char *toDecode, struct gamestate* gs){
        char newLetter = *toDecode;
        printf("You chose to put letter %c\n", newLetter);
        if ( containsAtPos(newLetter, gs->word_to_find, gs) != -1 ) {
                gs->word_found[containsAtPos(newLetter, gs->word_to_find, gs)] = newLetter;
                gs->alreadyFound[containsAtPos(newLetter, gs->word_to_find, gs)] = 1;
        }
        if (containsAtPos(newLetter, gs->word_to_find, gs) == -1 ) { gs->error++;}
        if ( strcmp(gs->word_found, gs->word_to_find) == 0 || strcasecmp(gs->word_found,gs->word_to_find) == 0) { gs->win = 1;}
        if (gs->error == 8) { gs->win = -1;}
}


int main(int argc, char* argv[])
{

        if ( argc < 3 ) {
                printf(MAG "[Error] Unrecognize command. \n");
                printf(CYN "[Usage] ./tcpServer [portServer][nbClients] \n");
                printf(MAG "portServer:" WHT "Port you want to use for your server\n");
                printf(MAG "nbClients:" WHT "How many simultaneous clients can connect to your server \n");
                printf(MAG "[Error] Program exited. \n");
                exit(1);
        }

        int sockfd;
        int nbClients = atoi(argv[2]);
        struct sockaddr_in servaddr;
        char buff[BUFF_SIZE];

        // socket create and verification
        sockfd = socket(AF_INET, SOCK_STREAM, 0);
        if (sockfd == -1) {
                printf("socket creation failed...\n");
                exit(0);
        }
        else
                bzero(&servaddr, sizeof(servaddr));

        // assign IP, PORT
        servaddr.sin_family = AF_INET;
        servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
        servaddr.sin_port = htons(atoi(argv[1]));

        char * ipServ = inet_ntoa(servaddr.sin_addr);
        // Binding newly created socket to given IP and verification
        if ((bind(sockfd, (SA*)&servaddr, sizeof(servaddr))) != 0) {
                perror("socket bind failed...\n");
                exit(0);
        }
        // Now server is ready to listen and verification


        printf(CYN "\n==========================================\n");
        printf(CYN "|            SERVER %s:%d         |\n", ipServ,htons(servaddr.sin_port));
        printf(CYN "==========================================\n");

        if ((listen(sockfd, nbClients)) != 0) {
                printf("Listen failed...\n");
                exit(0);
        }

        struct sockaddr_in cli;
        int len = sizeof(cli);

        // Accept the data packet from client and verification
        int connfd = accept(sockfd, (SA*)&cli, (socklen_t* ) &len);
        if (connfd < 0) {
                printf("server acccept failed...\n");
                exit(0);
        }
        else
                printf(GRN "[Connection] client %s:%d connected.\n", inet_ntoa(cli.sin_addr),htons(cli.sin_port));
        char SelectFun[4];
        recvWithSize(connfd, SelectFun, inet_ntoa(cli.sin_addr),cli.sin_port);
        bzero(SelectFun, 4);


        struct gamestate gs;
        initGame(&gs);
        sendStruct(connfd, &gs);

        while(1) {
                char playRec;
                recvWithSize(connfd, &playRec, inet_ntoa(cli.sin_addr), cli.sin_port);
                printf("play from user : %c\n", playRec);
                char letter = playRec;

                executePlay(&letter, &gs);
                sendStruct(connfd, &gs);
        }
        return 0;
}
