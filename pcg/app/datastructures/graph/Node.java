package datastructures.graph;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

public class Node {
    private String name;
    private String bundle;
    private int level;
    private boolean effect;

    public Node(String name, boolean effect) {
	level = 0;
	this.name = name;
	this.effect = effect;
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

    // ToString

    public String toString() {
	return name;
    }

    // Getters and Setters

    public String getBundle() {
	return bundle;
    }

    public void setBundle(String bundle) {
	this.bundle = bundle;
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

}
