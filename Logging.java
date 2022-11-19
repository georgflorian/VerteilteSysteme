import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Logging extends Thread{
    public int id;
    public String massage;
    public String method;
    public int jitter;
    public int time;

        //Threads werden automatisch gestartet sobald ein Objekt der Klasse initialisiert wurde
        public void run(){

            send(id, massage, method, time);        //Thread senden wird gestartet

        }

        //Konstruktor der Klasse Logging
        public Logging(int id, String massage, String method, int jitter, int time){
            this.id = id;
            this.massage = massage;
            this.method = method;
            this.jitter = (int)(Math.random()*1000);        //Jitter wird für jede Nachricht zufällig gewählt
            this.time = time;
        }

    //Die Funktion send sorgt für die Erstellung und Versendung der UDP-Pakete
    void send ( int id, String massage, String method, int time){

        try {
            DatagramSocket ds = new DatagramSocket();                               //Für das Versende eines UDP-Paketes wird ein neuer Sockel geöffnet

            Thread.sleep(jitter);                                                   //Der Thread wartet den Jitter ab bevor eer beginnt
            int port = 9000;                                                        //Der Port des Logging Knotens wird festgelegt
            String log = id + ": " + massage + " " + method +" Time: " + time;      //Nachricht wird zusammengefügt
            byte [] buffer = log.getBytes();                                        //Nachricht wird in ein byte überführt
            int i = buffer.length;
            DatagramPacket DPsend = new DatagramPacket(buffer, 0, i, InetAddress.getLocalHost(), port);     //Das UDP-Paket wird erstellt
            ds.send(DPsend);                                                                                      //Das Paket wird verssendet

        } catch (Exception e) {
            System.out.println("logging fehlgeschlagen.");
            e.printStackTrace();
        }

    }


}


