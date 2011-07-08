package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import trees.CNAList;
import trees.CNATable;
import trees.CNATreeNode;
import trees.CustomTree;
import trees.CustomTreeNode;
import trees.MnecTree;
import trees.MsufTree;

public class CNAlgorithm {
    private CNATable originalTable;
    private CNATable msufTable;
    private CNATable mnecTable;
    private CustomTree cnaTree;

    public CNAlgorithm(String[][] table, Boolean useBaumgartnerSample) {
	originalTable = new CNATable(table);

	// Init the fist Step of CNA-Algorithm
	identifySUF(originalTable);
	// framingMinimalTheory();
    }

    /**
     * Step 2 (Step 1 not necessary)
     * 
     * @param table
     **/
    private void identifySUF(CNATable table) {
	CNATable sufTable = table.clone();
	sufTable.removeZeroEffects();
	System.out.println("SUF Table:\n" + sufTable);
	indentifyMSUF(table, sufTable);
    }

    private void indentifyMSUF(CNATable table, CNATable sufTable) {
	MsufTree msufTree;
	// i = 1 because first line holds factor names.
	for (int i = 1; i < sufTable.size(); i++) {
	    CNAList list = sufTable.get(i);
	    // IMPORTANT: Remove effect column of suffLine, if not tree we not
	    // correctly be built.
	    list.remove(list.size() - 1);

	    CNATreeNode root = new CNATreeNode(list);
	    msufTree = new MsufTree(root);
	    msufTree.fillUpTree(root);
	    msufTable = msufTree.getTable(root, originalTable);
	    msufTable.removeDuplicated();
	}
	System.out.println("MSUF Table:\n" + msufTable);

	identifyNEC(msufTable);
    }

    private void identifyNEC(CNATable msufTable) {
	CNATable bundleTable = msufTable.summarizeBundles(msufTable,
		originalTable);
	CNAList necList = msufTable.getNecList();
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
	// Remove effect column
	necList.remove(necList.size() - 1);

	CNATreeNode root = new CNATreeNode(necList);
	mnecTree = new MnecTree(root);

	mnecTree.fillUpTree(root);
	mnecTable = mnecTree.getTable(root, bundleTable);
	mnecTable.removeDuplicated();
	mnecTable.negate();

	System.out.println("\nMNEC Table:\n" + mnecTable);
	framingMinimalTheory(bundleTable);
    }

    private void framingMinimalTheory(CNATable bundleTable) {
	CustomTreeNode node;
	CNAList fmt = new CNAList();
	cnaTree = new CustomTree();
	CNATable factorTable = new CNATable();
	int effectNameIndex = bundleTable.get(0).size() - 1;

	CustomTreeNode root = new CustomTreeNode(bundleTable.get(0).get(
		effectNameIndex));
	cnaTree.setRoot(root);
	// Get Factor Names for Node naming
	CNAList mnecNames = mnecTable.getFactorNames(bundleTable.get(0));
	factorTable.setFactors(mnecNames);

	for (int row = 0; row < factorTable.size(); row++) {
	    for (int col = 0; col < factorTable.get(row).size(); col++) {
		node = new CustomTreeNode(factorTable.get(row).get(col));
		if (factorTable.get(row).size() > 1) {
		    node.setBundle("" + (row + 1));
		    cnaTree.addChildtoRootX(node, root);
		}
		cnaTree.addChildtoRootX(node, root);
	    }
	    node = new CustomTreeNode("X" + (row + 1));
	    if (factorTable.get(row).size() > 1) {
		node.setBundle("" + (row + 1));
	    }
	    cnaTree.addChildtoRootX(node, root);
	}
	CustomTreeNode nodeY = new CustomTreeNode("Y"
		+ bundleTable.get(0).get(effectNameIndex));
	cnaTree.addChildtoRootX(nodeY, root);

	System.out.println("\nFMT: " + fmt);
    }

    // Helpers

    // Getters and Setters

    public CustomTree getTree() {
	return cnaTree;
    }
}
