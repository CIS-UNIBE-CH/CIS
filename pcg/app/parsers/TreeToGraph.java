package parsers;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

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
    private int depthInit;

    public TreeToGraph(CustomTree tree, int numberOfEffects) {
	depthInit = numberOfEffects + 1;
	graph = new OrderedSparseMultigraph<CustomTreeNode, CustomEdge>();
	this.tree = tree;
	
	CustomTreeNode root = this.tree.getRoot();
	graph.addVertex(root);
	createTree(root);
	root.setEffectLevel(depthInit);
	root.setIndex(1);
	setDepth(root, depthInit -= 1);
    }

    private void createTree(CustomTreeNode parent) {
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CustomTreeNode curChild = (CustomTreeNode) parent.getChildAt(i);
	    curChild.setIndex(i + 1);
	    graph.addVertex(curChild);
	    CustomEdge edge = new CustomEdge(curChild, parent);

	    if (curChild.isPartOfBundle()) {
		edge.setBundleNumber(curChild.getBundle());
	    }
	    graph.addEdge(edge, curChild, parent, EdgeType.DIRECTED);
	    createTree(curChild);
	}
    }

    private void setDepth(CustomTreeNode parent, int depth) {
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CustomTreeNode curChild = (CustomTreeNode) parent.getChildAt(i);
	    curChild.setEffectLevel(depth);
	}
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CustomTreeNode curChild = (CustomTreeNode) parent.getChildAt(i);
	    if (!curChild.isLeaf()) {
		setDepth(curChild, depth -= 1);
	    }
	}
    }

    public static OrderedSparseMultigraph<CustomTreeNode, CustomEdge> getGraph() {
	return graph;
    }
}
