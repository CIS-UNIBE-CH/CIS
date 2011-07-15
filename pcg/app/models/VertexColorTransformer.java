package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

import datastructures.Node;

/**
 * Vertex Color according to Bundle which vertex is member of and according if
 * bundle is cause or effect.
 */
public class VertexColorTransformer implements Transformer<Node, Paint> {
    @Override
    public Paint transform(Node node) {
	Node current = node;
	if (!current.isEffect()) {
	    return new Color(255, 153, 0);
	} else {
	    int bundleNumber;
	    if (current.isPartOfBundle()) {
		if (!current.isEffect()) {
		    return new Color(255, 153, 0);
		} else {
		    bundleNumber = Integer.valueOf(current.getBundle());
		    return new Color((100 + (30 * bundleNumber)) % 255, 200,
			    (100 + (30 * bundleNumber)) % 255);
		}
	    } else {
		bundleNumber = 0;
		return Color.GREEN;
	    }
	}
    }
}
