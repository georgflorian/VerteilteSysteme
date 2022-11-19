public class LamportClock extends Thread{
    public int time;

    //Konstruktor der Klasse Lamport Clock
    public LamportClock(){
    this.time=0;
    }

    //Die Funktion gibt den aktuellen Zeitstempel zurück
    public int getTS(){
        return this.time;
    }

    //Die Funkton setzt den Zeitstempel auf einen gewünschten Wert
    public void setTS(int newtime){
        this.time = newtime;
    }

    //Der Zeitstempel wird um 1 erhöht
    public void inc(){
        this.time ++;
    }

    //Die Funktion vergleicht 2 Zeitstempel und erhöt den größeren um 1
    public void merge(int time1, int time2){
        if(time1 > time2){
            setTS(time1 +1);
        }
        else{
            setTS(time2 +1);
        }
    }

}
