import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.CNAList;
import datastructures.MinimalTheorie;

public class MinimalTheorieTest extends UnitTest {
    static MinimalTheorie theorie;
    static CNAList factors;
    static String effect;

    @Before
    public void setup() {
	factors = new CNAList(",", "AB,C,DE");
	effect = "W";
	theorie = new MinimalTheorie(factors, effect);
    }

    @Test
    public void shouldTestToString() {
	assert (theorie.getFactors().size() != 0);
	String minTheorie = "ABX1 ∨ CX2 ∨ DEX3 => W";
	assertEquals(minTheorie, theorie.toString());
    }

    @Test
    public void shouldGetEffect() {
	assert (theorie.getEffect() != "");
	assertEquals(effect, theorie.getEffect());
    }

    @Test
    public void shouldGetFactors() {
	assert (theorie.getFactors().size() != 0);
	assertEquals(factors, theorie.getFactors());
    }

    @Test
    public void shouldSetEffect() {
	theorie.setEffect("L");
	assertEquals("L", theorie.getEffect());
    }

    @Test
    public void shouldSetFactors() {
	CNAList list = new CNAList(",", "A,D,L");
	theorie.setFactors(list);
	assertEquals(list, theorie.getFactors());
    }
}
