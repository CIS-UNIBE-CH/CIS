package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import helper.BaumgartnerSample;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import trees.SufTreeNode;

public class BooleanTest {
    private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sampleTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> msufTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> mnecTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sufTable;
    private ArrayList<ArrayList<String>> necTable = new ArrayList<ArrayList<String>>();
    private ArrayList<String> necLine = new ArrayList<String>();
    private DefaultTreeModel msufTree;
    private DefaultTreeModel mnecTree;
    private ArrayList<String> negatedNecLine = new ArrayList<String>();
    private ArrayList<ArrayList<String>> newSampleTable;

    public BooleanTest(String[][] table) {
	this.table = ArrayToArrayList(table);

	BaumgartnerSample baumgartnerSample = new BaumgartnerSample();
	sampleTable = baumgartnerSample.getSampleTable();
	System.out.println(baumgartnerSample);

	// CustomSample customSample = new CustomSample();
	// sampleTable = customSample.getSampleTable();
	// System.out.println(customSample);
	// sampleTable = ArrayToArrayList(table);

	identifySUF();
	identifyMSUF();
	identifyNEC();
	System.out.println(tableToString(necTable));

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

	    fillUpTree(root, msufTree);
	    newWalk(root, msufTable);

	    // Very interesting we got duplicated entries!
	    HashSet removeDuplicated = new HashSet(msufTable);
	    msufTable.clear();
	    msufTable.addAll(removeDuplicated);
	}
	System.out.println("MSUF Table:\n" + tableToString(msufTable));
    }

    /** Step 5 (Step 4 is not necessary, because we know where effect column is) */
    private void identifyNEC() {
	// Dini Table wode wotsch
	newSampleTable = createNewSampleTable(msufTable);
	System.out
		.println("New SampleTable:\n" + tableToString(newSampleTable));

	// GetNecLine
	for (int i = 0; i < msufTable.size(); i++) {
	    String line = "";
	    ArrayList<String> curMsuf = msufTable.get(i);
	    for (int j = 0; j < curMsuf.size(); j++) {
		if (curMsuf.get(j).equals("1") || curMsuf.get(j).equals("0")) {
		    line += curMsuf.get(j);
		}
	    }
	    necLine.add(line);
	}
	System.out.println("NEC Line: " + necLine);

	// Do the negate necLine
	for (int k = 0; k < necLine.size(); k++) {
	    String cur = necLine.get(k);
	    cur = cur.replace("1", "3");
	    cur = cur.replace("0", "1");
	    cur = cur.replace("3", "0");
	    negatedNecLine.add(cur);
	}
	// Wirkung adden
	negatedNecLine.add("1");

	System.out.println("Negated NEC Line (with Effect): " + negatedNecLine);

	// Do the NEC check
	boolean necOK = false;
	for (int j = 0; j < newSampleTable.size(); j++) {
	    if (newSampleTable.get(j).equals(necLine)) {
		necOK = false;
		System.out.println("NEC Line check FAILED!");
		break;
	    } else {
		necOK = true;
	    }
	}
	if (necOK) {
	    System.out.println("NEC Line is OK!");
	    System.out.println("nec " + necToString(necTable));
	    identifyMNEC();
	}

    }

    private void identifyMNEC() {
	negatedNecLine.remove(negatedNecLine.size() - 1);
	SufTreeNode root = new SufTreeNode(negatedNecLine);
	mnecTree = new DefaultTreeModel(root);

	fillUpTree(root, mnecTree);
	// treeToString(mnecTree);
	necWalk(root, mnecTable);

	HashSet removeDuplicated = new HashSet(mnecTable);
	mnecTable.clear();
	mnecTable.addAll(removeDuplicated);
	for (int row = 0; row < mnecTable.size(); row++) {
	    for (int col = 0; col < mnecTable.get(row).size(); col++) {
		String cur = mnecTable.get(row).get(col);
		cur = cur.replace("1", "3");
		cur = cur.replace("0", "1");
		cur = cur.replace("3", "0");
		mnecTable.get(row).set(col, cur);
	    }
	}
	System.out.println("MNEC Table:\n" + tableToString(mnecTable));
	System.out.println(mnecToString(mnecTable, newSampleTable.get(0)));
    }

    private void necWalk(SufTreeNode parent,
	    ArrayList<ArrayList<String>> mnecTable2) {
	int breaks = 0;
	int childCount = parent.getChildCount();

	// Count how many "broken" childs current parent has.
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    if (shouldNecBreak(child.getData())) {
		breaks++;
		// System.out.println(breaks);
	    }
	}
	// If every child of current parent breaks and parent itself does not
	// break, we got a msuf!
	if (breaks == childCount && !shouldNecBreak(parent.getData())) {
	    // System.out.println("braeksadd " + parent.getData());
	    mnecTable2.add(parent.getData());
	}
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    // Special condition for leaves, when they itself not break they are
	    // a msuf!
	    if (child.isLeaf() && (!shouldNecBreak(child.getData()))) {
		mnecTable2.add(child.getData());
		// System.out.println("leafadd " + child.getData());
	    } else {
		necWalk(child, mnecTable2);
	    }
	}

    }

    private boolean shouldNecBreak(ArrayList<String> curLine) {
	boolean isEqual = false;
	// System.out.println("curLine " + curLine);
	for (int r = 1; r < newSampleTable.size(); r++) {
	    ArrayList<String> curRow = newSampleTable.get(r);
	    for (int i = 0; i < curLine.size(); i++) {
		// Only if there is a 1 or 0 in nodes data compare, when a
		// dollar do nothing.
		if (curLine.get(i).equals("1") || curLine.get(i).equals("0")
			|| curLine.get(i).length() > 1) {
		    // System.out.println("::::: " + curLine.get(i) + " "
		    // + curRow.get(i));
		    if (curLine.get(i).equals(curRow.get(i))) {
			// System.out.println(" ----");
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
		if (curRow.get(curRow.size() - 1).equals("1")) {
		    // System.out.println("L " + curLine + " R " + curRow);
		    return false;
		}
	    }
	}
	return true;
    }

    /**
     * Helper Step 5: Transformation of necTable in a more human readable
     * representation
     * 
     * @return
     */
    private ArrayList<ArrayList<String>> createNewSampleTable(
	    ArrayList<ArrayList<String>> msufTable) {
	ArrayList<ArrayList<String>> necTableTemp = new ArrayList<ArrayList<String>>();

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
	    current.add(curSample.get(curSample.size() - 1));
	    necTableTemp.add(current);
	}
	return necTableTemp;
    }

    /**
     * Helper Step 5: Transformation of necTable in a more human readable
     * representation
     */
    private String mnecToString(ArrayList<ArrayList<String>> necTable,
	    ArrayList<String> names) {
	ArrayList<String> factorNames = names;
	System.out.println(names);
	String output = "";
	for (int row = 0; row < necTable.size(); row++)
	    for (int col = 0; col < necTable.get(row).size(); col++) {
		if (!necTable.get(row).get(col).equals("$")) {
		    factorNames.add(names.get(col));
		}
	    }
	for (int i = 0; i < factorNames.size() - 1; i++) {
	    if (i == factorNames.size() - 2)
		output += factorNames.get(i);
	    else
		output += factorNames.get(i) + " v ";
	}

	return output;
    }

    /** Use those both methods to print tree in syso. */
    private void treeToString(DefaultTreeModel model) {
	if (model != null) {
	    SufTreeNode root = (SufTreeNode) model.getRoot();
	    System.out.println("Root: " + root.toString());
	    treeToStringHelper(model, root);
	} else
	    System.out.println("Tree is empty.");
    }

    private void treeToStringHelper(TreeModel model, SufTreeNode parent) {
	int childCount;
	childCount = model.getChildCount(parent);
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) model.getChild(parent, i);
	    if (model.isLeaf(child))
		System.out.println("Leaf: " + child.toString());
	    else {
		System.out.print(child.toString() + " --\n");
		treeToStringHelper(model, child);
	    }
	}
    }

    /**
     * Core Algorithm of Step 3: Makes a pre order tree walk and detects msuf's
     */
    private void newWalk(SufTreeNode parent, ArrayList<ArrayList<String>> table) {
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
	    table.add(parent.getData());
	}
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    // Special condition for leaves, when they itself not break they are
	    // a msuf!
	    if (child.isLeaf() && !(shouldBreak(child.getData()))) {
		table.add(child.getData());
	    } else {
		newWalk(child, table);
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
    private void fillUpTree(SufTreeNode parent, DefaultTreeModel tree) {
	if (parent.hasOneCare()) {
	} else {
	    ArrayList<String> data = parent.getData();
	    ArrayList<Integer> indexes = parent.getCareIndexes();

	    for (int i = 0; i < indexes.size(); i++) {
		ArrayList<String> curNode = permutation(
			(ArrayList<String>) data.clone(), indexes.get(i));
		SufTreeNode newNode = new SufTreeNode(curNode);
		tree.insertNodeInto(newNode, parent, i);
		fillUpTree(newNode, tree);
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
}