package helpers;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */
import datastructures.cna.CNATable;

public class BaumgartnerSampleTable {
    private CNATable sampleTable = new CNATable();

    public BaumgartnerSampleTable() {
	sampleTable = new CNATable("\n", ",", "A,B,D,E,C\n" + "1,1,1,1,1\n"
		+ "1,1,0,1,1\n" + "1,0,1,1,1\n" + "0,1,1,1,1\n" + "0,1,0,1,1\n"
		+ "1,0,0,0,1\n" + "0,0,1,1,0\n" + "0,0,0,0,0");
    }

    public CNATable getSampleTable() {
	return sampleTable;
    }

}