package models;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import Tree.CustomTree;
import Tree.CustomTreeNode;

/** Builds out of the tree JGraphT nodes and edges */
public class Parser {
	private static DefaultDirectedGraph<CustomTreeNode, DefaultEdge> graph;
	private CustomTree tree;
	private JGraphModelAdapter<CustomTreeNode, DefaultEdge> jgAdapter;

	public Parser() {
		graph = new DefaultDirectedGraph<CustomTreeNode, DefaultEdge>(
				DefaultEdge.class);
		tree.getInstance();
		createGraphNodes();
		jgAdapter = new JGraphModelAdapter<CustomTreeNode, DefaultEdge>(graph);
		positionNodes();
	}

	private void createGraphNodes() {
		ArrayList<CustomTreeNode> childs = tree.getChildren();

		graph.addVertex(tree.getRoot());
		for (int i = 0; i < childs.size(); i++) {
			graph.addVertex(childs.get(i));
		}
	}

	private void positionNodes() {
		List nodes = jgAdapter.getRoots();
		int x = 50;
		int y = 100;

		for (int i = 0; i < nodes.size(); i++) {
			DefaultGraphCell cell = (DefaultGraphCell) nodes.get(i);

			AttributeMap attr = cell.getAttributes();
			Rectangle2D bounds = GraphConstants.getBounds(attr);

			int width = 100;
			int height = 30;
			Rectangle2D newBounds = new Rectangle2D.Double(x, y, width, height);

			GraphConstants.setBounds(attr, newBounds);
			AttributeMap cellAttr = new AttributeMap();
			cellAttr.put(cell, attr);
			jgAdapter.edit(cellAttr, null, null, null);

			x = x + 100;
			y = y + 50;
		}
	}

	public static DefaultDirectedGraph<CustomTreeNode, DefaultEdge> getGraph() {
		return graph;
	}

	public JGraphModelAdapter<CustomTreeNode, DefaultEdge> getJgAdapter() {
		return jgAdapter;
	}

}
