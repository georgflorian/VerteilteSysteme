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
        int x = execute();
        Thread t2 = new Sending(id,peers[x],peers);
        Thread t1 = new Recieving(id,peers);
        t1.start();
        t2.start();
    }


   public LamportWorker (int id, LamportNode [] peers, LamportNode logger, int sleep, int jitter) {

       this.id = id;
       this.peers = peers;
       this.logger = logger;
       this.sleep = sleep;
       this.jitter = jitter;



   }

    public int execute(){
        Random rand = new Random();
        int x = rand.nextInt(4);
        if (x == this.id){
            if (x == 0){
                x=x+1;
                return (x);
            }
            else{
                x = x-1;
                return (x);
            }
        }
        else
            return (x);

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
