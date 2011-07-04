package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import helper.BaumgartnerSample;
import helper.CustomSample;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import trees.SufTreeNode;

public class BooleanTest {
    private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sampleTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> msuf = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sufTable;
    private DefaultTreeModel tree;
    private ArrayList<String> sufLine;

    public BooleanTest(String[][] table) {
	this.table = ArrayToArrayList(table);

	BaumgartnerSample sample = new BaumgartnerSample();
	sampleTable = sample.getSampleTable();
	CustomSample customSample = new CustomSample();
	//sampleTable = customSample.getSampleTable();

	identifySUF();
	identifyMSUF();
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
	    sufLine = sufTable.get(i);

	    // IMPORTANT: Remove effect column of suffLine, based on which a
	    // tree will be generated.
	    sufLine.remove(sufLine.size() - 1);

	    SufTreeNode root = new SufTreeNode(sufLine);
	    tree = new DefaultTreeModel(root);
	    
	    fillUpTree(root);
	    newWalk(root);
	    
	    //Very interesting we got duplicated entries!
	    HashSet removeDuplicated = new HashSet(msuf);
	    msuf.clear();
	    msuf.addAll(removeDuplicated);
	}
	System.out.println("MSUF's" + msuf);
    }

    /** Makes a pre order tree walk and detects msuf's*/
    private void newWalk(SufTreeNode parent) {
	int breaks = 0;
	int childCount = parent.getChildCount();

	//Count how many "broken" childs current parent has.
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    if (shouldBreak(child.getData())) {
		breaks++;
	    }
	}
	//If every child of current parent breaks and parent itself does not break, we got a msuf!
	if (breaks == childCount && !shouldBreak(parent.getData())) {
		msuf.add(parent.getData());
	}
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    //Special condition for leaves, when they itself not break they are a msuf!
	    if (child.isLeaf() && !(shouldBreak(child.getData()))) {
		msuf.add(child.getData());
	    } else {
		newWalk(child);
	    }
	}
    }

    /** Used to compare data of a tree node with every tow of given original coincidence table.*/
    private boolean shouldBreak(ArrayList<String> curLine) {
	boolean isEqual = false;
	for (int r = 1; r < sampleTable.size(); r++) {
	    ArrayList<String> curRow = sampleTable.get(r);
	    for (int i = 0; i < curLine.size(); i++) {
		//Only if there is a 1 or 0 in nodes data compare, when a dollar do nothing.
		if (curLine.get(i).equals("1") || curLine.get(i).equals("0")) {
		    if (curLine.get(i).equals(curRow.get(i))) {
			isEqual = true;
		    } else {
			isEqual = false;
			break;
		    }
		}
	    }
	    //Check if there is a line in sample table with effect = 0 which matches nodes data.
	    if (isEqual) {
		if (curRow.get(curRow.size() - 1)
			.equals("0")) {
		    return true;
		}
	    }
	}
	return false;
    }

    private void fillUpTree(SufTreeNode parent) {
	if (parent.hasOneCare()) {
	} else {
	    ArrayList<String> data = parent.getData();
	    ArrayList<Integer> places = parent.getCareIndexes();
	    SufTreeNode newNode;
	    for (int i = 0; i < places.size(); i++) {
		ArrayList<String> curNode = permutation(
			(ArrayList<String>) data.clone(), places.get(i));
		newNode = new SufTreeNode(curNode);
		tree.insertNodeInto(newNode, parent, i);
		fillUpTree(newNode);
	    }
	}
    }

    private ArrayList<String> permutation(ArrayList<String> data, int place) {
	data.set(place, "$");
	return data;
    }

    /**
     * Those both methods are used, to set effect collumn in all nodes of tree
     * to 1 or 0
     */
    private void traverseSetEffect(DefaultTreeModel model, String effect) {
	if (model != null) {
	    SufTreeNode root = (SufTreeNode) model.getRoot();
	    root.setEffectValue(effect);
	    walkSetEffect(model, root, effect);
	} else
	    System.out.println("Tree is empty.");
    }

    private void walkSetEffect(TreeModel model, SufTreeNode parent,
	    String effect) {
	int childCount;
	childCount = model.getChildCount(parent);
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) model.getChild(parent, i);
	    child.setEffectValue(effect);
	    walkSetEffect(model, child, effect);
	}
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