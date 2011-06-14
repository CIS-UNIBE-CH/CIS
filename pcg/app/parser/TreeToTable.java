package parser;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import tree.CustomTree;
import tree.CustomTreeNode;

/** Parses a CustomTree to Coincidence Table */
public class TreeToTable {
	private CustomTree tree;
	private String table[][];
	private static ArrayList<CustomTreeNode> nodes;
	// TODO Bündelgrösse momentan noch fix. Auf variabel erweitern.
	private int bundleSize;
	private int numberOfFactors;
	private int numberOfBundles;

	public TreeToTable(CustomTree tree, int numberOfFactors, int numberOfBundles, int bundleSize) {
		this.tree = tree;
		this.numberOfBundles = numberOfBundles;
		this.numberOfFactors = numberOfFactors;
		this.bundleSize = bundleSize;
		// Calculate right size of table
		table = new String[(int) (Math.pow(2, numberOfFactors)) + 1][numberOfFactors + 1];

		// getBundles() needs a toString() for initialization. DO NOT DELETE THIS LINE
		tree.toString();

		// Init the Process
		addFactorNamesToTable();
		generateCoincidenceTable();
		generateEffectColumn();
		System.out.println(toString());
	}

	/**
	 * Adds the first row to the table, in this row are the names of all factors
	 * of the graph. Except the X and Y factors.
	 */
	private void addFactorNamesToTable() {
		nodes = tree.getChildren();

		// Filter out all X and Y Factors
		for (int i = nodes.size() - 1; i >= 0; i--) {
			if ((nodes.get(i).toString().substring(0, 1).equals("X"))
					|| (nodes.get(i).toString().substring(0, 1).equals("Y"))) {
				nodes.remove(i);
			}
		}
		// Add "Wirkung" manually
		nodes.add(tree.getRoot());

		// Add all factors to table
		for (int i = 0; i < table[0].length; i++) {
			table[0][i] = nodes.get(i).toString();
		}
	}

	/** Generates the coincidence table with binary-counting method */
	private void generateCoincidenceTable() {
		ArrayList<String> numbers = new ArrayList<String>();

		// Generate Binary Numbers
		for (Integer i = 0; i < table.length - 1; i++) {
			String binaryNumber = Integer.toBinaryString(i);
			int binaryNumberLength = binaryNumber.length();

			// Add Pre-Zeros, so all binary numbers have the same length
			if (binaryNumberLength != (numberOfFactors)) {
				while (binaryNumber.length() < numberOfFactors) {
					binaryNumber = "0" + binaryNumber;
				}
			}
			numbers.add(binaryNumber);
		}

		// Fill in Table
		for (int r = 1; r < table.length; r++) {
			String curRow = numbers.get(r - 1);

			for (int c = 0; c < table[r].length - 1; c++) {
				table[r][c] = Character.toString(curRow.charAt(c));
			}
		}
	}

	/** Generates the column of the "Wirkung" in the Graph */
	// Läuft mit der Einschränkung, dass die Bündel eine fixe Grösse haben und
	// der Ort der Faktoren im Bündel bekannt ist. Bsp: Es ist bekannt, dass die
	// Faktoren von Bündel 1 immer an erster und zweiter Stelle sind etc.
	private void generateEffectColumn() {
		int relevanceCounter = 0;
		int bundleSizeCounter = 0;

		// Iteriert durch die Zeilen einer Schlaufe
		for (int r = 1; r < table.length; r++) {

			// ***For Bundles ***
			// Geht jeden Faktor einer Zeile der Koinzidenztabelle einzeln
			// durch. Bei einer "1" wird der relevanceCounter inkrementiert. Nur
			// wenn der relvance Counter und der bundleSizeCounter beide gleich
			// der bundleSize sind wird eine "1" bei der Wirkung geschrieben.
			// D.h. nur wenn die ersten beiden Einträge einer Zeile gleich "1"
			// oder der 2 und 3 Eintrag wird die Wirkung instantiiert. Hier wird
			// die Annahme gemacht, dass alle Bündel gleich gross und das die
			// Faktoren der Bündel zuerst kommen und dann erst die alternativ
			// Faktoren.
			for (int i = 0; i < numberOfBundles * bundleSize; i++) {
				relevanceCounter = relevanceCounter
						+ Integer.parseInt(table[r][i]);

				bundleSizeCounter++;

				if (bundleSizeCounter == bundleSize
						&& relevanceCounter == bundleSize) {
					relevanceCounter = 0;
					bundleSizeCounter = 0;
					table[r][table[r].length - 1] = "1";

					// Force quit loop because every "1" more is a
					// overdetermination
					i = numberOfBundles * bundleSize;
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

			// *** For Alternative Factors ***
			// Jede Zeile der Tabelle auf alternativ Faktoren durchgehen, aber
			// nur wenn nicht schon eine 1 bei der Wirkung steht.
			if (!table[r][table[r].length - 1].equals("1")) {
				// Iteriert nur über die alternativ Faktoren, die Bündel werden
				// nicht mehr angeschaut!
				for (int i = numberOfBundles * bundleSize; i < table[r].length - 1; i++) {
					relevanceCounter = relevanceCounter
							+ Integer.parseInt(table[r][i]);
					if (relevanceCounter > 0) {
						table[r][table[r].length - 1] = "1";

						// Force quit loop because every "1" more is a
						// overdetermination
						i = table[r].length - 1;
					} else {
						table[r][table[r].length - 1] = "0";
					}
				}
			}
			relevanceCounter = 0;
		}
	}

	@Override
	public String toString() {
		String print = "";
		for (int r = 0; r < table.length; r++) {
			print += r + "  ";
			for (int c = 0; c < table[r].length; c++) {
				print += "  " + table[r][c];
			}
			print += "\n";
		}
		return print;
	}

	public String[][] getTableAsArray() {
		return table;
	}

	public ArrayList<String> getTable() {
		ArrayList<String> tables = new ArrayList<String>();

		for (int r = 0; r < table.length; r++) {
			for (int c = 0; c < table[r].length; c++) {
				tables.add("" + table[r][c]);
			}
			tables.add("/n");
		}
		return tables;
	}
}
