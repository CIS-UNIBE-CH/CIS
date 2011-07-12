package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;
import java.util.HashSet;

import datastructures.CNAList;
import datastructures.CNATable;
import datastructures.CNATreeNode;
import datastructures.ListComparator;
import datastructures.MnecTree;
import datastructures.MsufTree;

public class CNAlgorithm {
    private CNATable originalTable;
    private CNAList effects;
    private CNATable sufTable;
    private CNATable msufTable;
    private CNAList necList;
    private CNAList fmt;
    private CNATable deleted;

    private CNATable mnecTable;

    public CNAlgorithm(String[][] table, int randomLines) {
	originalTable = new CNATable(table);
	deleted = new CNATable();
	for (int i = 0; i < randomLines; i++) {
	    int rand = (int) Math.random() * (originalTable.size() - 1) + 1;
	    deleted.add(originalTable.get(rand));
	    originalTable.remove(rand);
	}
	init();

    }

    public CNAlgorithm(CNATable table) {
	originalTable = table;
	init();
    }

    private void init() {
	identifyPE(originalTable);
    }

    /**
     * Step 0
     * 
     * @param originalTable
     */
    private void identifyPE(CNATable originalTable) {
	effects = new CNAList();
	CNATable table = originalTable.clone();
	ListComparator comparator = new ListComparator();

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
	System.out.println(indexes);
	effects = table.get(0);
	System.out.println(effects);
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
	run(effects, originalTable);
    }

    /**
     * Step 1
     * 
     * @param effects
     */
    private void run(CNAList effects, CNATable originalTable) {
	CNATable table;
	ArrayList<Integer> indexes = new ArrayList<Integer>();
	for (int col = 0; col < originalTable.get(0).size(); col++) {
	    for (int i = 0; i < effects.size(); i++) {
		if (originalTable.get(0).get(col).equals(effects.get(i))) {
		    indexes.add(col);
		}
	    }
	}

	for (int i = 0; i < indexes.size(); i++) {
	    table = originalTable.clone();
	    table.swap(indexes.get(i), originalTable.get(0).size() - 1);
	    identifySUF(table);
	}
	System.out.println(fmt);

    }

    /**
     * Step 2
     * 
     * @param table
     **/
    private void identifySUF(CNATable originalTable) {
	sufTable = originalTable.clone();
	sufTable.removeZeroEffects();
	indentifyMSUF(originalTable, sufTable);
    }

    private void indentifyMSUF(CNATable originalTable, CNATable sufTable) {
	MsufTree msufTree;
	msufTable = new CNATable();
	// i = 1 because first line holds factor names.
	for (int i = 1; i < sufTable.size(); i++) {
	    CNAList list = (CNAList) sufTable.get(i).clone();

	    // IMPORTANT: Remove effect column of suffLine, if not tree we not
	    // correctly be built.
	    list.removeLastElement();

	    CNATreeNode root = new CNATreeNode(list);
	    msufTree = new MsufTree(root);
	    msufTree.fillUpTree(root);
	    msufTree.walk(root, originalTable, msufTable);
	    msufTable.removeDuplicated();
	}
	identifyNEC(msufTable, originalTable);
    }

    private void identifyNEC(CNATable msufTable, CNATable originalTable) {
	CNATable bundleTable = msufTable.summarizeBundles(msufTable,
		originalTable);
	necList = msufTable.getNecList();
	necList.negate();
	// Add effect column
	necList.add("1");

	// Check if NEC Line does not exist in table.
	boolean necOK = false;
	for (int j = 0; j < bundleTable.size(); j++) {
	    if (bundleTable.get(j).equals(necList)) {
		necOK = false;
		System.out.println("NEC Line check has FAILED!");
		break;
	    } else {
		necOK = true;
	    }
	}
	if (necOK) {
	    identifyMNEC(necList, bundleTable);
	}
    }

    private void identifyMNEC(CNAList necList, CNATable bundleTable) {
	MnecTree mnecTree;
	mnecTable = new CNATable();
	// Remove effect column
	necList.remove(necList.size() - 1);

	CNATreeNode root = new CNATreeNode(necList);
	mnecTree = new MnecTree(root);

	mnecTree.fillUpTree(root);
	mnecTree.walk(root, bundleTable, mnecTable);
	mnecTable.removeDuplicated();
	mnecTable.negate();

	framingMinimalTheory(bundleTable);
    }

    private void framingMinimalTheory(CNATable bundleTable) {
	String datastructurestring = new String();
	fmt = new CNAList();
	CNAList list = new CNAList();
	CNAList mnecNames = mnecTable.getFactorNames(bundleTable.get(0));

	String coFactor = "X";
	for (int i = 0; i < mnecNames.size(); i++) {
	    String curMnec = mnecNames.get(i);
	    list.add(curMnec + coFactor + "" + (i + 1));
	}

	for (int k = 0; k < list.size(); k++) {
	    datastructurestring += list.get(k);
	    if (k != list.size() - 1) {
		datastructurestring += " ∨ ";
	    }
	}
	datastructurestring += " => " + bundleTable.get(0).getLastElement();
	fmt.add(datastructurestring);
    }

    // Getters and Setters

    public CNATable getOriginalTable() {
	return originalTable;
    }

    public CNAList getFmt() {
	return fmt;
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
	return necList;
    }

    public CNAList getEffects() {
	return effects;
    }

    public CNATable getDeleted() {
	return deleted;
    }

}
