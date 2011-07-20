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
    private MinimalTheorySet set;
    private ArrayList<ArrayList<Object>> levels;
    private MinimalTheory prevMT;
    private boolean makeEpi;

    public RandomMTSetGenerator(ArrayList<ArrayList<Object>> levels, boolean epi) {
	namesOriginal = new CNATable();
	set = new MinimalTheorySet();
	this.levels = levels;
	prevMT = null;
	this.makeEpi = epi;

	generateFactorNames();
	generateMTSet();
	makeChain();
	System.out.println("Final Set: " + set);
    }

    // For Testing only
    public RandomMTSetGenerator() {
	namesOriginal = new CNATable();
	set = new MinimalTheorySet();
	prevMT = null;
    }

    private void makeChain() {
	String effect = "";
	for (int i = 0; i < set.size(); i++) {
	    MinimalTheory cur = set.get(i);
	    if (!effect.equals("")) {
		int lastBundle = cur.getBundleFactors().size() - 1;
		int lastFactor = cur.getBundleFactors().get(lastBundle).size() - 1;
		CNATable temp = cur.getBundleFactors();
		temp.get(lastBundle).set(lastFactor, effect);
		cur.setBundleFactors(temp);
	    }
	    effect = cur.getEffect();
	    if (makeEpi && i == set.size() - 2) {
		makeEpi(cur, set.get(i + 1));
		break;
	    }
	}
    }

    private void makeEpi(MinimalTheory cur, MinimalTheory next) {
	String curFirst = cur.getBundleFactors().get(0).get(0);
	CNATable nextBundles = next.getBundleFactors();
	nextBundles.get(0).set(0, curFirst);
	next.setBundleFactors(nextBundles);

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
	for (int i = 0; i < levels.size(); i++) {
	    MinimalTheory theory = new MinimalTheory();
	    boolean madeFactors = false;

	    if (!hasNoBundles((ArrayList<Integer>) levels.get(i).get(0))) {
		addBundles(theory, (ArrayList<Integer>) levels.get(i).get(0));
		madeFactors = true;
	    }
	    if ((Integer) levels.get(i).get(1) != 0) {
		addAlterFactors(theory, (Integer) levels.get(i).get(1));
		madeFactors = true;
	    }
	    if (madeFactors) {
		addEffect(theory);
	    }
	}
    }

    public void addBundles(MinimalTheory theory, ArrayList<Integer> bundles) {
	ArrayList<Integer> bundleSizes = bundles;
	CNATable names = namesEffects.clone();
	String bundle = "";

	for (Integer number : bundleSizes) {
	    bundle = "";
	    for (int i = 0; i < number; i++) {
		int random = randomIndex(names.size());
		int randomSec = randomNegativePositiv();
		String factor = names.get(random).get(randomSec);
		if (!factorExistsInPrevMT(factor)) {
		    bundle += factor;
		    // If here is no remove, factor could appear in more than
		    // one bundle in same MT
		    names.remove(random);
		} else {
		    i--;
		}
	    }
	    theory.addBundle(bundle);
	}
    }

    public void addAlterFactors(MinimalTheory theory, int noOfAlterFactors) {
	CNATable names = namesEffects.clone();

	String factor = "";
	for (int i = 0; i < noOfAlterFactors; i++) {
	    int random = randomIndex(names.size());
	    int randomSec = randomNegativePositiv();
	    factor = names.get(random).get(randomSec);
	    if (!factorExistsInPrevMT(factor)) {
		theory.addBundle(factor);
		names.remove(random);
	    } else {
		i--;
	    }
	}
    }

    public void addEffect(MinimalTheory theory) {
	String effect = "";
	int random = 0;

	while (true) {
	    random = randomIndex(namesEffects.size());
	    effect = namesEffects.get(random).get(0);
	    if (!effectEqualsMTFactor(effect, theory)) {
		break;
	    }
	}
	theory.setEffect(effect);
	namesEffects.remove(random);
	prevMT = theory;
	System.out.println("Theory: " + theory);
	set.add(theory);
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

    private boolean hasNoBundles(ArrayList<Integer> list) {
	int counter = 0;
	for (Integer cur : list) {
	    counter += cur;
	}
	if (counter == 0) {
	    return true;
	} else {
	    return false;
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