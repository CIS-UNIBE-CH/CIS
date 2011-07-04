package helper;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

public class CustomSample {
    private ArrayList<ArrayList<String>> sampleTable = new ArrayList<ArrayList<String>>();

    public CustomSample() {

	ArrayList<String> firstRow = new ArrayList<String>();
	firstRow.add("A");
	firstRow.add("B");
	firstRow.add("D");
	firstRow.add("E");
	firstRow.add("C");
	sampleTable.add(firstRow);

	ArrayList<String> secondRow = new ArrayList<String>();
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("0");
	secondRow.add("1");
	sampleTable.add(secondRow);

	ArrayList<String> thirdRow = new ArrayList<String>();
	thirdRow.add("1");
	thirdRow.add("1");
	thirdRow.add("0");
	thirdRow.add("1");
	thirdRow.add("1");
	sampleTable.add(thirdRow);

	ArrayList<String> ninethRow = new ArrayList<String>();
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	sampleTable.add(ninethRow);

	System.out.println("\nBaumgartner original Table:");
	for (int i = 0; i < sampleTable.size(); i++) {
	    System.out.println(sampleTable.get(i));
	}
	System.out.println("\n");
    }

    public ArrayList<ArrayList<String>> getSampleTable() {
	return sampleTable;
    }
}
