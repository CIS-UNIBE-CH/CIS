package datastructures;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <pcg.unibe.ch@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.cna.CNAList;
import datastructures.cna.CNATable;

public class CNATableTests extends UnitTest {
    private CNAList list;
    private CNATable table;

    @Before
    public void setup() {
	table = new CNATable();
	list = new CNAList();
	list.add("1");
	list.add("2");
	list.add("3");
	table.add(list);
	list = new CNAList();
	list.add("3");
	list.add("2");
	list.add("1");
	table.add(list);
    }

    @Test
    public void shouldTestRegex() {
	CNATable testTable = new CNATable("\n", ",", "1,2,3\n3,2,1");
	assertEquals(2, testTable.size());
	assertEquals(table.toString(), testTable.toString());
    }

    @Test
    public void shoudRemoveZeroEffect() {
	CNATable testTable = new CNATable(";", ",", "1,0,0;" + "0,1,0;"
		+ "0,0,1;" + "0,0,0;");
	CNATable noEffectTable = new CNATable(";", ",", "0,0,1");
	testTable.removeZeroEffects();
	assertEquals(noEffectTable.toString(), testTable.toString());

    }

    @Test
    public void shouldRemove() {
	table = new CNATable(";", ",", "0,2,3;3,2,0");
	assertEquals(2, table.size());
	table.remove(0);
	assertEquals(1, table.size());
    }

    @Test
    public void shoudClone() {
	table = new CNATable(";", ",", "0,2,3;3,2,0");
	CNATable newTable = table.clone();
	assertEquals(table, newTable);
	assertEquals(table.size(), newTable.size());
	newTable.remove(0);
	assert (table.size() != newTable.size());
	assert (table != newTable);
    }

    @Test
    public void shouldRemoveCol() {
	table = new CNATable(";", ",", "0,2,3;3,2,0");
	table.removeCol(1);
	CNATable testTable = new CNATable(";", ",", "0,3;3,0");
	assertEquals(testTable, table);
    }

    @Test
    public void shouldSwap() {
	table = new CNATable(";", ",", "0,2,3;3,2,0");
	CNATable testTable = new CNATable(";", ",", "3,2,0;0,2,3");
	table.swap(0, 2);
	assertEquals(testTable, table);

    }
}
