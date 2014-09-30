package Grafer;

import java.util.*;

public class GrafLista<N> implements GrafInterface<N> {
	private Map<N, List<Kant<N>>> noder = new HashMap<N, List<Kant<N>>>();

	public void add(N newNown) {
		if (!noder.containsKey(newNown)) {
			noder.put(newNown, new ArrayList<Kant<N>>()); 	// Lägg till staden med en lista med kanter
		}
	}

	public void connect(N from, N to, String namn, int tid) {	// Skapa koppling mellan två städer
		List<Kant<N>> tillLista = noder.get(to);
		List<Kant<N>> frånLista = noder.get(from);
		if (frånLista == null || tillLista == null)
			throw new NoSuchElementException("Minst en av städerna är inte valda!");
		if (tid < 0)
			throw new IllegalArgumentException("Tiden kan inte vara negativ!");
		for (Kant<N> e : tillLista) {
			if (e.getNamn().equals(namn))
				throw new IllegalStateException("En förbindelse med samma namn finns redan!");
			}
		Kant<N> eFrån = new Kant<N>(to, namn, tid);
		Kant<N> eTill = new Kant<N>(from, namn, tid);
		eFrån.setBakKant(eTill);
		eTill.setBakKant(eFrån);
		frånLista.add(eFrån);
		tillLista.add(eTill);

		System.out.println(from + " " + eFrån.toString());
		System.out.println(to + " " + eTill.toString());
	}

	public void setConnectionWeight(N from, N to, String namn, int tid) {
		List<Kant<N>> tillLista = noder.get(to);
		List<Kant<N>> frånLista = noder.get(from);
		Kant<N> tillKant = null;
		Kant<N> frånKant = null;
		if (frånLista == null || tillLista == null)			// Kolla om det finns en förbindelse namnen finns
			throw new NoSuchElementException("Minst en av städerna finns inte!");
		boolean hittatTillKant = false;
		for (Kant<N> k : tillLista) {
			if (k.getNamn().equals(namn)) {
				tillKant = k;
				hittatTillKant = true;
				break;
			}
		}
		boolean hittatFrånKant = false;
		for (Kant<N> k : frånLista) {
			if (k.getNamn().equals(namn)) {
				frånKant = k;
				hittatFrånKant = true;
				break;
			}
		}
		if (!hittatTillKant || !hittatFrånKant)
			throw new NoSuchElementException("The connection does not exist");
		if (tid < 0)
			throw new IllegalArgumentException("Weight can not be negative");
		tillKant.setTid(tid);
		frånKant.setTid(tid);
	}

	public Map<N, List<Kant<N>>> getNodes() {		// Returnerar en kopia med alla noder
		return new HashMap<N, List<Kant<N>>>(noder);
	}

	public List<Kant<N>> getEdgesFrom(N nod) {		// Returnera städernas kopplingar
		if (noder.get(nod) == null)
			throw new NoSuchElementException("Staden finns inte!");
		return new ArrayList<Kant<N>>(noder.get(nod));
	}

	public List<Kant<N>> getEdgesBetween(N from, N to) {		// Hitta förbindelser mellan två städer
		List<Kant<N>> edgesBetween = new ArrayList<Kant<N>>();
		if (noder.get(from) == null || noder.get(to) == null)
			throw new NoSuchElementException("Minst en av städerna finns inte!");	
		List<Kant<N>> frånKanter = noder.get(from);
		for (Kant<N> k : frånKanter) {
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