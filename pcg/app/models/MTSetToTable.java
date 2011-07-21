package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;
import java.util.HashMap;

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

public class MTSetToTable {
    private MinimalTheorySet set = new MinimalTheorySet();
    ArrayList<CNATable> tables = new ArrayList<CNATable>();
    CNATable curMTTable = new CNATable();
    MinimalTheory curMT = new MinimalTheory();
    CNATable finalTable = new CNATable();
    CNATable table1;
    CNATable table2;
    CNATable finalList = new CNATable();

    public MTSetToTable(MinimalTheorySet set) {
	this.set = set;

	createTables();
	table1 = tables.get(0);
	table2 = tables.get(1);
	mergeTables();
    }

    private void mergeTables() {
	mergeZeroLines();
	mergeOneLines();

	HashMap map = new HashMap();
	for (int j = 0; j < finalList.get(0).size() - 1; j++) {
	    map.put(finalList.get(0).get(j), finalList.get(0).get(j));
	    if(map.containsKey(finalList.get(0).get(j + 1))){
		finalList.removeCol(j);
		break;
	    }
	}
	System.out.println("Merged List\n" + finalList);
    }

    private int getIndexEffect(String effectName) {
	System.out.println("Effect Name: " + effectName);
	for (int i = 0; i < table1.get(0).size(); i++) {
	    String cur = table1.get(0).get(i);
	    System.out.println("Potential: " + cur);
	    if (cur.equals(effectName)) {
		return i;
	    }
	}
	return -1;
    }

    private void mergeZeroLines() {
	CNATable tempMerge = new CNATable();
	CNATable table1temp = new CNATable();
	CNATable table2temp = new CNATable();

	// OK
	for (CNAList cur : table1) {
	    if (cur.lastElementIsZero()) {
		table1temp.add(cur);
	    }
	}

	String effectName = table1.get(0).getLastElement();
	int index = getIndexEffect(effectName);
	for (CNAList cur : table2) {
	    if (cur.ElementIsZero(index)) {
		table2temp.add(cur);
	    }
	}
	int howMany = table2temp.size();
	System.out.println("Many: " + howMany);

	for (CNAList cur : table1temp) {
	    for (int i = 0; i < howMany; i++) {
		tempMerge.add(cur);
	    }
	}
	// Add names
	finalList.add(mergeTwoCNALists(table1.get(0), table2.get(0)));
	for (int i = 0; i < tempMerge.size(); i++) {
	    CNAList cur1 = tempMerge.get(i);
	    CNAList cur2 = table2temp.get(i % table2temp.size());
	    finalList.add(mergeTwoCNALists(cur1, cur2));
	}

    }

    private void mergeOneLines() {
	CNATable tempMerge = new CNATable();
	CNATable table1temp = new CNATable();
	CNATable table2temp = new CNATable();

	// OK
	for (CNAList cur : table1) {
	    if (cur.lastElementIsOne()) {
		table1temp.add(cur);
	    }
	}

	String effectName = table1.get(0).getLastElement();
	int index = getIndexEffect(effectName);
	for (CNAList cur : table2) {
	    if (cur.ElementIsOne(index)) {
		table2temp.add(cur);
	    }
	}
	int howMany = table2temp.size();
	System.out.println("Many: " + howMany);

	for (CNAList cur : table1temp) {
	    for (int i = 0; i < howMany; i++) {
		tempMerge.add(cur);
	    }
	}
	System.out.println("TempMerge\n" + tempMerge);
	System.out.println("Temp2\n" + table2temp);

	// Add names
	// finalList.add(mergeTwoCNALists(table1.get(0), table2.get(0)));
	for (int i = 0; i < tempMerge.size(); i++) {
	    CNAList cur1 = tempMerge.get(i);
	    CNAList cur2 = table2temp.get(i % table2temp.size());
	    finalList.add(mergeTwoCNALists(cur1, cur2));
	}

    }

    public CNAList mergeTwoCNALists(CNAList first, CNAList second) {
	CNAList merged = new CNAList();
	merged.addAll(first);
	merged.addAll(second);
	return merged;
    }

    public void createTables() {
	for (MinimalTheory theory : set) {
	    curMT = theory;
	    curMTTable = new CNATable();

	    addFactorNames();
	    binaryFillUp();
	    makeEffectCol();
	    tables.add(curMTTable);
	    System.out.println("***************");
	    for (CNATable table : tables) {
		System.out.println("Table:\n" + table);
	    }
	    System.out.println("***************");
	}
    }

    public void addFactorNames() {
	CNAList factorNames = new CNAList();
	for (String cur : curMT.getFactors()) {
	    factorNames.add(cur);
	}
	curMTTable.add(factorNames);
    }

    public void binaryFillUp() {
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

    public void makeEffectCol() {
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

    public ArrayList<CNATable> getTables() {
	return tables;
    }

    public CNATable getOneMT() {
	return curMTTable;
    }
}
