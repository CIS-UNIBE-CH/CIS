package datastructures.graph;

import edu.uci.ics.jung.graph.util.EdgeType;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */


public class Edge {

    private String bundleLabel;
    private Node source;
    private Node destination;
    private EdgeType type;

    public Edge(Node source, Node destination) {
	this.source = source;
	this.destination = destination;
	this.type = EdgeType.DIRECTED;
	bundleLabel = "";
    }

    public double calcEdgeLength() {
	double firstX = source.getX();
	double firstY = source.getY();
	double secondX = destination.getX();
	double secondY = destination.getY();

	// Do some pythagoras
	double cathetusX = Math.abs(firstX - secondX);
	double cathetusY = Math.abs(firstY - secondY);
	double hypotenuse = Math.hypot(cathetusX, cathetusY);

	return hypotenuse;
    }

    public boolean equals(Edge edge) {
	return (destination.equals(edge.getDestination()) && source.equals(edge
		.getSource()));
    }

    // to String
    @Override
    public String toString() {
	return bundleLabel;
    }

    public String toInfoString() {
	return "{" + source + " --> " + destination + "} " + "B: "
		+ bundleLabel;
    }

    // Getters and Setters

    public String getBundleLabel() {
	return bundleLabel;
    }

    public void setBundleLabel(String bundleLabel) {
	this.bundleLabel = bundleLabel;
    }

    public EdgeType getType() {
	return type;
    }

    public void setType(EdgeType type) {
	this.type = type;
    }

    public Node getSource() {
	return source;
    }

    public Node getDestination() {
	return destination;
    }
}
