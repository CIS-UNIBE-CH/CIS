package parsers;

import datastructures.graph.Edge;
import datastructures.graph.Graph;
import datastructures.graph.Node;
import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;

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
	    for (String factor : theorie.getFactors()) {
		Node node = new Node(factor, false);
		if (!graph.containsVertex(node)) {
		    graph.addVertex(node);

		}

	    }

	}
    }

    // Getters and Setters

    public Graph<Node, Edge> getGraph() {
	return graph;
    }

}
