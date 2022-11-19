import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Recieving extends Thread{
    public int id;
    public LamportNode[] peers;
    public int jitter;

    //Threads werden automatisch gestartet sobald ein Objekt der Klasse initialisiert wurde
    public void run(){

        recieve(id, peers);

    }

    //Konstruktor der Klasse Recieving
    public Recieving(int id, LamportNode[] peers, int jitter){
        this.id = id;
        this.peers = peers;
        this.jitter = jitter;
    }

    //Die Klasse recieve nimmt die Nachrichten des Lamport Workers entgegen
    public void recieve ( int id,LamportNode[] peers){

        try {
            LamportNode worker  = peers[id];                        //Die passende Lamport Node des Workers wird aus den peers ermittelt
            DatagramSocket ds = new DatagramSocket(worker.port);    //Ein neuer Sockel wird auf dem Port der Node geöffnet
            byte[] receive = new byte[99999];                       //Das byte welches zum Empfangen der Nachrichten verwendet wird, wird initialisiert
            DatagramPacket DpRecieve = null;                        //Das UDP-Paket welches zum Empfangen der Nachrichten verwendet wird, wird initialisiert

            while (true){                                                       //Während der while-Schleife wird auf eingehende Nachrichten gewartet

                DpRecieve = new DatagramPacket(receive, receive.length);        //DpRecieve wird auf recieve initialisiert
                ds.receive(DpRecieve);                                          //Der Sockel ds empfängt das eingehende UDP-Paket

                String text = data(receive).toString();                         //Der Inhalt des Pakets wird in einem String gespeichert
                String node = text.substring(0,1);                              //Um die versenddende Node zu ermitteln wird die mitesendete id extrahiert

                int node2 = Integer.parseInt(node);                             //Die extrahierte id wird in einen Integer wert geparsed

                if (node2 < peers.length) {                                                         //Wenn der Wert größer als die Menge der Nodes ist wird kein merge ausgeführt
                    peers[id].clock.merge(peers[id].clock.getTS(), peers[node2].clock.getTS());     //Die Zeitstempel der zwei beteiligten Nodes werden gemerged
                }

                System.out.println("Message: "+ text);                                              //Die empfangene Nachricht wird ausgegeben


                Thread t1 = new Logging(id, data(receive).toString(), "Recieved", jitter, peers[id].clock.getTS());          //Der Thread für das Logging wird initialisiert
                t1.start();                                                                                                         //Der Thread für das Logging wird gestartet

                receive = new byte[99999];      //Der Inhalt von recieve wird zurückgesetzt

            }

        } catch (Exception e) {
            System.out.println("Empfangen fehlgeschlagen.");
            e.printStackTrace();
        }

    }

    //Die Klasse data nimmt die bytes des UDP-Paketes entgegen und wandelt sie in einen String um
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
