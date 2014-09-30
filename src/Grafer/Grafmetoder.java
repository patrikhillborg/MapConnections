package Grafer;

import java.util.*;

public class Grafmetoder<N> {
	private Map<N, List<Kant>> noder = new HashMap<N, List<Kant>>();	//Skapa en map med nyckeln Stad och v�rdet Lista av st�der
	
	public static <N> boolean pathExists(GrafInterface<N> graf, N from, N to) {
        Set<N> bes�kta = new HashSet<N>();
        searchGraph(graf, bes�kta, from);        
        return bes�kta.contains(to);
    }
	
    public static <N> void searchGraph(GrafInterface<N> graf, Set<N> bes�kta, N var) {
        bes�kta.add(var);        
        for(Kant<N> k : graf.getEdgesFrom(var)) {
            N to = k.getDest();           
            if(!bes�kta.contains(to))
                searchGraph(graf, bes�kta, to);
        }
    }
     
    public static <N> List<Kant<N>> shortestPath(GrafInterface<N> graf, N from, N to) {     //Returnera lista med den snabbaste v�gen mellan tv� noder   
        if(!pathExists(graf, from, to))
            return null;
        Map<N, Dijkstra<N>> dijk = new HashMap<N, Dijkstra<N>>();        
        for(N nod : graf.getNodes().keySet()) {
            dijk.put(nod, new Dijkstra<N>());
            if(nod == from)
                dijk.get(nod).setSnabbast(0);
        }        
        Map<N, Dijkstra<N>> dijkMap = shortestPathInternal(graf, to, dijk);        
        List<Kant<N>> kortastV�g = new ArrayList<Kant<N>>();        
        return dijkSorteraV�g(graf, kortastV�g, dijkMap, to);
    }
     
    private static <N> Map<N, Dijkstra<N>> shortestPathInternal(GrafInterface<N> graf, N to, Map<N, Dijkstra<N>> dijkObject) {
        if(dijkObject.get(to).getKlar())
            return dijkObject;
             
        for(Map.Entry<N, Dijkstra<N>> entry : dijkObject.entrySet()) {          
            if(entry.getValue().getSnabbast() == dijkGetSnabbast(dijkObject) && entry.getValue().getKlar() == false) {
               entry.getValue().setKlar(true);               
               for(Kant<N> k : graf.getEdgesFrom(entry.getKey())) {
                   if(k.getTid() + entry.getValue().getSnabbast() < dijkObject.get(k.getDest()).getSnabbast()) {
                       dijkObject.get(k.getDest()).setSnabbast(k.getTid() + entry.getValue().getSnabbast());
                       dijkObject.get(k.getDest()).setFrom(entry.getKey());
                    }
                }
            }
        }         
        return shortestPathInternal(graf, to, dijkObject);
    }
     
    private static <N> int dijkGetSnabbast(Map<N, Dijkstra<N>> dijkObject) {
        int snabbast = Integer.MAX_VALUE;        
        for(Dijkstra<N> dijk : dijkObject.values()) {
            if(dijk.getKlar() == false && dijk.getSnabbast() < snabbast)
                snabbast = dijk.getSnabbast();
        }         
        return snabbast;
    }
     
    private static <N> List<Kant<N>> dijkSorteraV�g(GrafInterface<N> graf, List<Kant<N>> kortastV�g, Map<N, Dijkstra<N>> dijkObject, N nod) {
        Kant<N> h�llKant = null;         
        if(dijkObject.get(nod).getFrom() == null)
            return kortastV�g;        
        dijkSorteraV�g(graf, kortastV�g, dijkObject, dijkObject.get(nod).getFrom());        
        for(Kant<N> k : graf.getEdgesBetween(dijkObject.get(nod).getFrom(), nod)) {
            if(h�llKant == null)
                h�llKant = k;
            else if(k.getTid() < h�llKant.getTid())
                h�llKant = k;
        }         
        kortastV�g.add(h�llKant);        
        return kortastV�g;
    }
}