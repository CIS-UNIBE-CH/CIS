package datastructures.random;

import java.util.ArrayList;

public class Random {
    ArrayList<ArrayList<Integer>> bundleSizes;
    ArrayList<Integer> noOfAlterFactors;
    ArrayList<Boolean> epiphenomenon = new ArrayList<Boolean>();
    boolean makeEpiphenomenon;

    public Random(ArrayList<ArrayList<Integer>> bundleSizes,
	    ArrayList<Integer> noOfAlterFactors, boolean makeEpiphenomenon) {
	this.bundleSizes = bundleSizes;
	this.noOfAlterFactors = noOfAlterFactors;
	this.makeEpiphenomenon = makeEpiphenomenon;

	swapAlterFactorNullWithZero();
	removeBundleSizesNull();
	createEpiList();
    }

    private void removeBundleSizesNull() {
	for (int i = bundleSizes.size() - 1; i >= 0; i--) {
	    ArrayList<Integer> list = bundleSizes.get(i);
	    for (int j = list.size() - 1; j >= 0; j--) {
		Integer cur = list.get(j);
		if (cur == null) {
		    list.remove(j);
		}
	    }
	    if (list.size() == 0) {
		bundleSizes.remove(i);
	    }
	}
    }

    private void swapAlterFactorNullWithZero() {
	for (int i = noOfAlterFactors.size() - 1; i >= 0; i--) {
	    Integer cur = noOfAlterFactors.get(i);
	    if (cur == null) {
		noOfAlterFactors.set(i, 0);
	    }
	}
    }

    private void createEpiList() {
	for (int i = 0; i < bundleSizes.size(); i++) {
	    epiphenomenon.add(false);
	}
	if (makeEpiphenomenon) {
	    int length = bundleSizes.size();
	    epiphenomenon.set(length - 1, true);
	    epiphenomenon.set(length - 2, true);
	}
    }

    public ArrayList<ArrayList<Integer>> getBundleSizes() {
        return bundleSizes;
    }

    public ArrayList<Integer> getNoOfAlterFactors() {
        return noOfAlterFactors;
    }

    public ArrayList<Boolean> getEpi() {
        return epiphenomenon;
    }
}
