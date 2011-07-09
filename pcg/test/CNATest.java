/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import static org.junit.Assert.assertEquals;
import helper.BaumgartnerSampleTable;

import org.junit.BeforeClass;
import org.junit.Test;

import trees.CNATable;
import algorithms.CNAlgorithm;

public class CNATest {
    private static CNAlgorithm cnaAlgorithm;

    @BeforeClass
    public static void setup() {

	cnaAlgorithm = new CNAlgorithm(
		new BaumgartnerSampleTable().getSampleTable());
    }

    @Test
    public void shouldTestIdentifySUF() {
	CNATable sufTable = new CNATable(";", ",", "A,B,D,E,C;" + "1,1,1,1,1;"
		+ "1,1,0,1,1;" + "1,0,1,1,1;" + "0,1,1,1,1;" + "0,1,0,1,1;"
		+ "1,0,0,0,1");
	assertEquals(sufTable.toString(), cnaAlgorithm.getSufTable().toString());
    }

    @Test
    public void shouldTestIdentifyMSUF() {
	CNATable msufTable = new CNATable(";", ",", "$,1,$,$;" + "$,$,0,1;"
		+ "1,$,$,$;");
	assertEquals(msufTable.toString(), cnaAlgorithm.getMsufTable()
		.toString());
    }
    //
    // @Test
    // public void shouldTestCreateBundleCoincTable() {
    // ArrayList<ArrayList<String>> calculatedBundleCoincTable = cnaAlgorithm
    // .getBundleCoincTable();
    // ArrayList<ArrayList<String>> expectedBundleCoincTable = new
    // ArrayList<ArrayList<String>>();
    //
    // ArrayList<String> firstRow = new ArrayList<String>();
    // firstRow.add("B");
    // firstRow.add("DE");
    // firstRow.add("A");
    // firstRow.add("C");
    // expectedBundleCoincTable.add(firstRow);
    //
    // ArrayList<String> secondRow = new ArrayList<String>();
    // secondRow.add("1");
    // secondRow.add("11");
    // secondRow.add("1");
    // secondRow.add("1");
    // expectedBundleCoincTable.add(secondRow);
    //
    // ArrayList<String> thirdRow = new ArrayList<String>();
    // thirdRow.add("1");
    // thirdRow.add("01");
    // thirdRow.add("1");
    // thirdRow.add("1");
    // expectedBundleCoincTable.add(thirdRow);
    //
    // ArrayList<String> fourthRow = new ArrayList<String>();
    // fourthRow.add("0");
    // fourthRow.add("11");
    // fourthRow.add("1");
    // fourthRow.add("1");
    // expectedBundleCoincTable.add(fourthRow);
    //
    // ArrayList<String> fifthRow = new ArrayList<String>();
    // fifthRow.add("1");
    // fifthRow.add("11");
    // fifthRow.add("0");
    // fifthRow.add("1");
    // expectedBundleCoincTable.add(fifthRow);
    //
    // ArrayList<String> sixthRow = new ArrayList<String>();
    // sixthRow.add("1");
    // sixthRow.add("01");
    // sixthRow.add("0");
    // sixthRow.add("1");
    // expectedBundleCoincTable.add(sixthRow);
    //
    // ArrayList<String> seventhRow = new ArrayList<String>();
    // seventhRow.add("0");
    // seventhRow.add("00");
    // seventhRow.add("1");
    // seventhRow.add("1");
    // expectedBundleCoincTable.add(seventhRow);
    //
    // ArrayList<String> eigthRow = new ArrayList<String>();
    // eigthRow.add("0");
    // eigthRow.add("11");
    // eigthRow.add("0");
    // eigthRow.add("0");
    // expectedBundleCoincTable.add(eigthRow);
    //
    // ArrayList<String> ninethRow = new ArrayList<String>();
    // ninethRow.add("0");
    // ninethRow.add("00");
    // ninethRow.add("0");
    // ninethRow.add("0");
    // expectedBundleCoincTable.add(ninethRow);
    //
    // for (int i = 0; i < calculatedBundleCoincTable.size(); i++) {
    // ArrayList<String> curRowCalc = calculatedBundleCoincTable.get(i);
    // ArrayList<String> curRowExpec = expectedBundleCoincTable.get(i);
    // assertEquals(curRowExpec, curRowCalc);
    // }
    // }
    //
    // @Test
    // public void shouldTestIdentifyNEC() {
    // ArrayList<String> calculatedNecLine = cnaAlgorithm
    // .getNecLineForTesting();
    // ArrayList<String> expectedNecLine = new ArrayList<String>();
    // expectedNecLine.add("1");
    // expectedNecLine.add("01");
    // expectedNecLine.add("1");
    //
    // ArrayList<String> calculatedNegatedNecLine = cnaAlgorithm
    // .getNegatedNecLineForTesting();
    // ArrayList<String> expectedNegatedNecLine = new ArrayList<String>();
    // expectedNegatedNecLine.add("0");
    // expectedNegatedNecLine.add("10");
    // expectedNegatedNecLine.add("0");
    // expectedNegatedNecLine.add("1");
    //
    // assertEquals(expectedNecLine, calculatedNecLine);
    // assertEquals(expectedNegatedNecLine, calculatedNegatedNecLine);
    // }
    //
    // @Test
    // public void shouldTestIdentifyMNEC() {
    // ArrayList<ArrayList<String>> calculatedMnecTable = cnaAlgorithm
    // .getMnecTableForTesting();
    // ArrayList<ArrayList<String>> expectedMnecTable = new
    // ArrayList<ArrayList<String>>();
    //
    // ArrayList<String> firstRow = new ArrayList<String>();
    // firstRow.add("1");
    // firstRow.add("$");
    // firstRow.add("$");
    // expectedMnecTable.add(firstRow);
    //
    // ArrayList<String> secondRow = new ArrayList<String>();
    // secondRow.add("$");
    // secondRow.add("$");
    // secondRow.add("1");
    // expectedMnecTable.add(secondRow);
    //
    // for (int i = 0; i < calculatedMnecTable.size(); i++) {
    // ArrayList<String> curRowCalc = calculatedMnecTable.get(i);
    // ArrayList<String> curRowExpec = expectedMnecTable.get(i);
    // assertEquals(curRowExpec, curRowCalc);
    // }
    // }
    //
    // @Test
    // public void shouldTestIdentifyFMT() {
    // ArrayList<String> calculatedFmtTable = cnaAlgorithm.getFmt();
    // ArrayList<String> expectedFmtTable = new ArrayList<String>();
    // expectedFmtTable.add("AX1");
    // expectedFmtTable.add("BX2");
    // expectedFmtTable.add("YC");
    //
    // assertEquals(expectedFmtTable, calculatedFmtTable);
    // }
}