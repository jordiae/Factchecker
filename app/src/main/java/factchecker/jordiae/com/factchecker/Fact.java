package factchecker.jordiae.com.factchecker;

/**
 * Created by jordiae on 7/03/16.
 */


public class Fact {
    private long date;

    Fact(long date){
        this.date = date;
    }
    Fact(){

    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}