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
    private ArrayList<SufTreeNode> msufCandidates = new ArrayList<SufTreeNode>();
    private ArrayList<ArrayList<String>> msuf = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> msufFinal = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sufTable;
    private DefaultTreeModel tree;
    private ArrayList<String> sufLine;
    private boolean canAdd = true;

    public BooleanTest(String[][] table) {
	this.table = ArrayToArrayList(table);

	BaumgartnerSample sample = new BaumgartnerSample();
	sampleTable = sample.getSampleTable();

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
	for (int k = 1; k < sufTable.size(); k++) {
	    canAdd = true;
	    sufLine = sufTable.get(k);

	    // IMPORTANT: Remove effect column of suffLine, based on which a
	    // tree will be generated.
	    sufLine.remove(sufLine.size() - 1);

	    SufTreeNode root = new SufTreeNode(sufLine);
	    tree = new DefaultTreeModel(root);
	    fillUpTree(root);
	    compareTraverse(tree);
	}
	HashSet removeDuplicated = new HashSet(msuf);
	msuf.clear();
	msuf.addAll(removeDuplicated);
	System.out.println("MSUF's" + msuf);
    }

    /**
     * Those two methods walk trough tree and compare according to compare()
     * method
     */
    private void compareTraverse(DefaultTreeModel tree) {
	if (tree != null) {
	    SufTreeNode root = (SufTreeNode) tree.getRoot();
	    boolean stop = false;
	    for (int row = 1; row < sampleTable.size(); row++) {
		ArrayList<String> curRow = sampleTable.get(row);
		if (shouldBreak(curRow, root.getData())) {
		    stop = true;
		}
	    }
	    if (!stop) {
		compareWalk(tree, root);
	    }
	} else
	    System.out.println("Tree is empty.");
    }

    private void compareWalk(TreeModel tree, SufTreeNode parent) {
	int childCount;
	childCount = tree.getChildCount(parent);
	// Those boolean make sure only nodes will be added to msuf candidate
	// list, which are equal according to compare() method.
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) tree.getChild(parent, i);
	    if (tree.isLeaf(child)) {
		// System.out.println(child.getData());

		for (int row = 1; row < sampleTable.size(); row++) {
		    ArrayList<String> curRow = sampleTable.get(row);
		    if (shouldBreak(curRow, child.getData())) {
			// System.out.println("CurrentParent: " +
			// parent.getData());
			if (canAdd) {
			    System.out.println("parent " + parent.getData());
			    System.out.println("node " + child.getData());
			    System.out.println("origLine: " + curRow);
			    msuf.add(parent.getData());
			}
			canAdd = false;
		    }
		}
	    } else {
		for (int row = 1; row < sampleTable.size(); row++) {
		    ArrayList<String> curRow = sampleTable.get(row);
		    if (shouldBreak(curRow, child.getData())) {
			if (canAdd) {
			    
			    System.out.println("node " + child.getData());
			    System.out.println("origLine: " + curRow);
			    msuf.add(parent.getData());
			}
			canAdd = false;
		    }
		}
		// System.out.println(child.getData());
		// System.out.println("****************");
		compareWalk(tree, child);
	    }
	}

    }

    /**
     * Used to compare if a line off original table and data of a node are
     * equal, $ will be ignored
     */
    private boolean shouldBreak(ArrayList<String> origTableLine,
	    ArrayList<String> curLine) {
	// System.out.println("origTableLine: " + origTableLine);
	// System.out.println("curLine " + curLine);

	for (int i = 0; i < curLine.size(); i++) {
	    if (curLine.get(i).equals("1") || curLine.get(i).equals("0")) {
		if (curLine.get(i).equals(origTableLine.get(i))) {
		} else {
		    return false;
		}
	    }
	}
	if (origTableLine.get(origTableLine.size() - 1).equals("0")) {
	    //System.out.println("FLAAAAAAAAAAAAAG");
	    return true;
	} else {
	    return false;
	}
    }

    private void fillUpTree(SufTreeNode parent) {
	if (parent.hasOneCare()) {
	    ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < parent.getData().size(); i++) {
		list.add("$");
	    }
	    SufTreeNode newNode = new SufTreeNode(list);
	    tree.insertNodeInto(newNode, parent, 0);
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