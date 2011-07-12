package parsers;

import java.util.ArrayList;

import datastructures.CNAList;
import datastructures.CustomTree;
import datastructures.CustomTreeNode;

public class StringToTree {
    private CNAList inputTable = new CNAList();
    private CustomTree tree;
    private ArrayList<ArrayList<String>> allNodes = new ArrayList<ArrayList<String>>();
    private CustomTreeNode parent;
    private int effectLevel;

    public StringToTree(CNAList input) {
	//this.inputTable.add("L ∨ K ∨ A ∨ B  => C");
	//this.inputTable.add("ZX2 ∨ QX2 ∨ CX1  => E");
	//this.inputTable.add("C ∨ B => A");
	this.inputTable.add("A ∨ B ∨ 1 => C");
	this.inputTable.add("C ∨ D ∨ 2 => E");
	this.inputTable.add("E ∨ F ∨ 3 => G");
	this.inputTable.add("G ∨ H ∨ 4 => I");
	//inputTable = input;

	tree = new CustomTree();
	init();
    }

    private void init() {
	for (int i = 0; i < inputTable.size(); i++) {
	    getBundleAndEffect(inputTable.get(i));
	}

	effectLevel = allNodes.size();
	CustomTreeNode root = new CustomTreeNode(allNodes.get(
		allNodes.size() - 1).get(0));
	root.setEffectLevel(effectLevel);
	root.setIsEffect(true);
	tree.setRoot(root);
	parent = root;

	for (int j = allNodes.size()-1; j >= 0; j--) {
	    String nextParentName = null;
	    if (j != 0) {
		nextParentName = allNodes.get(j - 1).get(0);
	    }
	    createTree(allNodes.get(j), nextParentName);
	    effectLevel--;
	}
    }

    private void getBundleAndEffect(String input) {
	ArrayList<String> nodes = new ArrayList<String>();
	String array2[] = input.split("=>");
	String effect = array2[array2.length - 1];
	effect = effect.replace(" ", "");
	nodes.add(effect);

	String array[] = array2[0].split("∨");
	for (int i = 0; i < array.length; i++) {
	    String current = array[i];
	    current = current.replace(" ", "");
	    nodes.add(current);
	}
	allNodes.add(nodes);
    }

    //TODO Make this mess recursiv
    private void createTree(ArrayList<String> nodes, String nextParentName) {
	parent.setEffectLevel(effectLevel);
	parent.setIsEffect(true);
	CustomTreeNode curParent = parent;
	
	int bundleNumber = 1;
	
	for (int i = 1; i < nodes.size(); i++) {
	    String curBundle = nodes.get(i);
	    int j = 0;
	    if (curBundle.length() == 2 && curBundle.charAt(0) == '¬') {
		CustomTreeNode node = new CustomTreeNode(""
			+ curBundle.charAt(j) + curBundle.charAt(j + 1));
		node.setEffectLevel(effectLevel);
		node.setIsEffect(false);
		tree.addChildtoParentX(node, curParent);
	    } else if (curBundle.length() == 1) {
		CustomTreeNode node1 = new CustomTreeNode(""
			+ curBundle.charAt(j));
		node1.setEffectLevel(effectLevel);
		node1.setIsEffect(false);
		tree.addChildtoParentX(node1, curParent);
		String factor = "" + node1.toString().charAt(0);
		if (nextParentName != null
			&& factor.equals(nextParentName)) {
		    parent = node1;
		}
	    } else {
		while (j < curBundle.length()) {
		    if (curBundle.charAt(j) == '¬') {
			CustomTreeNode node = new CustomTreeNode(""
				+ curBundle.charAt(j) + curBundle.charAt(j + 1));
			node.setBundle(Integer.toString(bundleNumber));
			node.setEffectLevel(effectLevel);
			node.setIsEffect(true);
			tree.addChildtoParentX(node, curParent);
			j = j + 2;
		    } else if (curBundle.charAt(j) == 'X') {
			CustomTreeNode node = new CustomTreeNode(""
				+ curBundle.charAt(j) + curBundle.charAt(j + 1));
			node.setBundle(Integer.toString(bundleNumber));
			node.setEffectLevel(effectLevel);
			node.setIsEffect(true);
			tree.addChildtoParentX(node, curParent);
			j = j + 2;
		    } else {
			CustomTreeNode node = new CustomTreeNode(""
				+ curBundle.charAt(j));
			node.setBundle(Integer.toString(bundleNumber));
			node.setEffectLevel(effectLevel);
			node.setIsEffect(true);
			tree.addChildtoParentX(node, curParent);
			j++;
			String factor = "" + node.toString().charAt(0);
			if (nextParentName != null
				&& factor.equals(nextParentName)) {
			    parent = node;
			}
		    }
		}
	    }
	    bundleNumber++;
	}
	//CustomTreeNode nodeY = new CustomTreeNode("Y" + nodes.get(0));
	//nodeY.setEffectLevel(effectLevel);
	//nodeY.setIsEffect(false);
	//tree.addChildtoParentX(nodeY, curParent);
    }

    public CustomTree getTree() {
	return tree;
    }
    
    public int getNumOfEffects(){
	return allNodes.size();
    }
}
