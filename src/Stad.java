import javax.swing.*;
import java.awt.*;

public class Stad extends JComponent {
	private boolean tryckt = false;
	private String stadNamn;
	private Nod n;

	public Stad(String stadNamn, int x, int y) {
		this.stadNamn = stadNamn;
		n = new Nod(stadNamn);
		setBounds(x, y, 100, 100);
		setPreferredSize(new Dimension(50, 50));
	}
	
	protected void paintComponent(Graphics g){  
        super.paintComponent(g); 
        if (tryckt) 
            g.setColor(Color.RED); 
        else
            g.setColor(Color.BLUE); 
        	g.fillOval(0, 0, 15, 15); 
        	g.drawString(stadNamn, 15, 15); 
        } 

	public Nod getNod() {
		return n;
	}

	public String toString() {
		return stadNamn;
	}
	
	public void tryckt(){ 
        tryckt =! tryckt; 
        repaint(); 
    } 
}