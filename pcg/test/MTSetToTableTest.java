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
	
	CNAList factors = new CNAList(',', "A,B,");
	MinimalTheory theory = new MinimalTheory(factors, "C");
	
	CNAList factors1 = new CNAList(',', "C,E,");
	MinimalTheory theory1 = new MinimalTheory(factors1, "F");
	
	set.add(theory);
	set.add(theory1);
    }

    @Test
    public void shouldTest() {
	MTSetToTable parser = new MTSetToTable(set);
    }
}