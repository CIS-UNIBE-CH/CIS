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
import datastructures.mt.MinimalTheorySet;

public class MinimalTheorieSetTests extends UnitTest {

    static MinimalTheorySet theories;
    static MinimalTheory theorie;
    static CNAList factors;
    static String effect;

    @Before
    public void setup() {
	theories = new MinimalTheorySet();
	factors = new CNAList(",", "A,B,C,DE");
	effect = "W";
	theorie = new MinimalTheory(factors, effect);
	theories.add(theorie);
    }

    @Test
    public void shouldAdd() {
	assertEquals(1, theories.size());
	assertEquals(theorie, theories.get(0));

	CNAList newfactors = new CNAList(",", "W,F,H");
	String newEffect = "R";
	MinimalTheory newTheorie = new MinimalTheory(newfactors, newEffect);
	theories.add(newTheorie);
	assertEquals(2, theories.size());
	assertEquals(newTheorie, theories.get(1));

    }

    @Test
    public void shouldRemove() {
	CNAList newfactors = new CNAList(",", "W,F,H");
	String newEffect = "R";
	MinimalTheory newTheorie = new MinimalTheory(newfactors, newEffect);
	theories.add(newTheorie);
	assertEquals(2, theories.size());
	theories.remove(1);
	assertEquals(1, theories.size());
	assertEquals(theorie, theories.get(0));

    }

    @Test
    public void shouldGetSingles() {
	CNAList newfactors = new CNAList(",", "W,F,H");
	String newEffect = "R";
	MinimalTheory newTheorie = new MinimalTheory(newfactors, newEffect);
	theories.add(newTheorie);
	assertEquals(2, theories.size());
    }

    @Test
    public void shouldGetNumbersOfFactors() {
	assertEquals(5, theories.getNumberOfFactors());
    }

    @Test
    public void shouldGetNumbersOfEffects() {
	assertEquals(1, theories.getNumberOfEffects());
    }

    @Test
    public void shouldGetAllFactors() {
	assertEquals("D E A B C ", theories.getAllFactors().toString());
    }

    @Test
    public void shouldGetAllEffects() {
	assertEquals("W ", theories.getAllEffects().toString());
    }

}
