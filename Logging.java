import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Logging extends Thread{
    public int id;
    public String massage;
    public String method;
    public int jitter;
    public int time;

        public void run(){

            send(id, massage, method, time);

        }

        public Logging(int id, String massage, String method, int jitter, int time){
            this.id = id;
            this.massage = massage;
            this.method = method;
            this.jitter = (int)(Math.random()*1000);
            this.time = time;
        }

    void send ( int id, String massage, String method, int time){

        try {
            DatagramSocket ds = new DatagramSocket();

            Thread.sleep(jitter);
            int port = 9000;
            String log = id + ": " + massage + " " + method +" Time: " + time;
            byte [] buffer = log.getBytes();
            int i = buffer.length;
            DatagramPacket DPsend = new DatagramPacket(buffer, 0, i, InetAddress.getLocalHost(), port);
            ds.send(DPsend);

        } catch (Exception e) {
            System.out.println("logging fehlgeschlagen.");
            e.printStackTrace();
        }

    }


}


