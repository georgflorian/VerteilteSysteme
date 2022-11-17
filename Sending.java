import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class Sending extends Thread{

 public int id;
 public LamportNode destination;
 public LamportNode[] peers;
 public int jitter;

    public void run (){

        send(id,destination,peers);

    }

    public Sending (int id, LamportNode destination, LamportNode[] peers,int jitter){
        this.id = id;
        this.destination = destination;
        this.peers = peers;
        this.jitter = jitter;
    }

    void send ( int id, LamportNode destination, LamportNode[] peers){

        try {
            peers[id].clock.inc();
            DatagramSocket ds = new DatagramSocket();
            Thread.sleep((long)(Math.random()*1000));
            int port = destination.port;
            Random rand = new Random();
            int x = rand.nextInt();
            String massage =id + " Hello" + " " + x;
            byte [] buffer = massage.getBytes();
            int i = buffer.length;
            DatagramPacket DPsend = new DatagramPacket(buffer, 0, i, InetAddress.getLocalHost(), port);
            ds.send(DPsend);
            Thread t1 = new Logging(id,massage,"Send", jitter, peers[id].clock.getTS());
            t1.start();

        } catch (Exception e) {
            System.out.println("Senden fehlgeschlagen.");
            e.printStackTrace();
        }

    }
}

