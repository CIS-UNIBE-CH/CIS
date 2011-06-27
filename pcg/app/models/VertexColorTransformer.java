package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */
import java.awt.Color;
import java.awt.Paint;

import org.apache.commons.collections15.Transformer;

import tree.CustomTreeNode;

/**
 * Vertex Color according to Bundle which vertex is member of and according if
 * bundle is cause or effect.
 */
public class VertexColorTransformer implements
	Transformer<CustomTreeNode, Paint> {
    @Override
    public Paint transform(CustomTreeNode node) {
	CustomTreeNode current = node;
	if (current.isRoot()) {
	    return new Color(255, 153, 0);
	} else {
	    int bundleNumber;
	    if (current.isPartOfBundle()) {
		bundleNumber = Integer.valueOf(current.getBundle());
		return new Color((100 + (30 * bundleNumber)) % 255, 200,
			(100 + (30 * bundleNumber)) % 255);
	    } else {
		bundleNumber = 0;
		return Color.GREEN;
	    }
	}
    }
}
