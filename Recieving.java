import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Recieving extends Thread{
    public int id;
    public LamportNode[] peers;
    public int jitter;

    public void run(){

        recieve(id, peers);

    }

    public Recieving(int id, LamportNode[] peers, int jitter){
        this.id = id;
        this.peers = peers;
        this.jitter = jitter;
    }

    public void recieve ( int id,LamportNode[] peers){

        try {
            LamportNode worker  = peers[id];
            DatagramSocket ds = new DatagramSocket(worker.port);
            byte[] receive = new byte[99999];
            DatagramPacket DpRecieve = null;

            while (true){

                DpRecieve = new DatagramPacket(receive, receive.length);
                ds.receive(DpRecieve);

                String text = data(receive).toString();
                String node = text.substring(0,1);

                int node2 = Integer.parseInt(node);

                if (node2 < peers.length) {
                    peers[id].clock.merge(peers[id].clock.getTS(), peers[node2].clock.getTS());
                    //System.out.println("Merge");
                }

                System.out.println("Message: "+ text);


                Thread t1 = new Logging(id, data(receive).toString(), "Recieved", jitter, peers[id].clock.getTS());
                t1.start();

                receive = new byte[99999];

            }

        } catch (Exception e) {
            System.out.println("Empfangen fehlgeschlagen.");
            e.printStackTrace();
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
