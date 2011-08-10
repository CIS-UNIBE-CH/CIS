package datastructures.tree;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <pcg.unibe.ch@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import helpers.CombinationGenerator;

import java.util.ArrayList;

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
		// TODO Problem here, is this condition always right.
		// TODO non redundandency check is missing!
		if (!compare(child.getCoincLine(), bundleTable)) {
		    System.out.println("Added leaf");
		    mnecTable.add(child.getCoincLine());
		}
	    }
	}
    }

    private boolean compare(CNAList list, CNATable bundleTable) {
	boolean found = false;
	CNAList clone = (CNAList) list.clone();
	ArrayList<CNAList> negations = makeNegations(clone);
	
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

    private ArrayList<CNAList> makeNegations(CNAList list) {
	ArrayList<CNAList> negations = new ArrayList<CNAList>();

	for (String str : list) {
	    if (str.length() == 1) {
		if (negations.size() == 0) {
		    CNAList list1 = new CNAList();
		    list1.add(str);
		    negations.add(list1);
		} else {
		    for (CNAList list1 : negations) {
			list1.add(str);
		    }
		}

	    } else {
		int size = str.length();
		CNAList permutations = generatePermutations(size, str);

		if (negations.size() == 0) {
		    for (String perm : permutations) {
			CNAList list1 = new CNAList();
			list1.add(perm);
			negations.add(list1);
		    }
		} else {
		    ArrayList<CNAList> swap = new ArrayList<CNAList>();
		    for (CNAList curList : negations) {
			for (String perm : permutations) {
			    CNAList clone = curList.clone(curList);
			    clone.add(perm);
			    swap.add(clone);
			}
		    }
		    negations.clear();
		    negations.addAll(swap);
		}
	    }
	}
	System.out.println("list: " + list.toString());
	System.out.println("Negations\n" + negations);
	System.out.println("****************************");

	for (CNAList cur : negations) {
	    cur.add("1");
	}

	return negations;

    }

    private CNAList generatePermutations(int size, String str) {
	CNAList permutations = new CNAList();
	String input[] = new String[2];
	input[0] = "0";
	input[1] = "1";
	CombinationGenerator<String> generator = new CombinationGenerator<String>(
		size, input);

	for (String s[] : generator) {
	    String cur = "";
	    for (int i = 0; i < s.length; i++) {
		cur += s[i];
	    }
	    permutations.add(cur);
	}
	CNAList list = new CNAList();
	list.add(str);
	list.negate();

	// System.out.println("List" + list);
	// System.out.println("Negated List: " + list);
	// System.out.println("Permutations: " + permutations);

	for (int i = permutations.size() - 1; i >= 0; i--) {
	    if (permutations.get(i).equals(list.get(0))) {
		permutations.remove(i);
	    }
	}

	// System.out.println("Permutations after: " + permutations);

	return permutations;
    }
}