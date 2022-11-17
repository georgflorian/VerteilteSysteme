import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Recieving extends Thread{
    public int id;
    public LamportNode[] peers;

    public void run(){

        recieve(id, peers);

    }

    public Recieving(int id, LamportNode[] peers){
        this.id = id;
        this.peers = peers;
    }

    public void recieve ( int id,LamportNode[] peers){

        try {
            //System.out.println("Empfangen starten.");
            LamportNode worker  = peers[id];
            DatagramSocket ds = new DatagramSocket(worker.port);
            //System.out.println("Socket geöffnet.");
            byte[] receive = new byte[99999];
            DatagramPacket DpRecieve = null;

            while (true){

                DpRecieve = new DatagramPacket(receive, receive.length);
                ds.receive(DpRecieve);

                System.out.println("Message: "+ data(receive)); //"Message: "+ data(receive)

                receive = new byte[99999];

            }

        } catch (Exception e) {
            System.out.println("Empfangen fehlgeschlagen.");
        }

    }

    public static StringBuilder data(byte[] a){

        StringBuilder text = new StringBuilder();
        int i = 0;
        while (a[i] != 0){
            text.append((char) a[i]);
            i++;
        }
        return text;
    }
}
