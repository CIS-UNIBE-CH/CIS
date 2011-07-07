/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import play.test.UnitTest;
import algorithms.CNA;

public class CNATest extends UnitTest {
    private static CNA cnaAlgorithm;

    @BeforeClass
    public static void setup() {
	// Dummy Array to prevent null pointer exception.
	String table[][] = new String[1][1];
	table[0][0] = "";

	cnaAlgorithm = new CNA(table, true);
    }

    @Test
    public void shouldTestIdentifySUF() {
	ArrayList<ArrayList<String>> calculatedSufTable = cnaAlgorithm
		.getSufTableForTesting();
	ArrayList<ArrayList<String>> expectedSufTable = new ArrayList<ArrayList<String>>();

	ArrayList<String> firstRow = new ArrayList<String>();
	firstRow.add("A");
	firstRow.add("B");
	firstRow.add("D");
	firstRow.add("E");
	firstRow.add("C");
	expectedSufTable.add(firstRow);

	ArrayList<String> secondRow = new ArrayList<String>();
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("1");
	expectedSufTable.add(secondRow);

	ArrayList<String> thirdRow = new ArrayList<String>();
	thirdRow.add("1");
	thirdRow.add("1");
	thirdRow.add("0");
	thirdRow.add("1");
	thirdRow.add("1");
	expectedSufTable.add(thirdRow);

	ArrayList<String> fourthRow = new ArrayList<String>();
	fourthRow.add("1");
	fourthRow.add("0");
	fourthRow.add("1");
	fourthRow.add("1");
	fourthRow.add("1");
	expectedSufTable.add(fourthRow);

	ArrayList<String> fifthRow = new ArrayList<String>();
	fifthRow.add("0");
	fifthRow.add("1");
	fifthRow.add("1");
	fifthRow.add("1");
	fifthRow.add("1");
	expectedSufTable.add(fifthRow);

	ArrayList<String> sixthRow = new ArrayList<String>();
	sixthRow.add("0");
	sixthRow.add("1");
	sixthRow.add("0");
	sixthRow.add("1");
	sixthRow.add("1");
	expectedSufTable.add(sixthRow);

	ArrayList<String> seventhRow = new ArrayList<String>();
	seventhRow.add("1");
	seventhRow.add("0");
	seventhRow.add("0");
	seventhRow.add("0");
	seventhRow.add("1");
	expectedSufTable.add(seventhRow);

	for (int i = 0; i < calculatedSufTable.size(); i++) {
	    ArrayList<String> curRowCalc = calculatedSufTable.get(i);
	    ArrayList<String> curRowExpec = expectedSufTable.get(i);
	    assertEquals(curRowExpec, curRowCalc);
	}
    }

    @Test
    public void shouldTestIdentifyMSUF() {
	ArrayList<ArrayList<String>> calculatedMsufTable = cnaAlgorithm
		.getMsufTableForTesting();
	ArrayList<ArrayList<String>> expectedMsufTable = new ArrayList<ArrayList<String>>();

	ArrayList<String> firstRow = new ArrayList<String>();
	firstRow.add("$");
	firstRow.add("1");
	firstRow.add("$");
	firstRow.add("$");
	expectedMsufTable.add(firstRow);

	ArrayList<String> secondRow = new ArrayList<String>();
	secondRow.add("$");
	secondRow.add("$");
	secondRow.add("0");
	secondRow.add("1");
	expectedMsufTable.add(secondRow);

	ArrayList<String> thirdRow = new ArrayList<String>();
	thirdRow.add("1");
	thirdRow.add("$");
	thirdRow.add("$");
	thirdRow.add("$");
	expectedMsufTable.add(thirdRow);

	for (int i = 0; i < calculatedMsufTable.size(); i++) {
	    ArrayList<String> curRowCalc = calculatedMsufTable.get(i);
	    ArrayList<String> curRowExpec = expectedMsufTable.get(i);
	    assertEquals(curRowExpec, curRowCalc);
	}
    }

