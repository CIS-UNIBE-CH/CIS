package algorithms;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import helpers.BaumgartnerSampleTable;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import algorithms.cna.CNAlgorithm;
import algorithms.cna.NecException;
import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.random.RandomMTGeneratorHelper;
import datastructures.random.RandomMTSetGenerator;

public class CNATest extends UnitTest {
    private static CNAlgorithm cnaAlgorithm;

    @Before
    public void setup() throws NecException {

	cnaAlgorithm = new CNAlgorithm(
		new BaumgartnerSampleTable().getSampleTable());
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
	CNAList necList = new CNAList(",", "1,01,1");
	assertEquals(necList, cnaAlgorithm.getNecList());
    }

    @Test
    public void shouldTestIdentifyMNEC() {
	CNATable mnecTable = new CNATable(";", ",", "$,$,1;1,$,$");
	assertEquals(mnecTable, cnaAlgorithm.getMnecTable());
    }

    @Test
    public void shouldTestIdentifyMinimatTheorieSet() {
	assertEquals("[DX1 ∨ BX2 ∨ YE => E, AX1 ∨ BX2 ∨ YC => C]", cnaAlgorithm.getSets().get(0));
    }

    @Test
    public void shouldTestWithGeneratedTable() {
	ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> bundle1 = new ArrayList<Integer>();
	ArrayList<Integer> alterFactros = new ArrayList<Integer>();
	bundle1.add(2);
	alterFactros.add(1);
	list.add(bundle1);
	RandomMTGeneratorHelper helper = new RandomMTGeneratorHelper(list,
		alterFactros);
	RandomMTSetGenerator generator = new RandomMTSetGenerator(
		helper.getCompleteList(), false);

	assertNotNull(generator.getMTSet());

    }
}