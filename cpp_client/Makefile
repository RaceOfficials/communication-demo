# Makefile for the socket programming example
#
simple_client_objects = ClientSocket.o Socket.o client.o


all : client

client: $(simple_client_objects)
	g++ -o client $(simple_client_objects)


Socket: Socket.cpp
ServerSocket: ServerSocket.cpp
ClientSocket: ClientSocket.cpp
simple_client_main: client.cpp


clean:
	rm -f *.o simple_server simple_client