    @Test
    public void shouldTestCreateNewSampleTable() {
	ArrayList<ArrayList<String>> calculatedNewSampleTable = cnaAlgorithm
		.getNewSampleTable();
	ArrayList<ArrayList<String>> expectedNewSampleTable = new ArrayList<ArrayList<String>>();

	ArrayList<String> firstRow = new ArrayList<String>();
	firstRow.add("B");
	firstRow.add("DE");
	firstRow.add("A");
	firstRow.add("C");
	expectedNewSampleTable.add(firstRow);

	ArrayList<String> secondRow = new ArrayList<String>();
	secondRow.add("1");
	secondRow.add("11");
	secondRow.add("1");
	secondRow.add("1");
	expectedNewSampleTable.add(secondRow);

	ArrayList<String> thirdRow = new ArrayList<String>();
	thirdRow.add("1");
	thirdRow.add("01");
	thirdRow.add("1");
	thirdRow.add("1");
	expectedNewSampleTable.add(thirdRow);

	ArrayList<String> fourthRow = new ArrayList<String>();
	fourthRow.add("0");
	fourthRow.add("11");
	fourthRow.add("1");
	fourthRow.add("1");
	expectedNewSampleTable.add(fourthRow);

	ArrayList<String> fifthRow = new ArrayList<String>();
	fifthRow.add("1");
	fifthRow.add("11");
	fifthRow.add("0");
	fifthRow.add("1");
	expectedNewSampleTable.add(fifthRow);

	ArrayList<String> sixthRow = new ArrayList<String>();
	sixthRow.add("1");
	sixthRow.add("01");
	sixthRow.add("0");
	sixthRow.add("1");
	expectedNewSampleTable.add(sixthRow);

	ArrayList<String> seventhRow = new ArrayList<String>();
	seventhRow.add("0");
	seventhRow.add("00");
	seventhRow.add("1");
	seventhRow.add("1");
	expectedNewSampleTable.add(seventhRow);

	ArrayList<String> eigthRow = new ArrayList<String>();
	eigthRow.add("0");
	eigthRow.add("11");
	eigthRow.add("0");
	eigthRow.add("0");
	expectedNewSampleTable.add(eigthRow);

	ArrayList<String> ninethRow = new ArrayList<String>();
	ninethRow.add("0");
	ninethRow.add("00");
	ninethRow.add("0");
	ninethRow.add("0");
	expectedNewSampleTable.add(ninethRow);

	for (int i = 0; i < calculatedNewSampleTable.size(); i++) {
	    ArrayList<String> curRowCalc = calculatedNewSampleTable.get(i);
	    ArrayList<String> curRowExpec = expectedNewSampleTable.get(i);
	    assertEquals(curRowExpec, curRowCalc);
	}
    }

    @Test
    public void shouldTestIdentifyNEC() {
	ArrayList<String> calculatedNecLine = cnaAlgorithm
		.getNecLineForTesting();
	ArrayList<String> expectedNecLine = new ArrayList<String>();
	expectedNecLine.add("1");
	expectedNecLine.add("01");
	expectedNecLine.add("1");

	ArrayList<String> calculatedNegatedNecLine = cnaAlgorithm
		.getNegatedNecLineForTesting();
	ArrayList<String> expectedNegatedNecLine = new ArrayList<String>();
	expectedNegatedNecLine.add("0");
	expectedNegatedNecLine.add("10");
	expectedNegatedNecLine.add("0");
	expectedNegatedNecLine.add("1");

	assertEquals(expectedNecLine, calculatedNecLine);
	assertEquals(expectedNegatedNecLine, calculatedNegatedNecLine);
    }

    @Test
    public void shouldTestIdentifyMNEC() {
	ArrayList<ArrayList<String>> calculatedMnecTable = cnaAlgorithm
		.getMnecTableForTesting();
	ArrayList<ArrayList<String>> expectedMnecTable = new ArrayList<ArrayList<String>>();

	ArrayList<String> firstRow = new ArrayList<String>();
	firstRow.add("1");
	firstRow.add("$");
	firstRow.add("$");
	expectedMnecTable.add(firstRow);

	ArrayList<String> secondRow = new ArrayList<String>();
	secondRow.add("$");
	secondRow.add("$");
	secondRow.add("1");
	expectedMnecTable.add(secondRow);

	for (int i = 0; i < calculatedMnecTable.size(); i++) {
	    ArrayList<String> curRowCalc = calculatedMnecTable.get(i);
	    ArrayList<String> curRowExpec = expectedMnecTable.get(i);
	    assertEquals(curRowExpec, curRowCalc);
	}
    }

    @Test
    public void shouldTestIdentifyFMT() {
	ArrayList<String> calculatedFmtTable = cnaAlgorithm.getFmt();
	ArrayList<String> expectedFmtTable = new ArrayList<String>();
	expectedFmtTable.add("AX1");
	expectedFmtTable.add("BX2");
	expectedFmtTable.add("YC");

	assertEquals(expectedFmtTable, calculatedFmtTable);
    }
}