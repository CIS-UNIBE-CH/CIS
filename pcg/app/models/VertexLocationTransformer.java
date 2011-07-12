package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

import datastructures.CustomTreeNode;

/** Responsible for node positioning */
public class VertexLocationTransformer implements
	Transformer<CustomTreeNode, Point2D> {

    private double yCoordinate = 0;
    private double xSpace = 70;
    private double ySpace = 120;
    private double xCoordinate = 0;

    @Override
    public Point2D transform(CustomTreeNode vertex) {
	CustomTreeNode curNode = vertex;

	yCoordinate = ySpace * curNode.getEffectLevel() - 90;
	curNode.setyCoordinate(yCoordinate);

	if (!curNode.isLeaf()) {
	    xCoordinate = (curNode.getChildCount() * xSpace) / 2d;
	    return new Point2D.Double(xCoordinate, yCoordinate);
	} else {
	    CustomTreeNode sameLevelEffect = getSameLevelEffect(curNode);
	    if (sameLevelEffect != null) {
		double sameLevelEffectCoordinate = (sameLevelEffect
			.getChildCount() * xSpace) / 2d;
		double sameLevelEffectIndex = sameLevelEffect.getIndex();
		xCoordinate = sameLevelEffectCoordinate
			+ (curNode.getIndex() - sameLevelEffectIndex) * xSpace;
		return new Point2D.Double(xCoordinate, yCoordinate);
	    }
	    else{
		xCoordinate = (/*(xSpace * curNode.getEffectLevel()*/
			xSpace + (xSpace * (curNode.getIndex() - 1))) -35;
		return new Point2D.Double(xCoordinate, yCoordinate);
	    }
	}
    }

    private CustomTreeNode getSameLevelEffect(CustomTreeNode curNode) {
	CustomTreeNode parent = (CustomTreeNode) curNode.getParent();
	for (int i = 0; i < parent.getChildCount(); i++) {
	    if (!parent.getChildAt(i).isLeaf()) {
		return (CustomTreeNode) parent.getChildAt(i);
	    }
	}
	return null;
    }

    public void reset() {
	yCoordinate = 0;
	xCoordinate = 0;
    }

    public double getyRoot() {
	return yCoordinate;
    }

    public double getSpace() {
	return xSpace;
    }
}
