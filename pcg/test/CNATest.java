/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import helpers.BaumgartnerSampleTable;

import org.junit.BeforeClass;
import org.junit.Test;

import play.test.UnitTest;
import algorithms.CNAlgorithm;
import datastructures.CNAList;
import datastructures.CNATable;

public class CNATest extends UnitTest {
    private static CNAlgorithm cnaAlgorithm;

    @BeforeClass
    public static void setup() {

	cnaAlgorithm = new CNAlgorithm(
		new BaumgartnerSampleTable().getSampleTable());
    }

    @Test
    public void shouldTestIdentifySUF() {
	CNATable sufTable = new CNATable(';', ',', "A,B,D,E,C;" + "1,1,1,1,1;"
		+ "1,1,0,1,1;" + "1,0,1,1,1;" + "0,1,1,1,1;" + "0,1,0,1,1;"
		+ "1,0,0,0,1");
	assertEquals(sufTable, cnaAlgorithm.getSufTable());
    }

    @Test
    public void shouldTestIdentifyMSUF() {
	CNATable msufTable = new CNATable(';', ',', "$,1,$,$;" + "$,$,0,1;"
		+ "1,$,$,$;");
	assertEquals(msufTable, cnaAlgorithm.getMsufTable());
    }

    @Test
    public void shouldTestIdentifyNEC() {
	CNAList necList = new CNAList(',', "0,10,0");
	assertEquals(necList, cnaAlgorithm.getNecList());
    }

    @Test
    public void shouldTestIdentifyMNEC() {
	CNATable mnecTable = new CNATable(';', ',', "1,$,$;$,$,1");
	assertEquals(mnecTable, cnaAlgorithm.getMnecTable());
    }

    @Test
    public void shouldTestIdentifyMinimatTheorieSet() {

    }

    // Random Tests

    // TODO
    // @Test
    // public void randomFactorTest() {
    // // Factors between 1 and 6
    // int factors = (int) (Math.random() * 6 + 1);
    // int bundleSize = (int) (Math.random() * factors + 1);
    // int bundles = (int) (Math.random() * (factors / bundleSize));
    // RandomGraphGenerator generator = new RandomGraphGenerator(bundles,
    // factors, bundleSize);
    // CNATable table = new CNATable(generator.getTableAsArray());
    // cnaAlgorithm = new CNAlgorithm(table);
    // // TODO
    // String can = cnaAlgorithm.getFmtTable().get(1);
    // assertEquals(generator.getTree().toString(),
    // cnaAlgorithm.getFmt().get(1));
    // }
}