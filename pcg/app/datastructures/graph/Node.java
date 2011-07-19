package datastructures.graph;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

public class Node implements Comparable<Node> {
    private String name;
    private String bundle;
    private int level;
    private boolean effect;
    private boolean destination;
    private double x;
    private double y;

    public Node(String name, boolean effect) {
	level = -1;
	this.name = name;
	this.effect = effect;
	this.x = 0.0;
	this.y = 0.0;
    }

    // TODO Delete?
    /** Checks if the node is part of cause bundle */
    public boolean isPartOfBundle() {
	if (bundle != null) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public boolean equals(Object obj) {
	return (name.equals(obj.toString()));
    }

    // ToString

    @Override
    public String toString() {
	return name;
    }

    // Getters and Setters

    public String getBundle() {
	return bundle;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setBundle(String bundle) {
	this.bundle = bundle;
    }

    public void setEffect(boolean effect) {
	this.effect = effect;
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(int level) {
	this.level = level;
    }

    public boolean isEffect() {
	return effect;
    }

    @Override
    public int compareTo(Node o) {
	return o.getLevel() - level;
    }

    public void setCoordinates(double x, double y) {
	this.x = x;
	this.y = y;
    }

    public double getX() {
	return x;
    }

    public double getY() {
	return y;
    }

    public boolean isDestination() {
        return destination;
    }

    public void setDestination(boolean destination) {
        this.destination = destination;
    }
}
