package parser;

import java.util.ArrayList;
import java.util.HashMap;

import tree.CustomTree;
import tree.CustomTreeNode;

public class TreeToTable {
	private CustomTree tree;
	private int table[][];
	private HashMap<Integer, String> bundles;
	private static ArrayList<CustomTreeNode> nodes;

	public TreeToTable(CustomTree tree, int numberOfFactors, int numberOfBundles) {
		this.tree = tree;
		System.out.println("Generated Tree to String: " + tree.toString());
		table = new int[(int) Math.pow(2, numberOfFactors)][numberOfFactors + 1];
		tree.toString();
		bundles = tree.getBundles();
		nodes = tree.getChildren();
		for (int i = nodes.size() - 1; i >= 0; i--) {
			if ((nodes.get(i).toString().substring(0, 1).equals("X"))
					|| (nodes.get(i).toString().substring(0, 1).equals("Y"))) {
				nodes.remove(i);
			}
		}
		nodes.add(new CustomTreeNode("W"));

		generatePermutations();
		fillTable();
		printTable();
	}

	private void generatePermutations() {

	}

	private void fillTable() {
		// Mit der Annahme, dass alle Faktoren kausal relevant sind die im
		// Graphen aufgeführt sind gilt folgendes:
		for (int i = 0; i < table[0].length; i++) {
			table[0][i] = 0;
			table[1][i] = 1;
		}
	}

	public void printTable() {
		for (int i = 0; i < nodes.size(); i++) {
			System.out.print("  " + nodes.get(i).toString());
		}
		System.out.println("");
		for (int r = 0; r < table.length; r++) {
			for (int c = 0; c < table[r].length; c++) {
				System.out.print("  " + table[r][c]);
			}
			System.out.println("");
		}
	}
}
