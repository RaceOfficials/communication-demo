import java.io.*;
import java.net.Socket;
import java.net.SocketException;


public class ConnectionHandler extends Thread {

    private Socket clientSocket;

    private int numConnections = 0;

    public ConnectionHandler(Socket socket){
        this.clientSocket = socket;
        numConnections++;
        //printConnectionInfo(clientSocket);
        run();
    }


    @Override
    public void run(){
        // VALID RUNNING MODE
            try {
                PrintWriter printWriter =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Server.readComms(bufferedReader);
                Server.writeSocket(printWriter);

            } catch (SocketException e) {
                System.out.println("Socket is missing");
                System.exit(-1);
            } catch (IOException e){
                System.out.println("No I/O");
            }

    }


    public static void printConnectionInfo(Socket socket){
        System.out.println("Local Address: " + socket.getLocalAddress());
        System.out.println("Local Port: " + socket.getLocalPort());
        System.out.println("Remote Address: " + socket.getInetAddress());
        System.out.println("Remote Port: " + socket.getPort());
    }




}
