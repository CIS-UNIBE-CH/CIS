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
    private ArrayList<ArrayList<Object>> testUserInput;

    @Before
    public void setup() {
	testUserInput = new ArrayList<ArrayList<Object>>();

	Integer noOfAlterFactors = 2;
	ArrayList<Integer> bundleSizes = new ArrayList<Integer>();
	for (int i = 0; i < 2; i++) {
	    bundleSizes.add(2);
	}

	ArrayList<Object> level1 = new ArrayList<Object>();
	level1.add(bundleSizes);
	level1.add(noOfAlterFactors);
	level1.add(true);
	ArrayList<Object> level2 = new ArrayList<Object>();
	level2.add(bundleSizes);
	level2.add(noOfAlterFactors);
	level2.add(true);
	ArrayList<Object> level3 = new ArrayList<Object>();
	level3.add(bundleSizes);
	level3.add(noOfAlterFactors);
	level3.add(true);
	ArrayList<Object> level4 = new ArrayList<Object>();
	level4.add(bundleSizes);
	level4.add(noOfAlterFactors);
	level4.add(true);
	testUserInput.add(level1);
	testUserInput.add(level2);
	testUserInput.add(level3);
	testUserInput.add(level4);
    }

    @Test
    public void shouldCreateFactorNames() {
	generator = new RandomMTSetGenerator();
	generator.generateFactorNames();

	assertEquals("[A, ¬A]", generator.getNamesOriginal().get(0).toString());
	assertEquals("[W, ¬W]",
		generator.getNamesOriginal().get(generator.getNamesOriginal().size() - 1)
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
	    int randomIndex = generator.random(50);
	    assertTrue(randomIndex >= 0 && randomIndex < 50);
	}
    }

    @Test
    public void shouldCreateBundlesAndAlterFactors() {
	for (int i = 0; i < 500; i++) {
	    RandomMTSetGenerator generator = new RandomMTSetGenerator(
		    testUserInput);
	    MinimalTheorySet set = generator.getMTSet();

	    for (int j = 0; j < set.size(); j++) {
		ArrayList<Integer> bundleSizes = (ArrayList<Integer>) testUserInput
			.get(j).get(0);
		int numberOfAlterFactors = (Integer) testUserInput.get(j)
			.get(1);
		assertEquals(bundleSizes.size() + numberOfAlterFactors, set
			.get(j).getBundles().size());
	    }
	}
    }

    public void shouldMakeChain() {
	for (int i = 0; i < 500; i++) {
	    RandomMTSetGenerator generator = new RandomMTSetGenerator(
		    testUserInput);
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
	RandomMTSetGenerator generator = new RandomMTSetGenerator(testUserInput);
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
		    testUserInput);
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
	    generator = new RandomMTSetGenerator(testUserInput);
	    MinimalTheorySet set = generator.getMTSet();
	    System.out.println("Set: " + set);
	    for (int i = 0; i < set.size(); i += 2) {
		CNAList list = set.get(i).getFactors();
		CNAList list1 = set.get(i + 1).getFactors();

		System.out.println("List: " + list);
		System.out.println("List1" + list1);

		HashSet duplicate = new HashSet(list);
		list.clear();
		list.addAll(duplicate);
		HashSet duplicate1 = new HashSet(list1);
		list1.clear();
		list1.addAll(duplicate1);
		int counter = 0;
		for (String cur : list) {
		    for (String curNext : list1) {
			if (cur.equals(curNext)) {
			    counter++;
			}
		    }
		}
		assertEquals(1, counter);
	    }
	}
    }

    // @Test
    // public void shouldTest() {
    // RandomMTSetGenerator generator = new RandomMTSetGenerator(testUserInput);
    // System.out.println("Set: " + generator.getMTSet());
    // System.out.println("Bundles: "
    // + generator.getMTSet().get(0).getBundleFactors());
    // System.out.println("Factors: "
    // + generator.getMTSet().get(0).getBundles());
    // }
}
