import javax.swing.*;
import java.awt.*;

public class Bilder extends JPanel{
	private ImageIcon bild;
	
	Bilder(String filnamn){
		setLayout(null);
		bild = new ImageIcon(filnamn);
		setPreferredSize(new Dimension(bild.getIconWidth(), bild.getIconHeight()));		//Viktigt med ordningen på bred och höjd
		setMaximumSize(new Dimension(bild.getIconWidth(), bild.getIconHeight()));
		setMinimumSize(new Dimension(bild.getIconWidth(), bild.getIconHeight()));
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0, this);
	}

}

