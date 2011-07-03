package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import helper.BaumgartnerSample;
import helper.SufTreeNode;

import java.util.ArrayList;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class BooleanTest {

    private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sampleTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> msuf = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sufTable;
    private DefaultTreeModel tree;

    public BooleanTest(String[][] table) {
	this.table = ArrayToArrayList(table);

	BaumgartnerSample sample = new BaumgartnerSample();
	sampleTable = sample.getSampleTable();

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

	// for (int row = 1; row < sufTable.size(); row++) {
	ArrayList<String> sufLine = sufTable.get(0);

	ArrayList<String> test = new ArrayList<String>();
	test.add("1");
	test.add("1");
	test.add("1");

	SufTreeNode root = new SufTreeNode(test);
	tree = new DefaultTreeModel(root);
	fillUpTree(root);
	System.out.println("Traversed Tree*********************");
	traverse(tree);
	System.out.println("Traversed Tree*********************");
	// }

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
		// System.out.println(tmp);
		fillUpTree(newNode);
	    }
	}
    }

    private ArrayList<String> permutation(ArrayList<String> data, int place) {
	data.set(place, "$");
	return data;
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

    public void traverse(DefaultTreeModel model) {
	if (model != null) {
	    Object root = model.getRoot();
	    System.out.println("Root: " + root.toString());
	    walk(model, root);
	} else
	    System.out.println("Tree is empty.");
    }

    protected void walk(TreeModel model, Object o) {
	int cc;
	cc = model.getChildCount(o);
	for (int i = 0; i < cc; i++) {
	    Object child = model.getChild(o, i);
	    if (model.isLeaf(child))
		System.out.println(child.toString());
	    else {
		System.out.print("------------\n" + child.toString() + " --\n");
		walk(model, child);
	    }
	}
    }
}