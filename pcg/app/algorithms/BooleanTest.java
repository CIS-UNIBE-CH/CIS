package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import helper.BaumgartnerSample;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.tree.DefaultTreeModel;

import trees.SufTreeNode;

public class BooleanTest {
    private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sampleTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> msufTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> necTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> necTableTemp = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sufTable;
    private DefaultTreeModel msufTree;

    public BooleanTest(String[][] table) {
	this.table = ArrayToArrayList(table);

	BaumgartnerSample baumgartnerSample = new BaumgartnerSample();
	sampleTable = baumgartnerSample.getSampleTable();
	// sampleTable = ArrayToArrayList(table);
	System.out.println(baumgartnerSample);

	// CustomSample customSample = new CustomSample();
	// sampleTable = customSample.getSampleTable();

	identifySUF();
	identifyMSUF();
	createNec(msufTable);
	System.out
		.println("asldfkajsdflkasjdf: " + tableToString(necTableTemp));
	identifyNEC();
    }

    /** Step 2 **/
    private void identifySUF() {
	// sufTable = clone2DArrayList(table);
	sufTable = clone2DArrayList(sampleTable);
	for (int r = sufTable.size() - 1; r >= 0; r--) {
	    if (sufTable.get(r).get(sufTable.get(r).size() - 1).equals("0")) {
		sufTable.remove(r);
	    }
	}
	System.out.println("SUF Table:\n" + tableToString(sufTable));
    }

    /** Step 3 **/
    private void identifyMSUF() {
	for (int i = 1; i < sufTable.size(); i++) {
	    ArrayList<String> sufLine = sufTable.get(i);

	    // IMPORTANT: Remove effect column of suffLine, based on which a
	    // tree will be generated.
	    sufLine.remove(sufLine.size() - 1);

	    SufTreeNode root = new SufTreeNode(sufLine);
	    msufTree = new DefaultTreeModel(root);

	    fillUpTree(root);
	    msufWalk(root);

	    // Very interesting we got duplicated entries!
	    HashSet removeDuplicated = new HashSet(msufTable);
	    msufTable.clear();
	    msufTable.addAll(removeDuplicated);
	}
	System.out.println("MSUF Table:\n" + tableToString(msufTable));
    }

    /** Step 5 (Step 4 is not necessary, because we know where effect column is) */
    private void identifyNEC() {
	// Create NEC
	String nec = "";
	for (int i = 0; i < msufTable.size(); i++) {
	    nec += msufTable.get(i).toString();
	}
	// Filter not used characters from ArrayList to String transformation.
	nec = nec.replace("[", "");
	nec = nec.replace("]", "");
	nec = nec.replace(" ", "");
	nec = nec.replace("$", "");
	nec = nec.replace(",", "");

	// Switch from NEC to ¬NEC
	nec = nec.replace("1", "3");
	nec = nec.replace("0", "1");
	nec = nec.replace("3", "0");
	nec += "1";

	ArrayList<String> necTemp = new ArrayList<String>();
	for (int k = 0; k < nec.length(); k++) {
	    char current = nec.charAt(k);
	    necTemp.add(Character.toString(current));
	}

	// Check if ¬NEC exists in original coincidence table, if not, NEC is a
	// NEC.
	boolean necOK = true;
	for (int i = 0; i < sampleTable.size(); i++) {
	    ArrayList<String> curRow = sampleTable.get(i);
	    if (curRow.equals(necTemp)) {
		necOK = false;
		System.out.println("No nec found!!!");
	    }
	}
	if (necOK) {
	    System.out.println("NEC is OK!\n");
	    necTable = clone2DArrayList(msufTable);

	    System.out.println("NEC Table:\n" + tableToString(necTable));
	    System.out.println("NEC as String:\n" + necToString(necTable));
	}
    }

    /**
     * Helper Step 5: Transformation of necTable in a more human readable
     * representation
     */
    private void createNec(ArrayList<ArrayList<String>> msufTable) {

	for (int k = 0; k < sampleTable.size(); k++) {
	    ArrayList<String> curSample = sampleTable.get(k);
	    ArrayList<String> current = new ArrayList<String>();
	    
	    for (int i = 0; i < msufTable.size(); i++) {
		String line = "";
		ArrayList<String> curMsuf = msufTable.get(i);
		for (int j = 0; j < curMsuf.size(); j++) {
		    if (curMsuf.get(j).equals("1")
			    || curMsuf.get(j).equals("0")) {
			line += curSample.get(j);
		    }
		}
		current.add(line);
	    }
	    //System.out.println("Line: " + line);
	    necTableTemp.add(current);
	}
    }

