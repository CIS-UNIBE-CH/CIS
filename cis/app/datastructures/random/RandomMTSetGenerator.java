package datastructures.random;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import java.util.ArrayList;

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

public class RandomMTSetGenerator {
    private CNATable namesOriginal;
    private CNATable names;
    private MinimalTheorySet set;
    private ArrayList<ArrayList<Object>> levels;
    private boolean makeEpi;

    public RandomMTSetGenerator(ArrayList<ArrayList<Object>> levels, boolean epi) {
	namesOriginal = new CNATable();
	set = new MinimalTheorySet();
	this.levels = levels;
	this.makeEpi = epi;

	generateFactorNames();
	generateMTSet();
	makeChain();
    }

    // For Testing only
    public RandomMTSetGenerator() {
	namesOriginal = new CNATable();
	set = new MinimalTheorySet();
	this.levels = new ArrayList<ArrayList<Object>>();
    }

    /** Step 0 */
    public void generateFactorNames() {
	for (int i = 65; i <= (65 + 22); i++) {
	    String curLetter = "" + (char) i;
	    String curLetterNegative = "¬" + (char) i;
	    CNAList list = new CNAList();
	    list.add(curLetter);
	    list.add(curLetterNegative);
	    namesOriginal.add(list);
	}
	names = namesOriginal.clone();
    }

    /** Step 1 */
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

    private void addBundles(MinimalTheory theory, ArrayList<Integer> bundles) {
	ArrayList<Integer> bundleSizes = bundles;
	String bundle = "";

	for (Integer number : bundleSizes) {
	    bundle = "";
	    for (int i = 0; i < number; i++) {
		int random = randomIndex(names.size());
		int randomSec = randomNegativePositiv();
		String factor = names.get(random).get(randomSec);
		bundle += factor;
		// If here is no remove, factor could appear in more than
		// one bundle in same MT
		names.remove(random);
	    }
	    theory.addBundle(bundle);
	}
    }

    private void addAlterFactors(MinimalTheory theory, int noOfAlterFactors) {

	String factor = "";
	for (int i = 0; i < noOfAlterFactors; i++) {
	    int random = randomIndex(names.size());
	    int randomSec = randomNegativePositiv();
	    factor = names.get(random).get(randomSec);
	    theory.addBundle(factor);
	    names.remove(random);
	}
    }

    private void addEffect(MinimalTheory theory) {
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
	set.add(theory);
    }

    /** Step 2 */
    public void makeChain() {
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

    /** Helpers */
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

    public void setLevels(ArrayList<ArrayList<Object>> levels) {
	this.levels = levels;
    }

    public void setMakeEpi(boolean makeEpi) {
	this.makeEpi = makeEpi;
    }
}