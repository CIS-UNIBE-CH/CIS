package datastructures.graph;

import java.util.Map;
import java.util.Set;

import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Pair;

public class Graph<Node, Edge> extends SparseMultigraph<Node, Edge> implements
	MultiGraph<Node, Edge> {

    public Graph() {
	super();
    }

    // @Override
    // public boolean addVertex(Node vertex) {
    // if (vertex == null) {
    // throw new IllegalArgumentException("vertex may not be null");
    // }
    // if (!vertices.keySet().contains(vertex)) {
    // vertices.put(vertex, new Pair<Set<Edge>>(new HashSet<Edge>(),
    // new HashSet<Edge>()));
    // return true;
    // } else {
    // return false;
    // }
    //
    // }

    @Override
    public boolean containsVertex(Node vertex) {
	String test = this.getVerticesMap().keySet().toString();
	if(test.contains(vertex.toString())){
	    return true;
	}else{
	    return false;
	}
    }

    public Node getNode(Node node) {
	Object[] array = this.getVerticesMap().keySet().toArray();
	for(int i = 0; i < array.length; i++){
	    if(array[i].toString().equals(node.toString())){
		return (Node) array[i];
	    }
	}
	return null;
	
    }

    public Map<Node, Pair<Set<Edge>>> getVerticesMap() {
	return vertices;
    }

    public Map<Edge, Pair<Node>> getEdgesMap() {
	return edges;
    }

}
