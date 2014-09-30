package Grafer;
public class Dijkstra <N>{
	    private int snabbast;
	    private boolean klar;
	    private N from;
	     
	    public Dijkstra() {
	        setSnabbast(Integer.MAX_VALUE);
	        setKlar(false);
	    }
	    public int getSnabbast() {
	        return snabbast;
	    } 
	    public void setSnabbast(int snabbast) {
	        this.snabbast = snabbast;
	    }
	    public boolean getKlar() {
	        return klar;
	    }
	    public void setKlar(boolean klar) {
	        this.klar = klar;
	    }
	    public N getFrom() {
	        return from;
	    }
	    public void setFrom(N from) {
	        this.from = from;
	    }	     
	    public String toString() {
	        return "Tid: " + snabbast + " Klar: " + klar;
    }
}

