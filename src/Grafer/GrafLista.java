package Grafer;

import java.util.*;

public class GrafLista<N> implements GrafInterface<N> {
	private Map<N, List<Kant<N>>> noder = new HashMap<N, List<Kant<N>>>();

	public void add(N newNown) {
		if (!noder.containsKey(newNown)) {
			noder.put(newNown, new ArrayList<Kant<N>>()); 	// L�gg till staden med en lista med kanter
		}
	}

	public void connect(N from, N to, String namn, int tid) {	// Skapa koppling mellan tv� st�der
		List<Kant<N>> tillLista = noder.get(to);
		List<Kant<N>> fr�nLista = noder.get(from);
		if (fr�nLista == null || tillLista == null)
			throw new NoSuchElementException("Minst en av st�derna �r inte valda!");
		if (tid < 0)
			throw new IllegalArgumentException("Tiden kan inte vara negativ!");
		for (Kant<N> e : tillLista) {
			if (e.getNamn().equals(namn))
				throw new IllegalStateException("En f�rbindelse med samma namn finns redan!");
			}
		Kant<N> eFr�n = new Kant<N>(to, namn, tid);
		Kant<N> eTill = new Kant<N>(from, namn, tid);
		eFr�n.setBakKant(eTill);
		eTill.setBakKant(eFr�n);
		fr�nLista.add(eFr�n);
		tillLista.add(eTill);

		System.out.println(from + " " + eFr�n.toString());
		System.out.println(to + " " + eTill.toString());
	}

	public void setConnectionWeight(N from, N to, String namn, int tid) {
		List<Kant<N>> tillLista = noder.get(to);
		List<Kant<N>> fr�nLista = noder.get(from);
		Kant<N> tillKant = null;
		Kant<N> fr�nKant = null;
		if (fr�nLista == null || tillLista == null)			// Kolla om det finns en f�rbindelse namnen finns
			throw new NoSuchElementException("Minst en av st�derna finns inte!");
		boolean hittatTillKant = false;
		for (Kant<N> k : tillLista) {
			if (k.getNamn().equals(namn)) {
				tillKant = k;
				hittatTillKant = true;
				break;
			}
		}
		boolean hittatFr�nKant = false;
		for (Kant<N> k : fr�nLista) {
			if (k.getNamn().equals(namn)) {
				fr�nKant = k;
				hittatFr�nKant = true;
				break;
			}
		}
		if (!hittatTillKant || !hittatFr�nKant)
			throw new NoSuchElementException("The connection does not exist");
		if (tid < 0)
			throw new IllegalArgumentException("Weight can not be negative");
		tillKant.setTid(tid);
		fr�nKant.setTid(tid);
	}

	public Map<N, List<Kant<N>>> getNodes() {		// Returnerar en kopia med alla noder
		return new HashMap<N, List<Kant<N>>>(noder);
	}

	public List<Kant<N>> getEdgesFrom(N nod) {		// Returnera st�dernas kopplingar
		if (noder.get(nod) == null)
			throw new NoSuchElementException("Staden finns inte!");
		return new ArrayList<Kant<N>>(noder.get(nod));
	}

	public List<Kant<N>> getEdgesBetween(N from, N to) {		// Hitta f�rbindelser mellan tv� st�der
		List<Kant<N>> edgesBetween = new ArrayList<Kant<N>>();
		if (noder.get(from) == null || noder.get(to) == null)
			throw new NoSuchElementException("Minst en av st�derna finns inte!");	
		List<Kant<N>> fr�nKanter = noder.get(from);
		for (Kant<N> k : fr�nKanter) {
			if (k.getDest() == to)
				edgesBetween.add(k);
		}
		return edgesBetween;
	}

	public String toString() {
		String str = "";
		for (Map.Entry<N, List<Kant<N>>> me : noder.entrySet()) {
			str += me.getKey() + ": ";
			for (Kant k : me.getValue())
				str += k.toString() + " ";
			str += "\n";
		}
		return str;
	}
}