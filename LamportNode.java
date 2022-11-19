import java.net.*;

public class LamportNode {
public String name;
public InetAddress address;
public int port;
public LamportClock clock;      //ab Teil (b)

//Konstruktor der Klasse Lamport Node
public LamportNode (String name, InetAddress ia, int port){
    this.name = name;
    this.address = ia;
    this.port = port;
    this.clock = new LamportClock ();       //ab Teil (b)
    }
} //class