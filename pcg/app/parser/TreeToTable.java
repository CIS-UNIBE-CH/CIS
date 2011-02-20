package parser;

import tree.CustomTree;

public class TreeToTable {
	private CustomTree tree;

	public TreeToTable(CustomTree tree) {
		this.tree = tree;
		System.out.println("Generated Tree to String: " + tree.toString());
	}
}
