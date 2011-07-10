package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import datastructures.CNAList;
import datastructures.CNATable;
import datastructures.CNATreeNode;
import datastructures.MnecTree;
import datastructures.MsufTree;

public class CNAlgorithm {
    private CNATable originalTable;
    private CNATable sufTable;
    private CNATable msufTable;
    private CNAList necList;

    private CNATable mnecTable;
    private String datastructurestring;

    public CNAlgorithm(String[][] table) {
	originalTable = new CNATable(table);
	init();
    }

    public CNAlgorithm(CNATable table) {
	originalTable = table;
	init();
    }

    private void init() {
	identifySUF(originalTable);
    }

    /**
     * Step 2 (Step 1 not necessary)
     * 
     * @param table
     **/
    private void identifySUF(CNATable table) {
	sufTable = table.clone();
	sufTable.removeZeroEffects();
	System.out.println("SUF Table:\n" + sufTable);
	indentifyMSUF(table, sufTable);
    }

    private void indentifyMSUF(CNATable table, CNATable sufTable) {
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
	System.out.println("MSUF Table:\n" + msufTable);

	identifyNEC(msufTable);
    }

    private void identifyNEC(CNATable msufTable) {
	CNATable bundleTable = msufTable.summarizeBundles(msufTable,
		originalTable);
	necList = msufTable.getNecList();
	necList.negate();
	// Add effect column
	necList.add("1");

	System.out.println("Negated NEC Line (with Effect): " + necList);
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
	    System.out
		    .println("NEC Line: " + msufTable.toString(originalTable));
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

	System.out.println("\nMNEC Table:\n" + mnecTable);
	System.out.println(bundleTable);
	framingMinimalTheory(bundleTable);
    }

    private void framingMinimalTheory(CNATable bundleTable) {
	datastructurestring = new String();
	CNAList fmt = new CNAList();
	CNAList mnecNames = mnecTable.getFactorNames(bundleTable.get(0));
	System.out.println("m" + mnecNames);

	String coFactor = "X";
	for (int i = 0; i < mnecNames.size(); i++) {
	    String curMnec = mnecNames.get(i);
	    fmt.add(curMnec + coFactor + "" + (i + 1));
	}

	for (int k = 0; k < fmt.size(); k++) {
	    datastructurestring += fmt.get(k);
	    if (k != fmt.size() - 1) {
		datastructurestring += " ∨ ";
	    }
	}
	datastructurestring += " => " + bundleTable.get(0).getLastElement();

	System.out.println("\nFMT: " + datastructurestring);
    }

    // Getters and Setters

    public String getdatastructurestring() {
	return datastructurestring;
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
}
