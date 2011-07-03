package parser;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import models.CustomEdge;
import trees.CustomTree;
import trees.CustomTreeNode;
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

		createNodes();
		createEdges();
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
