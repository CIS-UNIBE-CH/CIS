//package parsers;
//
///** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */
//
//import datastructures.Edge;
//import datastructures.CustomTree;
//import datastructures.Node;
//import datastructures.Graph;
//import edu.uci.ics.jung.graph.util.EdgeType;
//
///**
// * Builds out of the tree a JUNG Graph's nodes and edges.
// */
//public class TreeToGraph {
//    // Used OrderedSparseMultigraph, this order nodes after their adding time.
//    // So make sure the nodes will be generated in right oder in the method
//    // createsNodes()
//    private static Graph<Node, Edge> graph;
//    // The tree (internal data structure)
//    private CustomTree tree;
//    private int depthInit;
//    private int totalFactors;
//    private int heigt;
//
//    public TreeToGraph(CustomTree tree, int numberOfEffects, int numberOfFactors) {
//	heigt = numberOfEffects + 1;
//	depthInit = numberOfEffects + 1;
//	this.totalFactors = numberOfFactors;
//	graph = new Graph<Node, Edge>();
//	this.tree = tree;
//
//	Node root = this.tree.getRoot();
//	graph.addVertex(root);
//	createTree(root);
//	root.setEffectLevel(depthInit);
//	root.setIndex(1);
//	setDepth(root, depthInit -= 1);
//    }
//
//    // Do a reverse tree walk
//    private void createTree(Node parent) {
//	for (int i = 0; i < parent.getChildCount(); i++) {
//	    Node curChild = (Node) parent.getChildAt(i);
//	    if (!curChild.isLeaf()) {
//		createTree(curChild);
//		curChild.setIndex(i + 1);
//		graph.addVertex(curChild);
//		Edge edge = new Edge(curChild, parent);
//
//		if (curChild.isPartOfBundle()) {
//		    edge.setBundleNumber(curChild.getBundle());
//		}
//		graph.addEdge(edge, curChild, parent, EdgeType.DIRECTED);
//	    }
//	}
//	for (int i = 0; i < parent.getChildCount(); i++) {
//	    Node curChild = (Node) parent.getChildAt(i);
//	    if (curChild.isLeaf()) {
//		curChild.setIndex(i + 1);
//		graph.addVertex(curChild);
//		Edge edge = new Edge(curChild, parent);
//
//		if (curChild.isPartOfBundle()) {
//		    edge.setBundleNumber(curChild.getBundle());
//		}
//		graph.addEdge(edge, curChild, parent, EdgeType.DIRECTED);
//	    }
//	}
//
//    }
//
//    private void setDepth(Node parent, int depth) {
//	for (int i = 0; i < parent.getChildCount(); i++) {
//	    Node curChild = (Node) parent.getChildAt(i);
//	    curChild.setEffectLevel(depth);
//	}
//	for (int i = 0; i < parent.getChildCount(); i++) {
//	    Node curChild = (Node) parent.getChildAt(i);
//	    if (!curChild.isLeaf()) {
//		setDepth(curChild, depth -= 1);
//	    }
//	}
//    }
//
//    public static Graph<Node, Edge> getGraph() {
//	return graph;
//    }
//
//    public CustomTree getTree() {
//	return tree;
//    }
//
//    public int getTotalFactors() {
//	return totalFactors;
//    }
//
//    public int getHeigt() {
//	return heigt;
//    }
// }
