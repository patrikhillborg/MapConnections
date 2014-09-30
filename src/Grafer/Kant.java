package Grafer;
public class Kant<N> {

	private N destination;
	private String namn;
	private int tid;
	private Kant<N> bakKant;

	public Kant(N destination, String namn, int tid) {
		if (tid < 0) {
			throw new IllegalArgumentException("Negativ tid!");
		}
		this.destination = destination;
		this.namn = namn;
		this.tid = tid;
	}
	
	public N getDest() {
		return destination;
	}
	public String getNamn() {
		return namn;
	}
	public void setNamn(){
		this.namn = namn;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
        if(tid < 0)
            throw new IllegalArgumentException("Tiden kan inte vara negativ!");
             
        this.tid = tid;
    }
	public void setBakKant(Kant<N> bakKant) {
        this.bakKant = bakKant;
    }
     
    public Kant<N> getEdgeBack() {
        return bakKant;
    }

	public String toString() {
		return "Till " + destination + " " + "med " + namn + " " + "tar " + tid + " timmar";
	}
}