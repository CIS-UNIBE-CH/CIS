package datastructures;

import java.util.Map;
import java.util.Set;

import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;

public class CustomOrderedSparseMultigraph<V, E> extends
	OrderedSparseMultigraph<V, E> implements MultiGraph<V,E> {

    public CustomOrderedSparseMultigraph() {
	super();
    }

    public Map<V, Pair<Set<E>>> getVerticesMap() {
	return vertices;
    }
    public Map<E, Pair<V>> getEdgesMap() {
	return edges;
    }
}
