import java.net.*;
import java.io.*;
import java.util.*;

public class LamportWorker extends Thread{
public int id;
public LamportNode [] peers;
public LamportNode logger;
public int sleep;
public int jitter;


    public void run() {

        System.out.println("Start");

        send(id, peers[2]);

        recieve(id, peers[2]);

        System.out.println("Ende");

    }

   public LamportWorker (int id, LamportNode [] peers, LamportNode logger, int sleep, int jitter) {

       this.id = id;
       this.peers = peers;
       this.logger = logger;
       this.sleep = sleep;
       this.jitter = jitter;



   }


       static void send ( int id, LamportNode destination){

           try {
               System.out.println("Start Senden");
               System.out.println(destination.address);
               Socket client = new Socket(destination.address, destination.port);
               System.out.println("connected");
               OutputStream output = client.getOutputStream();
               System.out.println("ausgabe schreiben");
               PrintWriter writer = new PrintWriter(output, true);
               System.out.println("ausgabe aufnehmen");
               Random rand = new Random();
               String massage = "Hello" + " " + rand;
               System.out.println(massage);
               writer.println(massage);
               System.out.println("Senden Erfolgreich");
               System.out.println("Ende Senden");

           } catch (Exception e) {
               System.out.println("Senden fehlgeschlagen.");
           }

       }

       public void recieve ( int id, LamportNode ziel){

           try {
               System.out.println("Start empfangen");
               Socket node = new Socket(ziel.address, ziel.port);
               InputStream input = node.getInputStream();
               BufferedReader reader = new BufferedReader(new InputStreamReader(input));
               String line = reader.readLine();
               System.out.println("Empfangen Erfolgreich");

           } catch (Exception e) {
               System.out.println("Empfangen fehlgeschlagen.");
           }

       }

}
