package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

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

    public MTSetToTable(MinimalTheorySet set) {
	this.set = set;

	runner();
    }
    
    public void runner(){
	for(MinimalTheory theory : set){
	    curMT = theory;
	    curMTTable = new CNATable();
	    
	    addFactorNames();
	    binaryFillUp();
	    makeEffectCol();
	    tables.add(curMTTable);
	    System.out.println("***************");
	    for(CNATable table : tables){
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
	    if(!instantiation){
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
