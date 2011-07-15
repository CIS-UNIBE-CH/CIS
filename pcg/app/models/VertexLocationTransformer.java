//package models;
//
///** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */
//
//import java.awt.geom.Point2D;
//
//import org.apache.commons.collections15.Transformer;
//
//import datastructures.Node;
//
///** Responsible for node positioning */
//public class VertexLocationTransformer implements
//	Transformer<Node, Point2D> {
//
//    private double xSpace = 60;
//    private double ySpace = 120;
//    private double xEffect = 0;
//    private static double xPrevEffect = 0;
//    private static double xCause = 0;
//    private double yAll = 0;
//    private static int biggestLevel = 0;
//
//    @Override
//    public Point2D transform(Node vertex) {
//	Node curNode = vertex;
//	System.out.println("CurrentNode: " + curNode);
//	yAll = ySpace * curNode.getLevel() - 90;
//	curNode.setyCoordinate(yAll);
//
//	if (biggestLevel < curNode.getLevel()) {
//	    biggestLevel = curNode.getLevel();
//	}
//
//	if (!curNode.isEffect()) {
//	    xCause += 60;
//	    return new Point2D.Double(xCause, yAll);
//	} else {
//	    if (curNode.getLevel() == 2) {
//		xEffect = (((curNode.getChildCount() - 1) * 60) / 2d) + 60;
//		xPrevEffect = xEffect;
//		return new Point2D.Double(xEffect, yAll);
//	    } else {
//		xEffect = xPrevEffect + 60;
//		xPrevEffect = xEffect;
//		xCause = xEffect - 60;
//		return new Point2D.Double(xEffect, yAll);
//	    }
//	}
//    }
//
//    public int getHeight() {
//	return (int) ((biggestLevel - 1) * ySpace) + 40;
//    }
//
//    public void reset() {
//	yAll = 0;
//	xCause = 0;
//	xPrevEffect = 0;
//	xEffect = 0;
//    }
// }
