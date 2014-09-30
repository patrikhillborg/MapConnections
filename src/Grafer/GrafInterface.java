package Grafer;

import java.util.List;
import java.util.Map;

public interface GrafInterface <N> {
	
    public void add(N nyStad);
    public void connect(N from, N to, String namn, int tid);
    public void setConnectionWeight(N from, N to, String namn, int tid);
    public Map<N, List<Kant<N>>> getNodes();
    public List<Kant<N>> getEdgesFrom(N nod);
    public List<Kant<N>> getEdgesBetween(N from, N to);
}
