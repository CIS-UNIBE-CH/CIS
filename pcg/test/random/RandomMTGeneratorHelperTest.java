package random;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <pcg.unibe.ch@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import java.util.ArrayList;

import org.junit.Test;

import play.test.UnitTest;
import datastructures.random.RandomMTGeneratorHelper;

/** Run Test 500 times because random stuff is going on */
public class RandomMTGeneratorHelperTest extends UnitTest {
    ArrayList<ArrayList<Integer>> bundleSizes;
    ArrayList<Integer> alterFactor;

    public void generateRandomInput() {
	bundleSizes = new ArrayList<ArrayList<Integer>>();
	int levels = 3;
	for (int i = 0; i < levels; i++) {
	    ArrayList<Integer> level = new ArrayList<Integer>();
	    int bundlesPerLevel = 2;
	    for (int j = 0; j < bundlesPerLevel; j++) {
		level.add((int) (Math.random() * 5));
	    }
	    bundleSizes.add(level);
	}

	alterFactor = new ArrayList<Integer>();
	for (int j = 0; j < levels; j++) {
	    alterFactor.add((int) (Math.random() * 3));
	}
    }

    public void setOnRandomLevelBundleEmpty() {
	ArrayList<Integer> zeroBundle = new ArrayList<Integer>();
	for (int i = 0; i < 3; i++) {
	    zeroBundle.add(0);
	}
	for (int j = 0; j < Math.random() * 3; j++) {
	    bundleSizes.set((int) (Math.random() * bundleSizes.size() - 1),
		    zeroBundle);
	}
    }

    @Test
    public void shouldRemoveZerosInBundles() {
	for (int i = 0; i < 500; i++) {
	    generateRandomInput();
	    RandomMTGeneratorHelper input = new RandomMTGeneratorHelper();
	    input.setBundleSizesLevels(bundleSizes);
	    input.setAlterFactors(alterFactor);
	    input.removeZerosInBundles();
	    ArrayList<ArrayList<Integer>> bundleSizesGenerated = input
		    .getBundleSizesLevels();
	    for (ArrayList<Integer> list : bundleSizesGenerated) {
		for (Integer cur : list) {
		    assertTrue(cur > 0);
		}
	    }
	}
    }

    @Test
    public void shouldMakeAllZeroBundlesEmpty() {
	for (int i = 0; i < 500; i++) {
	    generateRandomInput();
	    setOnRandomLevelBundleEmpty();
	    RandomMTGeneratorHelper input = new RandomMTGeneratorHelper();
	    input.setBundleSizesLevels(bundleSizes);
	    input.setAlterFactors(alterFactor);
	    input.removeZerosInBundles();
	    ArrayList<ArrayList<Integer>> bundleSizesGenerated = input
		    .getBundleSizesLevels();
	    for (ArrayList<Integer> list : bundleSizesGenerated) {
		if (list.size() == 0) {
		    assertEquals(0, list.size());
		}
	    }
	}
    }

    @Test
    public void shouldCreateLevelsList() {
	for (int k = 0; k < 500; k++) {
	    generateRandomInput();
	    RandomMTGeneratorHelper input = new RandomMTGeneratorHelper();
	    input.setBundleSizesLevels(bundleSizes);
	    input.setAlterFactors(alterFactor);
	    input.createLevelsList();
	    for (int i = 0; i < bundleSizes.size(); i++) {
		assertEquals(bundleSizes.get(i), input.getCompleteList().get(i)
			.get(0));
	    }
	    for (int j = 0; j < alterFactor.size(); j++) {
		assertEquals(alterFactor.get(j), input.getCompleteList().get(j)
			.get(1));
	    }
	}
    }

}
