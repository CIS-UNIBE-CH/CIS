package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.awt.geom.Point2D;

import org.apache.commons.collections15.Transformer;

import datastructures.graph.Node;

/** Responsible for node positioning */
public class VertexLocationTransformer implements
	Transformer<Node, Point2D> {

    private static double x = 40;
    private static double y = 40;

    @Override
    public Point2D transform(Node vertex) {
	Node curNode = vertex;
	System.out.println("CurrentNode: " + curNode);
	y += 40;
	x += 40;
	return new Point2D.Double(x, y);
    }

    public void reset() {
	x = 0;
	y = 0;
    }
 }
