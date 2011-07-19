/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import models.RandomMTSetGenerator;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

public class RandomMTSetGeneratorTest extends UnitTest {
    private RandomMTSetGenerator generator;
    private ArrayList<ArrayList<Integer>> sampleUserInput;
    private ArrayList<Integer> alterFacNo;
    private ArrayList<Boolean> epi;

    @Before
    public void setup() {
	sampleUserInput = new ArrayList<ArrayList<Integer>>();

	Integer noOfAlterFactors = 2;
	ArrayList<Integer> bundleSizes = new ArrayList<Integer>();
	for (int i = 0; i < 2; i++) {
	    bundleSizes.add(2);
	}
	sampleUserInput.add(bundleSizes);
	sampleUserInput.add(bundleSizes);
	sampleUserInput.add(bundleSizes);
	sampleUserInput.add(bundleSizes);

	alterFacNo = new ArrayList<Integer>();
	alterFacNo.add(noOfAlterFactors);
	alterFacNo.add(noOfAlterFactors);
	alterFacNo.add(noOfAlterFactors);
	alterFacNo.add(noOfAlterFactors);
	
	epi = new ArrayList<Boolean>();
	epi.add(true);
	epi.add(true);
	epi.add(true);
	epi.add(true);
    }

    @Test
    public void shouldCreateFactorNames() {
	generator = new RandomMTSetGenerator();
	generator.generateFactorNames();

	assertEquals("A ¬A ", generator.getNamesOriginal().get(0).toString());
	assertEquals(
		"W ¬W ",
		generator.getNamesOriginal()
			.get(generator.getNamesOriginal().size() - 1)
			.toString());
	assertEquals(23, generator.getNamesOriginal().size());
    }

    @Test
    public void shouldCreateRandomNumbers() {
	generator = new RandomMTSetGenerator();

	for (int i = 0; i < 500; i++) {
	    int randomIndex = generator.randomNegativePositiv();
	    assertTrue(randomIndex == 0 || randomIndex == 1);
	}
	for (int j = 0; j < 50 * 10; j++) {
	    int randomIndex = generator.randomIndex(50);
	    assertTrue(randomIndex >= 0 && randomIndex < 50);
	}
    }

    @Test
    public void shouldCreateBundlesAndAlterFactors() {
	for (int i = 0; i < 500; i++) {
	    RandomMTSetGenerator generator = new RandomMTSetGenerator(
		    sampleUserInput, alterFacNo, epi);
	    MinimalTheorySet set = generator.getMTSet();

	    for (int j = 0; j < set.size(); j++) {
		ArrayList<Integer> bundleSizes = sampleUserInput.get(j);
		int numberOfAlterFactors = alterFacNo.get(j);
		assertEquals(bundleSizes.size() + numberOfAlterFactors, set
			.get(j).getBundles().size());
	    }
	}
    }

    public void shouldMakeChain() {
	for (int i = 0; i < 500; i++) {
	    RandomMTSetGenerator generator = new RandomMTSetGenerator(
		    sampleUserInput, alterFacNo, epi);
	    MinimalTheorySet set = generator.getMTSet();

	    assertEquals(5, set.size());

	    for (int j = 0; j < set.size(); j++) {
		String effectPrev = set.get(j).getEffect();
		if (j < set.size() - 1) {
		    String current = set.get(j + 1).toString();
		    assertTrue(current.contains(effectPrev));
		    assertTrue(current.charAt(current.indexOf(current) - 1) != '¬');
		}
	    }
	}
    }

    @Test
    public void shouldNotHaveSameFactorsAsCauseAndEffectInTheory() {
	RandomMTSetGenerator generator = new RandomMTSetGenerator(
		    sampleUserInput, alterFacNo, epi);
	MinimalTheorySet set = generator.getMTSet();
	for (int i = 0; i < 500; i++) {
	    for (MinimalTheory theory : set) {
		CNAList factors = theory.getFactors();
		for (String factor : factors) {
		    assertFalse(theory.getEffect().equals(factor));
		}
	    }
	}
    }

    @Test
    public void shouldNotHaveDuplicateInBundle() {
	for (int i = 0; i < 500; i++) {
	    RandomMTSetGenerator generator = new RandomMTSetGenerator(
		    sampleUserInput, alterFacNo, epi);
	    MinimalTheorySet set = generator.getMTSet();

	    for (MinimalTheory theory : set) {
		CNATable factors = theory.getBundleFactors();
		for (CNAList bundle : factors) {
		    HashMap map = new HashMap();
		    for (int j = 0; j < bundle.size(); j++) {
			assertFalse(map.containsKey(bundle.get(j)));
			if (!map.containsKey(bundle.get(j))) {
			    map.put(bundle.get(j), bundle.get(j));
			}
		    }
		}
	    }
	}
    }

    @Test
    public void shouldMakeEpiphenomenon() {
	for (int j = 0; j < 500; j++) {
	    RandomMTSetGenerator generator = new RandomMTSetGenerator(
		    sampleUserInput, alterFacNo, epi);
	    MinimalTheorySet set = generator.getMTSet();
	    for (int i = 0; i < set.size(); i += 2) {
		CNAList list = set.get(i).getFactors();
		CNAList list1 = set.get(i + 1).getFactors();

		HashSet duplicate = new HashSet(list);
		list.clear();
		list.addAll(duplicate);
		HashSet duplicate1 = new HashSet(list1);
		list1.clear();
		list1.addAll(duplicate1);
		int epiCounter = 0;
		for (String cur : list) {
		    for (String curNext : list1) {
			if (cur.equals(curNext)) {
			    epiCounter++;
			}
		    }
		}
		assertEquals(1, epiCounter);
	    }
	}
    }
}
