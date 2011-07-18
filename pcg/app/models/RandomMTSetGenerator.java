package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

public class RandomMTSetGenerator {
    private CNATable namesOriginal;
    private CNATable curNames;
    private ArrayList<ArrayList<Object>> allInformation;
    private MinimalTheorySet set;
    private String epi = "";
    private int counter = 0;

    public RandomMTSetGenerator(ArrayList<ArrayList<Object>> allInformation) {
	this.allInformation = allInformation;
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
	curNames = namesOriginal.clone();
    }

    public void generateMTSet() {
	String prevEffect = "";
	for (int i = 0; i < allInformation.size(); i++) {
	    MinimalTheory theorie = new MinimalTheory();

	    addBundles(allInformation.get(i), theorie);
	    addAlterFactors(theorie, allInformation.get(i), prevEffect);
	    prevEffect = addEffect(curNames, theorie);
	}
    }

    public void addBundles(ArrayList<Object> information, MinimalTheory theorie) {
	ArrayList<Integer> bundleSizes = (ArrayList<Integer>) information
		.get(0);
	CNATable names = curNames.clone();
	String bundle = "";
	System.out.println("Siez: " + names.size());
	names = removeEpi(names);
	System.out.println("Size2: " + names.size());
	for (Integer number : bundleSizes) {
	    bundle = "";
	    for (int i = 0; i < number; i++) {
		int random = random(names.size());
		int randomSec = randomSecond();
		bundle += (names.get(random).get(randomSec));
		names.remove(random);
	    }
	    theorie.addBundle(bundle);
	}
	if (counter == 1) {
	    theorie = swapFirstWithEpi(theorie);
	    counter++;
	}
	if ((Boolean) information.get(2) && counter == 0) {
	    detectEpi(bundle);
	    counter++;
	}
	if (counter == 2) {
	    counter = 0;
	}
    }

    private void detectEpi(String bundle) {
	if (bundle.charAt(bundle.length() - 2) == '¬') {
	    epi = "" + bundle.charAt(bundle.length() - 2)
		    + bundle.charAt(bundle.length() - 1);
	} else {
	    epi = "" + bundle.charAt(bundle.length() - 1);
	}
    }

    private MinimalTheory swapFirstWithEpi(MinimalTheory theorie) {
	if (theorie.getBundles().get(0).charAt(0) == '¬') {
	    String cur = theorie.getBundles().get(0);
	    cur = cur.substring(2);
	    cur = epi + cur;
	    theorie.getBundles().set(0, cur);
	} else {
	    String cur = theorie.getBundles().get(0);
	    cur = cur.substring(1);
	    cur = epi + cur;
	    theorie.getBundles().set(0, cur);
	}
	return theorie;
    }

    private CNATable removeEpi(CNATable names) {
	if (!epi.equals("")) {
	    for (int i = names.size()-1; i >= 0; i--) {
		if (names.get(i).get(0).equals(epi)
			|| names.get(i).get(1).equals(epi)) {
		    names.remove(i);
		}
	    }
	    return names;
	} else {
	    return names;
	}
    }

    public void addAlterFactors(MinimalTheory theorie,
	    ArrayList<Object> information, String prevEffect) {
	CNATable names = curNames.clone();
	int noOfAlterFactors = (Integer) information.get(1);

	if (!prevEffect.equals("")) {
	    theorie.addBundle(prevEffect);
	    noOfAlterFactors--;
	}
	for (int i = 0; i < noOfAlterFactors; i++) {
	    int random = random(names.size());
	    int randomSec = randomSecond();
	    theorie.addBundle(names.get(random).get(randomSec));
	    names.remove(random);
	}
    }

    public String addEffect(CNATable names, MinimalTheory theorie) {
	int random = random(names.size());

	theorie.setEffect(names.get(random).get(0));
	String effect = names.get(random).get(0);
	names.remove(random);
	set.add(theorie);
	return effect;
    }

    public int random(int size) {
	return (int) (Math.random() * (size - 1));
    }

    // Set to 1.4 because we want more positive than negative effects.
    public int randomSecond() {
	return ((int) (Math.random() * 1.4));
    }

    public CNATable getAllNames() {
	return namesOriginal;
    }

    public MinimalTheorySet getMTSet() {
	return set;
    }
}
