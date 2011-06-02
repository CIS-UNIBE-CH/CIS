package models;

import java.awt.Shape;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.visualization.decorators.AbstractVertexShapeTransformer;

/**
 * Controls the shape, size, and aspect ratio for each vertex.
 * 
 * @author Joshua O'Madadhain
 */
public class VertexShapeSizeAspect<CustomTreeNode, CustomEdge> extends
		AbstractVertexShapeTransformer<CustomTreeNode> implements
		Transformer<CustomTreeNode, Shape> {

	public VertexShapeSizeAspect() {
		setSizeTransformer(new Transformer<CustomTreeNode, Integer>() {
			public Integer transform(CustomTreeNode v) {
				return 40;
			}
		});
		setAspectRatioTransformer(new Transformer<CustomTreeNode, Float>() {

			public Float transform(CustomTreeNode v) {
				return 1.0f;
			}
		});
	}

	public Shape transform(CustomTreeNode v) {
		return factory.getEllipse(v);
	}
}