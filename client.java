import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Time;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    private static String name;
    public static void main(String[] args){
        name = args[0];
        try {
            Socket socket = new Socket();
            int port = 5001;
            InetSocketAddress IP = new InetSocketAddress("localhost", port); // commented out IP
            socket.connect(IP);
            System.out.println("Connected");
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            doCommunications(in, out, name);
        } catch (IOException e) {
            System.out.println("I/O Failed");
            System.exit(-1);
        }
    }

    private static void doCommunications(BufferedReader in, PrintWriter out, String name){
        Runnable communicationRunnable = () -> {
            try {
                if (in.ready()){
                    String msg = in.readLine();
                    if (msg.equals("2")){
                        System.out.println("Received restart signal");
                    } else if (msg.equals("1")){
                        // Server Status good
                    } else if (msg.equals("0")){
                       System.out.println("Received yellow flag");
                       // Teams handle yellow flag
                    } else if (msg.equals("-1")){
                        System.out.println("Received red flag");
                        // Teams handle red flag
                    }
                    out.println(getStatus() + " " + name + ": " + Time.valueOf(LocalTime.now()));
                }
            } catch (IOException e) {
                System.out.println("I/O Failed");

            }
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(communicationRunnable, 0, 1000, TimeUnit.MILLISECONDS);
        // socket close
    }

    private static int getStatus(){
        // Dummy Code: Implement in your control loop and call in client code
        // if good, return 1
        // if yellow flag, return 0
        // if red flag, return -1
        double random = Math.random() * 100;
        if (random >= 0 && random <= 94){
            return 1;
        } else if (random > 94 && random <= 97){
            return 0;
        } else {
            return -1;
        }
    }
}
