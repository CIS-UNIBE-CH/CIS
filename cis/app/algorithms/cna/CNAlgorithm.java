package algorithms.cna;

/** 
 * Copyright (C) <2011> 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe.ch@gmail.com>
 * @license GPLv3, for more informations see Readme.mdown 
 */

import java.util.ArrayList;
import java.util.HashSet;

import datastructures.cna.CNAList;
import datastructures.cna.CNAListComparator;
import datastructures.cna.CNATable;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;
import datastructures.tree.CNATreeNode;
import datastructures.tree.MnecTree;
import datastructures.tree.MsufTree;

public class CNAlgorithm {
    private final CNATable originalTable;
    private CNAList effects;
    private CNATable sufTable;
    private CNATable msufTable;
    private CNAList nec;
    private final ArrayList<MinimalTheorySet> sets;
    private CNATable mnecTable;
    private final ArrayList<MinimalTheory> allTheories;

    public CNAlgorithm(CNATable table) throws CNAException {
	originalTable = table;
	sets = new ArrayList<MinimalTheorySet>();
	allTheories = new ArrayList<MinimalTheory>();
	identifyPE(originalTable);
    }

    /**
     * Step 0
     * 
     * @param originalTable
     * @throws CNAException
     */
    private void identifyPE(CNATable originalTable) throws CNAException {
	effects = new CNAList();
	CNATable table = originalTable.clone();
	CNAListComparator comparator = new CNAListComparator();

	ArrayList<Integer> indexes = new ArrayList<Integer>();
	for (int row = 1; row < table.size(); row++) {
	    for (int row2 = 1; row2 < table.size(); row2++) {
		int cr = comparator.compare(table.get(row2), table.get(row));
		if (cr != -1) {
		    indexes.add(cr);
		}
	    }
	}
	HashSet duplicate = new HashSet(indexes);
	indexes.clear();
	indexes.addAll(duplicate);
	effects = table.get(0);
	for (int i = indexes.size() - 1; i >= 0; i--) {
	    effects.remove(indexes.get(i));
	}

	for (int i = 0; i < effects.size(); i++) {
	    String cur = effects.get(i);
	    if (cur.contains("¬")) {
		effects.remove(i);
		i--;
	    }

	}
	if (effects.size() < 1) {
	    throw new CNAException("No effects could be identified.");
	}
	run(effects, originalTable);
    }

    /**
     * Step 1
     * 
     * @param effects
     * @throws CNAException
     */
    private void run(CNAList effects, CNATable originalTable)
	    throws CNAException {
	ArrayList<Integer> indexes = new ArrayList<Integer>();
	for (int col = 0; col < originalTable.get(0).size(); col++) {
	    for (int i = 0; i < effects.size(); i++) {
		if (originalTable.get(0).get(col).equals(effects.get(i))) {
		    indexes.add(col);
		}
	    }
	}
	System.out.println("Table: " + originalTable);
	identifySUF(originalTable, indexes);
    }

    /**
     * Step 2
     * 
     * @param table
     * @throws CNAException
     **/
    private void identifySUF(CNATable originalTable, ArrayList<Integer> indexes)
	    throws CNAException {
	CNATable newOrderedOrigTable = new CNATable();
	for (int i = 0; i < indexes.size(); i++) {
	    newOrderedOrigTable = originalTable.clone();
	    newOrderedOrigTable.swap(indexes.get(i), originalTable.get(0)
		    .size() - 1);
	    System.out.println("**********************");
	    System.out.println("Effect: "
		    + newOrderedOrigTable.get(0).getLastElement());

	    sufTable = newOrderedOrigTable.clone();
	    sufTable.removeZeroEffects();
	    if (sufTable.size() <= 1) {
		if (i == indexes.size() - 1) {
		    throw new CNAException(
			    "You entered a coincidence table where no line with a instantiated effect exists or which is not according to minimal diversity condition.");
		}
	    } else {
		System.out.println("SufTable\n" + sufTable);
		indentifyMSUF(newOrderedOrigTable, sufTable);
	    }
	}
    }

