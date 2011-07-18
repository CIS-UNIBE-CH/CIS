package datastructures.graph;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import datastructures.cna.CNAList;
import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;
import edu.uci.ics.jung.graph.MultiGraph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class Matrix extends OrderedSparseMultigraph<Node, Edge> implements
	MultiGraph<Node, Edge> {

    private MinimalTheorieSet theories;
    private int[][] matrix;
    private CNAList names;
    private int side;
    private Map<Node, Point2D> graph = new HashMap<Node, Point2D>();
    private int deepest = 0;
    private int stackRuns = 1;

    public Matrix(MinimalTheorieSet theories) {
	this.theories = theories;
	names = theories.getAllNames();
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
	setLevels();
    }

    private void addEdges() {
	for (int i = 0; i < matrix.length; i++) {
	    for (int j = 0; j < matrix[i].length; j++) {
		if (matrix[i][j] == 1) {
		    Node source = getNode(names.get(i));
		    Node destination = getNode(names.get(j));
		    Edge edge = new Edge(source, destination);
		    addEdge(edge, source, destination, EdgeType.DIRECTED);
		}
	    }
	}

    }

    private void setLevels() {
	Stack<String> stack = new Stack<String>();
	Stack<Integer> deep = new Stack<Integer>();
	addMasterEffects(stack);
	for (int i = 0; i < stack.size(); i++) {
	    deep.push(0);
	}
	System.out.println(stack);
	while (stack.size() != 0) {
	    // System.out.println(stack);
	    // System.out.println(deep);
	    String effect = stack.pop();
	    // System.out.println(stack);
	    // System.out.println(deep);
	    // System.out.println(effect + " " + deep.peek());
	    getNode(effect).setLevel(deep.peek());
	    if (deepest < deep.peek()) {
		deepest = deep.peek();
	    }
	    deep.pop();
	    if (hasFactor(effect)) {
		addFactors(effect, stack, deep);
	    }
	    for (int i = deep.size(); i > stack.size() + 1; i--) {
		// System.out.println(stack);
		// System.out.println(deep);
		deep.pop();
		// System.out.println(stack);
		// System.out.println(" == " + deep);
	    }

	}
    }

    private boolean hasFactor(String effect) {
	for (int i = 0; i < matrix.length; i++) {
	    if (matrix[i][names.getIndex(effect)] == 1) {
		return true;
	    }
	}
	return false;
    }

    private void addFactors(String effect, Stack<String> stack,
	    Stack<Integer> deep) {
	int d;
	if (deep.size() == 0) {
	    d = stackRuns;
	    stackRuns++;
	} else
	    d = deep.peek() + 1;
	for (int i = 0; i < matrix.length; i++) {
	    if (matrix[i][names.getIndex(effect)] == 1) {
		stack.push(names.get(i));
		deep.push(d);
		// System.out.println(" -- " + stack);
		// System.out.println(" -- " + deep);
	    }
	}
    }

    private void addMasterEffects(Stack<String> stack) {
	for (int i = 0; i < matrix.length; i++) {
	    boolean allZero = true;
	    for (int j = 0; j < matrix[i].length; j++) {
		if (matrix[i][j] == 1) {
		    allZero = false;
		}
	    }
	    if (allZero) {
		stack.push(names.get(i));
		getNode(names.get(i)).setLevel(0);
		getNode(names.get(i)).setEffect(true);
	    }
	}
    }

    // Helpers
    private void fillUpMatrix() {
	for (int i = 0; i < matrix.length; i++) {
	    for (int j = 0; j < matrix[i].length; j++) {
		matrix[i][j] = 0;
	    }
	}
    }

    private void addNodes() {
	for (String string : theories.getAllNames()) {
	    this.addVertex(new Node(string, false));
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
	double x = 60;
	double y = 100 * (deepest) + 30;
	ArrayList<Node> nodeList = new ArrayList<Node>();
	nodeList.addAll(getVertices());
	Collections.sort(nodeList);
	System.out.println(nodeList);
	int level = deepest;
	int counter = 0;
	int prevCounter = 1;
	double start = 30;
	for (Node node : nodeList) {
	    if (level != node.getLevel()) {
		prevCounter = counter;
		x = (60 * prevCounter / 2) + start;
		start = x - 30;
		counter = 0;
	    }
	    graph.put(node, new Point2D.Double(x, y
		    + ((node.getLevel() * -1) * 100)));
	    level = node.getLevel();
	    counter++;
	    x += 60;
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
