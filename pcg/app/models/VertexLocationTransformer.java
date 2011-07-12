package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

import datastructures.CustomTreeNode;

/** Responsible for node positioning */
public class VertexLocationTransformer implements
	Transformer<CustomTreeNode, Point2D> {

    private double yCoordinate = 0;
    private double xSpace = 60;
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
		//xCoordinate -= 60; 
		return new Point2D.Double(xCoordinate1, yCoordinate);
	    } else {
		double xCoordinate1;
		System.out.println("****************");
		System.out.println("XCoordinatePrev: " + xCoordinatePrev);
		if(indexPrev != 1){
		    double cordMostLeft = xCoordinatePrev - 60 * (indexPrev-1);
		    double middle = ((curNode.getChildCount()-1) * 60)/2d;
		    System.out.println("CurNodeHere " + curNode);
		    System.out.println("IndexPrev " + indexPrev);
		    System.out.println("******MostLeft: " + cordMostLeft);
		    System.out.println("******Middle: " + cordMostLeft);
		    xCoordinate1 = cordMostLeft + middle;
		} else{
		    double cordMostLeft = xCoordinatePrev;
		    double middle = ((curNode.getChildCount()-1) * 60)/2d;
		    xCoordinate1 = cordMostLeft + middle; 
		}
		//double xCoordinate1 = ((xCoordinatePrev - 60 * (indexPrev - 1)) * curNode
		//	.getChildCount()) / 2d;
		//xCoordinate1 = xCoordinatePrev + 60;
//		System.out.println("Prev " + xCoordinatePrev);
//		System.out.println("IndexPrev " + curNode.getIndex());
		xCoordinatePrev = xCoordinate1;
		indexPrev = curNode.getIndex();
//		System.out.println("CurNodeHere " + curNode);
//		System.out.println("xCoordinate " + xCoordinate1);
//		System.out.println("Index " + curNode.getIndex());
		xCoordinate -= 60;
		return new Point2D.Double(xCoordinate1, yCoordinate);
	    }
	}
    }

    public void reset() {
	yCoordinate = 0;
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
