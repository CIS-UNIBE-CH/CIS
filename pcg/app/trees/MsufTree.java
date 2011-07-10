package trees;

import java.util.ArrayList;

public class MsufTree extends CNATree {

    CNATable msufTable = new CNATable();

    public MsufTree(CNATreeNode node) {
	super(node);
	// TODO Auto-generated constructor stub
    }

    @Override
    public CNATable getTable(CNATreeNode parent, CNATable originalTable) {
	walk(parent, originalTable);
	return msufTable;
    }

    private void walk(CNATreeNode parent, CNATable originalTable) {
	int breaks = 0;
	int childCount = parent.getChildCount();

	// Count how many "broken" children current parent has.
	for (int i = 0; i < childCount; i++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(i);
	    if (compare(child.getCoincLine(), originalTable)) {
		breaks++;
	    }
	}
	// If every child of current parent breaks and parent itself does not
	// break, we got a msuf!
	if (breaks == childCount
		&& !compare(parent.getCoincLine(), originalTable)) {
	    msufTable.add(parent.getCoincLine());
	}
	for (int i = 0; i < childCount; i++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(i);
	    // Special condition for leaves, when they itself not break they are
	    // a msuf!
	    if (child.isLeaf()
		    && !(compare(child.getCoincLine(), originalTable))) {
		msufTable.add(child.getCoincLine());
	    } else {
		walk(child, originalTable);
	    }
	}
    }

    private boolean compare(ArrayList<String> line, CNATable originalTable) {
	boolean isEqual = false;
	for (int row = 1; row < originalTable.size(); row++) {
	    CNAList curRow = originalTable.get(row);
	    for (int i = 0; i < line.size(); i++) {
		// Only if there is a 1 or 0 in nodes data compare, when a
		// dollar do nothing.
		if (line.get(i).equals("1") || line.get(i).equals("0")) {
		    if (line.get(i).equals(curRow.get(i))) {
			isEqual = true;
		    } else {
			isEqual = false;
			break;
		    }
		}
	    }
	    // Check if there is a line in sample table with effect = 0 which
	    // matches nodes data.
	    if (isEqual) {
		if (curRow.getLastElement().equals("0")) {
		    System.out.println(line + " " + curRow);
		    return true;
		}
	    }
	}
	return false;
    }
}
