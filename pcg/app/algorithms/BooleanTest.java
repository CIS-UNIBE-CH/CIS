package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

public class BooleanTest {

    private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> generatedFullCoincidenceTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sampleTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> msuf = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sufTable;
    // How many factors are in given coincidence table
    private int numberOfFactors = 0;
    // How many rows must be generated in generateCoincidenceTable()
    private int coincidenceTableSize = 0;

    public BooleanTest(String[][] table) {
	this.table = ArrayToArrayList(table);
	this.coincidenceTableSize = (int) Math.pow(2,
		(this.table.get(0).size() - 1));
	this.numberOfFactors = (this.table.get(0).size() - 1);

	// generateCoincidenceTable();

	sampleTable();
	identifySUF();
	identifyMSUF();
    }

    /** Step 2 **/
    public void identifySUF() {
	// sufTable = clone2DArrayList(table);
	sufTable = (ArrayList<ArrayList<String>>) sampleTable.clone();
	for (int r = sufTable.size() - 1; r >= 0; r--) {
	    if (sufTable.get(r).get(sufTable.get(r).size() - 1).equals("0")) {
		sufTable.remove(r);
	    }
	}
    }

    /** Step 3 **/
    public void identifyMSUF() {
	System.out.println("SufTable:\n" + tableToString(sufTable));
	ArrayList<ArrayList<String>> tempSufTable = (ArrayList<ArrayList<String>>) sufTable
		.clone();

	// Üse schön rekursiv algorithmus...
    }

    /** Generates a full coincidence table with binary-counting method */
    private void generateCoincidenceTable() {
	// Generate Binary Numbers
	generatedFullCoincidenceTable.clear();
	for (Integer i = 0; i < coincidenceTableSize; i++) {
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
	    // binaryNumber = binaryNumber + "1";

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

    public void sampleTable() {
	coincidenceTableSize = (int) Math.pow(2, 4);
	numberOfFactors = 4;

	ArrayList<String> firstRow = new ArrayList<String>();
	firstRow.add("A");
	firstRow.add("B");
	firstRow.add("D");
	firstRow.add("E");
	firstRow.add("C");
	sampleTable.add(firstRow);

	ArrayList<String> secondRow = new ArrayList<String>();
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("1");
	sampleTable.add(secondRow);

	ArrayList<String> thirdRow = new ArrayList<String>();
	thirdRow.add("1");
	thirdRow.add("1");
	thirdRow.add("0");
	thirdRow.add("1");
	thirdRow.add("1");
	sampleTable.add(thirdRow);

	ArrayList<String> fourthRow = new ArrayList<String>();
	fourthRow.add("1");
	fourthRow.add("0");
	fourthRow.add("1");
	fourthRow.add("1");
	fourthRow.add("1");
	sampleTable.add(fourthRow);

	ArrayList<String> fifthRow = new ArrayList<String>();
	fifthRow.add("0");
	fifthRow.add("1");
	fifthRow.add("1");
	fifthRow.add("1");
	fifthRow.add("1");
	sampleTable.add(fifthRow);

	ArrayList<String> sixthRow = new ArrayList<String>();
	sixthRow.add("0");
	sixthRow.add("1");
	sixthRow.add("0");
	sixthRow.add("1");
	sixthRow.add("1");
	sampleTable.add(sixthRow);

	ArrayList<String> seventhRow = new ArrayList<String>();
	seventhRow.add("1");
	seventhRow.add("0");
	seventhRow.add("0");
	seventhRow.add("0");
	seventhRow.add("1");
	sampleTable.add(seventhRow);

	ArrayList<String> eigthRow = new ArrayList<String>();
	eigthRow.add("0");
	eigthRow.add("0");
	eigthRow.add("1");
	eigthRow.add("1");
	eigthRow.add("0");
	sampleTable.add(eigthRow);

	ArrayList<String> ninethRow = new ArrayList<String>();
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	sampleTable.add(ninethRow);

	/*
	 * for(int i = 0; i< sampleTable.size(); i++){
	 * System.out.println("SampleTableRow\n " + sampleTable.get(i)); }
	 */
    }
}