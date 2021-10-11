#include "ClientSocket.h"
#include "SocketException.h"
#include <iostream>
#include <string>
#include <chrono>
#include <ctime>
#include <thread>

using namespace std;

#define _GLIBCXX_USE_NANOSLEEP

string getStatus(){
    // Dummy code, returning good status
    return "1";
}

void doCommunications(ClientSocket client_socket, string name){
        while (true){
            std::chrono::seconds interval(1);
            time_t rawtime;
            struct tm * timeinfo;
            char buffer[80];
            time (&rawtime);
            timeinfo = localtime(&rawtime);
            strftime(buffer,sizeof(buffer),"%H:%M:%S",timeinfo);
            std::string str(buffer);
            string reply;
            string message = getStatus() + " " + name + ": " + str + "\r\n";
            client_socket << message;
            client_socket >> reply;
            string neg_one = "-1";
            if (reply.find("2") != string::npos){
                cout << "Restart signal received." << endl;
            } else if (reply.find("-1") != string::npos){
                cout << "Red flag received" << endl;
                // Handle red flag
            } else if (reply.find("0") != string::npos){
                cout << "Yellow flag received" << endl;
                // Teams, handle yellow flag here
            } 
            std::this_thread::sleep_for(interval);
        }
    }



int main ( int argc, char *argv[] )
{
  try
    {
      // Create the client socket
      ClientSocket client_socket ("172.16.182.135", 5001 ); // enter your IP here, wherever you run the server (use ipconfig on windows)
      cout << "Connected." << endl;
      string name = argv[1];

      string reply;
      try{
        doCommunications(client_socket, name);	
      } catch ( SocketException& ) {}

    }
  catch ( SocketException& e )
    {
      cout << "Exception was caught:" << e.description() << "\n";
    }

  return 0;
}


