package models;

import java.awt.Shape;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.visualization.decorators.AbstractVertexShapeTransformer;

/**
 * Controls the shape, size, and aspect ratio for each vertex.
 * 
 */
public class VertexLookTransformer<CustomTreeNode, CustomEdge> extends
		AbstractVertexShapeTransformer<CustomTreeNode> implements
		Transformer<CustomTreeNode, Shape> {

	public VertexLookTransformer() {
		// transform size of vertex
		setSizeTransformer(new Transformer<CustomTreeNode, Integer>() {
			public Integer transform(CustomTreeNode v) {
				return 40;
			}
		});
		// transform aspect ratio
		setAspectRatioTransformer(new Transformer<CustomTreeNode, Float>() {

			public Float transform(CustomTreeNode v) {
				return 1.0f;
			}
		});
	}

	// trandform shape
	public Shape transform(CustomTreeNode v) {
		return factory.getEllipse(v);
	}
}