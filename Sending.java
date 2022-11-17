import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class Sending extends Thread{

 public int id;
 public LamportNode destination;
 public LamportNode[] peers;

    public void run (){

        send(id,destination,peers);

    }

    public Sending (int id, LamportNode destination, LamportNode[] peers){
        this.id = id;
        this.destination = destination;
        this.peers = peers;
    }

    void send ( int id, LamportNode destination, LamportNode[] peers){

        try {
            DatagramSocket ds = new DatagramSocket();

            Thread.sleep((long)(Math.random()*1000));
            //System.out.println("Start Senden: "+ id);
            int port = destination.port;
            Random rand = new Random();
            int x = rand.nextInt();
            String massage = "Hello" + " " + x;
            //System.out.println(massage);
            byte [] buffer = massage.getBytes();
            //System.out.println("buffer written");
            int i = buffer.length;
            //System.out.println(i);
            DatagramPacket DPsend = new DatagramPacket(buffer, 0, i, InetAddress.getLocalHost(), port);
            //System.out.println(DPsend.getPort());
            ds.send(DPsend);
            System.out.println("Senden Erfolgreich");

        } catch (Exception e) {
            System.out.println("Senden fehlgeschlagen.");
            e.printStackTrace();
        }

    }
}

