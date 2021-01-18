#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
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

struct gamestate {
        int error;
        int win;
        char* word_to_find;
        int* alreadyFound;
        int size;
        char word_found[];

};

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

void recvStruct(int sockfd, struct gamestate *gs, char* buff, char * ip, int port){
        recvWithSize(sockfd, buff, ip, port);
        gs->error = atoi(buff);
        //  printf("recv error: %d\n", gs->error);
        bzero(buff, BUFF_SIZE);

        recvWithSize(sockfd, buff, ip, port);
        gs->win = atoi(buff);
        //  printf("recv win: %d\n", gs->win);
        bzero(buff, BUFF_SIZE);

        recvWithSize(sockfd, buff, ip, port);
        gs->size = atoi(buff);
        //  printf("recv size: %d\n", gs->size);
        bzero(buff, BUFF_SIZE);

        gs->alreadyFound = malloc(gs->size*(sizeof(int)));

        for (size_t i = 0; i < gs->size; i++) {
                recvWithSize(sockfd, buff, ip, port);
                gs->alreadyFound[i] = atoi(buff);
                //  printf("recv alreadyFound %zu : %d\n", i, gs->alreadyFound[i]);
                bzero(buff, BUFF_SIZE);
        }

        recvWithSize(sockfd, buff, ip, port);
        for (size_t i = 0; i < strlen(buff); i++) {
                gs->word_found[i]=buff[i];
        }
        //  printf("recv wf: %s\n", gs->word_found);
        bzero(buff, BUFF_SIZE);

        recvWithSize(sockfd, buff, ip, port);
        gs->word_to_find = malloc(1024*sizeof(char));
        strcpy(gs->word_to_find, buff);
        //    printf("recv wtf: %s\n", gs->word_to_find);
        bzero(buff, BUFF_SIZE);

}

void errorPrint(int error){
        char HANG_STATES[7][10 * 9] =
        {
                "             +         +----     +----     +----     +----     +----     +----     +----  ",
                "             |         |         |   O     |   O     |   O     |   O     |   O     |   O  ",
                "             |         |         |         |   +     | --+     | --+--   | --+--   | --+--",
                "             |         |         |         |   |     |   |     |   |     |   |     |   |  ",
                "             |         |         |         |         |         |         |  /      |  / \\ ",
                "             |         |         |         |         |         |         |         |      ",
                "/*****\\   /*****\\   /*****\\   /*****\\   /*****\\   /*****\\   /*****\\   /*****\\   /*****\\   "
        };

        for (size_t i = 0; i < 7; i++) {
                printf("%.10s \n", &HANG_STATES[i][error*10]);
        }
}

void printWF(struct gamestate* gs){
        for (size_t i = 0; i < strlen(gs->word_to_find); i++) {
                printf("%c", gs->word_found[i]);
        }
}
void printGUI(char* s){
        for (size_t i = 0; i < strlen(s); i++) {
                printf("-");
        }
}
void gamePrint(struct gamestate* gs){
        switch (gs->win) {
        case 0:
                printf("\n\n\n"); errorPrint(gs->error);
                printf("\n\n\n");
                printf("+-------------"); printGUI(gs->word_found); printf("-------------+"); printf("\t"); printf("+--------------------+\n");
                printf("|%8s  Word : %s %8s|", "",gs->word_found,""); printf("\t"); printf("|    error : %d/8     |\n",gs->error);
                printf("+-------------"); printGUI(gs->word_found); printf("-------------+"); printf("\t"); printf("+--------------------+\n");


                break;
        case 1:
                errorPrint(gs->error); printf("Word : %s", gs->word_found); printf("\nYou found the word ! Congrats ! \n");
                break;
        case -1:
                errorPrint(gs->error); printf("Word : %s", gs->word_found); printf("\nYou didn't found the word, you're dead !\n" );
        }
}

int main(int argc, char* argv[])
{
        int sockfd, connfd;
        char buff[BUFF_SIZE];
        bzero(buff, BUFF_SIZE);
        struct sockaddr_in servaddr, cli;
        if ( argc < 3 ) {
                printf(MAG "[Error] Unrecognize command. \n");
                printf(CYN "[Usage] ./tcpClient [ipServer][portServer] \n");
                printf(MAG "[Error] Program exited. \n");
                exit(1);

        }
        // socket create and varification
        sockfd = socket(AF_INET, SOCK_STREAM, 0);
        if (sockfd == -1) {
                printf("socket creation failed...\n");
                exit(0);
        }
        else
                //  printf("Socket successfully created..\n");
                bzero(&servaddr, sizeof(servaddr));

        // assign IP, PORT
        servaddr.sin_family = AF_INET;
        servaddr.sin_addr.s_addr = inet_addr(argv[1]);
        servaddr.sin_port = htons(atoi(argv[2]));

        printf(CYN "\n=========================================\n");
        printf(CYN "|                  CLIENT                |\n");
        printf(CYN "=========================================\n");
        // connect the client socket to server socket
        if (connect(sockfd, (SA*)&servaddr, sizeof(servaddr)) != 0) {
                printf("connection with the server failed...\n");
                exit(0);
        }
        else

                printf(GRN "[Connection] connected to the server %s:%d\n", argv[1], atoi(argv[2]));
        int n = 1;
        struct gamestate gs;
        char toEdit[4];
        sprintf(toEdit, "%d", n);
        sendWithSize(sockfd, toEdit, strlen(toEdit));
        bzero(toEdit, 4);



        recvStruct(sockfd, &gs,buff,argv[1], atoi(argv[2]));

        while(1) {
                system("clear");
                gamePrint(&gs);
                printf("\n\n\n");
                printf("Play > ");
                char playToDecode;
                scanf("\n%c", &playToDecode);
                printf("%c\n", playToDecode);
                sendWithSize(sockfd, &playToDecode, 1);
                bzero(&playToDecode, 1);

                recvStruct(sockfd, &gs,buff,argv[1], atoi(argv[2]));
                if ( gs.win == -1 ) { break;}
                if ( gs.win == 1 ) { break;}
                system("clear");
                gamePrint(&gs);
                printf("\n\n\n");
                printf("Play > ");
        }
        if ( gs.win == -1 || gs.win == 1) {   system("clear");
                                              gamePrint(&gs);}
        printf(MAG "[Quit] client closed. Bye bye.\n");
        printf(RESET "\n" );
        return 0;
}
