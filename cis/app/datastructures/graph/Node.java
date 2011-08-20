package datastructures.graph;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe.ch@gmail.com>
 * @license GPLv3, for more informations see Readme.mdown
 */

public class Node {
    private String name;
    private int bundle;
    private int level;
    private boolean isEffect;
    private boolean isInnerEffect;
    private double x;
    private double y;

    public Node(String name, boolean isEffect) {
	level = -1;
	this.name = name;
	this.isEffect = isEffect;
	this.x = 0.0;
	this.y = 0.0;
	bundle = 0;
    }

    /** Checks if the node is part of cause bundle */
    public boolean isPartOfBundle() {
	return bundle != 0;
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

    public int getBundle() {
	return bundle;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setBundle(int bundle) {
	this.bundle = bundle;
    }

    public void setIsEffect(boolean isEffect) {
	this.isEffect = isEffect;
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(int level) {
	this.level = level;
    }

    public boolean isEffect() {
	return isEffect;
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

    public boolean isInnerEffect() {
	return isInnerEffect;
    }

    public void setIsInnerEffect(boolean isInnerEffect) {
	this.isInnerEffect = isInnerEffect;
    }
}
