import javax.swing.*;
import java.util.List;
import Grafer.Kant;

public class SnabbastForbindelse extends JPanel{
	
	private JTextArea textruta;
	private int totaltid = 0;
	
	public SnabbastForbindelse(List<Kant<Nod>> kanter, Nod from, Nod to){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel pan = new JPanel();
		pan.add(new JLabel("Från " + from.getNodnamn() + " till " + to.getNodnamn()));
		add(pan);
		textruta = new JTextArea();
		for(Kant<Nod> k : kanter){
			textruta.append(k.toString() + "\n");
			totaltid += k.getTid();
		}
		textruta.append("Total tid: " + totaltid);
		add(new JScrollPane(textruta));
		add(pan);
	}
}
