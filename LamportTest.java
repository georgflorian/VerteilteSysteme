import java.net.InetAddress;
import java.util.ArrayList;


public class LamportTest {
    public static int sleep = 100; //Wartezeit vor Versenden
    public static int jitter = 10; // jitter fuer Log-Nachrichten



public static void main (String[] args){

    try {
        LamportNode logger = new LamportNode ("Logger", InetAddress.getLocalHost (), 9000);
        LamportNode A = new LamportNode ("Einstein", InetAddress.getLocalHost (), 9001);
        LamportNode B = new LamportNode ("Euler", InetAddress.getLocalHost (), 9002);
        LamportNode C = new LamportNode ("Curie", InetAddress.getLocalHost (), 9003);
        LamportNode D = new LamportNode ("Turing", InetAddress.getLocalHost (), 9004);
        LamportNode [] peers = {A,B,C,D}; // Menge aller Worker

        //erzeuge Logger Threat und Worker Threads
        LamportLogger log = new LamportLogger (logger, peers);
        LamportWorker einstein = new LamportWorker (0, peers, logger, sleep, jitter );
        LamportWorker euler = new LamportWorker (1, peers, logger, sleep, jitter );
        LamportWorker curie = new LamportWorker (2, peers, logger, sleep, jitter );
        LamportWorker turing = new LamportWorker (3, peers, logger, sleep, jitter );

        log.start ();
        einstein.start ();
        euler.start ();
        curie.start ();
        turing.start ();

        Thread.sleep(800); //Zeit bis Stopp

        //Alles Stoppen
        einstein.interrupt ();
        euler.interrupt ();
        curie.interrupt ();
        turing.interrupt ();

        Thread.sleep(500);           //Puffer fuer Nachrichten im Umlauf
        log.interrupt ();
        Thread.sleep(500);          //Zeit fuer Logger

        int x = 1;                                                  //variable für Zeitstempelabgleich
        int z = 0;                                                  //variable zur Begrenzung der Schleifendurchläufe
        ArrayList<String> LogList = new ArrayList<String>();        //ArrayList für Log Daten
        LogList.addAll(LogRecieving.buffer);                        //Liste der Log Daten wird importiert
        int length = (LogList.size() - 1);                          //Länge der Liste wird für die for-Schleife gespeichert

        while ( z < length){                                                //Schleife für die Sortierung dder Log Daten startet
            for (String logdat : LogList){

                String[] teile = logdat.split("Time: ", length);      //Der Zeitstempel wird von der Nachricht getrennt
                int i = Integer.parseInt(teile[1]);

                if ( i == x) {
                    System.out.println(logdat);                             //Nachrichten mit dem passenden Zeitstempel werden ausgegeben
                }

            }
            x++;                                                            //Zeitstempel wird um 1 erhöht
            z++;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    System.exit (0);
}//main
}//class