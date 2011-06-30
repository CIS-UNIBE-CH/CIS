package algorithms;

import java.util.ArrayList;
import java.util.HashSet;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

public class BooleanTest {

    private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> generatedFullCoincidenceTable = new ArrayList<ArrayList<String>>();
    private ArrayList<String> msuf = new ArrayList<String>();
    private ArrayList<ArrayList<String>> sufTable;
    private ArrayList<ArrayList<String>> tempTable;
    private ArrayList<ArrayList<String>> tempSufTable;
    private int numberOfFactors = 0;
    private int countTo = 0;

    public BooleanTest(String[][] table) {
	this.table = ArrayToArrayList(table);
	this.countTo = (int) Math.pow(2, (this.table.get(0).size() - 1));
	this.numberOfFactors = (this.table.get(0).size() - 1);

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
    public void identifyMSUF(){
	System.out.println("SufTable:\n" + tableToString(sufTable));
	ArrayList<ArrayList<String>> goodLines = null;
	ArrayList<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();

	for (int x = 1; x < sufTable.size(); x++) {
	    ArrayList<String> testRow = (ArrayList<String>) sufTable.get(x).clone();
	    ArrayList<Integer> zeroPos = new ArrayList<Integer>();
	    ArrayList<String> candidateRow = null;
	    
	    //First Line has only 1, is a special case, because getCoincidenceLines() can't handle that line.
	    if (x == sufTable.size() - 1) {
		for (int i = 1; i < generatedFullCoincidenceTable.size(); i++) {
		    goodLines.add((ArrayList<String>) generatedFullCoincidenceTable.get(i).clone());
		}
	    } else {
		for (int j = 0; j < testRow.size(); j++) {
		    if (testRow.get(j).equals("0")) {
			zeroPos.add(j);
		    }
		}
		goodLines = getCoincidenceLines(zeroPos);
	    }

	    //Compare permutations with table and find min factors row.
	    for (int k = goodLines.size() - 1; k >= 0; k--) {
		ArrayList<String> curLine = (ArrayList<String>) goodLines
			.get(k).clone();
		for (int i = 1; i < table.size(); i++) {
		    ArrayList<String> curRow = (ArrayList<String>) table.get(i)
			    .clone();
		    if (curLine.equals(curRow)) {
			candidateRow = (ArrayList<String>) curRow.clone();
		    }
		}
	    }
	    // Add the found min factors row do finalList
	    finalList.add(candidateRow);
	    System.out.println("Final Rows " + finalList);
	    zeroPos.clear();
	    goodLines.clear();
	}
	//Filter out doubled min factors rows.
	HashSet removeDuplicated = new HashSet(finalList);
	finalList.clear();
	finalList.addAll(removeDuplicated);
	System.out.println("Final Rows without duplicated" + finalList);
	
    }

    public ArrayList<ArrayList<String>> getCoincidenceLines(
	    ArrayList<Integer> zeroPos) {
	ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();

	// i = 1 because first line is the zero row which is not relevant.
	for (int i = 1; i < generatedFullCoincidenceTable.size(); i++) {
	    ArrayList<String> curRow = (ArrayList<String>) generatedFullCoincidenceTable
		    .get(i).clone();
	    int counter = 0;
	    for (int k = 0; k < zeroPos.size(); k++) {
		if (curRow.get(zeroPos.get(k)).equals("0")) {
		    counter++;
		}
	    }
	    if (counter == zeroPos.size()) {
		curRow.set(curRow.size() - 1, "1");
		lines.add(curRow);
	    }
	}
	return lines;
    }

    /** Generates the coincidence table with binary-counting method */
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
	    binaryNumber = binaryNumber + "1";
	    for(int k = 0; k< binaryNumber.length(); k++){
		char oneBit = binaryNumber.charAt(k);
		number.add(Character.toString(oneBit));
	    }
	    generatedFullCoincidenceTable.add(number);
	}

	/*for (int j = 0; j < generatedFullCoincidenceTable.size(); j++) {
	    System.out.println("Generated "
		    + generatedFullCoincidenceTable.get(j));
	}*/
    }

    private ArrayList<ArrayList<String>> effectsToZero(
	    ArrayList<ArrayList<String>> sufTable2) {
	for (int i = 0; i < sufTable2.size(); i++) {
	    sufTable2.get(i).set(sufTable2.get(i).size() - 1, "0");
	}
	return sufTable2;
    }

    private boolean compareTables(ArrayList<String> line,
	    ArrayList<ArrayList<String>> tempTable2) {
	for (int i = 0; i < tempTable2.size(); i++) {
	    if (tempTable2.get(i).equals(line)) {
		return false;
	    }
	}
	return true;
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

    private void removeCol(ArrayList<ArrayList<String>> tableColToDelet,
	    int place) {
	for (int r = 0; r < tableColToDelet.size(); r++) {
	    tableColToDelet.get(r).remove(place);
	}
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
