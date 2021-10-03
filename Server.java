import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private static ArrayList<Thread> threadQueue = new ArrayList<>();
    private static ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<>();
    private static ArrayList<Socket> sockets = new ArrayList<>();
    private static boolean canTalk = false;
    private static String STATUS = "STARTING";

    public Server() {
        int port = 5001;
        System.out.println("Waiting on connections");
        try {
            serverSocket = new ServerSocket(port); // creating socket for server
            while (threadQueue.size() < 2){
                clientSocket = serverSocket.accept();
                sockets.add(clientSocket);
                clientSocket.setSoTimeout(5000); // set the read timeout to 5ms
                // create a thread to pass new clients to
                Thread clientThread = (new Thread(() -> {
                    ConnectionHandler connectionHandler = new ConnectionHandler(clientSocket);
                    connectionHandlers.add(connectionHandler);
                }));
                threadQueue.add(clientThread);
                clientThread.start();
                System.out.println("Waiting on " + String.valueOf(2 - threadQueue.size()) + " connections");
            }
        } catch (IOException e) {
            System.out.println("No I/O");
            //System.exit(-1);
        }
    }

    public static void main(String[] args){
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        new Server();
        System.out.println();
        System.out.println("Type START to start.");
        String message = "";
        while (true){
            try {
                if (keyboard.ready()){
                    message = keyboard.readLine();
                }
                if (message.equalsIgnoreCase("START")){
                    System.out.println("STARTING...");
                    canTalk = true;
                    message = ""; // Reset message to leave loop
                } else if (message.equalsIgnoreCase("YELLOW")){
                    System.out.println("ABORTING...");
                    message = ""; // Reset message to leave loop
                    yellow_flag();
                } else if (message.equalsIgnoreCase("RED")){
                    System.out.println("STOPPING...");
                    message = ""; // Reset message to leave loop
                    red_flag();
                } else if (message.equalsIgnoreCase("STATUS")){
                    System.out.println("SERVER CONNECTION STATUSES:");
                    printCommunications();
                    message = ""; // Reset message to leave loop
                } else if (message.equalsIgnoreCase("RESTART")){
                    if (STATUS.equalsIgnoreCase("YELLOW_FLAG")){
                        STATUS = "RESTARTING";
                        restart();
                        STATUS = "RUNNING";
                        message = "";
                    } else {
                        message = "";
                        System.out.println("Restart not possible");
                    }
                }
            } catch (IOException e){
                System.out.println("No I/O");
            }
        }
    }

    public static void writeSocket(PrintWriter printWriter){
        Runnable writeRunnable = () -> {
            if (canTalk) {
                printWriter.println(getServerStatusAsIntString());
            }
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(writeRunnable, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public static void readComms(BufferedReader in){
        Runnable communicationRunnable = () -> {
            if (canTalk) {
                try {
                    String msg = in.readLine();
                    System.out.println(msg);
                    String[] split_message = msg.split(" ");

                    if (split_message[0].equalsIgnoreCase("0")) { // if sent a yellow flag
                        yellow_flag();
                    } else if (split_message[0].equalsIgnoreCase("-1")) { // if sent a red flag
                        red_flag();
                    }
                } catch (SocketTimeoutException e){
                    red_flag(); // A socket timed out
                } catch (IOException e) {
                    System.out.println("Red flag sent: halting");
                    red_flag();
                    //System.out.println("I/O Failed");
                }
            }
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(communicationRunnable, 0, 500, TimeUnit.MILLISECONDS);
    }

    private static void printCommunications(){
        for (Socket socket : sockets){
            ConnectionHandler.printConnectionInfo(socket);
        }
    }

    private static void broadcast(String error_code){
        for (Socket socket : sockets){
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println(error_code);
            } catch (IOException e){
                //System.out.println("I/O Failed");
            }
        }
    }

    public static void yellow_flag(){
        broadcast("0");
        System.out.println("Entering yellow flag mode: all cars must stop");
        STATUS = "YELLOW_FLAG";
        canTalk = false;
    }

    public static void red_flag(){
        broadcast("-1");
        STATUS = "RED_FLAG";
        canTalk = false;
        for (Socket socket : sockets){
            try {
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    private static void restart() {
        canTalk = true;
    }

    public static String getServerStatusAsIntString(){
        switch (STATUS){
            case "RESTARTING":
                return "2";
            case "YELLOW_FLAG":
                return "0";
            case "RED_FLAG":
                return "-1";
            case "RUNNING":
                return "1";
        }
        return "1"; // default
    }

}

