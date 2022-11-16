import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LamportWorker extends Thread{
public int id;
public LamportNode [] peers;
public LamportNode logger;
public int sleep;
public int jitter;


    public void run() {

        //listener();
        execute();

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

        int x = this.id;
        //System.out.println(x);
        recieve(this.id, peers[x]);
   }

       void send ( int id, LamportNode destination){

           try {

               LamportNode worker  = peers[id];
               DatagramSocket ds = new DatagramSocket(worker.port);

               Thread.sleep((long)(Math.random()*1000));
               //System.out.println("Start Senden: "+ id);
               Random rand = new Random();
               int x = rand.nextInt();
               String massage = "Hello" + " " + x;
               System.out.println(massage);
               byte [] buffer = massage.getBytes();
               System.out.println(data(buffer));
               DatagramPacket DPsend = new DatagramPacket(buffer, buffer.length, destination.port);
               ds.send(DPsend);
               System.out.println("Senden Erfolgreich");

           } catch (Exception e) {
               System.out.println("Senden fehlgeschlagen.");
           }

       }

       public void recieve ( int id, LamportNode ziel){

           try {
               System.out.println("Empfangen starten.");
               LamportNode worker  = peers[id];
               DatagramSocket ds = new DatagramSocket(worker.port);
               System.out.println("Socket geöffnet.");
               byte[] receive = new byte[1];
               DatagramPacket DpRecieve = null;

               while (true){

                   DpRecieve = new DatagramPacket(receive, receive.length);
                   ds.receive(DpRecieve);

                   System.out.println("Message: "+ data(receive));

                   receive = new byte[1];

               }

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

       public static StringBuilder data(byte[] a){

        if (a == null){
            return null;
        }
        StringBuilder text = new StringBuilder();
        int i = 0;
        while (a[i] != 0){
            text.append((char) a[i]);
            i++;
        }
        System.out.println(text);
        return text;
       }

}
