package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import helper.BaumgartnerSampleTable;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.tree.DefaultTreeModel;

import trees.CustomTree;
import trees.CustomTreeNode;
import trees.SufTreeNode;

public class CNA {
    private ArrayList<ArrayList<String>> origCoincTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> bundleCoincTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> sufTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> msufTable = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> mnecTable = new ArrayList<ArrayList<String>>();
    private ArrayList<String> necLine = new ArrayList<String>();
    private ArrayList<String> negatedNecLine = new ArrayList<String>();
    private ArrayList<String> fmt = new ArrayList<String>();
    private CustomTree cnaTree = new CustomTree();
    private DefaultTreeModel msufTree;
    private DefaultTreeModel mnecTree;

    // For Testing Only
    @SuppressWarnings("unused")
    private boolean useBaumgartnerSample;
    private ArrayList<ArrayList<String>> sufTableForTesting = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> msufTableForTesting = new ArrayList<ArrayList<String>>();
    private ArrayList<String> necLineForTesting = new ArrayList<String>();
    private ArrayList<String> negatedNecLineForTesting = new ArrayList<String>();
    private ArrayList<ArrayList<String>> mnecTableForTesting = new ArrayList<ArrayList<String>>();

    public CNA(String[][] table, Boolean useBaumgartnerSample) {
	origCoincTable = ArrayToArrayList(table);
	this.useBaumgartnerSample = useBaumgartnerSample;

	// For Testing only
	if (useBaumgartnerSample) {
	    BaumgartnerSampleTable baumgartnerSampleTable = new BaumgartnerSampleTable();
	    System.out.println(baumgartnerSampleTable);
	    origCoincTable.clear();
	    origCoincTable = baumgartnerSampleTable.getSampleTable();
	}

	// Init the CNA-Algorithm
	identifySUF();
	identifyMSUF();
	identifyNEC();
	framingMinimalTheory();
    }

    /** Step 2 (Step 1 not necessary)**/
    private void identifySUF() {
	sufTable = clone2DArrayList(origCoincTable);
	for (int r = sufTable.size() - 1; r >= 0; r--) {
	    if (sufTable.get(r).get(sufTable.get(r).size() - 1).equals("0")) {
		sufTable.remove(r);
	    }
	}
	System.out.println("SUF Table:\n" + tableToString(sufTable));

	// For Testing only
	sufTableForTesting = clone2DArrayList(sufTable);
    }

    /** Step 3 **/
    private void identifyMSUF() {
	// i = 1 because first line holds factor names.
	for (int i = 1; i < sufTable.size(); i++) {
	    ArrayList<String> sufTableLine = sufTable.get(i);
	    // IMPORTANT: Remove effect column of suffLine, if not tree we not
	    // correctly be built.
	    sufTableLine.remove(sufTableLine.size() - 1);

	    SufTreeNode root = new SufTreeNode(sufTableLine);
	    msufTree = new DefaultTreeModel(root);

	    fillUpTree(root, msufTree);
	    msufDetectionWalk(root, msufTable);

	    // Very interesting we got duplicated entries!
	    HashSet removeDuplicated = new HashSet(msufTable);
	    msufTable.clear();
	    msufTable.addAll(removeDuplicated);
	}
	System.out.println("MSUF Table:\n" + tableToString(msufTable));

	// For Testing only
	msufTableForTesting = clone2DArrayList(msufTable);
    }

    /** Step 5 (Step 4 is not necessary, because we know where effect column is) */
    private void identifyNEC() {
	bundleCoincTable = createBundleCoincTable(msufTable);
	System.out.println("New Sample Table:\n"
		+ tableToString(bundleCoincTable));

	// Get NecLine
	for (int i = 0; i < msufTable.size(); i++) {
	    String line = "";
	    ArrayList<String> curMsuf = msufTable.get(i);
	    for (int j = 0; j < curMsuf.size(); j++) {
		if (curMsuf.get(j).equals("1") || curMsuf.get(j).equals("0")) {
		    line += curMsuf.get(j);
		}
	    }
	    necLine.add(line);
	}
	System.out.println("NEC Line: " + necLine);

	// Do the negate necLine
	for (int k = 0; k < necLine.size(); k++) {
	    String cur = necLine.get(k);
	    cur = cur.replace("1", "3");
	    cur = cur.replace("0", "1");
	    cur = cur.replace("3", "0");
	    negatedNecLine.add(cur);
	}
	// Add effect column
	negatedNecLine.add("1");

	System.out.println("NEC Line: " + necLine);
	System.out.println("Negated NEC Line (with Effect): " + negatedNecLine);

	// Check if NEC Line does not exist in table.
	boolean necOK = false;
	for (int j = 0; j < bundleCoincTable.size(); j++) {
	    if (bundleCoincTable.get(j).equals(necLine)) {
		necOK = false;
		System.out.println("NEC Line check has FAILED!");
		break;
	    } else {
		necOK = true;
	    }
	}

	// For Testing Only
	negatedNecLineForTesting = (ArrayList<String>) negatedNecLine.clone();
	necLineForTesting = (ArrayList<String>) necLine.clone();

	if (necOK) {
	    System.out.println("NEC Line: " + necToString(msufTable));
	    identifyMNEC();
	}
    }

