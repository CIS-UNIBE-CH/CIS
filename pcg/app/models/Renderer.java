package models;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import tree.CustomTreeNode;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.Renderer.Vertex;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;

/** Design of nodes, shape color etc */
public class Renderer implements Vertex<CustomTreeNode, CustomEdge> {
	@Override
	public void paintVertex(RenderContext<CustomTreeNode, CustomEdge> rc,
			Layout<CustomTreeNode, CustomEdge> layout, CustomTreeNode vertex) {
		GraphicsDecorator graphicsContext = rc.getGraphicsContext();
		Point2D center = layout.transform(vertex);
		Shape shape = null;
		Color color = null;

		shape = new Ellipse2D.Double(center.getX() - 10, center.getY() - 10,
				20, 20);
		color = new Color(184, 0, 138);

		graphicsContext.setPaint(color);
		graphicsContext.fill(shape);
	}
}
