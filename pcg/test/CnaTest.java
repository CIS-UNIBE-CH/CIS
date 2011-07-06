import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import play.test.UnitTest;
import algorithms.BooleanTest;

public class CnaTest extends UnitTest {
    private static BooleanTest test;
    
    @BeforeClass
    public static void setup() {
	String table[][] = new String[1][1];
	table[0][0] = "";
	test = new BooleanTest(table, true);
	
    }

    @Test
    public void shouldTestIdentifySUF() {
	ArrayList<ArrayList<String>> calculatedSufTable = test.getSufTableForTesting();
	ArrayList<ArrayList<String>> generatedSufTable = new ArrayList<ArrayList<String>>();
	ArrayList<String> firstRow = new ArrayList<String>();
	firstRow.add("A");
	firstRow.add("B");
	firstRow.add("D");
	firstRow.add("E");
	firstRow.add("C");
	generatedSufTable.add(firstRow);

	ArrayList<String> secondRow = new ArrayList<String>();
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("1");
	secondRow.add("1");
	generatedSufTable.add(secondRow);

	ArrayList<String> thirdRow = new ArrayList<String>();
	thirdRow.add("1");
	thirdRow.add("1");
	thirdRow.add("0");
	thirdRow.add("1");
	thirdRow.add("1");
	generatedSufTable.add(thirdRow);

	ArrayList<String> fourthRow = new ArrayList<String>();
	fourthRow.add("1");
	fourthRow.add("0");
	fourthRow.add("1");
	fourthRow.add("1");
	fourthRow.add("1");
	generatedSufTable.add(fourthRow);

	ArrayList<String> fifthRow = new ArrayList<String>();
	fifthRow.add("0");
	fifthRow.add("1");
	fifthRow.add("1");
	fifthRow.add("1");
	fifthRow.add("1");
	generatedSufTable.add(fifthRow);

	ArrayList<String> sixthRow = new ArrayList<String>();
	sixthRow.add("0");
	sixthRow.add("1");
	sixthRow.add("0");
	sixthRow.add("1");
	sixthRow.add("1");
	generatedSufTable.add(sixthRow);

	ArrayList<String> seventhRow = new ArrayList<String>();
	seventhRow.add("1");
	seventhRow.add("0");
	seventhRow.add("0");
	seventhRow.add("0");
	seventhRow.add("1");
	generatedSufTable.add(seventhRow);
	
	for(int i = 0; i < calculatedSufTable.size(); i++){
	    ArrayList<String> curRowCalc = calculatedSufTable.get(i);
	    for(int j = 0; j< generatedSufTable.size(); j++){
		ArrayList<String> curRowGen = generatedSufTable.get(i);
		assertEquals(curRowGen, curRowCalc);
	    }
	}
    }
    
    @Test
    public void shouldTestIdentifyMSUF() {
	ArrayList<ArrayList<String>> calculatedMsufTable = test.getMsufTable();
	ArrayList<ArrayList<String>> generatedMsufTable = new ArrayList<ArrayList<String>>();
	
	ArrayList<String> firstRow = new ArrayList<String>();
	firstRow.add("$");
	firstRow.add("1");
	firstRow.add("$");
	firstRow.add("$");
	generatedMsufTable.add(firstRow);

	ArrayList<String> secondRow = new ArrayList<String>();
	secondRow.add("$");
	secondRow.add("$");
	secondRow.add("0");
	secondRow.add("1");
	generatedMsufTable.add(secondRow);

	ArrayList<String> thirdRow = new ArrayList<String>();
	thirdRow.add("1");
	thirdRow.add("$");
	thirdRow.add("$");
	thirdRow.add("$");
	generatedMsufTable.add(thirdRow);
	
	for(int i = 0; i < calculatedMsufTable.size(); i++){
	    ArrayList<String> curRowCalc = calculatedMsufTable.get(i);
	    for(int j = 0; j< generatedMsufTable.size(); j++){
		ArrayList<String> curRowGen = generatedMsufTable.get(i);
		assertEquals(curRowGen, curRowCalc);
	    }
	}
    }
    
