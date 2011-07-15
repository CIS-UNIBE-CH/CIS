package parsers;

import datastructures.graph.Edge;
import datastructures.graph.Graph;
import datastructures.graph.Node;
import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;
import edu.uci.ics.jung.graph.util.EdgeType;

public class MTSetToGraph {

    private MinimalTheorieSet theories;
    private Graph<Node, Edge> graph;

    public MTSetToGraph(MinimalTheorieSet theories) {
	System.out.println(theories);
	this.theories = new MinimalTheorieSet();
	graph = new Graph<Node, Edge>();
	addMTSet(theories);
    }

    private void addMTSet(MinimalTheorieSet theories) {
	for (MinimalTheorie theorie : theories) {
	    Node effect = new Node(theorie.getEffect(), true);
	    graph.addVertex(effect);
	    for (String factor : theorie.getFactors()) {
		Node node = new Node(factor, false);
		if (!graph.containsVertex(node)) {
		    graph.addVertex(node);
		}
		if(graph.getNode(node) != null){
		    graph.addEdge(new Edge(graph.getNode(node), effect), graph.getNode(node), effect, EdgeType.DIRECTED);
		    System.out.println("Node " + graph.getNode(node));
		}else{
		    graph.addEdge(new Edge(node, effect), node, effect, EdgeType.DIRECTED);
		    System.out.println("NULL");
		}
	    }

	}
    }

    // Getters and Setters

    public Graph<Node, Edge> getGraph() {
	return graph;
    }

}
