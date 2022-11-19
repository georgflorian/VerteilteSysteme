import java.util.*;

public class LamportWorker extends Thread {
    public int id;
    public LamportNode[] peers;
    public LamportNode logger;
    public int sleep;
    public int jitter;

    //Threads werden automatisch gestartet sobald ein Objekt der Klasse initialisiert wurde
    public void run() {
        int x = execute();                                      //Funktion zur zufälligen Auswahl des Zielknotens
        Thread t2 = new Sending(id, peers[x], peers, jitter);
        Thread t1 = new Recieving(id, peers, jitter);           //2 neue Thread Objekt werden erzeugt
        t1.start();                                             //starten des empfangen Thread
        t2.start();                                             //starten des senden Threads
    }

    //Konstruktor für den Lamport Worker
    public LamportWorker(int id, LamportNode[] peers, LamportNode logger, int sleep, int jitter) {

        this.id = id;
        this.peers = peers;
        this.logger = logger;
        this.sleep = sleep;
        this.jitter = (int)(Math.random()*1000);


    }

    //Funktion execute stellt sicher dass ein knoten keine Nachricht an sich selbst senden kann
    public int execute() {
        Random rand = new Random();
        int x = rand.nextInt(4);
        if (x == this.id) {
            if (x == 0) {
                x = x + 1;
                return (x);
            } else {
                x = x - 1;
                return (x);
            }
        } else
            return (x);

    }
}
