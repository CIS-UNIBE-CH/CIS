package algorithms;

import helper.CombinationGenerator;

import java.util.ArrayList;

import tree.CustomTree;
import tree.CustomTreeNode;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

public class BooleanTest {

    private CustomTree tree;
    private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    private ArrayList<String> msuf = new ArrayList<String>();
    private ArrayList<ArrayList<String>> sufTable;
    private ArrayList<ArrayList<String>> tempTable;
    private ArrayList<ArrayList<String>> tempSufTable;

    public BooleanTest(String[][] table) {
	this.table = ArrayToArrayList(table);

	identifySUF();
	identifyMSUF();
	permutation();

    }

    public CustomTree creatTree() {
	tree = new CustomTree();
	CustomTreeNode root = new CustomTreeNode("W");
	tree.setRoot(root);

	return tree;
    }

    /** Step 2 **/
    public void identifySUF() {
	sufTable = clone2DArrayList(table);
	for (int r = 1; r < sufTable.size(); r++) {
	    if (sufTable.get(r).get(sufTable.get(r).size() - 1).equals("0"))
		sufTable.remove(r);
	}
	System.out.println(tableToString(sufTable));
    }

    /** Step 3 **/
    public void identifyMSUF() {
	tempTable = clone2DArrayList(table);
	sufTable = effectsToZero(sufTable);
	System.out.println("SufTable:\n" + tableToString(sufTable));
	/** sufTable bottom up **/
	for (int row = sufTable.size() - 1; row > 0; row--) {
	    /** go throw cols of sufTable **/
	    for (int col = 0; col < sufTable.get(row).size(); col++) {
		if (compareTables(sufTable.get(row), tempTable)) {
		    removeCol(sufTable, col);
		    removeCol(tempTable, col);
		    System.out.println(tableToString(sufTable));
		}
	    }
	}
	/** add factor to msuf **/
	for (int i = 0; i < sufTable.get(0).size() - 1; i++)
	    msuf.add(sufTable.get(0).get(i));
	System.out.println(msuf);
    }

    private void permutation() {
	ArrayList<String> test = new ArrayList<String>();
	test.add("A");
	test.add("B");
	test.add("C");
	// List<Character> set = Arrays.asList('A', 'B', 'C');
	CombinationGenerator<String> cg = new CombinationGenerator<String>(
		test, 2);
	for (ArrayList<String> combination : cg) {
	    System.out.println(combination);
	}

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
