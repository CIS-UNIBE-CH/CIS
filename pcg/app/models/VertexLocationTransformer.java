package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */
import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

import tree.CustomTreeNode;

/** New Class for node positioning */
public class VertexLocationTransformer implements
		Transformer<CustomTreeNode, Point2D> {
	static int xOther = 0;
	static int yOther = 30;

	//@Override
	public Point2D transform(CustomTreeNode vertex) {
		// for (int i = 0; i < graphNodes.size(); i++) {
		CustomTreeNode curNode = vertex;

		if (curNode.isRoot()) {
			int xRoot = 300;
			int yRoot = 300;

			return new Point2D.Double((double) xRoot, (double) yRoot);
		} else {
			xOther = xOther + 100;
			return new Point2D.Double((double) xOther, (double) yOther);
		}
	}
	// calculateJFrameSize(gapBetweenNodes, leftOffset, yRoot,
	// graphNodes.size(), y);
}
