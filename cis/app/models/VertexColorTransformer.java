package models;

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
    private final ArrayList<Color> colors = new ArrayList<Color>();

    @Override
    public Paint transform(Node node) {
	colors.clear();
	colors.add(Color.lightGray);
	colors.add(Color.magenta);
	colors.add(Color.green);
	colors.add(Color.gray);
	colors.add(Color.orange);
	colors.add(Color.red);
	colors.add(Color.cyan);
	colors.add(Color.pink);
	colors.add(Color.yellow);

	if (node.isEffect()) {
	    return new Color(255, 153, 0);
	} else {
	    int bundleNumber = node.getBundle();
	    return colors.get((bundleNumber % 9) - 1);
	}
    }
}