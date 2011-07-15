package datastructures.graph;

import java.util.Map;
import java.util.Set;

import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;
import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Graph<V, E> extends OrderedSparseMultigraph<V, E> implements
	MultiGraph<V, E> {

    private MinimalTheorieSet theories;

    public Graph(MinimalTheorieSet theories) {
	super();
	this.theories = theories;
	addMTSet();
    }

    public void addMTSet() {
	for (MinimalTheorie theorie : theories) {
	    Node effect = new Node(theorie.getEffect(), true);
	    for (String factor : theorie.getFactors()) {
		Node node = new Node(factor, false);
		addVertex((V) node);
		addEdge((E) new Edge(node, effect), (V) node, (V) effect,
			EdgeType.DIRECTED);
	    }
	}
    }

    public Map<V, Pair<Set<E>>> getVerticesMap() {
	return vertices;
    }

    public Map<E, Pair<V>> getEdgesMap() {
	return edges;
    }

}
