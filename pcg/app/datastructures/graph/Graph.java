package datastructures.graph;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import datastructures.cna.CNAList;
import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;
import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Graph extends AbstractGraph<Node, Edge> {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private MinimalTheorieSet theories;
    private int[][] matrix;
    private CNAList names;
    private int side;
    private Map<Node, Point2D> graph = new HashMap<Node, Point2D>();
    private int deepest = 0;
    private int stackRuns = 1;

    public Graph(MinimalTheorieSet theories) {
	this.theories = theories;
	names = theories.getAllNames();
	side = names.size();
	matrix = new int[side][side];
	nodes = new ArrayList<Node>();
	edges = new ArrayList<Edge>();
	fillUpMatrixZero();
	build(theories);
	addNodes();
	addEdges();
	setLevels();
    }

    private void addNodes() {
	for (String string : theories.getAllNames()) {
	    nodes.add(new Node(string, false));
	}
    }

    private void build(MinimalTheorieSet theories) {
	for (MinimalTheorie theorie : theories) {
	    for (String factor : theorie.getFactors()) {
		int posE = names.getIndex(theorie.getEffect());
		int posF = names.getIndex(factor);
		matrix[posF][posE] = 1;
	    }
	}
    }

    private void addEdges() {
	for (int i = 0; i < matrix.length; i++) {
	    for (int j = 0; j < matrix[i].length; j++) {
		if (matrix[i][j] == 1) {
		    Node source = getNode(names.get(i));
		    System.out.println(names.get(i));
		    Node destination = getNode(names.get(j));
		    Edge edge = new Edge(source, destination);
		    System.out.println(edge);
		    edges.add(edge);
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
	while (stack.size() != 0) {
	    String effect = stack.pop();
	    getNode(effect).setLevel(deep.peek());
	    if (deepest < deep.peek()) {
		deepest = deep.peek();
	    }
	    deep.pop();
	    if (hasFactor(effect)) {
		addFactors(effect, stack, deep);
	    }
	    for (int i = deep.size(); i > stack.size() + 1; i--) {
		deep.pop();
	    }

	}
    }

    // Helpers
    private void fillUpMatrixZero() {
	for (int i = 0; i < matrix.length; i++) {
	    for (int j = 0; j < matrix[i].length; j++) {
		matrix[i][j] = 0;
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
	ArrayList<Node> nodeList = nodes;
	Collections.sort(nodeList);
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
	for (Node n : nodes) {
	    if (n.equals(node))
		return n;
	}
	return null;
    }

    @Override
    public Node getDest(Edge arg0) {
	return arg0.getDestination();
    }

    @Override
    public Pair<Node> getEndpoints(Edge arg0) {
	ArrayList<Node> list = new ArrayList<Node>();
	list.add(arg0.getSource());
	list.add(arg0.getDestination());
	return new Pair<Node>(list);
    }

    @Override
    public Collection<Edge> getInEdges(Node arg0) {
	ArrayList<Edge> in = new ArrayList<Edge>();
	for (Edge e : edges) {
	    if (e.getDestination().equals(arg0))
		in.add(e);
	}
	return in;
    }

    @Override
    public Collection<Edge> getOutEdges(Node arg0) {
	ArrayList<Edge> out = new ArrayList<Edge>();
	for (Edge e : edges) {
	    if (e.getSource().equals(arg0))
		out.add(e);
	}
	return out;
    }

    @Override
    public Collection<Node> getPredecessors(Node arg0) {
	ArrayList<Node> pre = new ArrayList<Node>();
	for (Edge e : getInEdges(arg0)) {
	    pre.add(e.getSource());
	}
	return pre;
    }

    @Override
    public Node getSource(Edge arg0) {
	return arg0.getSource();
    }

    @Override
    public Collection<Node> getSuccessors(Node arg0) {
	ArrayList<Node> suc = new ArrayList<Node>();
	for (Edge e : getOutEdges(arg0)) {
	    suc.add(e.getSource());
	}
	return suc;
    }

    @Override
    public boolean isDest(Node arg0, Edge arg1) {
	return arg0.equals(arg1.getDestination());
    }

    @Override
    public boolean isSource(Node arg0, Edge arg1) {
	return arg0.equals(arg1.getSource());
    }

    @Override
    public boolean addVertex(Node arg0) {
	return nodes.add(arg0);
    }

    @Override
    public boolean containsEdge(Edge arg0) {
	return edges.contains(arg0);
    }

    @Override
    public boolean containsVertex(Node arg0) {
	return nodes.contains(arg0);
    }

    @Override
    public EdgeType getDefaultEdgeType() {
	return EdgeType.DIRECTED;
    }

    @Override
    public int getEdgeCount() {
	return edges.size();
    }

    @Override
    public int getEdgeCount(EdgeType arg0) {
	int i = 0;
	for (Edge e : edges)
	    if (e.getType().equals(arg0))
		i++;
	return i;
    }

    @Override
    public EdgeType getEdgeType(Edge arg0) {
	return arg0.getType();
    }

    @Override
    public Collection<Edge> getEdges() {
	return edges;
    }

    @Override
    public Collection<Edge> getEdges(EdgeType arg0) {
	ArrayList<Edge> e = new ArrayList<Edge>();
	for (Edge edge : edges) {
	    if (edge.getType().equals(arg0))
		e.add(edge);
	}
	return e;
    }

    @Override
    public Collection<Edge> getIncidentEdges(Node arg0) {
	ArrayList<Edge> inc = new ArrayList<Edge>();
	for (Edge e : edges) {
	    if (e.getSource().equals(arg0) || e.getDestination().equals(arg0))
		inc.add(e);
	}
	return inc;
    }

    @Override
    public Collection<Node> getNeighbors(Node arg0) {
	ArrayList<Node> neighbours = new ArrayList<Node>();
	neighbours.addAll(getPredecessors(arg0));
	neighbours.addAll(getSuccessors(arg0));
	return neighbours;
    }

    @Override
    public int getVertexCount() {
	return nodes.size();
    }

    @Override
    public Collection<Node> getVertices() {
	return nodes;
    }

    @Override
    public boolean removeEdge(Edge arg0) {
	return edges.remove(arg0);
    }

    @Override
    public boolean removeVertex(Node arg0) {
	return nodes.remove(arg0);
    }

    @Override
    public boolean addEdge(Edge arg0, Pair<? extends Node> arg1, EdgeType arg2) {
	return edges.add(arg0);
    }
}
