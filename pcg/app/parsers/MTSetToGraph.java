package parsers;

import datastructures.graph.Edge;
import datastructures.graph.Graph;
import datastructures.graph.Node;
import datastructures.mt.MinimalTheorieSet;

public class MTSetToGraph {

    private MinimalTheorieSet theories;
    private Graph<Node, Edge> graph;

    public MTSetToGraph(MinimalTheorieSet theories) {
	this.theories = new MinimalTheorieSet();

	// System.out.println(graph.getEdgesMap());

	// System.out.println(GraphMatrixOperations.graphToSparseMatrix(graph));
    }

    // Getters and Setters

    public Graph<Node, Edge> getGraph() {
	return graph;
    }

}
