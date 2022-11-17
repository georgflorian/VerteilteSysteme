public class LamportLogger extends Thread{
    public LamportNode logger;
    public LamportNode[] peers;

    public void run() {

        Thread t1 = new LogRecieving(logger.port, peers);
        t1.start();

    }

    public LamportLogger (LamportNode logger, LamportNode [] peers){
        this.logger = logger;
        this.peers = peers;
    }

}
