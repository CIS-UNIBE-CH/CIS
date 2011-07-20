package random;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import play.test.UnitTest;
import datastructures.cna.CNAList;
import datastructures.mt.MinimalTheorySet;
import datastructures.random.RandomGraphInput;
import datastructures.random.RandomMTSetGenerator;

/** Run Test 500 times because random stuff is going on */
public class RandomMTSetGeneratorTest extends UnitTest {
    private RandomMTSetGenerator generator;
    RandomGraphInput input;

    public void setup() {

	// Create Sample Input
	ArrayList<ArrayList<Integer>> bundleSizes = new ArrayList<ArrayList<Integer>>();
	int noOfLevels = (int) (Math.random() * 3 + 1);
	for (int i = 0; i < noOfLevels; i++) {
	    ArrayList<Integer> level = new ArrayList<Integer>();
	    int noOfBundles = (int) (Math.random() * 2 + 1);
	    for (int j = 0; j < noOfBundles; j++) {
		Random random = new Random();
		if (random.nextBoolean()) {
		    level.add((int) (Math.random() * 3) + 1);
		} else {
		    level.add(0);
		}
	    }
	    bundleSizes.add(level);
	}

	ArrayList<Integer> alterFactor = new ArrayList<Integer>();
	for (int j = 0; j < noOfLevels; j++) {
	    Random random = new Random();
	    if (random.nextBoolean()) {
		alterFactor.add((int) (Math.random() * 2 + 1));
	    } else {
		alterFactor.add(0);
	    }
	}

	input = new RandomGraphInput(bundleSizes, alterFactor);

    }

    @Test
    public void shouldNotHaveDuplicate() {
	for (int i = 0; i < 2000; i++) {
	    setup();
	    generator = new RandomMTSetGenerator(input.getLevels(), false);
	    MinimalTheorySet set = generator.getMTSet();
	    System.out.println("THE SET: " + set);
	    CNAList factors = set.getAllFactors();
	    HashMap map = new HashMap();
	    for (int j = 0; j < factors.size() - 1; j++) {
		map.put(factors.get(j), factors.get(j));
		assertFalse(map.containsKey(factors.get(j + 1)));
	    }
	}

    }
    //
    // @Test
    // public void shouldCreateFactorNames() {
    // generator = new RandomMTSetGenerator();
    // generator.generateFactorNames();
    //
    // assertEquals("A ¬A ", generator.getNamesOriginal().get(0).toString());
    // assertEquals(
    // "W ¬W ",
    // generator.getNamesOriginal()
    // .get(generator.getNamesOriginal().size() - 1)
    // .toString());
    // assertEquals(23, generator.getNamesOriginal().size());
    // }
    //
    // @Test
    // public void shouldCreateRandomNumbers() {
    // generator = new RandomMTSetGenerator();
    //
    // for (int i = 0; i < 500; i++) {
    // int randomIndex = generator.randomNegativePositiv();
    // assertTrue(randomIndex == 0 || randomIndex == 1);
    // }
    // for (int j = 0; j < 50 * 10; j++) {
    // int randomIndex = generator.randomIndex(50);
    // assertTrue(randomIndex >= 0 && randomIndex < 50);
    // }
    // }
    //
    // @Test
    // public void shouldCreateBundlesAndAlterFactors() {
    // for (int i = 0; i < 500; i++) {
    // RandomMTSetGenerator generator = new RandomMTSetGenerator(
    // sampleUserInput, alterFacNo, epi);
    // MinimalTheorySet set = generator.getMTSet();
    //
    // for (int j = 0; j < set.size(); j++) {
    // ArrayList<Integer> bundleSizes = sampleUserInput.get(j);
    // int numberOfAlterFactors = alterFacNo.get(j);
    // assertEquals(bundleSizes.size() + numberOfAlterFactors, set
    // .get(j).getBundles().size());
    // }
    // }
    // }
    //
    // public void shouldMakeChain() {
    // for (int i = 0; i < 500; i++) {
    // RandomMTSetGenerator generator = new RandomMTSetGenerator(
    // sampleUserInput, alterFacNo, epi);
    // MinimalTheorySet set = generator.getMTSet();
    //
    // assertEquals(5, set.size());
    //
    // for (int j = 0; j < set.size(); j++) {
    // String effectPrev = set.get(j).getEffect();
    // if (j < set.size() - 1) {
    // String current = set.get(j + 1).toString();
    // assertTrue(current.contains(effectPrev));
    // assertTrue(current.charAt(current.indexOf(current) - 1) != '¬');
    // }
    // }
    // }
    // }
    //
    // @Test
    // public void shouldNotHaveSameFactorsAsCauseAndEffectInTheory() {
    // RandomMTSetGenerator generator = new RandomMTSetGenerator(
    // sampleUserInput, alterFacNo, epi);
    // MinimalTheorySet set = generator.getMTSet();
    // for (int i = 0; i < 500; i++) {
    // for (MinimalTheory theory : set) {
    // CNAList factors = theory.getFactors();
    // for (String factor : factors) {
    // assertFalse(theory.getEffect().equals(factor));
    // }
    // }
    // }
    // }
    //
    // @Test
    // public void shouldNotHaveDuplicateInBundle() {
    // for (int i = 0; i < 500; i++) {
    // RandomMTSetGenerator generator = new RandomMTSetGenerator(
    // sampleUserInput, alterFacNo, epi);
    // MinimalTheorySet set = generator.getMTSet();
    //
    // for (MinimalTheory theory : set) {
    // CNATable factors = theory.getBundleFactors();
    // for (CNAList bundle : factors) {
    // HashMap map = new HashMap();
    // for (int j = 0; j < bundle.size(); j++) {
    // assertFalse(map.containsKey(bundle.get(j)));
    // if (!map.containsKey(bundle.get(j))) {
    // map.put(bundle.get(j), bundle.get(j));
    // }
    // }
    // }
    // }
    // }
    // }
    //
    // @Test
    // public void shouldMakeEpiphenomenon() {
    // for (int j = 0; j < 500; j++) {
    // RandomMTSetGenerator generator = new RandomMTSetGenerator(
    // sampleUserInput, alterFacNo, epi);
    // MinimalTheorySet set = generator.getMTSet();
    // for (int i = 0; i < set.size(); i += 2) {
    // CNAList list = set.get(i).getFactors();
    // CNAList list1 = set.get(i + 1).getFactors();
    //
    // HashSet duplicate = new HashSet(list);
    // list.clear();
    // list.addAll(duplicate);
    // HashSet duplicate1 = new HashSet(list1);
    // list1.clear();
    // list1.addAll(duplicate1);
    // int epiCounter = 0;
    // for (String cur : list) {
    // for (String curNext : list1) {
    // if (cur.equals(curNext)) {
    // epiCounter++;
    // }
    // }
    // }
    // assertEquals(1, epiCounter);
    // }
    // }
    // }
}
