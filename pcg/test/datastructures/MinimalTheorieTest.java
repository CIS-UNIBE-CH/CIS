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
import datastructures.mt.MinimalTheory;

public class MinimalTheorieTest extends UnitTest {
    static MinimalTheory theorie;
    static CNAList factors;
    static String effect;

    @Before
    public void setup() {
	factors = new CNAList(",", "AB,C,DE");
	effect = "W";
	theorie = new MinimalTheory(factors, effect);
    }

    @Test
    public void shouldTestToString() {
	assert (theorie.getBundles().size() != 0);
	String minTheorie = "ABX1 ∨ CX2 ∨ DEX3 ∨ YW => W";
	assertEquals(minTheorie, theorie.toString());
    }

    @Test
    public void shouldGetEffect() {
	assert (theorie.getEffect() != "");
	assertEquals(effect, theorie.getEffect());
    }

    @Test
    public void shouldGetFactors() {
	assert (theorie.getBundles().size() != 0);
	assertEquals(factors, theorie.getBundles());
    }

    @Test
    public void shouldSetEffect() {
	theorie.setEffect("L");
	assertEquals("L", theorie.getEffect());
    }

    @Test
    public void shouldSetFactors() {
	CNAList list = new CNAList(",", "A,D,L");
	theorie.setBundles(list);
	assertEquals(list, theorie.getBundles());
    }
}
