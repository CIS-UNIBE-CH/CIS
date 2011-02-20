package parser;

import java.util.Scanner;

import tree.CustomTree;

public class TreeToTable {
	private CustomTree tree;
	private int table[][];

	public TreeToTable(CustomTree tree, int numberOfFactors, int numberOfBundles) {
		this.tree = tree;
		System.out.println("Generated Tree to String: " + tree.toString());
		table = new int[(int) Math.pow(2, numberOfFactors)][numberOfFactors + 1];

		determineFactorsAndBundles();
		fillTable();
		printTable();
	}

	private void determineFactorsAndBundles() {
		String factors = tree.toString();
		Scanner scanner = new Scanner(factors);
		scanner.useDelimiter(" ∨ ");
		while (scanner.hasNext()) {
			System.out.println(scanner.next());
		}

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

		for (int r = 0; r < table.length; r++) {
			for (int c = 0; c < table[r].length; c++) {
				System.out.print(" " + table[r][c]);
			}
			System.out.println("");
		}
	}
}
