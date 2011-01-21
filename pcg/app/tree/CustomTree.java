package tree;

import java.util.ArrayList;

import javax.swing.tree.DefaultTreeModel;

/** Represents a graph as a tree */
public class CustomTree {
	private static DefaultTreeModel tree;
	private static ArrayList<CustomTreeNode> childs;
	private static CustomTree INSTANCE = new CustomTree();

	/** Custom Tree is a singelton */
	public static CustomTree getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CustomTree();
		}
		return INSTANCE;

	}

	private CustomTree() {
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

	public static void addChildtoRootX(CustomTreeNode child, CustomTreeNode root) {
		root.add(child);
	}

	public static ArrayList<CustomTreeNode> getChildren() {
		childs.clear();
		traverse(tree);
		return childs;
	}

	/** Übernommen von Internet */
	private static void traverse(DefaultTreeModel model) {
		CustomTreeNode root;
		if (model != null) {
			root = (CustomTreeNode) model.getRoot();
			walk(model, root);
		} else {
			System.out.println("Tree is empty.");
		}
	}

	/** Übernommen von Internet */
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
