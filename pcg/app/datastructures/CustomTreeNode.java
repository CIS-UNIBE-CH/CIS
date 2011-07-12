package datastructures;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import javax.swing.tree.DefaultMutableTreeNode;

/** Custom Tree Node inherits from DefaultMutableTreeNode */
public class CustomTreeNode extends DefaultMutableTreeNode {
    private String bundle;
    private String name;
    private double xCoordinate;
    private double yCoordinate;
    private int level = -1;
    private boolean isEffect;
    private int index;

    public CustomTreeNode(Object o) {
	super(o);
	name = (String) o;
    }

    public String getBundle() {
	return bundle;
    }

    public void setBundle(String bundle) {
	this.bundle = bundle;
    }

    public String getName() {
	return name;
    }

    /** Checks if the tree node is part of cause bundle */
    public boolean isPartOfBundle() {
	if (bundle != null) {
	    return true;
	} else {
	    return false;
	}
    }

    public double getxCoordinate() {
	return xCoordinate;
    }

    public void setxCoordinate(double xRoot) {
	this.xCoordinate = xRoot;
    }

    public double getyCoordinate() {
	return yCoordinate;
    }

    public void setyCoordinate(double yRoot) {
	this.yCoordinate = yRoot;
    }

    public int getEffectLevel() {
        return level;
    }

    public void setEffectLevel(int effectLevel) {
        this.level = effectLevel;
    }
    
    public boolean hasEffectLevel(){
	if(level != -1){
	    return true;
	}else{
	    return false;
	}
    }

    public boolean isEffect() {
        return isEffect;
    }

    public void setIsEffect(boolean isEffect) {
        this.isEffect = isEffect;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }  
}
