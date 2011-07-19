package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

public class RandomMTSetGenerator {
    private CNATable namesOriginal;
    private CNATable namesEffects;
    private CNATable namesCauses;
    private ArrayList<ArrayList<Object>> userInput;
    private MinimalTheorySet set;
    private String epiFactor = "";
    private int epiCounter = 0;

    public RandomMTSetGenerator(ArrayList<ArrayList<Object>> allInformation) {
	this.userInput = allInformation;
	namesOriginal = new CNATable();
	set = new MinimalTheorySet();

	generateFactorNames();
	generateMTSet();
    }

    // For Testing only
    public RandomMTSetGenerator() {
	namesOriginal = new CNATable();
	set = new MinimalTheorySet();
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
	for (int i = 0; i < userInput.size(); i++) {
	    MinimalTheory theorie = new MinimalTheory();

	    addBundles(userInput.get(i), theorie);
	    addAlterFactors(theorie, userInput.get(i), prevEffect);
	    prevEffect = addEffect(namesEffects, theorie);
	}
    }

    private CNATable getCauseNames(ArrayList<Object> information) {
	if (((Boolean) information.get(2) && epiCounter == 0)
		|| epiCounter == 1) {
	    if (namesCauses == null) {
		namesCauses = namesEffects.clone();
	    }
	    return namesCauses;
	} else {
	    return namesEffects.clone();
	}
    }

    public void addBundles(ArrayList<Object> information, MinimalTheory theorie) {
	ArrayList<Integer> bundleSizes = (ArrayList<Integer>) information
		.get(0);

	CNATable names = getCauseNames(information);
	String bundle = "";
	names = removeEpi(names);
	for (Integer number : bundleSizes) {
	    bundle = "";
	    for (int i = 0; i < number; i++) {
		int random = random(names.size());
		int randomSec = randomNegativePositiv();
		bundle += (names.get(random).get(randomSec));
		// If here is no remove factor could appear in more than one
		// bundle in same MT
		names.remove(random);
	    }
	    theorie.addBundle(bundle);
	}
	namesCauses = names;
	
	if (epiCounter == 1) {
	    theorie = swapFirstWithEpi(theorie);
	    epiCounter++;
	}
	if ((Boolean) information.get(2) && epiCounter == 0) {
	    detectEpi(bundle);
	    epiCounter++;
	}
	if (epiCounter == 2) {
	    epiCounter = 0;
	}
    }

    public void addAlterFactors(MinimalTheory theorie,
	    ArrayList<Object> information, String prevEffect) {
	CNATable names = getCauseNames(information);
	int noOfAlterFactors = (Integer) information.get(1);

	if (!prevEffect.equals("")) {
	    theorie.addBundle(prevEffect);
	    noOfAlterFactors--;
	}
	for (int i = 0; i < noOfAlterFactors; i++) {
	    int random = random(names.size());
	    int randomSec = randomNegativePositiv();
	    theorie.addBundle(names.get(random).get(randomSec));
	    names.remove(random);
	}
	namesCauses = names;
    }

    public String addEffect(CNATable names, MinimalTheory theorie) {
	String effect = "";
	int random = 0;
	while (true) {
	    random = random(names.size());
	    effect = names.get(random).get(0);
	    if (!effectIsFactor(effect, theorie)) {
		break;
	    }
	}

	theorie.setEffect(effect);
	names.remove(random);
	set.add(theorie);
	return effect;
    }

    private boolean effectIsFactor(String effect, MinimalTheory theorie) {
	CNAList factors = theorie.getFactors();
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

    private void detectEpi(String bundle) {
	if (bundle.charAt(bundle.length() - 2) == '¬') {
	    epiFactor = "" + bundle.charAt(bundle.length() - 2)
		    + bundle.charAt(bundle.length() - 1);
	} else {
	    epiFactor = "" + bundle.charAt(bundle.length() - 1);
	}
    }

    private MinimalTheory swapFirstWithEpi(MinimalTheory theorie) {
	if (theorie.getBundles().get(0).charAt(0) == '¬') {
	    String cur = theorie.getBundles().get(0);
	    cur = cur.substring(2);
	    cur = epiFactor + cur;
	    theorie.getBundles().set(0, cur);
	} else {
	    String cur = theorie.getBundles().get(0);
	    cur = cur.substring(1);
	    cur = epiFactor + cur;
	    theorie.getBundles().set(0, cur);
	}
	return theorie;
    }

    private CNATable removeEpi(CNATable names) {
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

    public int random(int size) {
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
