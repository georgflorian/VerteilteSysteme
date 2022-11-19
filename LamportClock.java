public class LamportClock extends Thread{
    public int time;

    public void run(){

    }

    public LamportClock(){
    this.time=0;

    }

    public int getTS(){
        //System.out.println(time);
        return this.time;
    }

    public void setTS(int newtime){
        this.time = newtime;
    }

    public void inc(){
        this.time ++;
    }

    public void merge(int time1, int time2){
        if(time1 > time2){
            //System.out.println(time1 + " " + time2);
            setTS(time1 +1);
            //System.out.println(getTS());
        }
        else{
            //System.out.println(time1 + " " + time2);
            setTS(time2 +1);
            //System.out.println(getTS());
        }
    }

}
