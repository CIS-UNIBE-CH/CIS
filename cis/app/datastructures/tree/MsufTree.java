package datastructures.tree;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe.ch@gmail.com>
 * @license GPLv3, for more informations see Readme.mdown
 */

import java.util.concurrent.Callable;

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;

public class MsufTree extends CNATree implements Callable<CNATable> {
    private CNATreeNode parent;
    private final CNATable originalTable;
    private final CNATable msufTable;
    private final boolean stopWalk;
    private CNATreeNode node;

    public MsufTree(CNATreeNode parent, CNATable originalTable,
	    CNATable msufTable, boolean stopWalk) {
	super(parent);
	this.parent = node;
	this.parent = parent;
	this.originalTable = originalTable;
	this.msufTable = msufTable;
	this.stopWalk = stopWalk;
    }

    @Override
    public CNATable call() {
	walk(parent, originalTable, msufTable, stopWalk);
	return msufTable;
    }

    public void walk(CNATreeNode parent, CNATable originalTable,
	    CNATable msufTable, boolean stopWalk) {
	int childsFound = 0;

	for (int j = 0; j < parent.getChildCount(); j++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(j);
	    if (compare(child.getCoincLine(), originalTable)) {
		childsFound++;
	    }
	}
	if (childsFound == parent.getChildCount()
		&& !compare(parent.getCoincLine(), originalTable)) {
	    msufTable.add(parent.getCoincLine());
	}
	if (compare(parent.getCoincLine(), originalTable)) {
	    stopWalk = true;
	}
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(i);

	    if (!child.isLeaf() && !stopWalk) {
		walk(child, originalTable, msufTable, stopWalk);
	    } else if (!stopWalk) {
		if (!compare(child.getCoincLine(), originalTable)) {
		    msufTable.add(child.getCoincLine());
		}
	    }
	}
	stopWalk = false;
    }

    private boolean compare(CNAList list, CNATable originalTable) {
	boolean found = false;
	CNAList newList = (CNAList) list.clone();
	newList.add("0");

	for (int i = 1; i < originalTable.size(); i++) {
	    CNAList line = originalTable.get(i);
	    for (int j = 0; j < newList.size(); j++) {
		if (!newList.get(j).equals("$")) {
		    if (newList.get(j).equals(line.get(j))) {
			found = true;
		    } else {
			found = false;
			break;
		    }
		}
	    }
	    if (found) {
		return found;
	    }
	}
	return found;
    }
}