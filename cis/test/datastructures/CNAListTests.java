package datastructures;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe.ch@gmail.com>
 * @license GPLv3, for more informations see Readme.mdown
 */

import org.junit.BeforeClass;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.cna.CNAList;

public class CNAListTests extends UnitTest {

    private static CNAList list;

    @BeforeClass
    public static void setup() {
	list = new CNAList();
	list.add("1");
	list.add("2");
	list.add("3");
    }

    @Test
    public void shouldTestRegex() {
	CNAList list2 = new CNAList(",", "1,2,3");
	assertEquals(list.toString(), list2.toString());
    }

    @Test
    public void shouldNegate() {
	list.invert();
	assertEquals("0 2 3 ", list.toString());
    }

    @Test
    public void shouldSwap() {
	CNAList list = new CNAList(",", "1,2,3");
	list.swap(1, 2);
	assertEquals("1 3 2 ", list.toString());
	list.swap(0, 2);
	assertEquals("2 3 1 ", list.toString());
    }
}
