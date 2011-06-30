package algorithms;

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
	// tempTable = clone2DArrayList(table);
	// sufTable = effectsToZero(sufTable);
	tempTable = clone2DArrayList(table);
	System.out.println("SufTable:\n" + tableToString(sufTable));
	int size = sufTable.get(0).size();

	ArrayList<Integer> zeroPos = new ArrayList<Integer>();
	// test.add(1);
	// test.add(3);

	// getCoincidenceLines(test);
	// for (int i = size; i >= 0; i--) {
	ArrayList<String> curRow = sufTable.get(sufTable.size() - 2);
	System.out.println("CurRow" + curRow);
	for (int j = 0; j < curRow.size(); j++) {
	    if (curRow.get(j).equals("0")) {
		zeroPos.add(j);
	    }
	}
	ArrayList<ArrayList<String>> goodLines = getCoincidenceLines(zeroPos);
	zeroPos.clear();
	// System.out.println("Good" + goodLines);
	ArrayList<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();

	for (int k = goodLines.size() - 1; k >= 0; k--) {
	    // System.out.println(goodLines.get(k));
	    ArrayList<String> curLine = (ArrayList<String>) goodLines.get(k)
		    .clone();
	    for (int i = 1; i < table.size(); i++) {
		ArrayList<String> curRow1 = (ArrayList<String>) table.get(i)
			.clone();
		// String curLineS = curLine.toString();
		boolean equal = true;
		for (int g = 0; g < curLine.size(); g++) {
		    if (g == curLine.size()-1) {
			if (!curLine.get(g).equals(curRow1)) {
			    equal = false;
			    break;
			}
		    } else {
			if (curLine.get(g).equals("1")) {
			    System.out.println("Line " + curLine);
			    // System.out.println("curLine " + curLine.get(g));
			    System.out.println("Row1 " + curRow1);
			    // System.out.println("curRow1 " + curRow1.get(g));
			    if (!curLine.get(g).equals(curRow1.get(g))) {
				equal = false;
				break;
			    }
			}
		    }
		    System.out.println("Boolean " + equal);
		    equal = true;
		}
		if (equal) {
		    finalList.add(table.get(i));
		}

		/*
		 * if(table.get(i).equals(goodLines.get(k))){ if(i == 1){
		 * //System.out.println("i" + i +"  " + table.get(i));
		 * finalList.add(table.get(i)); break; } else{
		 * 
		 * //System.out.println("ielse" + i +"  " + table.get(i-1));
		 * finalList.add(table.get(i)); break; }
		 * 
		 * }
		 */
		// System.out.println(table.get(i));
	    }
	}
	System.out.println("Final" + finalList);
	// }
    }

    public ArrayList<ArrayList<String>> getCoincidenceLines(
	    ArrayList<Integer> zeroPos) {
	ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
	tempTable = clone2DArrayList(table);
	for (int i = 1; i < tempTable.size(); i++) {
	    ArrayList<String> curRow = tempTable.get(i);
	    int counter = 0;
	    for (int k = 0; k < zeroPos.size(); k++) {
		if (curRow.get(zeroPos.get(k)).equals("0")) {
		    counter++;
		}
	    }
	    if (counter == zeroPos.size()) {
		System.out.println(curRow);
		curRow.set(curRow.size() - 1, "1");
		lines.add(curRow);
	    }
	}
	return lines;

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