    @Test
    public void shouldTestIdentifyNEC() {
	ArrayList<ArrayList<String>> calculatedNewSampleTable = test.getNewSampleTable();
	ArrayList<ArrayList<String>> generatedNewSampleTable = new ArrayList<ArrayList<String>>();
	
	ArrayList<String> firstRow = new ArrayList<String>();
	firstRow.add("B");
	firstRow.add("DE");
	firstRow.add("A");
	firstRow.add("C");
	calculatedNewSampleTable.add(firstRow);

	ArrayList<String> secondRow = new ArrayList<String>();
	secondRow.add("1");
	secondRow.add("11");
	secondRow.add("1");
	secondRow.add("1");
	calculatedNewSampleTable.add(secondRow);

	ArrayList<String> thirdRow = new ArrayList<String>();
	thirdRow.add("1");
	thirdRow.add("01");
	thirdRow.add("1");
	thirdRow.add("1");
	calculatedNewSampleTable.add(thirdRow);

	ArrayList<String> fourthRow = new ArrayList<String>();
	fourthRow.add("0");
	fourthRow.add("11");
	fourthRow.add("1");
	fourthRow.add("1");
	calculatedNewSampleTable.add(fourthRow);

	ArrayList<String> fifthRow = new ArrayList<String>();
	fifthRow.add("1");
	fifthRow.add("11");
	fifthRow.add("0");
	fifthRow.add("1");
	calculatedNewSampleTable.add(fifthRow);

	ArrayList<String> sixthRow = new ArrayList<String>();
	sixthRow.add("1");
	sixthRow.add("01");
	sixthRow.add("0");
	sixthRow.add("1");
	calculatedNewSampleTable.add(sixthRow);

	ArrayList<String> seventhRow = new ArrayList<String>();
	seventhRow.add("0");
	seventhRow.add("00");
	seventhRow.add("1");
	seventhRow.add("1");
	calculatedNewSampleTable.add(seventhRow);

	ArrayList<String> eigthRow = new ArrayList<String>();
	eigthRow.add("0");
	eigthRow.add("11");
	eigthRow.add("0");
	eigthRow.add("0");
	calculatedNewSampleTable.add(eigthRow);

	ArrayList<String> ninethRow = new ArrayList<String>();
	ninethRow.add("0");
	ninethRow.add("00");
	ninethRow.add("0");
	ninethRow.add("0");
	calculatedNewSampleTable.add(ninethRow);
	
	for(int i = 0; i < calculatedNewSampleTable.size(); i++){
	    ArrayList<String> curRowCalc = calculatedNewSampleTable.get(i);
	    for(int j = 0; j< generatedNewSampleTable.size(); j++){
		ArrayList<String> curRowGen = generatedNewSampleTable.get(i);
		assertEquals(curRowGen, curRowCalc);
	    }
	}
	
	ArrayList<String> calculatedNecLine = test.getNecLine();
	ArrayList<String> calucaltedNegatedNecLine = test.getNegatedNecLineForTesting();
	
	ArrayList<String> generatedNecLine = new ArrayList<String>();
	generatedNecLine.add("1");
	generatedNecLine.add("01");
	generatedNecLine.add("1");
	
	ArrayList<String> generatedNegatedNecLine = new ArrayList<String>();
	generatedNegatedNecLine.add("0");
	generatedNegatedNecLine.add("10");
	generatedNegatedNecLine.add("0");
	generatedNegatedNecLine.add("1");
	
	assertEquals(generatedNecLine, calculatedNecLine);
	assertEquals(generatedNegatedNecLine, calucaltedNegatedNecLine);
    }
    
    @Test
    public void shouldTestIdentifyMNEC() {
	ArrayList<ArrayList<String>> calculatedMnecTable = test.getMnecTable();
	ArrayList<ArrayList<String>> generatedMnecTable = new ArrayList<ArrayList<String>>();
	
	ArrayList<String> firstRow = new ArrayList<String>();
	firstRow.add("1");
	firstRow.add("$");
	firstRow.add("$");
	generatedMnecTable.add(firstRow);

	ArrayList<String> secondRow = new ArrayList<String>();
	secondRow.add("$");
	secondRow.add("$");
	secondRow.add("1");
	generatedMnecTable.add(secondRow);
	
	for(int i = 0; i < calculatedMnecTable.size(); i++){
	    ArrayList<String> curRowCalc = calculatedMnecTable.get(i);
	    for(int j = 0; j< generatedMnecTable.size(); j++){
		ArrayList<String> curRowGen = generatedMnecTable.get(i);
		assertEquals(curRowGen, curRowCalc);
	    }
	}
    }
    
    @Test
    public void shouldTestIdentifyFMT() {
	ArrayList<String> calculatedFmtTable = test.getFmt();
	ArrayList<String> generatedFmtTable = new ArrayList<String>();
	generatedFmtTable.add("AX1");
	generatedFmtTable.add("BX2");
	generatedFmtTable.add("YC");
	
	assertEquals(generatedFmtTable, calculatedFmtTable);
	
    }
}