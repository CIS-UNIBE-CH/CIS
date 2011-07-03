package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */
import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

import trees.CustomTreeNode;

/** Responsible for node positioning */
public class VertexLocationTransformer implements
		Transformer<CustomTreeNode, Point2D> {
	// x coordinate of Wirkung
	static int xRoot = 0;
	// y coordinaate of Wirkung
	static int yRoot = 280;
	// start coordinate of Ursache
	static int xOther = 0;
	// Left offset and space between Ursachen
	static int space = 80;
	// y coordinate for Ursachen.
	static int yOther = 30;

	@Override
	public Point2D transform(CustomTreeNode vertex) {
		CustomTreeNode curNode = vertex;

		if (curNode.isRoot()) {
			curNode.setxCoordinate(xRoot);
			curNode.setyCoordinate(yRoot);
			return new Point2D.Double(xRoot, yRoot);
		} else {
			xOther = xOther + space;
			curNode.setxCoordinate(xOther);
			curNode.setyCoordinate(yOther);
			return new Point2D.Double(xOther, yOther);
		}
	}

	public void setxRootPosition(int numberOfVertex) {
		xRoot = ((numberOfVertex) * space) / 2;
	}

	public void reset() {
		xOther = 0;
	}

	public static int getyRoot() {
		return yRoot;
	}

	public static int getSpace() {
		return space;
	}
}
