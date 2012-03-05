package datastructures.cna;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe.ch@gmail.com>
 * @license GPLv3, for more informations see Readme.mdown
 */

import java.util.ArrayList;
import java.util.HashSet;

public class CNATable extends ArrayList<CNAList> {
    private final ArrayList<CNAList> table;

    public CNATable() {
	table = new ArrayList<CNAList>();
    }

    public CNATable(String regexTable, String regexList, String string) {
	table = new ArrayList<CNAList>();

	String[] array = string.split("" + regexTable);
	for (int i = 0; i < array.length; i++) {
	    String temp = array[i];
	    temp = temp.replace("\n", "");
	    temp = temp.replace("\t", "");
	    temp = temp.replace(" ", "");
	    table.add(new CNAList(regexList, temp));
	}

    }

    public void swap(int first, int second) {
	for (CNAList list : table) {
	    list.swap(first, second);
	}
    }

    @Override
    public boolean add(CNAList list) {
	return table.add(list);
    }

    @Override
    public CNATable clone() {
	CNATable clone = new CNATable();
	CNAList list;
	for (int i = 0; i < table.size(); i++) {
	    list = (CNAList) table.get(i).clone();
	    clone.add(list);
	}
	return clone;
    }

    @Override
    public int size() {
	return table.size();
    }

    public void removeZeroEffects() {
	for (int i = 0; i < table.size(); i++) {
	    if (table.get(i).getLastElement().equals("0")) {
		table.remove(i);
		i--;
	    }
	}
    }

    @Override
    public CNAList remove(int index) {
	return table.remove(index);
    }

    public void removeDuplicated() {
	HashSet duplicate = new HashSet(table);
	table.clear();
	table.addAll(duplicate);
    }

    public void removeCol(int index) {
	for (CNAList list : table)
	    list.remove(index);
    }

    /**
     * Creates out of original coincidence Table a coincidence Table with
     * following schema: Every factor in a bundle is in same column, factor
     * order is same as in msuf Table.
     */
    public CNATable summarizeBundles(CNATable table, CNATable originalTable) {
	CNATable bundleTable = new CNATable();
	for (int k = 0; k < originalTable.size(); k++) {
	    CNAList current = new CNAList();
	    for (int i = 0; i < table.size(); i++) {
		String line = "";
		ArrayList<String> curMsuf = table.get(i);
		for (int j = 0; j < curMsuf.size(); j++) {
		    if (curMsuf.get(j).equals("1")
			    || curMsuf.get(j).equals("0")) {
			line += originalTable.get(k).get(j);
		    }
		}
		current.add(line);
	    }
	    // Add effect column
	    current.add(originalTable.get(k).get(
		    originalTable.get(k).size() - 1));
	    bundleTable.add(current);
	}
	return bundleTable;
    }

    public CNAList getNec() {
	CNAList necList = new CNAList();
	for (CNAList list : table) {
	    String line = new String();
	    for (String str : list) {
		if (str.equals("1") || str.equals("0")) {
		    line += str;
		}
	    }
	    necList.add(line);
	}
	return necList;
    }

    public CNAList getFactors() {
	CNAList factors = table.get(0).clone(table.get(0));
	factors.remove(factors.size() - 1);
	return factors;
    }

    public String getEffect() {
	String effect = table.get(0).get(table.get(0).size() - 1);
	return effect;
    }

    @Override
    public String toString() {
	String string = "";
	for (int r = 0; r < table.size(); r++) {
	    for (int c = 0; c < table.get(r).size(); c++) {
		String temp = table.get(r).get(c);
		string += (temp + " ");
	    }
	    string += "\n";
	}
	return string;
    }

    public String toString(CNATable originalTable) {
	ArrayList<String> factorNames = originalTable.get(0);
	String output = "";
	for (int i = 0; i < table.size(); i++) {
	    ArrayList<String> curRow = table.get(i);
	    for (int j = 0; j < curRow.size(); j++) {
		if (curRow.get(j).equals("1")) {
		    output += factorNames.get(j);
		}
		if (curRow.get(j).equals("0")) {
		    output += "¬" + factorNames.get(j);
		}
	    }
	    if (i < table.size() - 1) {
		output += " ∨ ";
	    }
	}
	return output;
    }

    // Getters and Setters

    public ArrayList<CNAList> getTable() {
	return table;
    }

    @Override
    public CNAList get(int i) {
	return table.get(i);
    }

}
