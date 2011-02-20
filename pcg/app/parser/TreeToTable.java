package parser;

import tree.CustomTree;

public class TreeToTable {
	private CustomTree tree;
	private int table[][];

	public TreeToTable(CustomTree tree, int numberOfFactors, int numberOfBundles) {
		this.tree = tree;
		System.out.println("Generated Tree to String: " + tree.toString());
		table = new int[(int) Math.pow(2, numberOfFactors)][numberOfFactors + 1];

		string();
	}

	public void string() {
		for (int r = 0; r < table.length; r++) {
			for (int c = 0; c < table[r].length; c++) {
				System.out.print(" " + table[r][c]);
			}
			System.out.println("");
		}
	}
}
