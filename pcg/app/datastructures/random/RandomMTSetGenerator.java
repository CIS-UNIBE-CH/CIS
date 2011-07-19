package datastructures.random;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

public class RandomMTSetGenerator {
    private CNATable namesOriginal;
    private CNATable namesEffects;
    // Hold at ervery index: At index 0 an ArrayList of bundlesizes, at 1 a int
    // (noOfAlterFactors) and at 2 if it's epiphenomenon.
    //private ArrayList<ArrayList<Object>> userInput;
    private MinimalTheorySet set;
    private String epiFactor;
    private int epiCounter;
    private MinimalTheory prevMT;
    private ArrayList<ArrayList<Integer>> bundleSizes;
    private ArrayList<Integer> alterFactors;
    private ArrayList<Boolean> epi;

    public RandomMTSetGenerator(ArrayList<ArrayList<Integer>> bundleSizes, ArrayList<Integer> alterFactors, ArrayList<Boolean> epi) {
	//this.userInput = userInput;
	namesOriginal = new CNATable();
	set = new MinimalTheorySet();
	epiFactor = "";
	epiCounter = 0;
	prevMT = null;
	this.bundleSizes = bundleSizes;
	this.alterFactors = alterFactors;
	this.epi = epi;

	generateFactorNames();
	generateMTSet();
    }

    // For Testing only
    public RandomMTSetGenerator() {
	namesOriginal = new CNATable();
	set = new MinimalTheorySet();
	epiFactor = "";
	epiCounter = 0;
	prevMT = null;
    }

    public void generateFactorNames() {
	for (int i = 65; i <= (65 + 22); i++) {
	    String curLetter = "" + (char) i;
	    String curLetterNegative = "¬" + (char) i;
	    CNAList list = new CNAList();
	    list.add(curLetter);
	    list.add(curLetterNegative);
	    namesOriginal.add(list);
	}
	namesEffects = namesOriginal.clone();
    }

    public void generateMTSet() {
	String prevEffect = "";
	for (int i = 0; i < bundleSizes.size(); i++) {
	    MinimalTheory theorie = new MinimalTheory();

	    addBundles(bundleSizes.get(i), theorie, epi.get(i));
	    addAlterFactors(theorie, alterFactors.get(i), prevEffect);
	    prevEffect = addEffect(namesEffects, theorie);
	}
    }

    public void addBundles(ArrayList<Integer> level, MinimalTheory theory, boolean epi) {
	ArrayList<Integer> bundleSizes = level;
	CNATable names = namesEffects.clone();
	String bundle = "";

	names = removeEpiFactor(names);
	for (Integer number : bundleSizes) {
	    bundle = "";
	    for (int i = 0; i < number; i++) {
		int random = randomIndex(names.size());
		int randomSec = randomNegativePositiv();
		if (!factorExistsInPrevMT(names.get(random).get(randomSec))) {
		    bundle += (names.get(random).get(randomSec));
		    // If here is no remove, factor could appear in more than
		    // one bundle in same MT
		    names.remove(random);
		} else {
		    i--;
		}
	    }
	    theory.addBundle(bundle);
	}
	if (epiCounter == 1) {
	    theory = swapFirstFactorWithEpi(theory);
	    epiCounter++;
	}
	if (epi && epiCounter == 0) {
	    getEpiFactor(bundle);
	    epiCounter++;
	}
	if (epiCounter == 2) {
	    epiCounter = 0;
	}
    }

    public void addAlterFactors(MinimalTheory theory, int noOfAlterFactors,
	    String prevEffect) {
	CNATable names = namesEffects.clone();
	//int noOfAlterFactors = (Integer) level.get(1);

	if (!prevEffect.equals("")) {
	    theory.addBundle(prevEffect);
	    noOfAlterFactors--;
	}
	for (int i = 0; i < noOfAlterFactors; i++) {
	    int random = randomIndex(names.size());
	    int randomSec = randomNegativePositiv();
	    if (!factorExistsInPrevMT(names.get(random).get(randomSec))) {
		theory.addBundle(names.get(random).get(randomSec));
		names.remove(random);
	    } else {
		i--;
	    }
	}
    }

    public String addEffect(CNATable names, MinimalTheory theory) {
	String effect = "";
	int random = 0;

	while (true) {
	    random = randomIndex(names.size());
	    effect = names.get(random).get(0);
	    if (!effectEqualsMTFactor(effect, theory)) {
		break;
	    }
	}
	theory.setEffect(effect);
	names.remove(random);
	prevMT = theory;
	set.add(theory);
	return effect;
    }

    public boolean factorExistsInPrevMT(String current) {
	if (prevMT == null) {
	    return false;
	}
	for (String prevFactor : prevMT.getFactors()) {
	    if (prevFactor.equals(current)) {
		return true;
	    }
	}
	return false;
    }

    private boolean effectEqualsMTFactor(String effect, MinimalTheory theory) {
	CNAList factors = theory.getFactors();
	int counter = 0;
	for (String factor : factors) {
	    if (factor.equals(effect)) {
		counter++;
	    }
	}
	if (counter == 0) {
	    return false;
	} else {
	    return true;
	}
    }

    private void getEpiFactor(String bundle) {
	if ( bundle.length() > 1 && bundle.charAt(bundle.length() - 2) == '¬') {
	    epiFactor = "" + bundle.charAt(bundle.length() - 2)
		    + bundle.charAt(bundle.length() - 1);
	} else {
	    epiFactor = "" + bundle.charAt(bundle.length() - 1);
	}
    }

    private MinimalTheory swapFirstFactorWithEpi(MinimalTheory theory) {
	if (theory.getBundles().get(0).charAt(0) == '¬') {
	    String cur = theory.getBundles().get(0);
	    cur = cur.substring(2);
	    cur = epiFactor + cur;
	    theory.getBundles().set(0, cur);
	} else {
	    String cur = theory.getBundles().get(0);
	    cur = cur.substring(1);
	    cur = epiFactor + cur;
	    theory.getBundles().set(0, cur);
	}
	return theory;
    }

    private CNATable removeEpiFactor(CNATable names) {
	if (!epiFactor.equals("")) {
	    for (int i = names.size() - 1; i >= 0; i--) {
		if (names.get(i).get(0).equals(epiFactor)
			|| names.get(i).get(1).equals(epiFactor)) {
		    names.remove(i);
		}
	    }
	    return names;
	} else {
	    return names;
	}
    }

    public int randomIndex(int size) {
	return (int) (Math.random() * (size - 1));
    }

    // Value set to 1.4 because we want more positive than negative effects.
    public int randomNegativePositiv() {
	return ((int) (Math.random() * 1.4));
    }

    public CNATable getNamesOriginal() {
	return namesOriginal;
    }

    public MinimalTheorySet getMTSet() {
	return set;
    }
}
