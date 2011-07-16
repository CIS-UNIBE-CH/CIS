package datastructures.graph;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

/**
 * This class provides labeling of cause bundles with a number for every cause
 * in a bundle. Inherited from: org.jgrapht.graph.DefaultEdge
 */
public class Edge {

    private String bundleLabel;
    private Node source;
    private Node destination;

    public Edge(Node source, Node destination) {
	this.source = source;
	this.destination = destination;
    }

    // TODO JR (new Node)
    public double calcEdgeLength() {
	// Node firstNode = nodes.get(0);
	// Node secondNode = nodes.get(1);
	// double firstX = firstNode.getxCoordinate();
	// double firstY = firstNode.getyCoordinate();
	// double secondX = secondNode.getxCoordinate();
	// double secondY = secondNode.getyCoordinate();
	//
	// // Do some pythagoras
	// double cathetusX = Math.abs(firstX - secondX);
	// double cathetusY = Math.abs(firstY - secondY);
	// double hypotenuse = Math.sqrt(Math.pow(cathetusX, 2)
	// + Math.pow(cathetusY, 2));
	//
	// return hypotenuse;
	return 0.0;
    }

    public void setBundleNumber(String bundleLabel) {
	this.bundleLabel = bundleLabel;
    }

    public int getBundleNumber() {
	if (bundleLabel != null) {
	    // System.out.println("bundle" + Integer.parseInt(bundleLabel));
	    return Integer.parseInt(bundleLabel);
	} else {
	    return 1;
	}
    }

    public boolean equals(Edge edge) {
	return (destination.equals(edge.getDestination()) && source.equals(edge
		.getSource()));
    }

    public Node getSource() {
	return source;
    }

    public Node getDestination() {
	return destination;
    }

    @Override
    public String toString() {
	if (bundleLabel != null) {
	    return bundleLabel;
	} else {
	    return "";
	}
    }
}
