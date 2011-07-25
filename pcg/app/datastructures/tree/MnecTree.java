package datastructures.tree;

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
//	System.out.println("Bundle Table\n"+ bundleTable);
//	System.out.println("\nParent: " + parent.getCoincLine());
	for (int j = 0; j < parent.getChildCount(); j++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(j);
//	    System.out.println("Child: " + child.getCoincLine());
	    if (compare(child.getCoincLine(), bundleTable)) {
//		System.out.println("true");
		childsFound++;
	    }else{
//		System.out.println("false");
	    }
	}
	if (childsFound == parent.getChildCount() && !compare(parent.getCoincLine(), bundleTable)) {
	    mnecTable.add(parent.getCoincLine());
	}

	for (int i = 0; i < parent.getChildCount(); i++) {
	    CNATreeNode child = (CNATreeNode) parent.getChildAt(i);
	    if (!child.isLeaf()) {
		walk(child, bundleTable, mnecTable);
	    }
	}
    }

    private boolean compare(CNAList list, CNATable bundleTable) {
	
	boolean wasFound = false;
	CNAList newList = (CNAList) list.clone();
	newList.add("1");

	for (int i = 1; i < bundleTable.size(); i++) {
	    CNAList bundle = bundleTable.get(i);
	    for (int j = 0; j < newList.size(); j++) {
		if (!newList.get(j).equals("$")) {
		    if (newList.get(j).equals(bundle.get(j))) {
			wasFound = true;
		    } else {
			wasFound = false;
			break;
		    }
		}
	    }
	    if(wasFound){
		return wasFound;
	    }
	}
	return wasFound;
    }

}