    private void indentifyMSUF(CNATable originalTable, CNATable sufTable)
	    throws CNAException {
	MsufTree msufTree;
	msufTable = new CNATable();
	// i = 1 because first line holds factor names.
	for (int i = 1; i < sufTable.size(); i++) {
	    CNAList list = (CNAList) sufTable.get(i).clone();

	    list.remove(list.size() - 1);
	    CNATreeNode root = new CNATreeNode(list);
	    msufTree = new MsufTree(root);
	    msufTree.fillUpTree(root);

	    boolean stopWalk = false;
	    msufTree.walk(root, originalTable, msufTable, stopWalk);
	    msufTable.removeDuplicated();
	}
	System.out.println("MsufTable\n" + msufTable);
	identifyNEC(msufTable, originalTable);
    }

    private void identifyNEC(CNATable msufTable, CNATable originalTable)
	    throws CNAException {
	CNATable bundleTable = msufTable.summarizeBundles(msufTable,
		originalTable);

	nec = msufTable.getNec();
	nec.invert();
	ArrayList<CNAList> negations = nec.negate(nec);

	for (CNAList negatedNec : negations) {
	    for (CNAList list : bundleTable) {
		if (list.equals(negatedNec)) {
		    throw new CNAException(
			    "No necessary condition could be identified.");
		}
	    }
	}

	this.nec = msufTable.getNec();
	identifyMNEC(nec, bundleTable, originalTable);
    }

    private void identifyMNEC(CNAList necList, CNATable bundleTable,
	    CNATable originalTable) {

	necList.invert();
	MnecTree mnecTree;
	mnecTable = new CNATable();
	CNATreeNode root = new CNATreeNode(necList);
	mnecTree = new MnecTree(root);

	mnecTree.fillUpTree(root);
	boolean stopWalk = false;
	mnecTree.walk(root, bundleTable, mnecTable, stopWalk);
	mnecTable.removeDuplicated();

	ArrayList<MinimalTheory> mtList = new ArrayList<MinimalTheory>();
	for (CNAList list : mnecTable) {
	    CNAList mtNames = new CNAList();
	    for (int i = 0; i < list.size(); i++) {
		if (!list.get(i).equals("$")) {
		    mtNames.add(bundleTable.get(0).get(i));
		}
	    }
	    MinimalTheory theory = new MinimalTheory(mtNames, originalTable
		    .get(0).getLastElement());
	    mtList.add(theory);
	}
	System.out.println("Bundle Table\n" + bundleTable);
	allTheories.addAll(mtList);
	System.out.println("MnecTable\n" + mnecTable);
	System.out.println("mtList\n" + mtList);
	System.out.println("**********************");
	createMTSets(mtList);
    }

    private void createMTSets(ArrayList<MinimalTheory> mtList) {
	if (sets.size() == 0) {
	    for (MinimalTheory theory : mtList) {
		MinimalTheorySet set = new MinimalTheorySet();
		set.add(theory);
		sets.add(set);
	    }
	} else {
	    ArrayList<MinimalTheorySet> temp = new ArrayList<MinimalTheorySet>();
	    for (MinimalTheorySet set : sets) {
		for (MinimalTheory theory : mtList) {
		    MinimalTheorySet clone = set.duplicate(set);
		    clone.add(theory);
		    temp.add(clone);
		}
	    }
	    sets.clear();
	    sets.addAll(temp);
	}

    }

    // Getters and Setters

    public CNATable getOriginalTable() {
	return originalTable;
    }

    public CNATable getSufTable() {
	return sufTable;
    }

    public CNATable getMsufTable() {
	return msufTable;
    }

    public CNATable getMnecTable() {
	return mnecTable;
    }

    public CNAList getNecList() {
	return nec;
    }

    public CNAList getEffects() {
	return effects;
    }

    public ArrayList<MinimalTheorySet> getSets() {
	return sets;
    }

    public ArrayList<MinimalTheory> getAllTheories() {
	return allTheories;
    }
}
