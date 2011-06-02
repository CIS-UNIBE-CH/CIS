package parser;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import models.CustomEdge;
import tree.CustomTree;
import tree.CustomTreeNode;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * Builds out of the tree a JGraphT and adds everything to a JGraphModelAdapter,
 * also known as parsing a tree to a JGraphT.
 */
public class TreeToGraph {
	// A JGraphT directed Graph
	// Used ordered Sparse multigraph, this order nodes after their adding time.
	// So make shure the nodes will be generated in right oder in the method
	// createsNodes()
	private static OrderedSparseMultigraph<CustomTreeNode, CustomEdge> graph;
	// The tree (internal data structure)
	private CustomTree tree;
	// The Adapter used for displaying JGraphT in JGraph
	// private JGraphModelAdapter<CustomTreeNode, CustomEdge> jgAdapter;

	private int nodeWidth;
	private int nodeHeight;
	private int sizeAppletX;
	private int sizeAppletY;

	public TreeToGraph(CustomTree tree) {
		nodeWidth = 100;
		nodeHeight = 30;

		graph = new OrderedSparseMultigraph<CustomTreeNode, CustomEdge>();

		this.tree = tree;

		// Initialize creating JGraphT and position node procedure
		createNodes();
		createEdges();
		// jgAdapter = new JGraphModelAdapter<CustomTreeNode,
		// CustomEdge>(graph);
		// calculateJGraphNodePosition();
	}

	private void createNodes() {
		ArrayList<CustomTreeNode> childs = tree.getChildren();
		for (int i = 0; i < childs.size(); i++) {
			System.out.println("Child: " + childs.get(i));
		}

		graph.addVertex(tree.getRoot());
		for (int i = 0; i < childs.size(); i++) {
			graph.addVertex(childs.get(i));
		}
	}

	private void createEdges() {
		CustomTreeNode root = tree.getRoot();
		ArrayList<CustomTreeNode> children = tree.getChildren();

		for (int i = 0; i < children.size(); i++) {
			CustomTreeNode curNode = children.get(i);
			// System.out.println("CurNode: " + curNode.getName());
			CustomEdge edge = new CustomEdge();

			if (curNode.isPartOfBundle()) {
				edge.setBundleNumber(curNode.getBundle());
			}

			graph.addEdge(edge, curNode, root, EdgeType.DIRECTED);
		}

	}

	/**
	 * Calculates the Position for all nodes in the Graph, currently only with x
	 * causes and one consequence (Wirkung).
	 */
	/*
	 * private void calculateJGraphNodePosition() { ArrayList<DefaultGraphCell>
	 * graphNodes = listOfJGraphTNodes(); // Values for Child Nodes int
	 * leftOffset = 30; int x = leftOffset; int y = 30; int gapBetweenNodes =
	 * 50;
	 * 
	 * // Values for Root Node int yRoot = 0; int xRoot = 0;
	 * 
	 * for (int i = 0; i < graphNodes.size(); i++) { DefaultGraphCell curCell =
	 * graphNodes.get(i); CustomTreeNode curNode = (CustomTreeNode)
	 * curCell.getUserObject();
	 * 
	 * if (curNode.isRoot()) { // Calculating the x-Coordinate of the root
	 * (Wirkung) in the // Graph in a way it will always displayed in the //
	 * x-coordinate-middle of the causes.
	 * 
	 * xRoot = ((nodeWidth * (graphNodes.size() - 1)) + (gapBetweenNodes *
	 * (graphNodes.size() - 2)) + leftOffset) / 2 - (nodeWidth / 2) +
	 * (leftOffset / 2); yRoot = 300;
	 * 
	 * positionJGraphNode(curCell, xRoot, yRoot);
	 * 
	 * } else { positionJGraphNode(curCell, x, y);
	 * 
	 * // Count x Value up x = x + nodeWidth + gapBetweenNodes; } }
	 * calculateJFrameSize(gapBetweenNodes, leftOffset, yRoot, graphNodes
	 * .size(), y); }
	 * 
	 * /** Positions the given JgraphT cell in the JGraphModelAdapter
	 * 
	 * @param cell a cell of JGraphModelAdapter, equals a node
	 * 
	 * @param x x coordinate of node as integer
	 * 
	 * @param y y coordinate of node as integer
	 */
	/*
	 * private void positionJGraphNode(DefaultGraphCell cell, int x, int y) {
	 * AttributeMap attr = cell.getAttributes(); Rectangle2D bounds =
	 * GraphConstants.getBounds(attr);
	 * 
	 * Rectangle2D newBounds = new Rectangle2D.Double(x, y, nodeWidth,
	 * nodeHeight);
	 * 
	 * GraphConstants.setBounds(attr, newBounds);
	 * GraphConstants.setBackground(attr, new Color(102, 102, 255));
	 * 
	 * AttributeMap cellAttr = new AttributeMap(); cellAttr.put(cell, attr);
	 * jgAdapter.edit(cellAttr, null, null, null); }
	 * 
	 * /** This Method calculates the size of the Jframe window with respect to
	 * number of causes in the graph.
	 * 
	 * @param gapBetweenNodes size of gap between two graph nodes
	 * 
	 * @param leftOffset offset between left border of JFrame and first node
	 * 
	 * @param yRoot y coordinate for tree root
	 * 
	 * @param numberOfNodes total number of nodes in tree
	 * 
	 * @param topOffset y coordinate of causes in graph
	 */
	/*
	 * private void calculateJFrameSize(int gapBetweenNodes, int leftOffset, int
	 * yRoot, int numberOfNodes, int topOffset) { sizeAppletX = ((nodeWidth *
	 * (numberOfNodes - 1)) + (gapBetweenNodes * (numberOfNodes - 2)) + 2 *
	 * leftOffset); sizeAppletY = yRoot + nodeHeight + topOffset; }
	 * 
	 * /** Because jgAdapter.getRoot() gives back a list with Nodes and Edges
	 * this method filters out all Edges
	 * 
	 * @return ArrayList with all nodes in the JGraphT Graph
	 */
	/*
	 * private ArrayList<DefaultGraphCell> listOfJGraphTNodes() { List all =
	 * jgAdapter.getRoots(); ArrayList<DefaultGraphCell> graphNodes = new
	 * ArrayList<DefaultGraphCell>();
	 * 
	 * for (int i = 0; i < all.size(); i++) { DefaultGraphCell cell =
	 * (DefaultGraphCell) all.get(i); if (cell.getUserObject() instanceof
	 * CustomTreeNode) { graphNodes.add(cell); } } return graphNodes;
	 * 
	 * }
	 */

	public static OrderedSparseMultigraph<CustomTreeNode, CustomEdge> getGraph() {
		return graph;
	}

	// public JGraphModelAdapter<CustomTreeNode, CustomEdge> getJgAdapter() {
	// return jgAdapter;
	// }

	public int getSizeAppletx() {
		return sizeAppletX;
	}

	public int getSizeApplety() {
		return sizeAppletY;
	}

}
