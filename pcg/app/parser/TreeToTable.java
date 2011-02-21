package parser;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;
import java.util.HashMap;

import tree.CustomTree;
import tree.CustomTreeNode;

public class TreeToTable {
	private CustomTree tree;
	private String table[][];
	private HashMap<Integer, String> bundles;
	private static ArrayList<CustomTreeNode> nodes;
	private int bundleSize = 2;
	private int numberOfFactors;
	private int numberOfBundles;

	public TreeToTable(CustomTree tree, int numberOfFactors, int numberOfBundles) {
		this.tree = tree;
		this.numberOfBundles = numberOfBundles;
		this.numberOfFactors = numberOfFactors;
		table = new String[(int) (Math.pow(2, numberOfFactors)) + 1][numberOfFactors + 1];

		System.out.println("Generated Tree to String: " + tree.toString());
		tree.toString(); // getBundles() needs a toString() for initalize
							// HashMap
		bundles = tree.getBundles();

		// Init the Process
		filterXAndYFactors();
		generateCoincidenceTable();
		generateEffectColumn();
		printTable();
	}

	private void filterXAndYFactors() {
		nodes = tree.getChildren();
		for (int i = nodes.size() - 1; i >= 0; i--) {
			if ((nodes.get(i).toString().substring(0, 1).equals("X"))
					|| (nodes.get(i).toString().substring(0, 1).equals("Y"))) {
				nodes.remove(i);
			}
		}
		nodes.add(new CustomTreeNode("W"));
	}

	private void generateCoincidenceTable() {
		ArrayList<String> numbers = new ArrayList<String>();

		// Generate Binary Numbers
		for (Integer i = 0; i < table.length - 1; i++) {
			String valueBin = Integer.toBinaryString(i);
			int valueBinLength = valueBin.length();

			// Fill in Zeros
			if (valueBinLength != (numberOfFactors)) {
				while (valueBin.length() < numberOfFactors) {
					valueBin = "0" + valueBin;
				}
			}
			numbers.add(valueBin);
		}

		// Fill in Table
		for (int r = 1; r < table.length; r++) {
			String curRow = numbers.get(r - 1);

			for (int c = 0; c < table[r].length - 1; c++) {
				table[r][c] = Character.toString(curRow.charAt(c));
			}
		}

	}

	private void generateEffectColumn() {
		int relevanceCounter = 0;
		int bundleSizeCounter = 0;

		for (int r = 1; r < table.length; r++) {

			// Bundles
			for (int i = 0; i < numberOfBundles * bundleSize; i++) {
				relevanceCounter = relevanceCounter
						+ Integer.parseInt(table[r][i]);
				bundleSizeCounter++;

				if (bundleSizeCounter == bundleSize
						&& relevanceCounter == bundleSize) {
					relevanceCounter = 0;
					bundleSizeCounter = 0;
					table[r][table[r].length - 1] = "1";

					// Do a break because, every "1" more is a overdetermination
					break;
				} else if (bundleSizeCounter == bundleSize) {
					bundleSizeCounter = 0;
					relevanceCounter = 0;
				}

			}
			relevanceCounter = 0;

			// Set all "null" to a Zero to avoid NullPointerException
			if (table[r][table[r].length - 1] == null) {
				table[r][table[r].length - 1] = "0";
			}

			// Co-Factors
			for (int i = numberOfBundles * bundleSize; i < table[r].length - 1; i++) {
				relevanceCounter = relevanceCounter
						+ Integer.parseInt(table[r][i]);
			}
			if (!table[r][table[r].length - 1].equals("1")) {
				if (relevanceCounter > 0) {
					table[r][table[r].length - 1] = "1";

					// Do a break because, every "1" more is a overdetermination
					break;
				} else {
					table[r][table[r].length - 1] = "0";
				}
			}
			relevanceCounter = 0;
		}

	}

	public void printTable() {
		// Print factor names
		for (int i = 0; i < table[0].length; i++) {
			table[0][i] = nodes.get(i).toString();
		}
		System.out.println("");

		// Print Coincidences
		for (int r = 0; r < table.length; r++) {
			System.out.print(r + "  ");
			for (int c = 0; c < table[r].length; c++) {
				System.out.print("  " + table[r][c]);
			}
			System.out.println("");
		}
	}
}
