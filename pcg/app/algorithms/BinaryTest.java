package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import tree.CustomTree;
import tree.CustomTreeNode;

//TODO: Erkennung der Wirkung! 
//TODO: Currently work only with number of bundles <= 2 and x alternativeFactors
//TODO: Fix github issue #16

public class BinaryTest {
	private CustomTree tree;
	private String[][] table;
	private ArrayList<Integer> colIndexesAlterFactors;
	private ArrayList<Integer> rowIndexesBundles;
	private ArrayList<ArrayList<String>> tableAsList;

	public BinaryTest(String[][] table) {
		this.table = table;
		colIndexesAlterFactors = new ArrayList<Integer>();
		rowIndexesBundles = new ArrayList<Integer>();
		tableAsList = new ArrayList<ArrayList<String>>();

		// Fill in 2D-ArrayList, remove all "Zero Lines"
		// Attention: table.length-1 means that the last line will no be added
		// because it's the line with only ones in it.
		for (int r = 0; r < table.length - 1; r++) {
			if (r == 0 || table[r][table[r].length - 1].equals("1")) {
				tableAsList.add(new ArrayList<String>());
				for (int c = 0; c < table[r].length; c++) {
					tableAsList.get(tableAsList.size() - 1).add(table[r][c]);
				}
			}
		}

		// Init Stuff, do not change order!
		identifyAlterFactors();
		removeAlterFactors();
		identifyBundles();
	}

	private void identifyAlterFactors() {
		int colIndexAlterFactor = 0;

		for (int r = 1; r < tableAsList.size(); r++) {
			boolean isTheFirst1 = true;
			int rowCounter = 0;

			for (int c = 0; c < tableAsList.get(r).size(); c++) {
				rowCounter = rowCounter
						+ Integer.parseInt(tableAsList.get(r).get(c));

				// Der Index der ersten in der Zeile aufgetrenden "1" sich
				// merken, denn ist Index von potentiellem alternativ faktor.
				// (Spart Schlaufendurchläufe).
				if (tableAsList.get(r).get(c).equals("1") && isTheFirst1) {
					colIndexAlterFactor = c;
					isTheFirst1 = false;
				}
			}
			// Wenn in einer Reihe nur zwei 1 vorkommen, dann ist es eine Reihe
			// wo nur ein alternativ Faktor und die Wirkung instantiiert sind.
			// Achtung: Funktioniert nur, da schon zuvor alle Zeilen wo Wirkung
			// nicht intantiiert ist ausgefiltert wurden.
			if (rowCounter == 2) {
				colIndexesAlterFactors.add(colIndexAlterFactor);
				rowCounter = 0;
			}

		}
	}

	/**
	 * Will remove all Lines in the table where a alternative factor and the
	 * "Wirkung" are instantiate, because they aren't needed any longer and
	 * would made it impossible to identify bundles with the "binary" method.
	 * Furthermore the columns of the alternative factors will be removed from
	 * the table.
	 */
	private void removeAlterFactors() {
		int colIndex = 0;
		ArrayList<ArrayList<String>> tableListTemp = new ArrayList<ArrayList<String>>();

		// A row where a alternativ factor is instantiated
		boolean badRow = false;

		// Add to temp table the first line
		tableListTemp.add(new ArrayList<String>());
		tableListTemp.get(0).addAll(tableAsList.get(0));

		// Filters out all rows where a alternative Factor is instantiated.
		for (int i = 1; i < tableAsList.size(); i++) {
			for (int j = 0; j < colIndexesAlterFactors.size(); j++) {
				colIndex = colIndexesAlterFactors.get(j);

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

		// Filter out all columns of the alternative factors
		for (int i = 0; i < tableAsList.size(); i++) {
			for (int j = 0; j < colIndexesAlterFactors.size(); j++) {
				int index = colIndexesAlterFactors.get(j);
				tableAsList.get(i).remove(index);
			}
		}

	}

	/**
	 * Identifies bundles via the binary method. Means when to lines are a
	 * complementary of each other this means when a binary OR of both lines
	 * gives back a lines with only ones in it, the both lines are the ones
	 * which hold the bundles.
	 */
	private void identifyBundles() {
		int actualRow = 0;
		int rowToTest = 0;
		String referenceBinary = "";

		for (int k = 0; k < tableAsList.get(0).size() - 1; k++) {
			referenceBinary = referenceBinary + "1";
		}
		// Für Spezialfall, wenn nur 1 Bündel und 1 Faktor instantiiert sind.
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

				// When reference binary which means the result of the binary OR
				// we search for is equal to actual result of binary OR then add
				// the index of the rows to arrayList.
				if (referenceBinary.equals(Integer.toBinaryString(result))) {
					rowIndexesBundles.add(i);
					if (referenceBinary.equals("11")) {
						i = tableAsList.size();
					}
				}
			}
		}
	}

	/** Creates the CustomTree which will be plotted as a Graph */
	public CustomTree createTree() {
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

		// alternative factors
		boolean hasAlterFactors = false;
		for (int i = 0; i < colIndexesAlterFactors.size(); i++) {
			int curIndex = colIndexesAlterFactors.get(i);
			CustomTreeNode curNode = new CustomTreeNode(table[0][curIndex]);
			tree.addChildtoRootX(curNode, root);
			hasAlterFactors = true;
		}
		if (hasAlterFactors) {
			CustomTreeNode y = new CustomTreeNode("Y");
			tree.addChildtoRootX(y, root);
		}

		return tree;
	}

	/** This helper method converts a line in the table to a binary int value */
	private int rowAsInt(String actualRow) {
		actualRow = actualRow.replaceAll(" ", "");
		actualRow = actualRow.replaceAll(",", "");
		actualRow = actualRow.substring(1, actualRow.length() - 2);
		int actualRowInt = Integer.parseInt(actualRow, 2);

		return actualRowInt;
	}

	private void listToString() {
		for (int i = 0; i < tableAsList.size(); i++) {
			for (int j = 0; j < tableAsList.get(i).size(); j++) {
				System.out.print("  " + tableAsList.get(i).get(j));
			}
			System.out.println("");
		}
	}
}