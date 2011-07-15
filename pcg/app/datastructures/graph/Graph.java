package datastructures.graph;

import java.util.Map;
import java.util.Set;

import datastructures.cna.CNAList;
import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;
import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;

public class Graph<Node, Edge> extends OrderedSparseMultigraph<Node, Edge>
	implements MultiGraph<Node, Edge> {

    private MinimalTheorieSet theories;
    private Node node;

    public Graph(MinimalTheorieSet theories) {
	super();
	this.theories = theories;
	addMTSet();
    }

    public void addMTSet() {
	for (MinimalTheorie theorie : theories) {
	    CNAList factors = theorie.getFactors();
	    for (int i = 0; i < factors.size(); i++) {

	    }
	}

    }

    public Map<Node, Pair<Set<Edge>>> getVerticesMap() {
	return vertices;
    }

    public Map<Edge, Pair<Node>> getEdgesMap() {
	return edges;
    }

}
