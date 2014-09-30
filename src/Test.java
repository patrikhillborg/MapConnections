import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.*;
import static javax.swing.JOptionPane.*;
import java.util.*;

import Grafer.Dijkstra;
import Grafer.GrafInterface;
import Grafer.GrafLista;
import Grafer.Grafmetoder;
import Grafer.Kant;

public class Test extends JFrame{
	JButton hittaväg, visaförb, nyplats, nyförb, ändraförb;  
	Bilder bild = null;
	JPanel syd;
	Nod frånNod, tillNod;
	MouseLyss ml = new MouseLyss();
	GrafInterface<Nod> graf;
	JFileChooser jfc;
	//JFileChooser jfc = new JFileChooser(System.getProperty("./P2Bilder"));
	
	Test(){
		super("Kartsök");
			jfc = new JFileChooser("C:/Users/Patri/Documents/P2Bilder");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("P2Bilder", "jpg", "gif", "png");
			jfc.addChoosableFileFilter(filter);
			
			
			JMenuBar meny = new JMenuBar();
			setJMenuBar(meny);
			JMenu arkiv = new JMenu("Arkiv");
			meny.add(arkiv);
			JMenuItem nytt = new JMenuItem("Ny");
			nytt.addActionListener(new NyLyss());
			arkiv.add(nytt);
			JMenuItem avsluta = new JMenuItem("Avsluta");
			avsluta.addActionListener(new AvslutaLyss());
			arkiv.add(avsluta);
			
			JMenu operation = new JMenu("Operationer");
			meny.add(operation);
			JMenuItem hittav = new JMenuItem("Hitta väg");
			hittav.addActionListener(new HittaVag());
			operation.add(hittav);
			JMenuItem visaf = new JMenuItem("Visa förbindelse");
			visaf.addActionListener(new VisafLyss());
			operation.add(visaf);
			JMenuItem nyp = new JMenuItem("Ny plats");
			nyp.addActionListener(new NyPlatsLyss());
			operation.add(nyp);
			JMenuItem nyf = new JMenuItem("Ny förbindelse");
			nyf.addActionListener(new NyfLyss());
			operation.add(nyf);
			JMenuItem ändraf = new JMenuItem("Ändra förbindelse");
			ändraf.addActionListener(new AndrafLyss());
			operation.add(ändraf);
			
			JPanel norr = new JPanel();
			add(norr, BorderLayout.NORTH);
			
			hittaväg = new JButton("Hitta väg");
			hittaväg.addActionListener(new HittaVag());
			norr.add(hittaväg);
			
			visaförb = new JButton("Visa förbindelse");
			visaförb.addActionListener(new VisafLyss());
			norr.add(visaförb);
			
			nyplats = new JButton("Ny plats");
			nyplats.addActionListener(new NyPlatsLyss());
			norr.add(nyplats);
			
			nyförb = new JButton("Ny förbindelse");
			nyförb.addActionListener(new NyfLyss());
			norr.add(nyförb);
			
			ändraförb = new JButton("Ändra förbindelse");
			ändraförb.addActionListener(new AndrafLyss());
			norr.add(ändraförb);
			
			syd = new JPanel();
			add(syd, BorderLayout.CENTER);
			
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			pack();
			setVisible(true);
	}
	