    /** Step 6 */
    private void identifyMNEC() {
	// Remove effect column
	negatedNecLine.remove(negatedNecLine.size() - 1);

	SufTreeNode root = new SufTreeNode(negatedNecLine);
	mnecTree = new DefaultTreeModel(root);

	fillUpTree(root, mnecTree);
	mnecDetectionWalk(root, mnecTable);

	HashSet removeDuplicated = new HashSet(mnecTable);
	mnecTable.clear();
	mnecTable.addAll(removeDuplicated);

	for (int row = 0; row < mnecTable.size(); row++) {
	    for (int col = 0; col < mnecTable.get(row).size(); col++) {
		String cur = mnecTable.get(row).get(col);
		cur = cur.replace("1", "3");
		cur = cur.replace("0", "1");
		cur = cur.replace("3", "0");
		mnecTable.get(row).set(col, cur);
	    }
	}
	System.out.println("\nMNEC Table:\n" + tableToString(mnecTable));
	System.out.println("MNEC: "
		+ getMnecFactorNames(mnecTable, bundleCoincTable.get(0)));

	// For Testing Only
	mnecTableForTesting = clone2DArrayList(mnecTable);
    }

    /** Step 7 and Build Tree for Graph Plotting */
    private void framingMinimalTheory() {
	int effectNameIndex = bundleCoincTable.get(0).size() - 1;
	// Setup Tree and root node.
	CustomTreeNode root = new CustomTreeNode(bundleCoincTable.get(0).get(
		effectNameIndex));
	cnaTree.setRoot(root);
	// Get Factor Names for Node naming
	ArrayList<String> mnecNames = getMnecFactorNames(mnecTable,
		bundleCoincTable.get(0));

	String coFactor = "X";
	for (int i = 0; i < mnecNames.size(); i++) {
	    String curMnec = mnecNames.get(i);
	    int bundleNumber = i + 1;

	    ArrayList<String> singleFactor = new ArrayList<String>();
	    int j = 0;
	    while (j < curMnec.length()) {
		// If Factor is negative. j+2 because of ¬ character
		if (curMnec.charAt(j) == '¬') {
		    singleFactor.add("" + curMnec.charAt(j)
			    + curMnec.charAt(j + 1));
		    j = j + 2;
		} else {
		    singleFactor.add("" + curMnec.charAt(j));
		    j++;
		}
	    }
	    // Adding all Factors of current MNEC Bundle to tree and set proper
	    // bundle number.
	    for (int k = 0; k < singleFactor.size(); k++) {
		CustomTreeNode node = new CustomTreeNode(singleFactor.get(k));
		node.setBundle("" + bundleNumber);
		cnaTree.addChildtoRootX(node, root);
	    }
	    // Add current Bundle plus X-CoFactor to fmt list.
	    fmt.add(curMnec + coFactor + "" + (i + 1));

	    // Add X-CoFactor Node to current bundle.
	    CustomTreeNode nodeX = new CustomTreeNode(coFactor + "" + (i + 1));
	    nodeX.setBundle("" + bundleNumber);
	    cnaTree.addChildtoRootX(nodeX, root);
	}
	// Add Y-Alternative Factor to Tree.
	CustomTreeNode nodeY = new CustomTreeNode("Y"
		+ bundleCoincTable.get(0).get(effectNameIndex));
	cnaTree.addChildtoRootX(nodeY, root);
	// Add Y-Alternative Factor to Tree.
	fmt.add("Y" + bundleCoincTable.get(0).get(effectNameIndex));

	System.out.println("\nFMT: " + fmt);
    }

