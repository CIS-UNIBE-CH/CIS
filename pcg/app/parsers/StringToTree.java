package parsers;

import java.util.ArrayList;

import datastructures.CNAList;
import datastructures.CustomTree;
import datastructures.CustomTreeNode;

public class StringToTree {
    private CNAList input = new CNAList();
    private CustomTree tree;
    private ArrayList<ArrayList<String>> nodesAll = new ArrayList<ArrayList<String>>();
    private CustomTreeNode myParent;

    public StringToTree(CNAList input) {
	this.input.add("A ∨ B => C");
	this.input.add("C ∨ D => E");

	tree = new CustomTree();

	init();
	// createTree();
    }

    private void init() {
	for (int i = 0; i < input.size(); i++) {
	    getBundleAndEffect(input.get(i));
	}

	CustomTreeNode root = new CustomTreeNode(nodesAll.get(
		nodesAll.size() - 1).get(0));
	tree = new CustomTree();
	tree.setRoot(root);
	myParent = root;

	for (int j = nodesAll.size()-1; j >= 0; j--) {
	    String nextParent = null;
	    if (j != 0) {
		nextParent = nodesAll.get(j - 1).get(0);
		System.out.println("Next Parent oben" + nextParent);
	    }
	    createTree(nodesAll.get(j), nextParent);
	}
	System.out.println("Nodes: " + nodesAll);
    }

    private void getBundleAndEffect(String input) {
	ArrayList<String> nodes = new ArrayList<String>();
	String array2[] = input.split("=>");
	String effect = array2[array2.length - 1];
	effect = effect.replace(" ", "");
	//System.out.println("Effect:***" + effect + "*******");
	nodes.add(effect);

	String array[] = array2[0].split("∨");
	for (int i = 0; i < array.length; i++) {
	    String current = array[i];
	    current = current.replace(" ", "");
	    nodes.add(current);
	}
	nodesAll.add(nodes);
    }

    //TODO Make this mess recursiv
    private void createTree(ArrayList<String> nodes, String nextParent) {
	System.out.println("First my Parent: " + myParent);
	CustomTreeNode myParent1 = myParent;
	System.out.println("myParent1 " + myParent1);

	int bundleNumber = 1;
	for (int i = 1; i < nodes.size(); i++) {
	    String curBundle = nodes.get(i);
	    int j = 0;
	    if (curBundle.length() == 2 && curBundle.charAt(0) == '¬') {
		CustomTreeNode node = new CustomTreeNode(""
			+ curBundle.charAt(j) + curBundle.charAt(j + 1));
		tree.addChildtoParentX(node, myParent1);
	    } else if (curBundle.length() == 1) {
		CustomTreeNode node1 = new CustomTreeNode(""
			+ curBundle.charAt(j));
		tree.addChildtoParentX(node1, myParent1);
		
		String bla = "" + node1.toString().charAt(0);
		System.out.println("BlaO: " + bla);
		if (nextParent != null
			&& bla.equals(nextParent)) {
		    myParent = node1;
		    System.out.println("myParent " + myParent);
		    System.out.println("Next Parent unten: " + node1);
		}
	    } else {
		while (j < curBundle.length()) {
		    if (curBundle.charAt(j) == '¬') {
			CustomTreeNode node = new CustomTreeNode(""
				+ curBundle.charAt(j) + curBundle.charAt(j + 1));
			node.setBundle(Integer.toString(bundleNumber));
			tree.addChildtoParentX(node, myParent1);
			j = j + 2;
		    } else if (curBundle.charAt(j) == 'X') {
			CustomTreeNode node = new CustomTreeNode(""
				+ curBundle.charAt(j) + curBundle.charAt(j + 1));
			node.setBundle(Integer.toString(bundleNumber));
			tree.addChildtoParentX(node, myParent1);
			j = j + 2;
		    } else {
			CustomTreeNode node = new CustomTreeNode(""
				+ curBundle.charAt(j));
			node.setBundle(Integer.toString(bundleNumber));
			tree.addChildtoParentX(node, myParent1);
			j++;
			String bla = "" + node.toString().charAt(0);
			System.out.println("Bla: " + bla);
			if (nextParent != null
				&& bla.equals(nextParent)) {
			    myParent = node;
			    System.out.println("Next Parent unten: " + node);
			}
		    }
		}
	    }
	    System.out.println("myParent " + myParent);
	    bundleNumber++;
	}
	CustomTreeNode nodeY = new CustomTreeNode("Y" + nodes.get(0));
	tree.addChildtoParentX(nodeY, myParent1);
    }

    public CustomTree getTree() {
	return tree;
    }
}
