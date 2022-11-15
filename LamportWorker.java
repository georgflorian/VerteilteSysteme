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

        execute();

        listener();

    }

   public LamportWorker (int id, LamportNode [] peers, LamportNode logger, int sleep, int jitter) {

       this.id = id;
       this.peers = peers;
       this.logger = logger;
       this.sleep = sleep;
       this.jitter = jitter;

   }

    void execute(){
        Random rand = new Random();
        int x = rand.nextInt(4);
        if (x == this.id){
            if (x == 0){
                x=x+1;
                send(this.id, peers[x]);
            }
            else{
                x = x-1;
                send(this.id, peers[x]);
            }
        }
        else
            send(this.id, peers[x]);

   }

   void listener(){
        while (true) {
            int x = this.id;
            //System.out.println(x);
            recieve(this.id, peers[x]);
        }
   }

       static void send ( int id, LamportNode destination){

           try {
               //Thread.sleep((long)(Math.random()*1000));
               //System.out.println("Start Senden: "+ id);
               Socket client = new Socket(destination.address, destination.port);
               OutputStream output = client.getOutputStream();
               PrintWriter writer = new PrintWriter(output, true);
               Random rand = new Random();
               int x = rand.nextInt();
               String massage = "Hello" + " " + x;
               System.out.println(massage);
               writer.println(massage);
               client.close();
               //System.out.println("Senden Erfolgreich");

           } catch (Exception e) {
               System.out.println("Senden fehlgeschlagen.");
           }

       }

       public void recieve ( int id, LamportNode ziel){

           try {
               //Thread.sleep((long)(Math.random()*1000));
               System.out.println("Start empfangen: " + id);
               Socket node = new Socket(ziel.address, ziel.port);
               System.out.println("Connecting...");
               BufferedReader reader = new BufferedReader(new InputStreamReader(node.getInputStream()));
               System.out.println("Reader initialized");
               String line = reader.readLine();
               System.out.println("Empfangen Erfolgreich: "+ line);
               node.close();


           } catch (Exception e) {
               System.out.println("Empfangen fehlgeschlagen.");
           }

       }

       public void logging (){
        try{

        }
        catch (Exception e) {
            System.out.println();
        }
       }

}
