package util;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import models.Plotter;
import parser.TreeToJgraph;
import tree.CustomTree;
import tree.CustomTreeNode;

/**
 * TODO: Erkennung der Wirkung! TODO: Currently work only with number of bundles
 * <= 2 and x CoFactors
 */
public class ComplexTest {
	private String[][] table;
	private ArrayList<Integer> colIndexesCoFactors;
	private ArrayList<Integer> rowIndexesBundles;
	private ArrayList<ArrayList<String>> tableAsList;
	private CustomTree tree;

	public ComplexTest(String[][] table) {
		this.table = table;
		colIndexesCoFactors = new ArrayList<Integer>();
		rowIndexesBundles = new ArrayList<Integer>();
		tableAsList = new ArrayList<ArrayList<String>>();

		// Fill in 2D-ArrayList, remove all "Zero Lines"
		// Attention: table.length-1 means that the last line will no be added
		// because it's the line with only ones.
		for (int r = 0; r < table.length - 1; r++) {
			if (r == 0 || table[r][table[r].length - 1].equals("1")) {
				tableAsList.add(new ArrayList<String>());
				for (int c = 0; c < table[r].length; c++) {
					tableAsList.get(tableAsList.size() - 1).add(table[r][c]);
				}
			}
		}
		// Init Stuff, do not change order!
		identifyCoFactors();
		removeCoFactors();
		identifyBundles();
		createTree();
	}

	private void identifyCoFactors() {
		int colIndexCoFactor = 0;

		for (int r = 1; r < tableAsList.size(); r++) {
			boolean isTheFirst1 = true;
			int rowCounter = 0;

			for (int c = 0; c < tableAsList.get(r).size(); c++) {
				rowCounter = rowCounter
						+ Integer.parseInt(tableAsList.get(r).get(c));

				// Die erste in der Zeile aufgetrende 1 sich merken, denn sie
				// könnte Index für Co-Factor sein. (Spart Schlaufendurchläufe).
				if (tableAsList.get(r).get(c).equals("1") && isTheFirst1) {
					colIndexCoFactor = c;
					isTheFirst1 = false;
				}
			}
			if (rowCounter == 2) {
				colIndexesCoFactors.add(colIndexCoFactor);
				rowCounter = 0;
			}

		}
	}

	/**
	 * Will remove all Lines in the table where a co factor and the "Wirkung are
	 * instantiate, because they aren't needed any longer. Furthermore the
	 * collumns of the co-factors will be removed from the table
	 */
	private void removeCoFactors() {
		int colIndex = 0;
		ArrayList<ArrayList<String>> tableListTemp = new ArrayList<ArrayList<String>>();

		boolean badRow = false;

		// Add to temp table the first line
		tableListTemp.add(new ArrayList<String>());
		tableListTemp.get(0).addAll(tableAsList.get(0));

		// Filters out all rows where a Co-Factor and the "Wirkung" is
		// instantiated
		for (int i = 1; i < tableAsList.size(); i++) {
			for (int j = 0; j < colIndexesCoFactors.size(); j++) {
				colIndex = colIndexesCoFactors.get(j);

				if (tableAsList.get(i).get(colIndex).equals("1")) {
					badRow = true;
				}
			}

			if (!badRow) {
				// Add the good Row to temp ArrayList
				tableListTemp.add(new ArrayList<String>());
				tableListTemp.get(tableListTemp.size() - 1).addAll(
						tableAsList.get(i));
			}
			badRow = false;
		}
		tableAsList = tableListTemp;

		// Filter out all Columns of the Co-Factors
		for (int i = 0; i < tableAsList.size(); i++) {
			for (int j = 0; j < colIndexesCoFactors.size(); j++) {
				int index = colIndexesCoFactors.get(j);
				tableAsList.get(i).remove(index);
			}
		}

	}

	private void identifyBundles() {
		int actualRow = 0;
		int rowToTest = 0;
		String referenceBinary = "";

		for (int k = 0; k < tableAsList.get(0).size() - 1; k++) {
			referenceBinary = referenceBinary + "1";
		}
		// Für Spezialfall, wenn nur 1 Bündel und 1 Faktor
		// Warning: Works only with Bundles of size=2
		if (referenceBinary.equals("11")) {
			tableAsList.add(new ArrayList<String>());
			for (int c = 0; c < tableAsList.size(); c++) {
				tableAsList.get(tableAsList.size() - 1).add("0");
			}
		}

		for (int i = 1; i < tableAsList.size(); i++) {
			actualRow = rowAsInt(tableAsList.get(i).toString());

			for (int j = 1; j < tableAsList.size(); j++) {
				rowToTest = rowAsInt(tableAsList.get(j).toString());

				// Do a binary OR
				int result = actualRow ^ rowToTest;

				if (referenceBinary.equals(Integer.toBinaryString(result))) {
					rowIndexesBundles.add(i);
					if (referenceBinary.equals("11")) {
						i = tableAsList.size();
					}
				}
			}
		}

	}

	private void createTree() {
		tree = new CustomTree();
		CustomTreeNode root = new CustomTreeNode("W");
		tree.setRoot(root);
		int bundleCounter = 1;

		// Bundles
		for (int j = 0; j < rowIndexesBundles.size(); j++) {
			int curRowIndex = rowIndexesBundles.get(j);

			for (int colIndex = 0; colIndex < tableAsList.get(0).size() - 1; colIndex++) {
				if (tableAsList.get(curRowIndex).get(colIndex).equals("1")) {
					CustomTreeNode curNode = new CustomTreeNode(tableAsList
							.get(0).get(colIndex));
					curNode.setBundle(String.valueOf(bundleCounter));
					tree.addChildtoRootX(curNode, root);
				}
			}
			CustomTreeNode x = new CustomTreeNode("X" + bundleCounter);
			x.setBundle(String.valueOf(bundleCounter));
			tree.addChildtoRootX(x, root);
			bundleCounter = bundleCounter + 1;
		}

		// Co-factors
		boolean hasCoFactors = false;
		for (int i = 0; i < colIndexesCoFactors.size(); i++) {
			int curIndex = colIndexesCoFactors.get(i);
			CustomTreeNode curNode = new CustomTreeNode(table[0][curIndex]);
			tree.addChildtoRootX(curNode, root);
			hasCoFactors = true;
		}
		if (hasCoFactors) {
			CustomTreeNode y = new CustomTreeNode("Y");
			tree.addChildtoRootX(y, root);
		}

		Plotter plotter = new Plotter();
		plotter.plot(new TreeToJgraph(tree));
	}

	/** This helper method converts a line in the table to a binary int value */
	private int rowAsInt(String actualRow) {
		actualRow = actualRow.replaceAll(" ", "");
		actualRow = actualRow.replaceAll(",", "");
		actualRow = actualRow.substring(1, actualRow.length() - 2);
		int actualRowInt = Integer.parseInt(actualRow, 2);

		return actualRowInt;
	}

	public void listToString() {
		for (int i = 0; i < tableAsList.size(); i++) {
			for (int j = 0; j < tableAsList.get(i).size(); j++) {
				System.out.print("  " + tableAsList.get(i).get(j));
			}
			System.out.println("");
		}
	}
}