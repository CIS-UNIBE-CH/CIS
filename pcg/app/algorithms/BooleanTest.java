package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import helper.CombinationGenerator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;

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

	// sampleTable();
	// DO NOT DELETE NEXT LINE
	// generateCoincidenceTable();

	// identifySUF();
	// identifyMSUF();
	combTester();
    }

    public void combTester() {
	ArrayList<String> list = new ArrayList<String>();
	list.add("A");
	list.add("B");
	list.add("C");
	list.add("D");

	CombinationGenerator gen = new CombinationGenerator(list, 3);

	while (gen.hasNext()) {
	    System.out.println("Combination" + gen.next());
	}
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
	// sampleTable.add(eigthRow);

	ArrayList<String> ninethRow = new ArrayList<String>();
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	// sampleTable.add(ninethRow);

	/*
	 * for(int i = 0; i< sampleTable.size(); i++){
	 * System.out.println("SampleTableRow\n " + sampleTable.get(i)); }
	 */
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
	System.out.println("***************££££££££££££££££££££££££");
	System.out.println("SufTable:\n" + tableToString(sufTable));
	ArrayList<ArrayList<String>> permutationsOfActualRow = new ArrayList<ArrayList<String>>();

	// Iterate over the whole SUF table
	for (int l = 1; l < sampleTable.size(); l++) {
	    ArrayList<String> actualRow = sampleTable.get(l);
	    dPermutationSorter(getDLines(actualRow));
	    // Holds the indexes of the zeros in one row. Will be needed to find
	    // the permutations of that row.
	    /*
	     * ArrayList<Integer> indexesOfZeros = new ArrayList<Integer>();
	     * ArrayList<String> msufRow = new ArrayList<String>();
	     * 
	     * // First Line has only 1, is a special case, because //
	     * getCoincidenceLines() can't handle that line. // TODO Implement
	     * erkennung von einer Line. System.out.println("ActualRow " +
	     * actualRow); if (l == 1) { for (int i = 1; i <
	     * generatedFullCoincidenceTable.size(); i++) {
	     * permutationsOfActualRow.add(generatedFullCoincidenceTable
	     * .get(i)); } System.out.println("Permutations \n" +
	     * tableToString(permutationsOfActualRow)); } else { for (int j = 0;
	     * j < actualRow.size(); j++) { if (actualRow.get(j).equals("0")) {
	     * indexesOfZeros.add(j); } } permutationsOfActualRow =
	     * getDLines(indexesOfZeros); System.out.println("Permutations \n" +
	     * tableToString(permutationsOfActualRow)); }
	     * 
	     * // Compare permutations with table and find min factors row. for
	     * (int k = permutationsOfActualRow.size() - 1; k >= 0; k--) {
	     * ArrayList<String> curLine = permutationsOfActualRow.get(k);
	     * 
	     * for (int i = 1; i < sampleTable.size() - 1; i++) {
	     * ArrayList<String> curRow = sampleTable.get(i);
	     * 
	     * } }
	     * 
	     * // Add the found min factors row do finalList //
	     * msuf.add(msufRow);
	     * 
	     * // Clear some arraylists indexesOfZeros.clear();
	     * permutationsOfActualRow.clear();
	     */
	}
	/*
	 * // Filter out duplicated min factors rows. //HashSet swap = new
	 * HashSet(msuf); //msuf.clear(); //msuf.addAll(swap);
	 * System.out.println("MSUF's: " + msuf);
	 */
    }

    /**
     * Gets all permutations of specific row of sufTable, based on a generated
     * full coincidence table
     */
    public ArrayList<ArrayList<String>> getDLines(
	    ArrayList<String> suffTableLine) {

	generateCoincidenceTable();
	ArrayList<ArrayList<String>> coincidenceTableCopy = (ArrayList<ArrayList<String>>) generatedFullCoincidenceTable
		.clone();
	for (int l = 0; l < coincidenceTableCopy.size(); l++) {
	    ArrayList<String> curRow = coincidenceTableCopy.get(l);

	    // Set 1 to D
	    for (int i = 0; i < curRow.size(); i++) {
		if (curRow.get(i).equals("1")) {
		    curRow.set(i, "D");
		}
	    }

	    for (int i = 0; i < curRow.size(); i++) {
		if (curRow.get(i).equals("0")) {
		    curRow.set(i, suffTableLine.get(i));
		}
	    }
	    // Dpermutations = (ArrayList<ArrayList<String>>)
	    // coincidenceTableCopy.clone();
	}
	System.out.println("ActRow: \n" + suffTableLine);
	System.out.println("DPermutations: \n"
		+ tableToString(coincidenceTableCopy));
	return coincidenceTableCopy;
    }

    public ArrayList<ArrayList<String>> dPermutationSorter(
	    ArrayList<ArrayList<String>> list) {
	System.out.println("list: \n" + tableToString(list));

	ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>();
	int key = 0;

	MultiMap<Integer, ArrayList<String>> map = new MultiHashMap<Integer, ArrayList<String>>();

	for (int row = 0; row < list.size(); row++) {
	    for (int col = 0; col < list.get(row).size(); col++) {
		if (list.get(row).get(col).equals("D"))
		    key++;
	    }
	    map.put(key, list.get(row));
	    key = 0;
	}
	System.out.println(map);
	int pos = 0;
	System.out.println("Size" + map.size());
	for (int m = 0; m < map.size(); m++) {

	    for (int i = 0; i < list.get(0).size(); i++) {
		List listTemp = (List) map.get(key);
		newList.add((ArrayList<String>) listTemp.get(i));
		// map.;
	    }
	    pos++;
	}
	System.out.println("sorted: \n" + tableToString(newList));
	return newList;

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
	    // System.out.println("Coincidence " + number);
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