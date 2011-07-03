package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import helper.BaumgartnerSample;
import helper.SufTreeNode;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class BooleanTest {

    private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sampleTable = new ArrayList<ArrayList<String>>();
    private ArrayList<SufTreeNode> msufNodes = new ArrayList<SufTreeNode>();
    private ArrayList<ArrayList<String>> msuf = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sufTable;
    private DefaultTreeModel tree;
    private ArrayList<String> sufLine;
    private ArrayList<String> test, test1;

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
    }

    /** Step 3 **/
    private void identifyMSUF() {
	System.out.println("SufTable:\n" + tableToString(sufTable));

	test = new ArrayList<String>();
	// Line with which tree will be created should not have a effect.
	test.add("1");
	test.add("1");
	test.add("1");
	test.add("1");
	// test.add("1");

	test1 = new ArrayList<String>();
	test1.add("1");
	test1.add("0");
	test1.add("0");

	// Create Tree of a sufTable Row
	// TODO for loop over all suftable lines
	// for (int k = 1; k < sufTable.size(); k++) {
	sufLine = sufTable.get(3);
	sufLine.remove(sufLine.size() - 1);

	SufTreeNode root = new SufTreeNode(sufLine);
	tree = null;
	tree = new DefaultTreeModel(root);
	fillUpTree(root);
	traverseSetEffect(tree, "1");

	// System.out.println("Traversed Tree*********************");
	// traverse(tree);
	// System.out.println("Traversed Tree*********************");

	// Compare tree with every line of original table
	for (int row = 1; row < sampleTable.size(); row++) {
	    ArrayList<String> curRow = sampleTable.get(row);
	    compareTraverse(tree, curRow);

	}

	for (int y = 0; y < msufNodes.size(); y++) {
	    msufNodes.get(y).getData()
		    .set(msufNodes.get(y).getData().size() - 1, "0");
	    // System.out.println(msufNodes.get(y).getData());
	}

	boolean ok = false;

	for (int row = 1; row < sampleTable.size(); row++) {
	    ArrayList<String> curRow = sampleTable.get(row);
	    for (int y = msufNodes.size() - 1; y >= 0; y--) {
		ArrayList<String> msufRow = msufNodes.get(y).getData();
		ArrayList<Integer> places = msufNodes.get(y).getCarePlace();
		for (int i = places.size() - 1; i >= 0; i--) {
		    int curPlace = places.get(i);
		    if (curRow.get(curRow.size() - 1).equals("0")) {
			if (msufRow.get(curPlace).equals(curRow.get(curPlace))) {
			    ok = true;
			} else {
			    ok = false;
			}
		    }

		}
		if (ok) {
		    System.out.println("Removed " + msufNodes.get(y));
		    msufNodes.remove(y);

		    // msuf.add(msufRow);
		}
		System.out.println(ok + "\n*******************");
	    }

	    System.out.println(ok + "\n*******************");
	}
	System.out.println(msufNodes.size());

	// Determine node with most cares
	// TODO Implementieren was tun, wenn mehrere Nodes mit gleich viel
	// cares, ACHTUNG wichtig, damit algo richtig funktioniert.
	int caresOld = 10000000;
	int caresCur = 0;
	SufTreeNode least = null;
	for (int i = 0; i < msufNodes.size(); i++) {
	    SufTreeNode curNode = msufNodes.get(i);
	    caresCur = curNode.getCarePlace().size();
	    if (caresCur == 2) {
		msuf.add(curNode.getData());
	    }

	    // msuf.add(curNode.getData());
	}
	HashSet removeDuplicated = new HashSet(msuf);

	msuf.clear();

	msuf.addAll(removeDuplicated);
	// }

	System.out.println("Most:********** " + msuf);
	// System.out.println("$$$$$Mos$$$$$t: " + most.getData());

	// System.out.println("Traversed Tree*********************");
	// traverse(tree);
	// System.out.println("Traversed Tree*********************");
	// }

    }

    private void compareTraverse(DefaultTreeModel tree,
	    ArrayList<String> origLine) {
	if (tree != null) {
	    SufTreeNode root = (SufTreeNode) tree.getRoot();
	    // System.out.println("Root: " + root.toString());
	    // System.out.println("Compare: " + compare(origLine,
	    // root.getData()));
	    // if (compare(origLine, root.getData())) {
	    // msufNodes.add(root);
	    // }
	    compareWalk(tree, root, origLine);
	} else
	    System.out.println("Tree is empty.");
    }

    private void compareWalk(TreeModel tree, SufTreeNode parent,
	    ArrayList<String> origTableLine) {
	int cc;
	cc = tree.getChildCount(parent);
	boolean firstTrue = false;
	boolean firstFalse = true;

	for (int i = 0; i < cc; i++) {
	    SufTreeNode child = (SufTreeNode) tree.getChild(parent, i);

	    if (tree.isLeaf(child)) {
		if (!firstTrue) {
		    firstTrue = compare(origTableLine, child.getData());
		}
		if (firstTrue && firstFalse) {
		    firstFalse = compare(origTableLine, child.getData());
		    // System.out.println("Leaf: " + child.toString());
		    // System.out.println("Compare: "
		    // + compare(origLine, child.getData()));
		    msufNodes.add(child);
		}
		// if (!compare(sufLine, child.getData())) {
		// break;
		// }
	    } else {
		if (!firstTrue) {
		    firstTrue = compare(origTableLine, child.getData());
		}
		if (firstTrue && firstFalse) {
		    firstFalse = compare(origTableLine, child.getData());
		    // System.out.println(child.toString());
		    // System.out.println("Compare: "
		    // + compare(origLine, child.getData()));
		    msufNodes.add(child);
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
	    ArrayList<Integer> places = parent.getCarePlace();
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
	    root.setEffect(effect);
	    walkSetEffect(model, root, effect);
	} else
	    System.out.println("Tree is empty.");
    }

    private void walkSetEffect(TreeModel model, SufTreeNode parent,
	    String effect) {
	int cc;
	cc = model.getChildCount(parent);
	for (int i = 0; i < cc; i++) {
	    SufTreeNode child = (SufTreeNode) model.getChild(parent, i);
	    child.setEffect(effect);
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
	int cc;
	cc = model.getChildCount(parent);
	for (int i = 0; i < cc; i++) {
	    SufTreeNode child = (SufTreeNode) model.getChild(parent, i);
	    if (model.isLeaf(child))
		System.out.println("Leaf: " + child.toString());
	    else {
		System.out.print(child.toString() + " --\n");
		treeToStringHelper(model, child);
	    }
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