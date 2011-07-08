package trees;

import java.util.ArrayList;

public class MnecTree extends CNATree {

    private CNATable mnecTable = new CNATable();

    public MnecTree(CNATreeNode node) {
	super(node);
    }

    @Override
    public CNATable getTable(CNATreeNode parent, CNATable bundleTable) {
	walk(parent, bundleTable);
	return mnecTable;
    }

    private void walk(CNATreeNode parent, CNATable bundleTable) {
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
		walk(child, bundleTable);
	    }
	}
    }

    /** Helper mnecWalk(). */
    private boolean compare(CNAList list, CNATable bundleTable) {
	boolean isEqual = false;
	for (int r = 1; r < bundleTable.size(); r++) {
	    ArrayList<String> curRow = bundleTable.get(r);
	    for (int i = 0; i < list.size(); i++) {
		// Only if there is a 1 or 0 in nodes data compare, when a
		// dollar do nothing.
		if (list.get(i).equals("1") || list.get(i).equals("0")
			|| list.get(i).length() > 1) {
		    if (list.get(i).equals(curRow.get(i))) {
			isEqual = true;
		    } else {
			isEqual = false;
			break;
		    }
		}
	    }
	    if (isEqual) {
		if (curRow.get(curRow.size() - 1).equals("1")) {
		    return false;
		}
	    }
	}
	return true;
    }
}
