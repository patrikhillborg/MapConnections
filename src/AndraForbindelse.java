import javax.swing.*;
import java.util.List;
import Grafer.Kant;

public class AndraForbindelse extends JPanel{

	List<Kant<Nod>> f�rbindelser;
	private JComboBox box;
	
	AndraForbindelse(List<Kant<Nod>> nyaF�rbindelser){
		f�rbindelser = nyaF�rbindelser;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		box = new JComboBox(f�rbindelser.toArray());
		add(box);
	}
	public Kant<Nod> getNod(){
		Kant<Nod> k = (Kant<Nod>)box.getSelectedItem();
		return k;
	}
}
