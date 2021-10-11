# Instructions

Provided in this repository are a .jar file that you can use to run an example of the server locally on your computer, as well as the code that you can run on your Raspberry Pi to communicate with the server.
You should use this jar as a means to test your communications protocol with our server, to ensure that you are able to send heartbeats and receive status codes from the server.

All that is important in terms of formatting is shown in each of these files. The format should be as following for messages you are sending to the server:

`(STATUS CODE) (TEAM NAME): (TIMESTAMP)`

Any extra additions to that string that needs to be sent are shown in the respective client code. 

**NOTE:** This code is only *one* of the *two* communications protocols we will be using in the race. Due to the nature of the positioning system's setup, we are unable to add these heartbeats/codes to that server. For that reason, you will connect to that server separately. An example of what this code *might* look like in python is provided in the mqtt folder, as well as an example packet that you can use to practice parsing what you will receive from the server. This is subject to change, as this has not yet been tested, since the positioning system is not yet set up. 

## Running the Server

The .jar file in this repository can be ran locally for you to test your communication ability. Download the jar file [here](https://github.com/RaceOfficials/socket-server/raw/main/client/socket/server.jar), and run it on your machine using the following command:
`java -jar server.jar`.

## Java
Example java code is provided in the `Client.java` file. Before you are able to test your ablility to communicate, you need to update the IP in the client code. 
Replace "localhost" on line 20 of the code with your IP. You can find this on windows by running `ipconfig`. 

If the server is running, you can do the following:
Compile the code on your RPI by using `javac Client.java`
Run the file with `java Client (TEAM_NAME)`.

## Python
Example python code is provided in the `client.py` file. Before you are able to test your ablility to communicate, you need to update the IP in the client code. 
Replace "localhost" on line 9 of the code with your IP. You can find this on windows by running `ipconfig`. 

If the server is running, you can do the following:
Run the file with `python client.py (TEAM_NAME)`. Depending on your python installation, you may have to do `python3 client.py (TEAM NAME)`

## C++
Example C++ code is provided in the `cpp_client` folder. Before you are able to test your ablility to communicate, you need to update the IP in the client code. 
Replace "localhost" on line 53 of the code with your IP. You can find this on windows by running `ipconfig`. 

More speciifc instructions for C++ can be found in the README in the `cpp_client` folder. 

If you have other specific questions, please feel free to reach out on GroupMe or Discord (isaac#2222), or call/text at (405)-695-1054

