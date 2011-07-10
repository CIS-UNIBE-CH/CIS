package helpers;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */
import datastructures.CNATable;

public class BaumgartnerSampleTable {
    private CNATable sampleTable = new CNATable();

    public BaumgartnerSampleTable() {
	sampleTable = new CNATable(";", ",", "A,B,D,E,C;" + "1,1,1,1,1;"
		+ "1,1,0,1,1;" + "1,0,1,1,1;" + "0,1,1,1,1;" + "0,1,0,1,1;"
		+ "1,0,0,0,1;" + "0,0,1,1,0;" + "0,0,0,0,0");
    }

    public CNATable getSampleTable() {
	return sampleTable;
    }

    @Override
    public String toString() {
	String output;
	output = "\nBaumgartner original Table:";
	for (int i = 0; i < sampleTable.size(); i++) {
	    output += "\n" + sampleTable.get(i);
	}
	output += "\n";
	return output;
    }
}