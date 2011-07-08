package trees;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import javax.swing.tree.DefaultTreeModel;

abstract class CNATree extends DefaultTreeModel {
    private CNATreeNode node;

    public CNATree(CNATreeNode node) {
	super(node);
	this.node = node;
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

    public abstract CNATable getTable(CNATreeNode parent, CNATable originalTable);

    /**
     * Helper for fillUpTree(). Sets the $ character.
     */
    private CNAList dollarSetter(CNAList line, int index) {
	line.set(index, "$");
	return line;
    }

    public String toString() {
	return stringWalk(node);
    }

    // TODO Funktioniert noch nicht....
    private String stringWalk(CNATreeNode node) {
	String string = "Tree: {";
	for (int i = 0; i < node.getChildCount(); i++) {
	    CNATreeNode child = (CNATreeNode) node.getChildAt(i);
	    if (child.isLeaf()) {
		string += child.toString() + "}";
		return string;
	    } else {
		string += "|" + child + "|";
		stringWalk(child);
	    }
	}
	return string;
    }

}