package parser;

import java.util.ArrayList;

import trees.CustomTree;
import trees.CustomTreeNode;

public class StringToTree {
    private String input;
    private CustomTree tree;
    private ArrayList<String> nodes = new ArrayList<String>();

    public StringToTree(String input) {
	this.input = input;
	tree = new CustomTree();

	getBundleAndEffect();
	createTree();
    }

    private void getBundleAndEffect() {
	String array2[] = input.split("=>");
	String effect = array2[array2.length - 1];
	effect = effect.replace(" ", "");
	System.out.println("Effect:***" + effect + "*******");
	nodes.add(effect);

	String array[] = array2[0].split("∨");
	for (int i = 0; i < array.length; i++) {
	    String current = array[i];
	    current = current.replace(" ", "");
	    nodes.add(current);
	}
	System.out.println("Nodes " + nodes);
    }

    private void createTree() {
	CustomTreeNode root = new CustomTreeNode(nodes.get(0));
	tree = new CustomTree();
	tree.setRoot(root);

	int bundleNumber = 1;
	for (int i = 1; i < nodes.size(); i++) {
	    String curBundle = nodes.get(i);
	    int j = 0;
	    if(curBundle.length() == 2 && curBundle.charAt(0) == '¬'){
		CustomTreeNode node = new CustomTreeNode(""
			+ curBundle.charAt(j) + curBundle.charAt(j + 1));
		tree.addChildtoRootX(node, root);
	    }
	    else if (curBundle.length() == 1) {
		CustomTreeNode node = new CustomTreeNode(""
			+ curBundle.charAt(j));
		tree.addChildtoRootX(node, root);
	    } else {
		while (j < curBundle.length()) {
		    if (curBundle.charAt(j) == '¬') {
			CustomTreeNode node = new CustomTreeNode(""
				+ curBundle.charAt(j) + curBundle.charAt(j + 1));
			node.setBundle(Integer.toString(bundleNumber));
			tree.addChildtoRootX(node, root);
			j = j + 2;
		    } else if (curBundle.charAt(j) == 'X') {
			CustomTreeNode node = new CustomTreeNode(""
				+ curBundle.charAt(j) + curBundle.charAt(j + 1));
			node.setBundle(Integer.toString(bundleNumber));
			tree.addChildtoRootX(node, root);
			j = j + 2;
		    } else {
			CustomTreeNode node = new CustomTreeNode(""
				+ curBundle.charAt(j));
			node.setBundle(Integer.toString(bundleNumber));
			tree.addChildtoRootX(node, root);
			j++;
		    }
		}
	    }
	    bundleNumber++;
	}
	CustomTreeNode nodeY = new CustomTreeNode("Y" + nodes.get(0));
	tree.addChildtoRootX(nodeY, root);
    }

    public CustomTree getTree() {
	return tree;
    }
}
