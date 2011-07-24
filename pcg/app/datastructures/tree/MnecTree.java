package datastructures.tree;

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;

public class MnecTree extends CNATree {

    public MnecTree(CNATreeNode node) {
	super(node);
    }

    public void walk(CNATreeNode parent, CNATable bundleTable,
	    CNATable mnecTable) {
	int breaks = 0;
	int childCount = parent.getChildCount();

	// Count how many "broken" childs current parent has.
	for (int i = 0; i < childCount; i++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(i);
	    if (compare(child.getCoincLine(), bundleTable)) {
		breaks++;
	    }
	}
	// If every child of current parent breaks and parent itself does not
	// break, we got a MNEC!
	if (breaks == childCount
		&& !compare(parent.getCoincLine(), bundleTable)) {
	    mnecTable.add(parent.getCoincLine());
	}
	for (int i = 0; i < childCount; i++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(i);
	    // Special condition for leaves, when they itself not break they are
	    // a MNEC!
	    if (child.isLeaf() && (!compare(child.getCoincLine(), bundleTable))) {
		mnecTable.add(child.getCoincLine());
	    } else {
		walk(child, bundleTable, mnecTable);
	    }
	}
    }

    private boolean compare(CNAList list, CNATable bundleTable) {
	// CNAList newList = (CNAList) list.clone();
	// newList.negate();
	// for (CNAList bundle : bundleTable) {
	// boolean equal = true;
	// for (int i = 0; i < bundle.size() - 1; i++) {
	// if (!list.get(1).equals(bundle.get(i))
	// && !bundle.get(i).equals("$")
	// && bundle.getLastElement().equals("0")) {
	// equal = false;
	// break;
	// }
	// if(eaual){
	// }
	// }
	boolean isEqual = false;
	CNAList newList = (CNAList) list.clone();
	newList.negate();
	for (CNAList bundle : bundleTable) {
	    for (int i = 0; i < list.size(); i++) {
		// Only if there is a 1 or 0 in nodes data compare, when a
		// dollar do nothing.
		if (newList.get(i).equals("1") || newList.get(i).equals("0")
			|| newList.get(i).length() > 1) {
		    if (newList.get(i).equals(bundle.get(i))) {
			isEqual = true;
		    } else {
			isEqual = false;
			break;
		    }
		}
	    }
	    if (isEqual) {
		if (bundle.getLastElement().equals("1")) {
		    return false;
		}
	    }
	}
	return true;
    }

}
