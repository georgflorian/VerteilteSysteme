import java.util.*;

public class LamportWorker extends Thread {
    public int id;
    public LamportNode[] peers;
    public LamportNode logger;
    public int sleep;
    public int jitter;


    public void run() {
        int x = execute();
        Thread t2 = new Sending(id, peers[x], peers, jitter);
        Thread t1 = new Recieving(id, peers, jitter);
        t1.start();
        t2.start();
    }


    public LamportWorker(int id, LamportNode[] peers, LamportNode logger, int sleep, int jitter) {

        this.id = id;
        this.peers = peers;
        this.logger = logger;
        this.sleep = sleep;
        this.jitter = (int)(Math.random()*1000);


    }

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
