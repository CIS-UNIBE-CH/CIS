package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

public class MTSetToTable {
    private MinimalTheorySet set;
    ArrayList<CNATable> mtCoincTables;
    CNATable curMTTable;
    MinimalTheory curMT;
    CNATable curTable;
    CNATable nextTable;
    CNATable coincTable;

    public MTSetToTable(MinimalTheorySet set) {
	this.set = set;
	mtCoincTables = new ArrayList<CNATable>();
	curMTTable = new CNATable();
	curMT = new MinimalTheory();
	coincTable = new CNATable();

	createTables();
	mergeTables();
//	coincTable.get(0).set(coincTable.get(0).size()-1, "W");
//	System.out.println("Merged Tables\n" + coincTable);
    }

    /** Step 1 */
    private void createTables() {
	for (MinimalTheory theory : set) {
	    curMT = theory;
	    curMTTable = new CNATable();

	    addFactorNames();
	    binaryFillUp();
	    fillUpEffectCol();
	    mtCoincTables.add(curMTTable);
	}
	System.out.println("***************");
	for (CNATable table : mtCoincTables) {
	    System.out.println("All Tables\n" + table);
	}
	System.out.println("***************");
    }

    private void addFactorNames() {
	CNAList factorNames = new CNAList();
	for (String cur : curMT.getFactors()) {
	    factorNames.add(cur);
	}
	curMTTable.add(factorNames);
    }

    private void binaryFillUp() {
	// Generate Binary Numbers
	int countTo = (int) Math.pow(2, curMTTable.get(0).size());
	for (int i = 0; i < countTo; i++) {
	    String binaryNumber = Integer.toBinaryString(i);
	    int binaryNumberLength = binaryNumber.length();

	    // Add Pre-Zeros, so all binary numbers have the same length
	    if (binaryNumberLength != (curMTTable.get(0).size())) {
		while (binaryNumber.length() < curMTTable.get(0).size()) {
		    binaryNumber = "0" + binaryNumber;
		}
	    }
	    CNAList binNumber = new CNAList();
	    for (int j = 0; j < binaryNumber.length(); j++) {
		binNumber.add("" + binaryNumber.charAt(j));
	    }
	    curMTTable.add(binNumber);
	}
    }

    private void fillUpEffectCol() {
	// Add effect name
	String effect = curMT.getEffect();
	curMTTable.get(0).add(effect);
	CNATable bundledFactors = curMT.getBundleFactors();

	for (int i = 1; i < curMTTable.size(); i++) {
	    int index = 0;
	    boolean instantiation = false;
	    for (CNAList cur : bundledFactors) {
		int endIndex = index + cur.size();
		if (isBundleInstantiated(index, endIndex, curMTTable.get(i))) {
		    curMTTable.get(i).add("1");
		    instantiation = true;
		    break;
		}
		index += cur.size();
	    }
	    if (!instantiation) {
		curMTTable.get(i).add("0");
	    }
	}
    }

    /** Step 2 */
    private void mergeTables() {
	curTable = mtCoincTables.get(0);
	if (mtCoincTables.size() == 1) {
	    coincTable = curTable;
	} else {
	    for (int i = 1; i < mtCoincTables.size(); i++) {
		nextTable = mtCoincTables.get(i);
		mergeZeroLines();
		mergeOneLines();
		coincTable.removeDuplicatedCol();
		curTable = coincTable;
	    }
	}
	System.out.println("Merged Tables\n" + coincTable);
    }

    private void mergeZeroLines() {
	CNATable zeroLinesMerge = new CNATable();
	CNATable firstOnlyZeroLines = new CNATable();
	CNATable secondOnlyZeroLines = new CNATable();

	for (CNAList cur : curTable) {
	    if (cur.lastElementIsZero()) {
		firstOnlyZeroLines.add(cur);
	    }
	}

	String effectName = curTable.get(0).getLastElement();
	int index = getIndexOfEffectInNextTable(effectName);
	for (CNAList cur : nextTable) {
	    if (cur.elementIsZero(index)) {
		secondOnlyZeroLines.add(cur);
	    }
	}

	int howMany = secondOnlyZeroLines.size();
	for (CNAList cur : firstOnlyZeroLines) {
	    for (int i = 0; i < howMany; i++) {
		zeroLinesMerge.add(cur);
	    }
	}
	// Add factor names
	coincTable = new CNATable();
	coincTable.add(mergeTwoCNALists(curTable.get(0), nextTable.get(0)));

	for (int i = 0; i < zeroLinesMerge.size(); i++) {
	    CNAList cur1 = zeroLinesMerge.get(i);
	    CNAList cur2 = secondOnlyZeroLines.get(i
		    % secondOnlyZeroLines.size());
	    coincTable.add(mergeTwoCNALists(cur1, cur2));
	}
    }

    private void mergeOneLines() {
	CNATable oneLinesMerge = new CNATable();
	CNATable firstOnlyOneLines = new CNATable();
	CNATable secondOnlyOneLines = new CNATable();

	for (CNAList cur : curTable) {
	    if (cur.lastElementIsOne()) {
		firstOnlyOneLines.add(cur);
	    }
	}

	String effectName = curTable.get(0).getLastElement();
	int index = getIndexOfEffectInNextTable(effectName);
	for (CNAList cur : nextTable) {
	    if (cur.ElementIsOne(index)) {
		secondOnlyOneLines.add(cur);
	    }
	}

	int howMany = secondOnlyOneLines.size();
	for (CNAList cur : firstOnlyOneLines) {
	    for (int i = 0; i < howMany; i++) {
		oneLinesMerge.add(cur);
	    }
	}

	for (int i = 0; i < oneLinesMerge.size(); i++) {
	    CNAList cur1 = oneLinesMerge.get(i);
	    CNAList cur2 = secondOnlyOneLines
		    .get(i % secondOnlyOneLines.size());
	    coincTable.add(mergeTwoCNALists(cur1, cur2));
	}

    }

    /** Helpers */
    private int getIndexOfEffectInNextTable(String effectName) {
	for (int i = 0; i < nextTable.get(0).size(); i++) {
	    String cur = nextTable.get(0).get(i);
	    if (cur.equals(effectName)) {
		return i;
	    }
	}
	return -1;
    }

    public CNAList mergeTwoCNALists(CNAList first, CNAList second) {
	CNAList merged = new CNAList();
	merged.addAll(first);
	merged.addAll(second);
	return merged;
    }

    private boolean isBundleInstantiated(int index, int end, CNAList row) {
	String bundle = "";
	for (int i = index; i < end; i++) {
	    bundle += row.get(i);
	}
	if (bundle.contains("0")) {
	    return false;
	} else {
	    return true;
	}

    }

    public ArrayList<CNATable> getMTCoincTables() {
	return mtCoincTables;
    }

    public CNATable getCoincTable() {
	return coincTable;
    }
}
