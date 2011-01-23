package tree;

import java.util.ArrayList;

import javax.swing.tree.DefaultTreeModel;

/** Represents a graph in a tree datastructure */
public class CustomTree {

	private static DefaultTreeModel tree;
	private static ArrayList<CustomTreeNode> childs;

	public CustomTree() {
		CustomTreeNode root = new CustomTreeNode("DefaultRoot");
		tree = new DefaultTreeModel(root);
		childs = new ArrayList<CustomTreeNode>();
	}

	public static CustomTreeNode getRoot() {
		return (CustomTreeNode) tree.getRoot();
	}

	public static void setRoot(CustomTreeNode root) {
		tree.setRoot(root);
	}

	/** Ads a child to specified root or parent node */
	public static void addChildtoRootX(CustomTreeNode child, CustomTreeNode root) {
		root.add(child);
	}

	/**
	 * gets the childs of the root node in the tree
	 * 
	 * @return ArrayList with all childs of the root node in the tree
	 */
	public static ArrayList<CustomTreeNode> getChildren() {
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
		CustomTreeNode root;
		if (model != null) {
			root = (CustomTreeNode) model.getRoot();
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
	private static void walk(DefaultTreeModel model, CustomTreeNode root) {
		int childCount;
		childCount = model.getChildCount(root);
		for (int i = 0; i < childCount; i++) {
			CustomTreeNode child = (CustomTreeNode) model.getChild(root, i);
			if (model.isLeaf(child)) {
				System.out.println(child);
				childs.add(child);
			} else {
				System.out.println(child);
				childs.add(child);
				walk(model, child);
			}
		}
	}

}
