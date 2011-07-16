package datastructures;

import org.junit.BeforeClass;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.cna.CNAList;
import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;

public class MinimalTheorieSetTests extends UnitTest {

    static MinimalTheorieSet theories;
    static MinimalTheorie theorie;
    static CNAList factors;
    static String effect;

    @BeforeClass
    public static void setup() {
	theories = new MinimalTheorieSet();
	factors = new CNAList(',', "A,B,C,DE");
	effect = "W";
	theorie = new MinimalTheorie(factors, effect);

    }

    @Test
    public void shouldAdd() {
	assertEquals(0, theories.size());
	theories.add(theorie);
	assertEquals(1, theories.size());
	assertEquals(theorie, theories.get(0));

	CNAList newfactors = new CNAList(',', "W,F,H");
	String newEffect = "R";
	MinimalTheorie newTheorie = new MinimalTheorie(newfactors, newEffect);
	theories.add(newTheorie);
	assertEquals(2, theories.size());
	assertEquals(newTheorie, theories.get(1));

    }

    @Test
    public void shouldRemove() {
	assertEquals(2, theories.size());
	theories.remove(1);
	assertEquals(1, theories.size());
	assertEquals(theorie, theories.get(0));

    }

    @Test
    public void shouldGetSingles() {
	CNAList newfactors = new CNAList(',', "W,F,H");
	String newEffect = "R";
	MinimalTheorie newTheorie = new MinimalTheorie(newfactors, newEffect);
	theories.add(newTheorie);
	assertEquals(2, theories.size());
    }

    @Test
    public void shouldGetNumbersOfFactors() {
	assertEquals(7, theories.getNumberOfFactors());
    }

    @Test
    public void shouldGetNumbersOfEffects() {
	assertEquals(2, theories.getNumberOfEffects());
    }

    @Test
    public void shouldGetAllFactors() {
	assertEquals("F W A B C DE H ", theories.getAllFactors().toString());
    }

    @Test
    public void shouldGetAllEffects() {
	assertEquals("W R ", theories.getAllEffects().toString());
    }

}
