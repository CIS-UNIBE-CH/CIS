package models;

import tree.CustomTreeNode;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.Renderer.Vertex;

/** Design of nodes, shape color etc */
public class Renderer implements Vertex<CustomTreeNode, CustomEdge> {

	@Override
	public void paintVertex(RenderContext<CustomTreeNode, CustomEdge> arg0,
			Layout<CustomTreeNode, CustomEdge> arg1, CustomTreeNode arg2) {
		// TODO Auto-generated method stub

	}
	/*
	 * @Override public void paintVertex(RenderContext<CustomTreeNode,
	 * CustomEdge> rc, Layout<CustomTreeNode, CustomEdge> layout, CustomTreeNode
	 * vertex) { GraphicsDecorator graphicsContext = rc.getGraphicsContext();
	 * Point2D center = layout.transform(vertex); Shape shape = null; Color
	 * color = null;
	 * 
	 * shape = new Ellipse2D.Double(center.getX() - 20, center.getY() - 20, 40,
	 * 40); color = new Color(184, 0, 138);
	 * 
	 * graphicsContext.setPaint(color); graphicsContext.fill(shape); }
	 */
}
