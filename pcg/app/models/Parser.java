package models;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;

import tree.CustomTree;
import tree.CustomTreeNode;

/** Builds out of the tree JGraphT nodes and edges */
public class Parser {
	private static DefaultDirectedGraph<CustomTreeNode, CustomEdge> graph;
	private CustomTree tree;
	private JGraphModelAdapter<CustomTreeNode, CustomEdge> jgAdapter;
	private int nodeWidth = 100;
	private int nodeHeight = 30;
	private int sizeAppletX;
	private int sizeAppletY;

	public Parser() {
		graph = new DefaultDirectedGraph<CustomTreeNode, CustomEdge>(
				CustomEdge.class);
		tree.getInstance();
		createGraphNodes();
		createGraphEdges();
		jgAdapter = new JGraphModelAdapter<CustomTreeNode, CustomEdge>(graph);
		calculateNodePosition();
	}

	private void createGraphNodes() {
		ArrayList<CustomTreeNode> childs = tree.getChildren();

		graph.addVertex(tree.getRoot());
		for (int i = 0; i < childs.size(); i++) {
			graph.addVertex(childs.get(i));
		}
	}

	private void createGraphEdges() {
		CustomTreeNode root = tree.getRoot();
		ArrayList<CustomTreeNode> children = tree.getChildren();

		for (int i = 0; i < children.size(); i++) {
			CustomTreeNode curNode = children.get(i);
			CustomEdge edge = new CustomEdge();

			if (curNode.isPartOfBundle()) {
				edge.setBundleNumber(curNode.getBundle());
			}

			graph.addEdge(curNode, root, edge);
		}

	}

	/**
	 * Because jgAdapter.getRoot() gives back a list with Nodes and Edges this
	 * method filters out all Edges
	 */
	private ArrayList<DefaultGraphCell> listOfGraphNodes() {
		List all = jgAdapter.getRoots();
		ArrayList<DefaultGraphCell> graphNodes = new ArrayList<DefaultGraphCell>();

		for (int i = 0; i < all.size(); i++) {
			DefaultGraphCell cell = (DefaultGraphCell) all.get(i);
			if (cell.getUserObject() instanceof CustomTreeNode) {
				graphNodes.add(cell);
			}
		}
		return graphNodes;

	}

	/**
	 * Calculates the Position for all nodes in the Graph, currently only with X
	 * causes and one consequence (Wirkung).
	 */
	private void calculateNodePosition() {
		ArrayList<DefaultGraphCell> graphNodes = listOfGraphNodes();
		// Values for Child Nodes
		int leftBorder = 30;
		int x = leftBorder;
		int y = 30;
		int gapBetweenNodes = 50;

		// Values for Root Node
		int yRoot = 0;
		int xRoot = 0;

		for (int i = 0; i < graphNodes.size(); i++) {
			DefaultGraphCell curCell = graphNodes.get(i);
			CustomTreeNode curNode = (CustomTreeNode) curCell.getUserObject();

			if (curNode.isRoot()) {
				// Calculating the x-Coordinate of the root (Wirkung) in the
				// Graph in a way it will always displayed in the
				// x-coordinate-middle of the causes.

				xRoot = ((nodeWidth * (graphNodes.size() - 1))
						+ (gapBetweenNodes * (graphNodes.size() - 2)) + leftBorder)
						/ 2 - (nodeWidth / 2) + (leftBorder / 2);
				yRoot = 300;

				positionNode(curCell, xRoot, yRoot);

			} else {
				positionNode(curCell, x, y);

				// Count x Value up
				x = x + nodeWidth + gapBetweenNodes;
			}
		}
		calculateAppletSize(gapBetweenNodes, leftBorder, yRoot,
				graphNodes.size(), y);
	}

	/**
	 * This Method calculates the size of the applet window with respect to
	 * number of causes in the graph
	 */
	private void calculateAppletSize(int xGap, int initXValue, int yRoot,
			int numberOfNodes, int y) {
		sizeAppletX = ((nodeWidth * (numberOfNodes - 1))
				+ (xGap * (numberOfNodes - 2)) + 2 * initXValue);
		sizeAppletY = yRoot + nodeHeight + y;
	}

	/** Positions the given JgraphT cell in the JGraphModelAdapter */
	private void positionNode(DefaultGraphCell cell, int x, int y) {
		AttributeMap attr = cell.getAttributes();
		Rectangle2D bounds = GraphConstants.getBounds(attr);

		Rectangle2D newBounds = new Rectangle2D.Double(x, y, nodeWidth,
				nodeHeight);

		GraphConstants.setBounds(attr, newBounds);
		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		jgAdapter.edit(cellAttr, null, null, null);
	}

	public static DefaultDirectedGraph<CustomTreeNode, CustomEdge> getGraph() {
		return graph;
	}

	public JGraphModelAdapter<CustomTreeNode, CustomEdge> getJgAdapter() {
		return jgAdapter;
	}

	public int getSizeAppletx() {
		return sizeAppletX;
	}

	public int getSizeApplety() {
		return sizeAppletY;
	}

}
