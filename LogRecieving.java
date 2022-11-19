import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class LogRecieving extends Thread{
    public int id;
    public LamportNode[] peers;
    public static List<String> buffer;
    public int i;

    //Threads werden automatisch gestartet sobald ein Objekt der Klasse initialisiert wurde
    public void run(){

        recieve(id, peers);     //Ausführung des recieve Threads

    }

    //Konstruktor der Klasse Log Recieving
    public LogRecieving(int id, LamportNode[] peers){
        this.id = id;
        this.peers = peers;
        List<String> buffer = new ArrayList<>();        //Erstellen des Buffers
        this.buffer = buffer;
        this.i = 1;
    }

    //Die Recieve Funktion lauscht auf dem Port des Lamport Loggers nach neuen Nachrichten
    public void recieve ( int id,LamportNode[] peers){

        try {
            DatagramSocket ds = new DatagramSocket(id);     //Der Sockel wird am Port des Loggers geöffnet
            byte[] receive = new byte[99999];               //Das byte welches zum Empfangen der Nachrichten verwendet wird, wird initialisiert
            DatagramPacket DpRecieve = null;                //Das UDP-Paket welches zum Empfangen der Nachrichten verwendet wird, wird initialisiert

            while (true){                                                   //Während der while-Schleife wird auf eingehende Nachrichten gewartet

                DpRecieve = new DatagramPacket(receive, receive.length);    //DpRecieve wird auf recieve initialisiert
                ds.receive(DpRecieve);                                      //Der Sockel ds empfängt das eingehende UDP-Paket

                buffer.add(data(receive).toString());                       //Der Inhalt des Pakets wird dem buffer angehängt

                receive = new byte[99999];                                  //Der Inhalt von recieve wird zurückgesetzt

            }

        } catch (Exception e) {
            System.out.println("Empfangen fehlgeschlagen.");
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
