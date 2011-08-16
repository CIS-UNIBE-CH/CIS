package datastructures.tree;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import java.util.ArrayList;

import javax.swing.tree.DefaultTreeModel;

import datastructures.cna.CNAList;

abstract class CNATree extends DefaultTreeModel {
    private String string;

    public CNATree(CNATreeNode node) {
	super(node);
	string = new String();
    }

    public void fillUpTree(CNATreeNode parent) {
	if (parent.hasOneCare()) {
	} else {
	    CNAList data = parent.getCoincLine();
	    ArrayList<Integer> indexes = parent.getCareIndexes();

	    for (int i = 0; i < indexes.size(); i++) {
		CNAList curNode = dollarSetter((CNAList) data.clone(),
			indexes.get(i));
		CNATreeNode newNode = new CNATreeNode(curNode);
		insertNodeInto(newNode, parent, i);
		fillUpTree(newNode);
	    }
	}
    }
    
    /**
     * Helper for fillUpTree(). Sets the $ character.
     */
    private CNAList dollarSetter(CNAList line, int index) {
	line.set(index, "$");
	return line;
    }

    public String toString(CNATreeNode parent) {
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(i);
	    if (child.isLeaf()) {
		string += child.toString() + "\n";
	    } else {
		string += child.toString() + "\n";
		toString(child);
	    }
	}
	return string;
    }

}