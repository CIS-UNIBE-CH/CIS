package random;

import java.util.ArrayList;

import org.junit.Test;

import play.test.UnitTest;
import datastructures.random.Random;

public class RandomTest extends UnitTest {

    @Test
    public void shouldRemoveBundleSizesNulls() {
	
    }
    
    @Test
    public void shouldSwapAlterFactorNullWithZero() {
	Random random = new Random(0);
	ArrayList<Integer> alterFactor = new ArrayList<Integer>();
	
	alterFactor.add(null);
	alterFactor.add(null);
	alterFactor.add(null);
	
	random.setNoOfAlterFactors(alterFactor);
	random.swapAlterFactorNullWithZero();
	assertEquals("[0, 0, 0]", random.getNoOfAlterFactors().toString());
	
	alterFactor.clear();
	alterFactor.add(1);
	alterFactor.add(1);
	alterFactor.add(1);
	random.setNoOfAlterFactors(alterFactor);
	random.swapAlterFactorNullWithZero();
	assertEquals("[1, 1, 1]", random.getNoOfAlterFactors().toString());
    }
    
    @Test
    public void shouldCreateEpiList(){
	Random random = new Random(3);
	
	random.setMakeEpiphenomenon(false);
	random.createEpiList();
	assertEquals(false, random.isMakeEpiphenomenon());
	assertEquals("[false, false, false]", random.getEpi().toString());
	
	Random random1 = new Random(3);
	random1.setMakeEpiphenomenon(true);
	random1.createEpiList();
	assertEquals(true, random1.isMakeEpiphenomenon());
	assertEquals("[false, true, true]", random1.getEpi().toString());
    }
}
