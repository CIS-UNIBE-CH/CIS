package random;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import play.test.UnitTest;
import datastructures.random.RandomGraphInput;

/** Run Test 500 times because random stuff is going on */
public class RandomGraphInputTest extends UnitTest {

    @Test
    public void shouldRemoveBundleSizesNulls() {
	for (int k = 0; k < 500; k++) {
	    int addCounter = 0;
	    ArrayList<ArrayList<Integer>> bundleSizes = new ArrayList<ArrayList<Integer>>();

	    int noOfLevels = (int) (Math.random() * 10 + 1);
	    for (int i = 0; i < noOfLevels; i++) {
		ArrayList<Integer> level = new ArrayList<Integer>();
		int noOfBundles = (int) (Math.random() * 10 + 1);
		for (int j = 0; j < noOfBundles; j++) {
		    Random random = new Random();
		    if (random.nextBoolean()) {
			level.add((int) (Math.random() * 3));
			addCounter++;
		    } else {
			level.add(null);
		    }
		}
		bundleSizes.add(level);
	    }

	    RandomGraphInput randomGraphInput = new RandomGraphInput(noOfLevels);
	    randomGraphInput.setBundleSizes(bundleSizes);
	    randomGraphInput.removeBundleSizesNulls();

	    int referenceAddCounter = 0;
	    for (ArrayList<Integer> level : bundleSizes) {
		for (Integer cur : level) {
		    assertNotNull(cur);
		    referenceAddCounter++;
		}
	    }
	    assertEquals(referenceAddCounter, addCounter);
	}
    }

    @Test
    public void shouldSwapAlterFactorNullWithZero() {
	for (int i = 0; i < 500; i++) {
	    ArrayList<Integer> alterFactor = new ArrayList<Integer>();
	    int notNullCounter = 0;
	    int nullCounter = 0;
	    int noOfBundles = (int) (Math.random() * 10 + 1);

	    for (int j = 0; j < noOfBundles; j++) {
		Random random = new Random();
		if (random.nextBoolean()) {
		    alterFactor.add((int) (Math.random() * 5 + 1));
		    notNullCounter++;
		} else {
		    alterFactor.add(null);
		    nullCounter++;
		}
	    }

	    RandomGraphInput randomGraphInput = new RandomGraphInput(
		    noOfBundles);
	    randomGraphInput.setNoOfAlterFactors(alterFactor);
	    randomGraphInput.swapAlterFactorNullWithZero();

	    int referenceNotNullCounter = 0;
	    int referenceNullCounter = 0;
	    for (Integer cur : alterFactor) {
		assertNotNull(cur);
		if (cur == 0) {
		    referenceNullCounter++;
		} else {
		    referenceNotNullCounter++;
		}
	    }
	    assertEquals(referenceNullCounter, nullCounter);
	    assertEquals(referenceNotNullCounter, notNullCounter);
	}
    }

    @Test
    public void shouldCreateEpiList() {
	for (int i = 0; i < 500; i++) {
	    int noOfLevels = (int) (Math.random() * 10 + 2);
	    RandomGraphInput randomGraphInput = new RandomGraphInput(noOfLevels);
	    Random random = new Random();
	    randomGraphInput.setMakeEpiphenomenon(random.nextBoolean());
	    randomGraphInput.createEpiList();

	    for (int j = 0; j < randomGraphInput.getEpi().size(); j++) {
		Boolean cur = randomGraphInput.getEpi().get(j);
		if (j >= randomGraphInput.getEpi().size() - 2
			&& randomGraphInput.isMakeEpiphenomenon()) {
		    assertEquals(true, cur);
		} else {
		    assertEquals(false, cur);
		}
	    }
	}
    }
}
