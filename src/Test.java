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
	JButton hittav�g, visaf�rb, nyplats, nyf�rb, �ndraf�rb;  
	Bilder bild = null;
	JPanel syd;
	Nod fr�nNod, tillNod;
	MouseLyss ml = new MouseLyss();
	GrafInterface<Nod> graf;
	JFileChooser jfc;
	//JFileChooser jfc = new JFileChooser(System.getProperty("./P2Bilder"));
	
	Test(){
		super("Karts�k");
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
			JMenuItem hittav = new JMenuItem("Hitta v�g");
			hittav.addActionListener(new HittaVag());
			operation.add(hittav);
			JMenuItem visaf = new JMenuItem("Visa f�rbindelse");
			visaf.addActionListener(new VisafLyss());
			operation.add(visaf);
			JMenuItem nyp = new JMenuItem("Ny plats");
			nyp.addActionListener(new NyPlatsLyss());
			operation.add(nyp);
			JMenuItem nyf = new JMenuItem("Ny f�rbindelse");
			nyf.addActionListener(new NyfLyss());
			operation.add(nyf);
			JMenuItem �ndraf = new JMenuItem("�ndra f�rbindelse");
			�ndraf.addActionListener(new AndrafLyss());
			operation.add(�ndraf);
			
			JPanel norr = new JPanel();
			add(norr, BorderLayout.NORTH);
			
			hittav�g = new JButton("Hitta v�g");
			hittav�g.addActionListener(new HittaVag());
			norr.add(hittav�g);
			
			visaf�rb = new JButton("Visa f�rbindelse");
			visaf�rb.addActionListener(new VisafLyss());
			norr.add(visaf�rb);
			
			nyplats = new JButton("Ny plats");
			nyplats.addActionListener(new NyPlatsLyss());
			norr.add(nyplats);
			
			nyf�rb = new JButton("Ny f�rbindelse");
			nyf�rb.addActionListener(new NyfLyss());
			norr.add(nyf�rb);
			
			�ndraf�rb = new JButton("�ndra f�rbindelse");
			�ndraf�rb.addActionListener(new AndrafLyss());
			norr.add(�ndraf�rb);
			
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
	
	private boolean tv�St�derValda() {		 
        if(fr�nNod != null && tillNod != null) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Tv� st�der beh�ver vara valda!", "Error",
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
		private JTextField f�rbnamn, f�rbtid;
		public NyForbindelse(Nod to, Nod from){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
	        JPanel upp = new JPanel(); 
	        upp.add(new JLabel("F�rbindelse fr�n " + to.getNodnamn() + " till " + from.getNodnamn())); 
	        add(upp); 
	      
	        JPanel mitten = new JPanel(); 
	        f�rbnamn = new JTextField(10); 
	        mitten.add(new JLabel("Namn: ")); 
	        mitten.add(f�rbnamn); 
	        add(mitten); 
	      
	        JPanel ner = new JPanel(); 
	        f�rbtid = new JTextField(5); 
	        ner.add(new JLabel("Tid:")); 
	        ner.add(f�rbtid); 
	        add(ner);
		}
		public String getF�rbnamn(){ 
	        return f�rbnamn.getText(); 
	    } 	      
	    public void setF�rbnamn(String namn){ 
	        f�rbnamn.setText(namn); 
	    } 	      
	    public int getF�rbtid(){ 
	        return Integer.parseInt(f�rbtid.getText()); 
	    } 	      
	    public void setF�rbtid(int tid){
	        f�rbtid.setText(String.valueOf(tid));
	    }
	}
	
	public class Andrafruta extends JPanel{
		private JTextField f�rbnamn, f�rbtid;
		 public Andrafruta(Nod from, Nod to, String nodnamn, int tid){
			 setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			 JPanel upp = new JPanel();
			 upp.add(new JLabel("Fr�n " + from.getNodnamn() + " till " + to.getNodnamn()));
			 add(upp);
			 JPanel mitten = new JPanel();
			 f�rbnamn = new JTextField(10);
			 f�rbnamn.setText(nodnamn);
			 f�rbnamn.setEditable(false);
			 mitten.add(new JLabel("Namn: "));
			 mitten.add(f�rbnamn);
			 add(mitten);
			 JPanel ner = new JPanel();
			 f�rbtid = new JTextField(5);
			 f�rbtid.setText(String.valueOf(tid));
			 ner.add(new JLabel("Tid: "));
			 ner.add(f�rbtid);
			 add(ner);
		 }
		 public String getNodnamn(){
			 return f�rbnamn.getText();
		 }
		 public int getTid(){
			 return Integer.parseInt(f�rbtid.getText());
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
            if (fr�nNod == null && tillNod == null) {
                fr�nNod = nod;
                st.tryckt();
                System.out.println("From: " + fr�nNod.getNodnamn());
            } else
            if (fr�nNod == nod) {
                fr�nNod = null;
                st.tryckt();
            } else
            if (fr�nNod != nod && tillNod == null) {
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
			} catch(NullPointerException e){JOptionPane.showMessageDialog(null, "Du m�ste ta fram en karta f�rst!", "Error",
                    JOptionPane.ERROR_MESSAGE); //Om det inte finns n�gon karta framme
			}
		}
	}
	
	private class NyfLyss implements ActionListener {
        public void actionPerformed(ActionEvent ave) {
            if (syd == null) 
                return;
            // Kontrollera att tv� noder �r valda 
            if (tv�St�derValda()) {                 
                NyForbindelse f�rb = new NyForbindelse(fr�nNod, tillNod); 
            for (;;) {
                try {
                    int svar = JOptionPane.showConfirmDialog(null, f�rb, "Ny f�rbindelse", JOptionPane.OK_CANCEL_OPTION); 
                    if (svar != OK_OPTION) { 
                       return;
                  } else if ((f�rb.getF�rbnamn().trim()).equals("")) {
                       JOptionPane.showMessageDialog(null, "F�rbindelsenamn beh�vs!", "Skriv in namn!", JOptionPane.ERROR_MESSAGE);
                  } else { 
                	  graf.connect(fr�nNod, tillNod, f�rb.getF�rbnamn(), f�rb.getF�rbtid());
                      return;
                       }
                  } 
                  	catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Tiden m�ste vara ett heltal", "Error",
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
			if(tv�St�derValda()){
				if(Grafmetoder.pathExists(graf, fr�nNod, tillNod)){
					VisaForbindelse vf = new VisaForbindelse(graf.getEdgesBetween(fr�nNod, tillNod), fr�nNod, tillNod);
					JOptionPane.showMessageDialog(null, vf, "Visa f�rbindelser", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(getContentPane(), "Det finns ingen f�rbindelse mellan " + fr�nNod + " och " + tillNod, "Error",
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
			if(!tv�St�derValda())
				return;
			if(graf.getEdgesBetween(fr�nNod, tillNod).size() == 0){
				JOptionPane.showMessageDialog(getContentPane(), "Det finns ingen f�rbindelse mellan dessa st�der!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(graf.getEdgesBetween(fr�nNod, tillNod).size() > 1){
				AndraForbindelse f�rb = new AndraForbindelse(graf.getEdgesBetween(fr�nNod, tillNod));
				int svar = JOptionPane.showConfirmDialog(null, f�rb, "�ndra f�rbindelse!", JOptionPane.OK_CANCEL_OPTION);
				if(svar != OK_OPTION)
					return;
				nyaKanter = f�rb.getNod();
				if(nyaKanter == null)
					return;
			}else{
				nyaKanter = graf.getEdgesBetween(fr�nNod, tillNod).get(0);
			}
			�ndraF�rbindelsemetod(nyaKanter);
		}
	}
	
	public void �ndraF�rbindelsemetod(Kant<Nod> nuvarande){
		Andrafruta af = new Andrafruta(fr�nNod, tillNod, nuvarande.getNamn(), nuvarande.getTid());
		for(;;){
			try{
				int svar = JOptionPane.showConfirmDialog(null, af, "Vill du �ndra f�rbindelse?", JOptionPane.OK_CANCEL_OPTION);
				if(svar != OK_OPTION)
					return;
				graf.setConnectionWeight(fr�nNod, tillNod, af.getNodnamn(), af.getTid());
				return;
			}catch (NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Tiden m�ste vara ett heltal", "Error", JOptionPane.ERROR_MESSAGE);
			}catch (IllegalArgumentException i){
				JOptionPane.showMessageDialog(null, "Tiden f�r inte vara negativt!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class HittaVag implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			if(syd == null)
				return;
			try{
			if(tv�St�derValda()){
				ArrayList<Kant<Nod>> kortastV�g = new ArrayList<Kant<Nod>>(Grafmetoder.shortestPath(graf, fr�nNod, tillNod));
				if(kortastV�g.size() == 0){
					JOptionPane.showMessageDialog(getContentPane(), "Det finns ingen f�rbindelse mellan " + fr�nNod.getNodnamn()
							+ " till " + tillNod.getNodnamn(), "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					SnabbastForbindelse sf = new SnabbastForbindelse(kortastV�g, fr�nNod, tillNod);
					JOptionPane.showMessageDialog(null, sf, "Snabbaste v�gen", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
			}catch (NullPointerException e){
				JOptionPane.showMessageDialog(null, "Du m�ste skapa en f�rbindelse f�rst!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void main(String[] args) {
		new Test();
	}
}