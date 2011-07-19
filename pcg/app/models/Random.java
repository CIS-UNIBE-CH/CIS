package models;

import java.util.ArrayList;

public class Random {
    ArrayList<ArrayList<Integer>> bundleSizes;
    ArrayList<Integer> alterFac;
    ArrayList<Boolean> epi = new ArrayList<Boolean>();
    boolean epiOn;

    public Random(ArrayList<ArrayList<Integer>> bundleSizes,
	    ArrayList<Integer> alterFac, boolean epiOn) {
	this.bundleSizes = bundleSizes;
	this.alterFac = alterFac;
	this.epiOn = epiOn;

	removeAlterFacNull();
	removeBundleSizesNull();
	epi();
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
	System.out.println("Bundle** " + bundleSizes);
    }

    private void removeAlterFacNull() {
	for (int i = alterFac.size() - 1; i >= 0; i--) {
	    Integer cur = alterFac.get(i);
	    if (cur == null) {
		alterFac.set(i, 0);
	    }
	}
	System.out.println("Alter** " + alterFac);
    }

    private void epi() {
	for (int i = 0; i < bundleSizes.size(); i++) {
	    epi.add(false);
	}
	if (epiOn) {
	    int length = bundleSizes.size();
	    epi.set(length - 1, true);
	    epi.set(length - 2, true);
	}
	System.out.println("Epi** " + epi);
    }

    public ArrayList<ArrayList<Integer>> getBundleSizes() {
        return bundleSizes;
    }

    public ArrayList<Integer> getAlterFac() {
        return alterFac;
    }

    public ArrayList<Boolean> getEpi() {
        return epi;
    }
}
