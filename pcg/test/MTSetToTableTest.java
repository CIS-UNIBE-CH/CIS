import models.MTSetToTable;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.cna.CNAList;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

public class MTSetToTableTest extends UnitTest {
    private MinimalTheorySet set;

    @Before
    public void setup() {
	set = new MinimalTheorySet();
	
	CNAList factors = new CNAList(',', "LA,¬VE,");
	MinimalTheory theory = new MinimalTheory(factors, "S");
	
	CNAList factors1 = new CNAList(',', "US,");
	MinimalTheory theory1 = new MinimalTheory(factors1, "Q");
	
//	CNAList factors2 = new CNAList(',', "F,G,");
//	MinimalTheory theory2 = new MinimalTheory(factors2, "H");
	
	set.add(theory);
	set.add(theory1);
//	set.add(theory2);
	System.out.println("Set: " + set);
    }

    @Test
    public void shouldTest() {
	MTSetToTable parser = new MTSetToTable(set);
    }
}