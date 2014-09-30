import java.util.*; 
import javax.swing.*; 
  
import Grafer.Kant; 
  
public class VisaForbindelse extends JPanel{
	private JList lista;
	public VisaForbindelse(List<Kant<Nod>> f�rbindelser, Nod from, Nod to){
		JPanel hela = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel mitten = new JPanel();
		mitten.add(new JLabel("Fr�n " + from.getNodnamn() + " till " + to.getNodnamn()));
		add(mitten);
		JPanel ner = new JPanel();
		lista = new JList(f�rbindelser.toArray());
		ner.add(new JScrollPane(lista));
		add(ner);
	}
}
