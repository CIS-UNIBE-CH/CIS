package parsers;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import models.CustomEdge;
import datastructures.CustomTree;
import datastructures.CustomTreeNode;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * Builds out of the tree a JUNG Graph's nodes and edges.
 */
public class TreeToGraph {
    // Used OrderedSparseMultigraph, this order nodes after their adding time.
    // So make sure the nodes will be generated in right oder in the method
    // createsNodes()
    private static OrderedSparseMultigraph<CustomTreeNode, CustomEdge> graph;
    // The tree (internal data structure)
    private CustomTree tree;

    public TreeToGraph(CustomTree tree) {
	graph = new OrderedSparseMultigraph<CustomTreeNode, CustomEdge>();

	this.tree = tree;
	graph.addVertex(tree.getRoot());

	// createNodes();
	// createEdges();
	createAll(tree.getRoot());
    }

    private void createAll(CustomTreeNode parent) {
	// if (parent.isLeaf()) {

	// } else {
	/*
	 * for (int i = 0; i < parent.getChildCount(); i++) { CustomTreeNode
	 * curChild = (CustomTreeNode) parent.getChildAt(i);
	 * System.out.println("Node: " + curChild);
	 * 
	 * graph.addVertex(curChild);
	 * 
	 * CustomEdge edge = new CustomEdge(curChild, parent);
	 * 
	 * if (curChild.isPartOfBundle()) {
	 * edge.setBundleNumber(curChild.getBundle()); } graph.addEdge(edge,
	 * curChild, parent, EdgeType.DIRECTED); }
	 */
//	System.out.println("********");
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CustomTreeNode curChild = (CustomTreeNode) parent.getChildAt(i);
	    createAll(curChild);

	    System.out.println("NodeTree: " + curChild);

	    graph.addVertex(curChild);

	    CustomEdge edge = new CustomEdge(curChild, parent);

	    if (curChild.isPartOfBundle()) {
		edge.setBundleNumber(curChild.getBundle());
	    }
	    graph.addEdge(edge, curChild, parent, EdgeType.DIRECTED);
	}
	// }
    }

    private void createNodes() {
	ArrayList<CustomTreeNode> childs = tree.getChildren();

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
	    CustomEdge edge = new CustomEdge(curNode, root);

	    if (curNode.isPartOfBundle()) {
		edge.setBundleNumber(curNode.getBundle());
	    }

	    graph.addEdge(edge, curNode, root, EdgeType.DIRECTED);
	}

    }

    public static OrderedSparseMultigraph<CustomTreeNode, CustomEdge> getGraph() {
	return graph;
    }
}
