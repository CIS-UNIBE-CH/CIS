import org.junit.BeforeClass;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.CNAList;
import datastructures.MinimalTheorie;
import datastructures.MinimalTheorieSet;

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
    public void shouldGetChains() {

    }

    @Test
    public void shouldGetEpi() {

    }

}
