package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

import datastructures.CustomTreeNode;

/** Responsible for node positioning */
public class VertexLocationTransformer implements
	Transformer<CustomTreeNode, Point2D> {

    private static int level;
    private double yCoordinate = 0;
    private double xSpace = 70;
    private double ySpace = 120;
    private double xCoordinate1 = 0;
    private static double xCoordinate = 0;
    private static double xCoordinatePrev = 0;
    private static double indexPrev = 0;

    @Override
    public Point2D transform(CustomTreeNode vertex) {
	CustomTreeNode curNode = vertex;

	yCoordinate = ySpace * curNode.getEffectLevel() - 90;
	curNode.setyCoordinate(yCoordinate);

	if (!curNode.isEffect()) {
	    xCoordinate += 60;
	    System.out.println("CurNode " + curNode);
	    System.out.println("xCoordinate " + xCoordinate);
	    return new Point2D.Double(xCoordinate, yCoordinate);
	} else {
	    if (curNode.getEffectLevel() == 2) {
		xCoordinate1 = (((curNode.getChildCount() - 1) * 60) / 2d) + 60;
		System.out.println("CurNode " + curNode);
		System.out.println("xCoordinate " + xCoordinate1);
		xCoordinatePrev = xCoordinate1;
		indexPrev = curNode.getIndex();
		return new Point2D.Double(xCoordinate1, yCoordinate);
	    } else {
		double xCoordinate1 = ((xCoordinatePrev - 60 * (indexPrev - 1)) * curNode
			.getChildCount()) / 2d;
		System.out.println("CurNode " + curNode);
		System.out.println("xCoordinate " + xCoordinate1);
		return new Point2D.Double(xCoordinate1, yCoordinate);
	    }
	}

	/*
	 * if (!curNode.isLeaf()) { xCoordinate = 0; xCoordinatePrev = 0;
	 * System.out.println("CurNode NotLeaf " + curNode);
	 * System.out.println("ChildCount " + curNode.getEffectLevel());
	 * calcEffectCoordinate(curNode); xCoordinate = xCoordinatePrev; //
	 * xCoordinate = ((curNode.getChildCount()-1) * xSpace / 2d) + 270d // ;
	 * // System.out.println("CurNode NotLeaf " + curNode); //
	 * System.out.println("ChildCount " + curNode.getChildCount()); //
	 * System.out.println("xCoordinate " + xCoordinate); return new
	 * Point2D.Double(xCoordinate, yCoordinate); //} else{ return new
	 * Point2D.Double(100, 100); //} /*else { CustomTreeNode sameLevelEffect
	 * = getSameLevelEffect(curNode); if (sameLevelEffect != null) { double
	 * sameLevelEffectCoordinate = ((sameLevelEffect .getChildCount() - 1) *
	 * xSpace) / 2d; double sameLevelEffectIndex =
	 * sameLevelEffect.getIndex(); xCoordinate = (sameLevelEffectCoordinate
	 * + (curNode.getIndex() - sameLevelEffectIndex) xSpace) + 200d;
	 * 
	 * // System.out.println("CurNode Leaf " + curNode); //
	 * System.out.println("xCoordinate " + xCoordinate); return new
	 * Point2D.Double(xCoordinate, yCoordinate); } else { // xCoordinate =
	 * (/* (xSpace * curNode.getEffectLevel()
	 */
	// xSpace + (xSpace * (curNode.getIndex() - 1d))) + 200d;

	// System.out.println("CurNode SpecialLeaf " + curNode);
	// System.out.println("xCoordinate " + xCoordinate);
	// return new Point2D.Double(xCoordinate, yCoordinate);
    }

    // }*/
    // }

    private CustomTreeNode getSameLevelEffect(CustomTreeNode curNode) {
	CustomTreeNode parent = (CustomTreeNode) curNode.getParent();
	for (int i = 0; i < parent.getChildCount(); i++) {
	    if (!parent.getChildAt(i).isLeaf()) {
		return (CustomTreeNode) parent.getChildAt(i);
	    }
	}
	return null;
    }

    private void calcEffectCoordinate(CustomTreeNode childParent) {
	boolean doIt = true;
	if (childParent.getEffectLevel() == 2) {
	    xCoordinatePrev = ((childParent.getChildCount() - 1) * xSpace / 2d) + 270d;
	    System.out.println("CurNode InRec " + childParent);
	    System.out.println("ChildCount " + childParent.getChildCount());
	    // System.out.println("xCoordinate " + xCoordinate);
	    indexPrev = childParent.getIndex();
	    doIt = false;
	}
	/*
	 * if(childParent.getxCoordinate() == -3000){ for(int i = 0; i <
	 * childParent.getChildCount(); i++){
	 * if(!childParent.getChildAt(i).isLeaf()){
	 * calcEffectCoordinate(childParent); } }
	 */
	else {
	    for (int i = 0; i < childParent.getChildCount(); i++) {
		if (!childParent.getChildAt(i).isLeaf()) {
		    calcEffectCoordinate(childParent);
		}
	    }
	}
	if (doIt) {
	    double tempCor = xCoordinatePrev;
	    double tempIndex = indexPrev;
	    double xCoordNew = ((tempCor - xSpace * (tempIndex - 1)) * childParent
		    .getChildCount()) / 2d;

	    indexPrev = childParent.getIndex();
	    xCoordinatePrev = xCoordNew;
	}
    }

    public void reset() {
	yCoordinate = 0;
	xCoordinate = 0;
	xCoordinate = 0;
	xCoordinatePrev = 0;
	indexPrev = 0;
    }

    public double getyRoot() {
	return yCoordinate;
    }

    public double getSpace() {
	return xSpace;
    }
}
