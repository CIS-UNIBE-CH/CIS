package datastructures.random;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import java.util.ArrayList;

import algorithms.cna.CNAException;

public class RandomMTGeneratorHelper {
    private ArrayList<ArrayList<Object>> completeList = new ArrayList<ArrayList<Object>>();
    private ArrayList<ArrayList<Integer>> bundleSizesLevels;
    private ArrayList<Integer> alterFactors;
    private boolean makeEpi;

    public RandomMTGeneratorHelper(
	    ArrayList<ArrayList<Integer>> bundleSizesLeveled,
	    ArrayList<Integer> alterFactors, boolean makeEpi)
	    throws CNAException {
	this.bundleSizesLevels = bundleSizesLeveled;
	this.alterFactors = alterFactors;
	this.makeEpi = makeEpi;

	cleanupNulls();
	nullToZeros();
	if (makeEpi) {
	    bundlesCheck();
	    alterFactorCheck();
	}
	removeZerosInBundles();
	createLevelsList();

    }

    // For Testing only.
    public RandomMTGeneratorHelper() {
    }

    private void cleanupNulls() {
	for (ArrayList<Integer> list : bundleSizesLevels) {
	    for (int i = 0; i < list.size(); i++) {
		if (list.get(i) == null)
		    list.set(i, 0);
	    }
	}

    }

    private void bundlesCheck() throws CNAException {
	int counter = 0;
	for (ArrayList<Integer> list : bundleSizesLevels) {
	    for (Integer cur : list) {
		counter += cur;
	    }
	}
	if (counter > 0) {
	    throw new CNAException(
		    "You can generate an epiphenomenon only with alternate Factors.");
	}
    }

    private void nullToZeros() {
	for (int i = 0; i < alterFactors.size(); i++) {
	    if (alterFactors.get(i) == null) {
		alterFactors.set(i, 0);
	    }
	}
    }

    private void alterFactorCheck() throws CNAException {
	int counter = 0;
	for (Integer cur : alterFactors) {
	    if (cur != 0) {
		counter++;
	    }
	}
	if (counter < 2) {
	    throw new CNAException(
		    "There must be at least two minimal theories with alternate factors to generate an epiphenomenon.");
	}
    }

    public void removeZerosInBundles() {
	for (int i = bundleSizesLevels.size() - 1; i >= 0; i--) {
	    ArrayList<Integer> list = bundleSizesLevels.get(i);
	    for (int j = list.size() - 1; j >= 0; j--) {
		Integer cur = list.get(j);
		if (cur == 0) {
		    list.remove(j);
		}
	    }
	}
    }

    public void createLevelsList() {
	for (int i = 0; i < bundleSizesLevels.size(); i++) {
	    ArrayList<Object> level = new ArrayList<Object>();
	    level.add(bundleSizesLevels.get(i));
	    level.add(alterFactors.get(i));
	    completeList.add(level);
	}
    }

    public ArrayList<ArrayList<Integer>> getBundleSizesLevels() {
	return bundleSizesLevels;
    }

    public ArrayList<Integer> getAlterFactors() {
	return alterFactors;
    }

    public ArrayList<ArrayList<Object>> getCompleteList() {
	return completeList;
    }

    public void setBundleSizesLevels(
	    ArrayList<ArrayList<Integer>> bundleSizesLevels) {
	this.bundleSizesLevels = bundleSizesLevels;
    }

    public void setAlterFactors(ArrayList<Integer> alterFactors) {
	this.alterFactors = alterFactors;
    }
}
