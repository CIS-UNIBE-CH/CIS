package datastructures.tree;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <pcg.unibe.ch@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;

public class MnecTree extends CNATree {

    public MnecTree(CNATreeNode node) {
	super(node);
    }

    @Override
    public void walk(CNATreeNode parent, CNATable bundleTable,
	    CNATable mnecTable) {
	int childsFound = 0;
	for (int j = 0; j < parent.getChildCount(); j++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(j);
	    if (compare(child.getCoincLine(), bundleTable)) {
		childsFound++;
	    }
	}
	if (childsFound == parent.getChildCount()
		&& !compare(parent.getCoincLine(), bundleTable)) {
	    // System.out.println("Found***************************: " +
	    // parent.getCoincLine());
	    mnecTable.add(parent.getCoincLine());
	}
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(i);
	    if (!child.isLeaf()) {
		walk(child, bundleTable, mnecTable);
	    } else {
		// TODO This is according to baumgartner paper, if this is on
		// graphs with bundles will be plottet correct hoewever there
		// are some cases we have a problem. But it is in the creat mt
		// set which cannot handle those special cases.
		if (!compare(child.getCoincLine(), bundleTable)) {
		    mnecTable.add(child.getCoincLine());
		    System.out.println("That case*********************");
		}
	    }
	}
    }

    private boolean compare(CNAList list, CNATable bundleTable) {
	boolean found = false;
	CNAList newList = (CNAList) list.clone();
	newList.add("1");

	for (int i = 1; i < bundleTable.size(); i++) {
	    CNAList bundle = bundleTable.get(i);
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
		// System.out.println("Check: " + newList);
		// System.out.println("origLine: " + bundle);
		return found;
	    }
	}
	return found;
    }
}