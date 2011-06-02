package models;

import java.awt.geom.Point2D;

import tree.CustomTreeNode;

/** New Class for node positioning */
public class Transformer implements
		org.apache.commons.collections15.Transformer<CustomTreeNode, Point2D> {
	@Override
	public Point2D transform(CustomTreeNode vertex) {
		int value = (vertex.getTest() * 40) + 20;
		return new Point2D.Double((double) value, (double) value);
	}
}