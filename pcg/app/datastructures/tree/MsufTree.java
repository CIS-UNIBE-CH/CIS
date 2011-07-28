package datastructures.tree;

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;

public class MsufTree extends CNATree {

    public MsufTree(CNATreeNode node) {
	super(node);
    }

    @Override
    public void walk(CNATreeNode parent, CNATable originalTable,
	    CNATable msufTable) {
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
	for (int i = 0; i < parent.getChildCount(); i++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(i);
	    if (!child.isLeaf()) {
		walk(child, originalTable, msufTable);
	    } else {
		if (!compare(child.getCoincLine(), originalTable)) {
		    msufTable.add(child.getCoincLine());
		}
	    }
	}
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