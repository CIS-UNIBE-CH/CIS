package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;
import java.util.HashSet;

public class BooleanTest {

    private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> generatedFullCoincidenceTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> msuf = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sufTable;
    // How many factors are in given coincidence table
    private int numberOfFactors = 0;
    // How many rows must be generated in generateCoincidenceTable()
    private int countTo = 0;

    public BooleanTest(String[][] table) {
	this.table = ArrayToArrayList(table);
	this.countTo = (int) Math.pow(2, (this.table.get(0).size() - 1));
	this.numberOfFactors = (this.table.get(0).size() - 1);

	// DO NOT DELETE NEXT LINE
	generateCoincidenceTable();

	identifySUF();
	identifyMSUF();
    }

    /** Step 2 **/
    public void identifySUF() {
	sufTable = clone2DArrayList(table);
	for (int r = 1; r < sufTable.size(); r++) {
	    if (sufTable.get(r).get(sufTable.get(r).size() - 1).equals("0"))
		sufTable.remove(r);
	}
    }

    /** Step 3 **/
    public void identifyMSUF() {
	System.out.println("SufTable:\n" + tableToString(sufTable));
	ArrayList<ArrayList<String>> permutationsOfActualRow = null;

	// Iterate over the whole SUF table
	for (int l = 1; l < sufTable.size(); l++) {
	    ArrayList<String> actualRow = sufTable.get(l);
	    // Holds the indexes of the zeros in one row. Will be needed to find
	    // the permutations of that row.
	    ArrayList<Integer> indexesOfZeros = new ArrayList<Integer>();
	    ArrayList<String> msufRow = null;

	    // First Line has only 1, is a special case, because
	    // getCoincidenceLines() can't handle that line.
	    if (l == sufTable.size() - 1) {
		for (int i = 1; i < generatedFullCoincidenceTable.size(); i++) {
		    permutationsOfActualRow.add(generatedFullCoincidenceTable
			    .get(i));
		}
	    } else {
		for (int j = 0; j < actualRow.size(); j++) {
		    if (actualRow.get(j).equals("0")) {
			indexesOfZeros.add(j);
		    }
		}
		permutationsOfActualRow = getCoincidenceLines(indexesOfZeros);
	    }

	    // Compare permutations with table and find min factors row.
	    for (int k = permutationsOfActualRow.size() - 1; k >= 0; k--) {
		ArrayList<String> curLine = permutationsOfActualRow.get(k);
		for (int i = 1; i < table.size(); i++) {
		    ArrayList<String> curRow = table.get(i);
		    if (curLine.equals(curRow)) {
			msufRow = curRow;
		    }
		}
	    }
	    // Add the found min factors row do finalList
	    msuf.add(msufRow);

	    // Clear some arraylists
	    indexesOfZeros.clear();
	    permutationsOfActualRow.clear();
	}
	// Filter out duplicated min factors rows.
	HashSet swap = new HashSet(msuf);
	msuf.clear();
	msuf.addAll(swap);
	System.out.println("MSUF's: " + msuf);
    }

    /**
     * Gets all permutations of specific row of sufTable, based on a generated
     * full coincidence table
     */
    public ArrayList<ArrayList<String>> getCoincidenceLines(
	    ArrayList<Integer> indexesOfZeros) {
	ArrayList<ArrayList<String>> permutations = new ArrayList<ArrayList<String>>();

	// i = 1 because first line is not relevant because in first line are
	// only zeros.
	for (int i = 1; i < generatedFullCoincidenceTable.size(); i++) {
	    int zeroCounter = 0;
	    ArrayList<String> curRow = generatedFullCoincidenceTable.get(i);

	    for (int k = 0; k < indexesOfZeros.size(); k++) {
		if (curRow.get(indexesOfZeros.get(k)).equals("0")) {
		    zeroCounter++;
		}
	    }
	    if (zeroCounter == indexesOfZeros.size()) {
		// Set effect manually to 1, needed for MSUF condition
		curRow.set(curRow.size() - 1, "1");
		permutations.add(curRow);
	    }
	}
	return permutations;
    }

    /** Generates a full coincidence table with binary-counting method */
    private void generateCoincidenceTable() {
	// Generate Binary Numbers
	for (Integer i = 0; i < countTo; i++) {
	    ArrayList<String> number = new ArrayList<String>();
	    String binaryNumber = Integer.toBinaryString(i);
	    int binaryNumberLength = binaryNumber.length();

	    // Add Pre-Zeros, so all binary numbers have the same length
	    if (binaryNumberLength != (numberOfFactors)) {
		while (binaryNumber.length() < numberOfFactors) {
		    binaryNumber = "0" + binaryNumber;
		}
	    }
	    // Set effect for every row to 1
	    binaryNumber = binaryNumber + "1";

	    // Add to 2D ArrayList
	    for (int k = 0; k < binaryNumber.length(); k++) {
		char oneBit = binaryNumber.charAt(k);
		number.add(Character.toString(oneBit));
	    }
	    generatedFullCoincidenceTable.add(number);
	}
    }

    private ArrayList<ArrayList<String>> clone2DArrayList(
	    ArrayList<ArrayList<String>> toClone) {
	ArrayList<ArrayList<String>> copy = new ArrayList<ArrayList<String>>();
	ArrayList<String> col;
	for (int i = 0; i < toClone.size(); i++) {
	    col = (ArrayList<String>) toClone.get(i).clone();
	    copy.add(col);
	}
	return copy;
    }

    public ArrayList<ArrayList<String>> ArrayToArrayList(String[][] table) {
	ArrayList<ArrayList<String>> tableList = new ArrayList<ArrayList<String>>();
	for (int r = 0; r < table.length; r++) {
	    tableList.add(new ArrayList<String>());
	    for (int c = 0; c < table[r].length; c++) {
		tableList.get(r).add(table[r][c]);
	    }
	}
	return tableList;
    }

    public String tableToString(ArrayList<ArrayList<String>> tableList) {
	String print = "";
	for (int r = 0; r < tableList.size(); r++) {
	    for (int c = 0; c < tableList.get(r).size(); c++) {
		print += tableList.get(r).get(c) + " ";
	    }
	    print += "\n";
	}
	return print;
    }
}