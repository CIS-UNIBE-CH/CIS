package helper;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

public class BaumgartnerSample {
    private ArrayList<ArrayList<String>> sampleTable = new ArrayList<ArrayList<String>>();

    public BaumgartnerSample() {

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
	secondRow.add("1");
	secondRow.add("1");
	sampleTable.add(secondRow);

	ArrayList<String> thirdRow = new ArrayList<String>();
	thirdRow.add("1");
	thirdRow.add("1");
	thirdRow.add("0");
	thirdRow.add("1");
	thirdRow.add("1");
	sampleTable.add(thirdRow);

	ArrayList<String> fourthRow = new ArrayList<String>();
	fourthRow.add("1");
	fourthRow.add("0");
	fourthRow.add("1");
	fourthRow.add("1");
	fourthRow.add("1");
	sampleTable.add(fourthRow);

	ArrayList<String> fifthRow = new ArrayList<String>();
	fifthRow.add("0");
	fifthRow.add("1");
	fifthRow.add("1");
	fifthRow.add("1");
	fifthRow.add("1");
	sampleTable.add(fifthRow);

	ArrayList<String> sixthRow = new ArrayList<String>();
	sixthRow.add("0");
	sixthRow.add("1");
	sixthRow.add("0");
	sixthRow.add("1");
	sixthRow.add("1");
	sampleTable.add(sixthRow);

	ArrayList<String> seventhRow = new ArrayList<String>();
	seventhRow.add("1");
	seventhRow.add("0");
	seventhRow.add("0");
	seventhRow.add("0");
	seventhRow.add("1");
	sampleTable.add(seventhRow);

	ArrayList<String> eigthRow = new ArrayList<String>();
	eigthRow.add("0");
	eigthRow.add("0");
	eigthRow.add("1");
	eigthRow.add("1");
	eigthRow.add("0");
	sampleTable.add(eigthRow);

	ArrayList<String> ninethRow = new ArrayList<String>();
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	ninethRow.add("0");
	sampleTable.add(ninethRow);
    }

    public ArrayList<ArrayList<String>> getSampleTable() {
	return sampleTable;
    }
    
    @Override
    public String toString(){
	String output;
	output = "\nBaumgartner original Table:";
	for (int i = 0; i < sampleTable.size(); i++) {
	    output += "\n" + sampleTable.get(i);
	}
	output += "\n";
	return output;
    }
}