    /**
     * Helper Step 5: Transformation of necTable in a more human readable
     * representation
     */
    private String necToString(ArrayList<ArrayList<String>> necTable) {
	ArrayList<String> factorNames = sampleTable.get(0);
	String output = "";
	for (int i = 0; i < necTable.size(); i++) {
	    ArrayList<String> curRow = necTable.get(i);
	    for (int j = 0; j < curRow.size(); j++) {
		if (curRow.get(j).equals("1")) {
		    output += factorNames.get(j);
		}
		if (curRow.get(j).equals("0")) {
		    output += "¬" + factorNames.get(j);
		}
	    }
	    if (i < necTable.size() - 1) {
		output += " ∨ ";
	    }
	}
	return output;
    }

    /**
     * Core Algorithm of Step 3: Makes a pre order tree walk and detects msuf's
     */
    private void msufWalk(SufTreeNode parent) {
	int breaks = 0;
	int childCount = parent.getChildCount();

	// Count how many "broken" childs current parent has.
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    if (shouldBreak(child.getData())) {
		breaks++;
	    }
	}
	// If every child of current parent breaks and parent itself does not
	// break, we got a msuf!
	if (breaks == childCount && !shouldBreak(parent.getData())) {
	    msufTable.add(parent.getData());
	}
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    // Special condition for leaves, when they itself not break they are
	    // a msuf!
	    if (child.isLeaf() && !(shouldBreak(child.getData()))) {
		msufTable.add(child.getData());
	    } else {
		msufWalk(child);
	    }
	}
    }

    /**
     * Helper Step 3: Used to compare data of a tree node with every tow of
     * given original coincidence table.
     */
    private boolean shouldBreak(ArrayList<String> curLine) {
	boolean isEqual = false;
	for (int r = 1; r < sampleTable.size(); r++) {
	    ArrayList<String> curRow = sampleTable.get(r);
	    for (int i = 0; i < curLine.size(); i++) {
		// Only if there is a 1 or 0 in nodes data compare, when a
		// dollar do nothing.
		if (curLine.get(i).equals("1") || curLine.get(i).equals("0")) {
		    if (curLine.get(i).equals(curRow.get(i))) {
			isEqual = true;
		    } else {
			isEqual = false;
			break;
		    }
		}
	    }
	    // Check if there is a line in sample table with effect = 0 which
	    // matches nodes data.
	    if (isEqual) {
		if (curRow.get(curRow.size() - 1).equals("0")) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * Helper Step 3: Will fill up a tree with following pattern: Root is a line
     * from sufTable, then make a permutation of one $ in level after root, then
     * with two $ etc.
     */
    private void fillUpTree(SufTreeNode parent) {
	if (parent.hasOneCare()) {
	} else {
	    ArrayList<String> data = parent.getData();
	    ArrayList<Integer> indexes = parent.getCareIndexes();

	    for (int i = 0; i < indexes.size(); i++) {
		ArrayList<String> curNode = permutation(
			(ArrayList<String>) data.clone(), indexes.get(i));
		SufTreeNode newNode = new SufTreeNode(curNode);
		msufTree.insertNodeInto(newNode, parent, i);
		fillUpTree(newNode);
	    }
	}
    }

    /** Helper Step 3: Sets the $ character, is helper for fillUpTree() */
    private ArrayList<String> permutation(ArrayList<String> data, int index) {
	data.set(index, "$");
	return data;
    }

    private ArrayList<ArrayList<String>> ArrayToArrayList(String[][] table) {
	ArrayList<ArrayList<String>> tableList = new ArrayList<ArrayList<String>>();
	for (int r = 0; r < table.length; r++) {
	    tableList.add(new ArrayList<String>());
	    for (int c = 0; c < table[r].length; c++) {
		tableList.get(r).add(table[r][c]);
	    }
	}
	return tableList;
    }

    private String tableToString(ArrayList<ArrayList<String>> tableList) {
	String print = "";
	for (int r = 0; r < tableList.size(); r++) {
	    for (int c = 0; c < tableList.get(r).size(); c++) {
		print += tableList.get(r).get(c) + " ";
	    }
	    print += "\n";
	}
	return print;
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
}