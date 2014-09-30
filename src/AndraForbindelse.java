import javax.swing.*;
import java.util.List;
import Grafer.Kant;

public class AndraForbindelse extends JPanel{

	List<Kant<Nod>> förbindelser;
	private JComboBox box;
	
	AndraForbindelse(List<Kant<Nod>> nyaFörbindelser){
		förbindelser = nyaFörbindelser;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		box = new JComboBox(förbindelser.toArray());
		add(box);
	}
	public Kant<Nod> getNod(){
		Kant<Nod> k = (Kant<Nod>)box.getSelectedItem();
		return k;
	}
}
