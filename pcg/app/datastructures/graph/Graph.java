package datastructures.graph;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;
import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Graph<V, E> extends OrderedSparseMultigraph<Node, Edge> implements
	MultiGraph<Node, Edge> {

    Map<Node, Point2D> graph = new HashMap<Node, Point2D>();
    MinimalTheorieSet theories;

    public Graph(MinimalTheorieSet theories) {
	super();
	this.theories = theories;
	addMTSet();
    }

    private void addMTSet() {
	for (MinimalTheorie theorie : theories) {
	    Node effect = new Node(theorie.getEffect(), true);
	    addVertex(effect);
	    for (String factor : theorie.getFactors()) {
		Node node = new Node(factor, false);
		if (!containsVertex(node)) {
		    this.addVertex(node);
		}
		addEdge(new Edge(getNode(node), effect), getNode(node), effect,
			EdgeType.DIRECTED);
	    }
	}
    }

    @Override
    public boolean containsVertex(Node vertex) {
	return (vertices.keySet().toString().contains(vertex.toString()));
    }

    public Node getNode(Node node) {
	for (Node n : this.getVertices()) {
	    if (node.equals(n))
		return n;
	}
	return node;
    }

    public Map<Node, Pair<Set<Edge>>> getVerticesMap() {
	return vertices;
    }

    public Map<Edge, Pair<Node>> getEdgesMap() {
	return edges;
    }

    public Map<Node, Point2D> getSortedGraph() {
	double x = 30.0;
	double y = 30.0;
	Node last = null;
	for (Node node : this.getVertices()) {
	    graph.put(node, new Point2D.Double(x, y));
	    x += 60;
	    y += 60;

	}
	return graph;
    }
}
