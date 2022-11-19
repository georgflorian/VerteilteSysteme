public class LamportLogger extends Thread{
    public LamportNode logger;
    public LamportNode[] peers;

    //Threads werden automatisch gestartet sobald ein Objekt der Klasse initialisiert wurde
    public void run() {

        Thread t1 = new LogRecieving(logger.port, peers);       //Neues Thread Objekt wird erzeugt
        t1.start();                                             //Thread wird gestartet

    }

    //Konstruktor der Klasse Lamport Logger
    public LamportLogger (LamportNode logger, LamportNode [] peers){
        this.logger = logger;
        this.peers = peers;
    }

}
