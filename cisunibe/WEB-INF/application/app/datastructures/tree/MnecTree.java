package datastructures.tree;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe.ch@gmail.com>
 * @license GPLv3, for more informations see Readme.mdown
 */

import java.util.ArrayList;

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;

public class MnecTree extends CNATree {

    public MnecTree(CNATreeNode node) {
	super(node);
    }

    public void walk(CNATreeNode parent, CNATable bundleTable,
	    CNATable mnecTable, boolean stopWalk) {
	int childsFound = 0;
	for (int j = 0; j < parent.getChildCount(); j++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(j);
	    if (compare(child.getCoincLine(), bundleTable)) {
		childsFound++;
	    }
	}
	if (childsFound == parent.getChildCount()
		&& !compare(parent.getCoincLine(), bundleTable)) {
	    mnecTable.add(parent.getCoincLine());
	}
	if(compare(parent.getCoincLine(), bundleTable)){
	    stopWalk = true;
	}
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(i);
	    if (!child.isLeaf() && !stopWalk) {
		walk(child, bundleTable, mnecTable, stopWalk);
	    }
	}
	stopWalk = false;
    }

    private boolean compare(CNAList list, CNATable bundleTable) {
	boolean found = false;
	CNAList clone = (CNAList) list.clone();
	ArrayList<CNAList> negations = clone.negate(clone);
	
	for (int i = 0; i < negations.size(); i++) {
	    CNAList newList = negations.get(i);
	    for (int i1 = 1; i1 < bundleTable.size(); i1++) {
		CNAList bundle = bundleTable.get(i1);
		for (int j = 0; j < newList.size(); j++) {
		    if (!newList.get(j).equals("$")) {
			if (newList.get(j).equals(bundle.get(j))) {
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
	}
	return found;
    }
}