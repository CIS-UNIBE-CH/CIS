package parsers;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import models.CustomGraphEdge;
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
    private static OrderedSparseMultigraph<CustomTreeNode, CustomGraphEdge> graph;
    // The tree (internal data structure)
    private CustomTree tree;
    private int depthInit;
    private int totalFactors;
    private int heigt;

    public TreeToGraph(CustomTree tree, int numberOfEffects, int numberOfFactors) {
	heigt = numberOfEffects + 1;
	depthInit = numberOfEffects + 1;
	this.totalFactors = numberOfFactors;
	graph = new OrderedSparseMultigraph<CustomTreeNode, CustomGraphEdge>();
	this.tree = tree;

	CustomTreeNode root = this.tree.getRoot();
	graph.addVertex(root);
	createTree(root);
	root.setEffectLevel(depthInit);
	root.setIndex(1);
	setDepth(root, depthInit -= 1);
    }

    //Do a reverse tree walk
    private void createTree(CustomTreeNode parent) {
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CustomTreeNode curChild = (CustomTreeNode) parent.getChildAt(i);
	    if (!curChild.isLeaf()) {
		createTree(curChild);
		curChild.setIndex(i + 1);
		graph.addVertex(curChild);
		CustomGraphEdge edge = new CustomGraphEdge(curChild, parent);

		if (curChild.isPartOfBundle()) {
		    edge.setBundleNumber(curChild.getBundle());
		}
		graph.addEdge(edge, curChild, parent, EdgeType.DIRECTED);
	    }
	}
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CustomTreeNode curChild = (CustomTreeNode) parent.getChildAt(i);
	    if (curChild.isLeaf()) {
		curChild.setIndex(i + 1);
		graph.addVertex(curChild);
		CustomGraphEdge edge = new CustomGraphEdge(curChild, parent);

		if (curChild.isPartOfBundle()) {
		    edge.setBundleNumber(curChild.getBundle());
		}
		graph.addEdge(edge, curChild, parent, EdgeType.DIRECTED);
	    }
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

    public static OrderedSparseMultigraph<CustomTreeNode, CustomGraphEdge> getGraph() {
	return graph;
    }

    public CustomTree getTree() {
        return tree;
    }

    public int getTotalFactors() {
        return totalFactors;
    }

    public int getHeigt() {
        return heigt;
    }
}
