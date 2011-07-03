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
    private ArrayList<ArrayList<String>> sufTable;
    private DefaultTreeModel tree;
    private ArrayList<String> sufLine;

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
	sufTable = (ArrayList<ArrayList<String>>) sampleTable.clone();
	for (int r = sufTable.size() - 1; r >= 0; r--) {
	    if (sufTable.get(r).get(sufTable.get(r).size() - 1).equals("0")) {
		sufTable.remove(r);
	    }
	}
	System.out.println("SUF Table:\n" + tableToString(sufTable));
    }

    /** Step 3 **/
    private void identifyMSUF() {
	// for (int k = 1; k < sufTable.size(); k++) {
	sufLine = sufTable.get(3);

	// IMPORTANT: Remove effect column of suffLine, based on which a tree
	// will be generated.
	sufLine.remove(sufLine.size() - 1);

	SufTreeNode root = new SufTreeNode(sufLine);
	tree = new DefaultTreeModel(root);
	fillUpTree(root);

	// After filling up the tree set the effect columns manually to 1. This
	// is needed for first comparison.
	traverseSetEffect(tree, "1");

	// Compare tree nodes data with all lines of original table.
	for (int row = 1; row < sampleTable.size(); row++) {
	    ArrayList<String> curRow = sampleTable.get(row);
	    compareTraverse(tree, curRow);
	}

	// Preparation for the following for-loop monster, set effect of all
	// candidates to "Zero"
	for (int y = 0; y < msufCandidates.size(); y++) {
	    msufCandidates.get(y).getData()
		    .set(msufCandidates.get(y).getData().size() - 1, "0");
	}

	// This for-loop monster filters all candidates which fullfill the
	// following condition: It should not be that there is a row in
	// original table which has effect not instantiated and is equal with
	// candidate line.
	boolean equal = false;
	for (int row = 1; row < sampleTable.size(); row++) {
	    ArrayList<String> curRow = sampleTable.get(row);

	    for (int y = msufCandidates.size() - 1; y >= 0; y--) {
		ArrayList<String> msufCandidate = msufCandidates.get(y)
			.getData();
		ArrayList<Integer> careIndexes = msufCandidates.get(y)
			.getCareIndexes();

		for (int i = careIndexes.size() - 1; i >= 0; i--) {
		    int curCareIndex = careIndexes.get(i);
		    if (curRow.get(curRow.size() - 1).equals("0")) {
			if (msufCandidate.get(curCareIndex).equals(
				curRow.get(curCareIndex))) {
			    equal = true;
			} else {
			    equal = false;
			}
		    }

		}
		if (equal) {
		    msufCandidates.remove(y);
		}
	    }
	}

	// Only consider nodes which have least number of cares, which means
	// which are the most minimalized
	// TODO Implementieren was tun, wenn mehrere Nodes mit gleich viel
	// cares, ACHTUNG wichtig, damit algo richtig funktioniert.
	int caresOld = 10000000;
	int caresCur = 0;
	SufTreeNode leastCares = null;

	for (int i = 0; i < msufCandidates.size(); i++) {
	    SufTreeNode curNode = msufCandidates.get(i);
	    caresCur = curNode.getCareIndexes().size();
	    if (caresCur < caresOld) {
		leastCares = curNode;
		caresOld = caresCur;
	    }
	}
	msuf.add(leastCares.getData());

	// Remove Duplicated
	HashSet removeDuplicated = new HashSet(msuf);
	msuf.clear();
	msuf.addAll(removeDuplicated);
	// }

	System.out.println("MSUF's: " + msuf);
    }

    /**
     * Those two methods walk trough tree and compare according to compare()
     * method
     */
    private void compareTraverse(DefaultTreeModel tree,
	    ArrayList<String> origLine) {
	if (tree != null) {
	    SufTreeNode root = (SufTreeNode) tree.getRoot();
	    compareWalk(tree, root, origLine);
	} else
	    System.out.println("Tree is empty.");
    }

    private void compareWalk(TreeModel tree, SufTreeNode parent,
	    ArrayList<String> origTableLine) {
	int childCount;
	childCount = tree.getChildCount(parent);
	// Those boolean make sure only nodes will be added to msuf candidate
	// list, which are equal according to compare() method.
	boolean firstEqual = false;
	boolean firstNotEqual = true;

	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) tree.getChild(parent, i);

	    if (tree.isLeaf(child)) {
		if (!firstEqual) {
		    firstEqual = compare(origTableLine, child.getData());
		}
		if (firstEqual && firstNotEqual) {
		    firstNotEqual = compare(origTableLine, child.getData());
		    msufCandidates.add(child);
		}
	    } else {
		if (!firstEqual) {
		    firstEqual = compare(origTableLine, child.getData());
		}
		if (firstEqual && firstNotEqual) {
		    firstNotEqual = compare(origTableLine, child.getData());
		    msufCandidates.add(child);
		}
		compareWalk(tree, child, origTableLine);
	    }
	}
    }

    /**
     * Used to compare if a line off original table and data of a node are
     * equal, $ will be ignored
     */
    private boolean compare(ArrayList<String> origTableLine,
	    ArrayList<String> curLine) {

	boolean areEqual = false;
	for (int i = 0; i < curLine.size(); i++) {
	    if (curLine.get(i).equals("1") || curLine.get(i).equals("0")) {
		if (curLine.get(i).equals(origTableLine.get(i))) {
		    areEqual = true;
		} else {
		    areEqual = false;
		}
	    }
	}
	if (areEqual) {
	    return true;
	} else {
	    return false;
	}
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
}