package datastructures.graph;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import datastructures.cna.CNAList;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;
import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class Graph extends AbstractGraph<Node, Edge> {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private MinimalTheorySet theories;
    private int[][] matrix;
    private CNAList names;
    private int side;
    private Map<Node, Point2D> graph = new HashMap<Node, Point2D>();
    private int deepest = 0;
    private int stackRuns;
    private int x;
    private int y;
    private int bundleNum;
    private Stack<String> stack;
    private Stack<Integer> deep;

    public Graph(MinimalTheorySet theories) {
	this.theories = theories;
	names = theories.getAllNames();
	side = names.size();
	matrix = new int[side][side];
	nodes = new ArrayList<Node>();
	edges = new ArrayList<Edge>();
	this.x = 1;
	this.y = 1;
	bundleNum = 1;
	stackRuns = 0;
	fillUpMatrixZero();
	build(theories);
	addNodes();
	addEdges();
	addMasterEffects();
	calculateGraph();
    }

    private void build(MinimalTheorySet theories) {
	for (MinimalTheory theorie : theories) {
	    for (String factor : theorie.getFactors()) {
		int posE = names.getIndex(theorie.getEffect());
		int posF = names.getIndex(factor);
		matrix[posF][posE] = 1;
	    }
	}
    }

    private synchronized void addNodes() {
	for (String string : names) {
	    nodes.add(new Node(string, false));
	}
    }

    private synchronized void addEdges() {
	for (MinimalTheory theorie : theories) {
	    Node source;
	    Node destination;
	    for (CNAList list : theorie.getBundleFactors()) {
		destination = getNode(theorie.getEffect());
		destination.setIsInnerEffect(true);
		if (list.size() > 1) {
		    for (String str : list) {
			source = getNode(str);
			source.setBundle(bundleNum);
			Edge edge = new Edge(source, destination);
			edge.setBundleLabel("" + bundleNum);
			edges.add(edge);
		    }
		    bundleNum++;
		} else {
		    source = getNode(list.get(0));
		    edges.add(new Edge(source, destination));
		}
	    }
	}
    }

    private synchronized void addMasterEffects() {
	for (int i = 0; i < matrix.length; i++) {
	    boolean allZero = true;
	    for (int j = 0; j < matrix[i].length; j++) {
		if (matrix[i][j] == 1) {
		    allZero = false;
		}
	    }
	    if (allZero) {
		stackRuns = 1;
		stack = new Stack<String>();
		deep = new Stack<Integer>();
		stack.push(names.get(i));
		deep.push(0);
		Node n = getNode(names.get(i));
		n.setLevel(0);
		n.setIsEffect(true);
		System.out.println("===========" + n);
		setLevels();
	    }
	}
    }

    private synchronized void setLevels() {
	System.out.println();
	System.out.println(this);
	while (!stack.empty()) {
	    System.out.println("S: " + stack);
	    System.out.println("D: " + deep);
	    String effect = stack.peek();
	    stack.pop();
	    Node node = getNode(effect);
	    if (node.getLevel() == -1 || node.getLevel() < deep.peek())
		node.setLevel(deep.peek());

	    if (deepest < deep.peek()) {
		deepest = deep.peek();
	    }
	    deep.pop();
	    System.out.println("S: " + stack);
	    System.out.println("D: " + deep);
	    System.out.println(effect);
	    if (hasFactor(effect)) {
		addFactors(effect);
		stackRuns++;
	    }
	}
    }

    private synchronized void addFactors(String effect) {
	for (int i = 0; i < matrix.length; i++) {
	    if (matrix[i][names.getIndex(effect)] == 1) {
		stack.push(names.get(i));
		deep.push(stackRuns);
	    }
	}
	System.out.println("-->S: " + stack);
	System.out.println("-->D: " + deep);
    }

    private synchronized boolean hasFactor(String effect) {
	for (int i = 0; i < matrix.length; i++) {
	    if (matrix[i][names.getIndex(effect)] == 1) {
		return true;
	    }
	}
	return false;
    }

    // Helpers
    private synchronized void fillUpMatrixZero() {
	for (int i = 0; i < matrix.length; i++) {
	    for (int j = 0; j < matrix[i].length; j++) {
		matrix[i][j] = 0;
	    }
	}
    }

    private synchronized void calculateGraph() {
	double x = 60;
	double y = 160 * (deepest) + 20;
	NodeLevelComperator byLevel = new NodeLevelComperator();
	NodeBundleComperator byBundle = new NodeBundleComperator();
	Collections.sort(nodes, byLevel);
	Collections.sort(nodes, byBundle);
	int level = deepest + 1;
	int counter = 0;
	int prevCounter = 1;
	int newX = 0;
	double start = 30;
	for (Node node : nodes) {
	    if (level != node.getLevel()) {
		prevCounter = counter;
		x = (60 * prevCounter / 2) + start;
		start = x - 30;
		counter = 0;
	    }
	    graph.put(node,
		    new Point2D.Double(x, y - ((node.getLevel()) * 130)));
	    node.setCoordinates(x, y - ((node.getLevel() * 130)));
	    level = node.getLevel();
	    counter++;
	    x += 60;
	    if (newX < x) {
		newX = (int) x;
	    }
	}
	this.x = newX;
	this.y = (int) (y + 100);

    }

    // toString
    @Override
    public synchronized String toString() {
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

    public synchronized Map<Node, Point2D> getGraph() {
	return graph;
    }

    public synchronized Node getNode(String node) {
	for (Node n : nodes) {
	    if (n.equals(node))
		return n;
	}
	return null;
    }

    @Override
    public synchronized Node getDest(Edge arg0) {
	return arg0.getDestination();
    }

    @Override
    public synchronized Pair<Node> getEndpoints(Edge arg0) {
	ArrayList<Node> list = new ArrayList<Node>();
	list.add(arg0.getSource());
	list.add(arg0.getDestination());
	return new Pair<Node>(list);
    }

    @Override
    public synchronized Collection<Edge> getInEdges(Node arg0) {
	ArrayList<Edge> in = new ArrayList<Edge>();
	for (Edge e : edges) {
	    if (e.getDestination().equals(arg0))
		in.add(e);
	}
	return in;
    }

    @Override
    public synchronized Collection<Edge> getOutEdges(Node arg0) {
	ArrayList<Edge> out = new ArrayList<Edge>();
	for (Edge e : edges) {
	    if (e.getSource().equals(arg0))
		out.add(e);
	}
	return out;
    }

    @Override
    public synchronized Collection<Node> getPredecessors(Node arg0) {
	ArrayList<Node> pre = new ArrayList<Node>();
	for (Edge e : getInEdges(arg0)) {
	    pre.add(e.getSource());
	}
	return pre;
    }

    @Override
    public synchronized Node getSource(Edge arg0) {
	return arg0.getSource();
    }

    @Override
    public synchronized Collection<Node> getSuccessors(Node arg0) {
	ArrayList<Node> suc = new ArrayList<Node>();
	for (Edge e : getOutEdges(arg0)) {
	    suc.add(e.getSource());
	}
	return suc;
    }

    @Override
    public synchronized boolean isDest(Node arg0, Edge arg1) {
	return arg0.equals(arg1.getDestination());
    }

    @Override
    public synchronized boolean isSource(Node arg0, Edge arg1) {
	return arg0.equals(arg1.getSource());
    }

    @Override
    public synchronized boolean addVertex(Node arg0) {
	return nodes.add(arg0);
    }

    @Override
    public synchronized boolean containsEdge(Edge arg0) {
	return edges.contains(arg0);
    }

    @Override
    public synchronized boolean containsVertex(Node arg0) {
	return nodes.contains(arg0);
    }

    @Override
    public synchronized EdgeType getDefaultEdgeType() {
	return EdgeType.DIRECTED;
    }

    @Override
    public synchronized int getEdgeCount() {
	return edges.size();
    }

    @Override
    public synchronized int getEdgeCount(EdgeType arg0) {
	int i = 0;
	for (Edge e : edges)
	    if (e.getType().equals(arg0))
		i++;
	return i;
    }

    @Override
    public synchronized EdgeType getEdgeType(Edge arg0) {
	return arg0.getType();
    }

    @Override
    public synchronized Collection<Edge> getEdges() {
	return edges;
    }

    @Override
    public synchronized Collection<Edge> getEdges(EdgeType arg0) {
	ArrayList<Edge> e = new ArrayList<Edge>();
	for (Edge edge : edges) {
	    if (edge.getType().equals(arg0))
		e.add(edge);
	}
	return e;
    }

    @Override
    public synchronized Collection<Edge> getIncidentEdges(Node arg0) {
	ArrayList<Edge> inc = new ArrayList<Edge>();
	for (Edge e : edges) {
	    if (e.getSource().equals(arg0) || e.getDestination().equals(arg0))
		inc.add(e);
	}
	return inc;
    }

    @Override
    public synchronized Collection<Node> getNeighbors(Node arg0) {
	ArrayList<Node> neighbours = new ArrayList<Node>();
	neighbours.addAll(getPredecessors(arg0));
	neighbours.addAll(getSuccessors(arg0));
	return neighbours;
    }

    @Override
    public synchronized int getVertexCount() {
	return nodes.size();
    }

    @Override
    public synchronized Collection<Node> getVertices() {
	return nodes;
    }

    @Override
    public synchronized boolean removeEdge(Edge arg0) {
	return edges.remove(arg0);
    }

    @Override
    public synchronized boolean removeVertex(Node arg0) {
	return nodes.remove(arg0);
    }

    @Override
    public synchronized boolean addEdge(Edge arg0, Pair<? extends Node> arg1,
	    EdgeType arg2) {
	return edges.add(arg0);
    }

    public synchronized int getX() {
	return x;
    }

    public synchronized int getY() {
	return y;
    }

}
