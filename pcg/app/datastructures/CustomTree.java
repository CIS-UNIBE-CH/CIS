package datastructures;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.tree.DefaultTreeModel;

/** Represents a graph in a tree datastructure */
public class CustomTree {

    private static DefaultTreeModel tree;
    private static ArrayList<Node> childs;
    private HashMap<Integer, String> bundles;

    public CustomTree() {
	bundles = new HashMap<Integer, String>();
	Node root = new Node("DefaultRoot");
	tree = new DefaultTreeModel(root);
	childs = new ArrayList<Node>();
    }

    public HashMap<Integer, String> getBundles() {
	return bundles;
    }

    public static Node getRoot() {
	return (Node) tree.getRoot();
    }

    public static void setRoot(Node root) {
	tree.setRoot(root);
    }

    /** Ads a child to specified root or parent node */
    public static void addChildtoParentX(Node child, Node parent) {
	parent.add(child);
    }

    /**
     * gets the childs of the root node in the tree
     * 
     * @return ArrayList with all childs of the root node in the tree
     */
    public static ArrayList<Node> getChildren() {
	childs.clear();
	traverse(tree);
	return childs;
    }

    /**
     * Übernommen von Internet, macht tree walk
     * 
     * @param model
     *            the tree data structure
     */
    private static void traverse(DefaultTreeModel model) {
	Node root;
	if (model != null) {
	    root = (Node) model.getRoot();
	    walk(model, root);
	} else {
	    System.out.println("Tree is empty.");
	}
    }

    /**
     * Übernommen von Internet, macht tree walk
     * 
     * @param model
     *            the tree data structure
     * @param root
     *            the root node in the tree
     */
    private static void walk(DefaultTreeModel model, Node root) {
	int childCount;
	childCount = model.getChildCount(root);
	for (int i = 0; i < childCount; i++) {
	    Node child = (Node) model.getChild(root, i);
	    if (model.isLeaf(child)) {
		childs.add(child);
	    } else {
		childs.add(child);
		walk(model, child);
	    }
	}
    }

    @Override
    public String toString() {
	childs = getChildren();
	String datastructurestring = "";
	bundles.clear();
	int key, k = 0;
	for (Node child : childs) {
	    if (child.getBundle() != null)
		key = Integer.parseInt(child.getBundle());
	    else {
		key = k;
		k--;
	    }
	    if (bundles.get(key) != null)
		bundles.put(key, bundles.get(key) + child.toString());
	    else
		bundles.put(key, child.toString());
	}
	//System.out.println(bundles);
	for (int i = 1; i <= bundles.size() + k; i++) {
	    datastructurestring += bundles.get(i).toString() + " ∨ ";
	}

	for (int i = 0; i > (k + 1); i--) {
	    datastructurestring += bundles.get(i) + " v ";
	}

	datastructurestring += bundles.get(k + 1);

	return "(" + datastructurestring + ")";
    }
}
