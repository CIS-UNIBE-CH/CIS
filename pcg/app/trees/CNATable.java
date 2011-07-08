package trees;

import java.util.ArrayList;
import java.util.HashSet;

public class CNATable extends ArrayList<CNAList> {
    private ArrayList<CNAList> table;

    public CNATable() {
	table = new ArrayList<CNAList>();
    }

    public CNATable(String[][] table) {
	this.table = arrayToCNATable(table);
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
	for (int row = 0; row < table.size(); row++) {
	    if (table.get(row).get(table.get(row).size() - 1).equals("0")) {
		table.remove(row);
	    }
	}
    }

    public void removeDuplicated() {
	HashSet duplicate = new HashSet(table);
	table.clear();
	table.addAll(duplicate);
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

    public CNAList getNecList() {
	CNAList necList = new CNAList();
	for (int i = 0; i < table.size(); i++) {
	    String line = "";
	    ArrayList<String> curMsuf = table.get(i);
	    for (int j = 0; j < curMsuf.size(); j++) {
		if (curMsuf.get(j).equals("1") || curMsuf.get(j).equals("0")) {
		    line += curMsuf.get(j);
		}
	    }
	    necList.add(line);
	}
	return necList;
    }

    // toString

    @Override
    public String toString() {
	String string = "";
	for (int r = 0; r < table.size(); r++) {
	    for (int c = 0; c < table.get(r).size(); c++) {
		string += table.get(r).get(c) + " ";
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

    // Helpers

    private ArrayList<CNAList> arrayToCNATable(String[][] table) {
	ArrayList<CNAList> list = new ArrayList<CNAList>();
	for (int r = 0; r < table.length; r++) {
	    list.add(new CNAList());
	    for (int c = 0; c < table[r].length; c++) {
		list.get(r).add(table[r][c]);
	    }
	}
	return list;
    }
}
