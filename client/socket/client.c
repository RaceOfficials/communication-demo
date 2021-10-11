#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <iostream>
#include <chrono>
#include <ctime>   


// Untested, but should work on Linux-based systems
void error(const char *msg)
{
    perror(msg);
    exit(0);
}

int main(int argc, char *argv[])
{
    int sockfd, portno = 5001;
    char name[] = argv[0];
    struct sockaddr_in serv_addr =;
    struct hostent *server;

    char buffer[256];
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) 
        error("Host could not be resolved.");
    server = gethostbyname("localhost");
    if (server == NULL) {
        fprintf(stderr,"Host could not be resolved\n");
        exit(0);
    }
    bzero((char *) &serv;_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr, 
         (char *)&serv;_addr.sin_addr.s_addr,
         server->h_length);
    serv_addr.sin_port = htons(portno);
    if (connect(sockfd, (struct sockaddr *) &serv;_addr, sizeof(serv_addr)) < 0) 
        error("An error has occurred while connecting.");
    doCommunications(sockfd, buffer, name);
    close(sockfd);
    return 0;
}

void doCommunications(int sockfd, char[] buffer, char[] name)
{
    while (true){
        std::chrono::seconds interval(1);
        int n;
        buffer = getStatus() + " " + name + ": " + std::chrono::system_clock::now();
        n = write(sockfd, buffer, strlen(buffer));
        if (n < 0) 
            error("Error writing to socket.");
        bzero(buffer,256);
        n = read(sockfd, buffer, 255);
        if (n < 0) 
            error("Error reading from socket.");
        if (buffer == "2"){
            printf("Restart signal received.");
        } else if (n == "1"){
            // status good, do nothing
        } else if (n == "0"){
            printf("Yellow flag received");
            // Teams, handle yellow flag here
        } else if (n == "-1"){
            printf("Red flag received");
            // Handle red flag
        }
        std::this_thread::sleep_for(interval);
    }
}

char[] getStatus(){
    // Dummy code, returning good status
    return "1";
}
