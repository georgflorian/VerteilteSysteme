import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class Sending extends Thread{

 public int id;
 public LamportNode destination;
 public LamportNode[] peers;
 public int jitter;

    //Threads werden automatisch gestartet sobald ein Objekt der Klasse initialisiert wurde
    public void run (){

        send(id,destination,peers);

    }

    //Konstruktor der Klasse Sending
    public Sending (int id, LamportNode destination, LamportNode[] peers,int jitter){
        this.id = id;
        this.destination = destination;
        this.peers = peers;
        this.jitter = jitter;
    }

    //Die Funktion send sended in zufälligen Zeitabständen Nachrichten an die angegebenen Nodes
    void send ( int id, LamportNode destination, LamportNode[] peers){
            while (true) {                                                  //In der while-Schleife werden Nachrichten versendet
                try {
                    DatagramSocket ds = new DatagramSocket();               //Ein neuer Sockel wird auf dem Port der Node geöffnet
                    Thread.sleep((long) (Math.random() * 1000));            //Eine zufällige Wartezeit wird generiert
                    int port = destination.port;                            //Der Port der Zielnode wird entgegengenommen
                    Random rand = new Random();
                    int x = rand.nextInt();                                 //Eine zufällige Zahl wird erzeugt un in einen Integer umgewandelt
                    String massage = id + " Hello" + " " + x;               //Die Nachricht wird zusammengebaut
                    byte[] buffer = massage.getBytes();                     //Die Nachricht wird in den buffer geschrieben
                    int i = buffer.length;
                    DatagramPacket DPsend = new DatagramPacket(buffer, 0, i, InetAddress.getLocalHost(), port);     //Das UDP-Paket wird erstellt
                    peers[id].clock.inc();                                                                                //Die Lamport-Uhr der sendenden Node wird erhöht
                    ds.send(DPsend);                                                                                      //Das Paket wird versendet
                    Thread t1 = new Logging(id, massage, "Send", jitter, peers[id].clock.getTS());                 //Der Logging Thread wird initialisiert
                    t1.start();                                                                                           //Der Logging Thread wird gestartet

                } catch (Exception e) {
                    System.out.println("Senden fehlgeschlagen.");
                    e.printStackTrace();
                }
            }


    }
}