	class NyLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			if(syd == null)
				return;
			int svar = jfc.showOpenDialog(Test.this);
			if(svar != JFileChooser.APPROVE_OPTION)	
				return;
			File fil = jfc.getSelectedFile();
			String filnamn = (String)fil.getAbsolutePath();
			if(bild != null)
				syd.remove(bild);
			bild = new Bilder(filnamn);
			syd.add(bild, BorderLayout.CENTER);
			graf = new GrafLista<Nod>();
			validate();
			pack();
			repaint();
		}
	}
	
	private boolean tvåStäderValda() {		 
        if(frånNod != null && tillNod != null) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Två städer behöver vara valda!", "Error",
                            JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
	
	class AvslutaLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			System.exit(0);
		}
	}

	class NyStad extends JPanel{
		JTextField stadnamn;
		
		NyStad(int x, int y){
			JPanel stadPanel = new JPanel();
			stadPanel.add(new JLabel("Stadens namn:"));
			setLayout(new FlowLayout());
			stadnamn = new JTextField(10);
			stadPanel.add(stadnamn);
			add(stadPanel);
		}
		
		public String getNamn(){
			return stadnamn.getText();
		}
	}
	
	class NyForbindelse extends JPanel{
		private JTextField förbnamn, förbtid;
		public NyForbindelse(Nod to, Nod from){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
	        JPanel upp = new JPanel(); 
	        upp.add(new JLabel("Förbindelse från " + to.getNodnamn() + " till " + from.getNodnamn())); 
	        add(upp); 
	      
	        JPanel mitten = new JPanel(); 
	        förbnamn = new JTextField(10); 
	        mitten.add(new JLabel("Namn: ")); 
	        mitten.add(förbnamn); 
	        add(mitten); 
	      
	        JPanel ner = new JPanel(); 
	        förbtid = new JTextField(5); 
	        ner.add(new JLabel("Tid:")); 
	        ner.add(förbtid); 
	        add(ner);
		}
		public String getFörbnamn(){ 
	        return förbnamn.getText(); 
	    } 	      
	    public void setFörbnamn(String namn){ 
	        förbnamn.setText(namn); 
	    } 	      
	    public int getFörbtid(){ 
	        return Integer.parseInt(förbtid.getText()); 
	    } 	      
	    public void setFörbtid(int tid){
	        förbtid.setText(String.valueOf(tid));
	    }
	}
	
	public class Andrafruta extends JPanel{
		private JTextField förbnamn, förbtid;
		 public Andrafruta(Nod from, Nod to, String nodnamn, int tid){
			 setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			 JPanel upp = new JPanel();
			 upp.add(new JLabel("Från " + from.getNodnamn() + " till " + to.getNodnamn()));
			 add(upp);
			 JPanel mitten = new JPanel();
			 förbnamn = new JTextField(10);
			 förbnamn.setText(nodnamn);
			 förbnamn.setEditable(false);
			 mitten.add(new JLabel("Namn: "));
			 mitten.add(förbnamn);
			 add(mitten);
			 JPanel ner = new JPanel();
			 förbtid = new JTextField(5);
			 förbtid.setText(String.valueOf(tid));
			 ner.add(new JLabel("Tid: "));
			 ner.add(förbtid);
			 add(ner);
		 }
		 public String getNodnamn(){
			 return förbnamn.getText();
		 }
		 public int getTid(){
			 return Integer.parseInt(förbtid.getText());
		 }
	}
	
	private class MouseLyss extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			if(syd == null)
				return;
			int x = mev.getX();
			int y = mev.getY();
			// System.out.println(mev.getX() + ", " + mev.getY());
			NyStad nystad = new NyStad(x, y);
			for (;;) {
				int svar = JOptionPane.showConfirmDialog(Test.this, nystad,
						null, JOptionPane.OK_CANCEL_OPTION);
				if (svar != OK_OPTION)
					return;
				String stadnamn = nystad.getNamn();
				Stad s = new Stad(stadnamn, x, y);
				graf.add(s.getNod());
				bild.add(s);
				s.addMouseListener(new StadLyss());
				validate();
				repaint();
				bild.removeMouseListener(ml);
				bild.setCursor(Cursor.getDefaultCursor());
				return;
			}
		}
	}

	private class StadLyss extends MouseAdapter {
		public void mouseClicked(MouseEvent mev){
			Stad st = (Stad) mev.getSource();
            Nod nod = st.getNod();
            if (frånNod == null && tillNod == null) {
                frånNod = nod;
                st.tryckt();
                System.out.println("From: " + frånNod.getNodnamn());
            } else
            if (frånNod == nod) {
                frånNod = null;
                st.tryckt();
            } else
            if (frånNod != nod && tillNod == null) {
                tillNod = nod;
                st.tryckt();
                System.out.println("To: " +tillNod.getNodnamn());
            } else
            if (tillNod == nod) {
                tillNod = null;
                st.tryckt();
            }
		}
	}

	private class NyPlatsLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			try{
			if(syd != null){
			bild.addMouseListener(ml);
			bild.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			}
			} catch(NullPointerException e){JOptionPane.showMessageDialog(null, "Du måste ta fram en karta först!", "Error",
                    JOptionPane.ERROR_MESSAGE); //Om det inte finns någon karta framme
			}
		}
	}
	
	private class NyfLyss implements ActionListener {
        public void actionPerformed(ActionEvent ave) {
            if (syd == null) 
                return;
            // Kontrollera att två noder är valda 
            if (tvåStäderValda()) {                 
                NyForbindelse förb = new NyForbindelse(frånNod, tillNod); 
            for (;;) {
                try {
                    int svar = JOptionPane.showConfirmDialog(null, förb, "Ny förbindelse", JOptionPane.OK_CANCEL_OPTION); 
                    if (svar != OK_OPTION) { 
                       return;
                  } else if ((förb.getFörbnamn().trim()).equals("")) {
                       JOptionPane.showMessageDialog(null, "Förbindelsenamn behövs!", "Skriv in namn!", JOptionPane.ERROR_MESSAGE);
                  } else { 
                	  graf.connect(frånNod, tillNod, förb.getFörbnamn(), förb.getFörbtid());
                      return;
                       }
                  } 
                  	catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Tiden måste vara ett heltal", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } 
                    catch (IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null, "Tiden kan inte vara negativ!", "Error",
                                JOptionPane.ERROR_MESSAGE);                     
                    }
                }
 
            }
        }       
    }
	
	private class VisafLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			if(tvåStäderValda()){
				if(Grafmetoder.pathExists(graf, frånNod, tillNod)){
					VisaForbindelse vf = new VisaForbindelse(graf.getEdgesBetween(frånNod, tillNod), frånNod, tillNod);
					JOptionPane.showMessageDialog(null, vf, "Visa förbindelser", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(getContentPane(), "Det finns ingen förbindelse mellan " + frånNod + " och " + tillNod, "Error",
							JOptionPane.ERROR_MESSAGE);
					}
				}
			}	
		}
	
	private class AndrafLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			Kant<Nod> nyaKanter;
			if(syd == null)
				return;
			if(!tvåStäderValda())
				return;
			if(graf.getEdgesBetween(frånNod, tillNod).size() == 0){
				JOptionPane.showMessageDialog(getContentPane(), "Det finns ingen förbindelse mellan dessa städer!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(graf.getEdgesBetween(frånNod, tillNod).size() > 1){
				AndraForbindelse förb = new AndraForbindelse(graf.getEdgesBetween(frånNod, tillNod));
				int svar = JOptionPane.showConfirmDialog(null, förb, "Ändra förbindelse!", JOptionPane.OK_CANCEL_OPTION);
				if(svar != OK_OPTION)
					return;
				nyaKanter = förb.getNod();
				if(nyaKanter == null)
					return;
			}else{
				nyaKanter = graf.getEdgesBetween(frånNod, tillNod).get(0);
			}
			ändraFörbindelsemetod(nyaKanter);
		}
	}
	
	public void ändraFörbindelsemetod(Kant<Nod> nuvarande){
		Andrafruta af = new Andrafruta(frånNod, tillNod, nuvarande.getNamn(), nuvarande.getTid());
		for(;;){
			try{
				int svar = JOptionPane.showConfirmDialog(null, af, "Vill du ändra förbindelse?", JOptionPane.OK_CANCEL_OPTION);
				if(svar != OK_OPTION)
					return;
				graf.setConnectionWeight(frånNod, tillNod, af.getNodnamn(), af.getTid());
				return;
			}catch (NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Tiden måste vara ett heltal", "Error", JOptionPane.ERROR_MESSAGE);
			}catch (IllegalArgumentException i){
				JOptionPane.showMessageDialog(null, "Tiden får inte vara negativt!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class HittaVag implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			if(syd == null)
				return;
			try{
			if(tvåStäderValda()){
				ArrayList<Kant<Nod>> kortastVäg = new ArrayList<Kant<Nod>>(Grafmetoder.shortestPath(graf, frånNod, tillNod));
				if(kortastVäg.size() == 0){
					JOptionPane.showMessageDialog(getContentPane(), "Det finns ingen förbindelse mellan " + frånNod.getNodnamn()
							+ " till " + tillNod.getNodnamn(), "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					SnabbastForbindelse sf = new SnabbastForbindelse(kortastVäg, frånNod, tillNod);
					JOptionPane.showMessageDialog(null, sf, "Snabbaste vägen", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
			}catch (NullPointerException e){
				JOptionPane.showMessageDialog(null, "Du måste skapa en förbindelse först!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void main(String[] args) {
		new Test();
	}
}