    /**
     * Core Algorithm of Step 3/identifyMSUF(). Makes a pre order tree walk and
     * detects msuf's.
     */
    private void msufDetectionWalk(SufTreeNode parent,
	    ArrayList<ArrayList<String>> table) {
	int breaks = 0;
	int childCount = parent.getChildCount();

	// Count how many "broken" children current parent has.
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    if (shouldBreakMsuf(child.getCoincLine())) {
		breaks++;
	    }
	}
	// If every child of current parent breaks and parent itself does not
	// break, we got a msuf!
	if (breaks == childCount && !shouldBreakMsuf(parent.getCoincLine())) {
	    table.add(parent.getCoincLine());
	}
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    // Special condition for leaves, when they itself not break they are
	    // a msuf!
	    if (child.isLeaf() && !(shouldBreakMsuf(child.getCoincLine()))) {
		table.add(child.getCoincLine());
	    } else {
		msufDetectionWalk(child, table);
	    }
	}
    }

    /**
     * Helper msufDetectionWalk(). Used to compare data of a tree node with
     * every row of given original coincidence table.
     */
    private boolean shouldBreakMsuf(ArrayList<String> line) {
	boolean isEqual = false;
	for (int r = 1; r < origCoincTable.size(); r++) {
	    ArrayList<String> curRow = origCoincTable.get(r);
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
		if (curRow.get(curRow.size() - 1).equals("0")) {
		    return true;
		}
	    }
	}
	return false;
    }

    /** Helper Step 6/identifyMNEC(). */
    private void mnecDetectionWalk(SufTreeNode parent,
	    ArrayList<ArrayList<String>> table) {
	int breaks = 0;
	int childCount = parent.getChildCount();

	// Count how many "broken" childs current parent has.
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    if (shouldBreakMnec(child.getCoincLine())) {
		breaks++;
	    }
	}
	// If every child of current parent breaks and parent itself does not
	// break, we got a MNEC!
	if (breaks == childCount && !shouldBreakMnec(parent.getCoincLine())) {
	    table.add(parent.getCoincLine());
	}
	for (int i = 0; i < childCount; i++) {
	    SufTreeNode child = (SufTreeNode) parent.getChildAt(i);
	    // Special condition for leaves, when they itself not break they are
	    // a MNEC!
	    if (child.isLeaf() && (!shouldBreakMnec(child.getCoincLine()))) {
		table.add(child.getCoincLine());
	    } else {
		mnecDetectionWalk(child, table);
	    }
	}
    }

    /** Helper mnecWalk(). */
    private boolean shouldBreakMnec(ArrayList<String> line) {
	boolean isEqual = false;
	for (int r = 1; r < bundleCoincTable.size(); r++) {
	    ArrayList<String> curRow = bundleCoincTable.get(r);
	    for (int i = 0; i < line.size(); i++) {
		// Only if there is a 1 or 0 in nodes data compare, when a
		// dollar do nothing.
		if (line.get(i).equals("1") || line.get(i).equals("0")
			|| line.get(i).length() > 1) {
		    if (line.get(i).equals(curRow.get(i))) {
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

    /**
     * Helper Step 3/identifyMSUF() and 6/identifyMNEC().
     */
    private void fillUpTree(SufTreeNode parent, DefaultTreeModel tree) {
	if (parent.hasOneCare()) {
	} else {
	    ArrayList<String> data = parent.getCoincLine();
	    ArrayList<Integer> indexes = parent.getCareIndexes();

	    for (int i = 0; i < indexes.size(); i++) {
		ArrayList<String> curNode = dollarSetter(
			(ArrayList<String>) data.clone(), indexes.get(i));
		SufTreeNode newNode = new SufTreeNode(curNode);
		tree.insertNodeInto(newNode, parent, i);
		fillUpTree(newNode, tree);
	    }
	}
    }

    /**
     * Helper for fillUpTree(). Sets the $ character.
     */
    private ArrayList<String> dollarSetter(ArrayList<String> line, int index) {
	line.set(index, "$");
	return line;
    }

    
    /**
     * Helper Step 5/identifyNEC(). Creates out of original coincidence Table a
     * coincidence Table with following schema: Every factor in a bundle is in
     * same column, factor order is same as in msuf Table.
     */
    private ArrayList<ArrayList<String>> createBundleCoincTable(
	    ArrayList<ArrayList<String>> table) {
	ArrayList<ArrayList<String>> bundleCoincTable = new ArrayList<ArrayList<String>>();

	for (int k = 0; k < origCoincTable.size(); k++) {
	    ArrayList<String> curSample = origCoincTable.get(k);
	    ArrayList<String> current = new ArrayList<String>();

	    for (int i = 0; i < table.size(); i++) {
		String line = "";
		ArrayList<String> curMsuf = table.get(i);
		for (int j = 0; j < curMsuf.size(); j++) {
		    if (curMsuf.get(j).equals("1")
			    || curMsuf.get(j).equals("0")) {
			line += curSample.get(j);
		    }
		}
		current.add(line);
	    }
	    // Add effect column
	    current.add(curSample.get(curSample.size() - 1));
	    bundleCoincTable.add(current);
	}
	return bundleCoincTable;
    }

    /**
     * Helper Step 5/identifyNEC(). Transformation of necTable in a more human
     * readable representation.
     */
    private String necToString(ArrayList<ArrayList<String>> necTable) {
	ArrayList<String> factorNames = origCoincTable.get(0);
	String output = "";
	for (int i = 0; i < necTable.size(); i++) {
	    ArrayList<String> curRow = necTable.get(i);
	    for (int j = 0; j < curRow.size(); j++) {
		if (curRow.get(j).equals("1")) {
		    output += factorNames.get(j);
		}
		if (curRow.get(j).equals("0")) {
		    output += "¬" + factorNames.get(j);
		}
	    }
	    if (i < necTable.size() - 1) {
		output += " ∨ ";
	    }
	}
	return output;
    }

    /**
     * Helper Step 6/identifyMNEC() and Step 7/framingMinimalTheory().
     * Transformation of mnecTable in a more human readable representation.
     */
    private ArrayList<String> getMnecFactorNames(
	    ArrayList<ArrayList<String>> mnecTable, ArrayList<String> names) {
	ArrayList<String> factorNames = names;
	ArrayList<String> mnecNames = new ArrayList<String>();

	for (int i = mnecTable.size() - 1; i >= 0; i--) {
	    ArrayList<String> curRow = mnecTable.get(i);
	    for (int j = 0; j < curRow.size(); j++) {
		String curCol = curRow.get(j);
		if (!curCol.equals("$")) {
		    mnecNames.add(factorNames.get(j));
		}
	    }
	}
	return mnecNames;
    }

    private ArrayList<ArrayList<String>> ArrayToArrayList(String[][] table) {
	ArrayList<ArrayList<String>> tableList = new ArrayList<ArrayList<String>>();
	for (int r = 0; r < table.length; r++) {
	    tableList.add(new ArrayList<String>());
	    for (int c = 0; c < table[r].length; c++) {
		tableList.get(r).add(table[r][c]);
	    }
	}
	return tableList;
    }

    private String tableToString(ArrayList<ArrayList<String>> tableList) {
	String print = "";
	for (int r = 0; r < tableList.size(); r++) {
	    for (int c = 0; c < tableList.get(r).size(); c++) {
		print += tableList.get(r).get(c) + " ";
	    }
	    print += "\n";
	}
	return print;
    }

    private ArrayList<ArrayList<String>> clone2DArrayList(
	    ArrayList<ArrayList<String>> toClone) {
	ArrayList<ArrayList<String>> copy = new ArrayList<ArrayList<String>>();
	ArrayList<String> col;
	for (int i = 0; i < toClone.size(); i++) {
	    col = (ArrayList<String>) toClone.get(i).clone();
	    copy.add(col);
	}
	return copy;
    }

    public CustomTree getCnaTree() {
	return cnaTree;
    }

    /** For Testing only */
    public ArrayList<String> getFmt() {
	return fmt;
    }

    public ArrayList<ArrayList<String>> getBundleCoincTable() {
	return bundleCoincTable;
    }

    public ArrayList<ArrayList<String>> getMsufTableForTesting() {
	return msufTableForTesting;
    }

    public ArrayList<ArrayList<String>> getMnecTableForTesting() {
	return mnecTableForTesting;
    }

    public ArrayList<ArrayList<String>> getSufTableForTesting() {
	return sufTableForTesting;
    }

    public ArrayList<String> getNecLineForTesting() {
	return necLineForTesting;
    }

    public ArrayList<String> getNegatedNecLineForTesting() {
	return negatedNecLineForTesting;
    }

    public void setUseBaumgartnerSample(boolean useBaumgartnerSample) {
	this.useBaumgartnerSample = useBaumgartnerSample;
    }
}
