import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class LogRecieving extends Thread{
    public int id;
    public LamportNode[] peers;

    public void run(){

        recieve(id, peers);

    }

    public LogRecieving(int id, LamportNode[] peers){
        this.id = id;
        this.peers = peers;
    }

    public void recieve ( int id,LamportNode[] peers){

        try {
            DatagramSocket ds = new DatagramSocket(id);
            byte[] receive = new byte[99999];
            DatagramPacket DpRecieve = null;

            while (true){

                DpRecieve = new DatagramPacket(receive, receive.length);
                ds.receive(DpRecieve);

                System.out.println("Log: "+ data(receive));

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
