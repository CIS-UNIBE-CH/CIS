package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;

import org.apache.commons.collections15.Transformer;

import datastructures.graph.Node;

/**
 * Vertex Color according to Bundle which vertex is member of and according if
 * bundle is cause or effect.
 */
public class VertexColorTransformer implements Transformer<Node, Paint> {
    private ArrayList<Color> color = new ArrayList<Color>();
    private static int index = 0;

    @Override
    public Paint transform(Node node) {
	color.clear();
	color.add(Color.blue);
	color.add(Color.lightGray);
	color.add(Color.magenta);
	color.add(Color.pink);
	color.add(Color.red);
	color.add(Color.orange);
	color.add(Color.cyan);
	color.add(Color.yellow);
	
	if (node.isEffect()) {
	    return new Color(255, 153, 0);
	} else {
	    if (node.isPartOfBundle()) {
		if (node.isDestination()) {
		    return new Color(255, 153, 0);
		} else {
		    int bundleNumber = Integer.parseInt(node.getBundle());
		    return color.get((bundleNumber-1)%8);
		}
	    } else {
		return Color.GREEN;
	    }
	}
    }
}
