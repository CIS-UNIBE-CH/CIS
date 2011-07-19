package datastructures.random;

import java.util.ArrayList;

public class Random {
    private ArrayList<ArrayList<Integer>> bundleSizes;
    private ArrayList<Integer> noOfAlterFactors;
    private ArrayList<Boolean> epiphenomenon = new ArrayList<Boolean>();
    private boolean makeEpiphenomenon;
    private int size;

    public Random(ArrayList<ArrayList<Integer>> bundleSizes,
	    ArrayList<Integer> noOfAlterFactors, boolean makeEpiphenomenon) {
	this.bundleSizes = bundleSizes;
	this.noOfAlterFactors = noOfAlterFactors;
	this.makeEpiphenomenon = makeEpiphenomenon;
	size = bundleSizes.size();

	swapAlterFactorNullWithZero();
	removeBundleSizesNulls();
	createEpiList();
    }
    
    //For Testing only
    public Random(int size){
	this.size = size;
    }

    public void removeBundleSizesNulls() {
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

    public void swapAlterFactorNullWithZero() {
	for (int i = noOfAlterFactors.size() - 1; i >= 0; i--) {
	    Integer cur = noOfAlterFactors.get(i);
	    if (cur == null) {
		noOfAlterFactors.set(i, 0);
	    }
	}
    }

    public void createEpiList() {
	for (int i = 0; i < size; i++) {
	    epiphenomenon.add(false);
	}
	if (makeEpiphenomenon) {
	    int length = size;
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

    public void setBundleSizes(ArrayList<ArrayList<Integer>> bundleSizes) {
        this.bundleSizes = bundleSizes;
    }

    public void setNoOfAlterFactors(ArrayList<Integer> noOfAlterFactors) {
        this.noOfAlterFactors = noOfAlterFactors;
    }

    public void setMakeEpiphenomenon(boolean makeEpiphenomenon) {
        this.makeEpiphenomenon = makeEpiphenomenon;
    }

    public boolean isMakeEpiphenomenon() {
        return makeEpiphenomenon;
    }
}
