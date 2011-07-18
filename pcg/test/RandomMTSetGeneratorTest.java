/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import models.RandomMTSetGenerator;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.cna.CNAList;
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

	assertEquals("[A, ¬A]", generator.getAllNames().get(0).toString());
	assertEquals("[W, ¬W]",
		generator.getAllNames().get(generator.getAllNames().size() - 1)
			.toString());
	assertEquals(23, generator.getAllNames().size());
    }

    @Test
    public void shouldCreateRandomNumbers() {
	generator = new RandomMTSetGenerator();

	for (int i = 0; i < 300; i++) {
	    int randomIndex = generator.randomSecond();
	    assertTrue(randomIndex == 0 || randomIndex == 1);
	}
	for (int j = 0; j < 50 * 10; j++) {
	    int randomIndex = generator.random(50);
	    assertTrue(randomIndex >= 0 && randomIndex < 50);
	}
    }

    @Test
    public void shouldCreateRightNumberOfBundlesAnd() {
	for (int i = 0; i < 300; i++) {
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
	generator = new RandomMTSetGenerator(testUserInput);
	MinimalTheorySet set = generator.getMTSet();

	assertEquals(5, set.size());

	for (int i = 0; i < set.size(); i++) {
	    String effectPrev = set.get(i).getEffect();
	    if (i < set.size() - 1) {
		String current = set.get(i + 1).toString();
		assertTrue(current.contains(effectPrev));
		assertTrue(current.charAt(current.indexOf(current) - 1) != '¬');
	    }
	}
    }

    @Test
    public void shouldMakeEpiphenomenon() {
	generator = new RandomMTSetGenerator(testUserInput);
	MinimalTheorySet set = generator.getMTSet();
	int counter = 0;

	for (int i = 0; i < set.size() - 1; i++) {
	    String cur = set.get(i).getBundles().toString();
	    cur = cur.replace("[", "");
	    cur = cur.replace("]", "");
	    cur = cur.replace(" ", "");
	    cur = cur.replace(",", "");
	    if (i < set.size() - 1) {
		String next = set.get(i + 1).getBundles().toString();
		next = next.replace("[", "");
		next = next.replace("]", "");
		next = next.replace(" ", "");
		next = next.replace(",", "");
		for (int j = 0; j < cur.length(); j++) {
		    String toTest = "";
		    if (cur.charAt(j) == '¬') {
			toTest = "" + cur.charAt(j) + cur.charAt(j + 1);
			j++;
		    } else {
			toTest = "" + cur.charAt(j);
		    }
		    if (next.contains(toTest)) {
			counter++;
		    }
		}
	    }
	    assertEquals(1, counter);
	    counter = 0;
	    i += 2;
	}
    }

    @Test
    public void shouldNotHaveDuplicateInBundle() {
	for (int i = 0; i < 300; i++) {
	    RandomMTSetGenerator generator = new RandomMTSetGenerator(testUserInput);
	    MinimalTheorySet set = generator.getMTSet();

	    for (MinimalTheory theory : set) {
		CNAList bundles = theory.getBundles();
		for (int j = 0; j < theory.getBundles().size(); j++) {
		    String curBundle = bundles.get(j);
		    for (int k = 0; k < curBundle.length(); k++) {
			int counter = 0;
			String toTest = "";
			if (curBundle.charAt(k) == '¬'
				&& k < curBundle.length() - 1) {
			    toTest = "" + curBundle.charAt(k)
				    + curBundle.charAt(k + 1);
			    k++;
			} else {
			    toTest = "" + curBundle.charAt(k);
			}
			for (int l = 0; l < curBundle.length(); l++) {
			    String compare = "";
			    if (curBundle.charAt(l) == '¬'
				    && l < curBundle.length() - 1) {
				compare = "" + curBundle.charAt(l)
					+ curBundle.charAt(l + 1);
				k++;
			    } else {
				compare = "" + curBundle.charAt(l);
			    }
			    if (toTest.equals(compare)) {
				counter++;
			    }
			}
			assertEquals(1, counter);
			counter = 0;
		    }
		}
	    }
	}
    }
    
    @Test
    public void shouldTest(){
	RandomMTSetGenerator generator = new RandomMTSetGenerator(testUserInput);
	    MinimalTheorySet set = generator.getMTSet();
    }
}
