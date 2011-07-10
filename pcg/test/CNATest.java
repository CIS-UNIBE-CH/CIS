/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import static org.junit.Assert.assertEquals;
import helpers.BaumgartnerSampleTable;

import org.junit.BeforeClass;
import org.junit.Test;

import algorithms.CNAlgorithm;
import datastructures.CNAList;
import datastructures.CNATable;

public class CNATest {
    private static CNAlgorithm cnaAlgorithm;

    @BeforeClass
    public static void setup() {

	cnaAlgorithm = new CNAlgorithm(
		new BaumgartnerSampleTable().getSampleTable());

	// RandomGraphGenerator generator = new RandomGraphGenerator(2, 5, 2);
	// CNATable table = new CNATable(generator.getTableAsArray());
	// cnaAlgorithm = new CNAlgorithm(table);
    }

    @Test
    public void shouldTestIdentifySUF() {
	CNATable sufTable = new CNATable(";", ",", "A,B,D,E,C;" + "1,1,1,1,1;"
		+ "1,1,0,1,1;" + "1,0,1,1,1;" + "0,1,1,1,1;" + "0,1,0,1,1;"
		+ "1,0,0,0,1");
	assertEquals(sufTable, cnaAlgorithm.getSufTable());
    }

    @Test
    public void shouldTestIdentifyMSUF() {
	CNATable msufTable = new CNATable(";", ",", "$,1,$,$;" + "$,$,0,1;"
		+ "1,$,$,$;");
	assertEquals(msufTable, cnaAlgorithm.getMsufTable());
    }

    @Test
    public void shouldTestIdentifyNEC() {
	CNAList necList = new CNAList(",", "0,10,0");
	assertEquals(necList, cnaAlgorithm.getNecList());
    }

    @Test
    public void shouldTestIdentifyMNEC() {
	CNATable mnecTable = new CNATable(";", ",", "1,$,$;$,$,1");
	assertEquals(mnecTable, cnaAlgorithm.getMnecTable());
    }

    @Test
    public void shouldTestIdentifyFMT() {
	assertEquals("BX1 ∨ AX2 => C", cnaAlgorithm.getdatastructurestring());
    }
}