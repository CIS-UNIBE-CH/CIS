package datastructures.graph;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import datastructures.cna.CNAList;
import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;
import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;

public class Matrix extends OrderedSparseMultigraph<Node, Edge> implements
	MultiGraph<Node, Edge> {

    private MinimalTheorieSet theories;
    private int[][] matrix;
    private CNAList names;
    private int side;
    private Map<Node, Point2D> graph = new HashMap<Node, Point2D>();

    public Matrix(MinimalTheorieSet theories) {
	this.theories = theories;
	names = new CNAList();

	names.addAll(theories.getAllFactors());
	names.addAll(theories.getAllEffects());
	side = names.size();
	matrix = new int[side][side];
	fillUpMatrix();
	build(theories);
    }

    private void build(MinimalTheorieSet theories) {
	for (MinimalTheorie theorie : theories) {
	    for (String factor : theorie.getFactors()) {
		int posE = names.getIndex(theorie.getEffect());
		int posF = names.getIndex(factor);
		matrix[posF][posE] = 1;
	    }
	}
	addNodes();
	addEdges();
    }

    private void addEdges() {

    }

    // Helpers
    private void fillUpMatrix() {
	for (int i = 0; i < matrix.length; i++) {
	    for (int j = 0; j < matrix[i].length; j++) {
		matrix[i][j] = 0;
	    }
	}
    }

    // FL: noch keine Sortierung....
    private void addNodes() {
	for (String string : theories.getAllFactors()) {
	    this.addVertex(new Node(string, false));
	}
	for (String string : theories.getAllEffects()) {
	    this.addVertex(new Node(string, true));
	}
    }

    // toString
    public String toString() {
	String str = "  " + names.toString() + "\n";
	for (int i = 0; i < matrix.length; i++) {
	    str += names.get(i) + " ";
	    for (int j = 0; j < matrix[i].length; j++) {
		str += matrix[i][j] + " ";
	    }
	    str += "\n";
	}
	return str;
    }

    // Getters and Setters

    public Map<Node, Point2D> getGraph() {
	double x = 30.0;
	double y = 30.0;
	for (Node node : this.getVertices()) {
	    graph.put(node, new Point2D.Double(x, y));
	    x += 60;
	    y += 60;
	}
	return graph;
    }

    public Node getNode(String node) {
	for (Node n : this.getVertices()) {
	    if (node.equals(n.toString()))
		return n;
	}
	return null;
    }
}
