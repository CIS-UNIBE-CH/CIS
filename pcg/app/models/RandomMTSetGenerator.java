package models;

import java.util.ArrayList;

import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

/**
 * Generates a random graph out of a given number of factors and bundles.
 * 
 * TODO: Die möglichkeit, dass der gleiche Faktor in mehreren Bündeln vorkommt
 * ist noch nicht abgedeckt. (Siehe Kommentar Zeile: 90)
 * 
 */
public class RandomMTSetGenerator {
    private int bundles;
    private int totalFactors;
    private int bundleSize;
    private ArrayList<String> nodeNames = new ArrayList<String>();
    private MinimalTheorie mt = new MinimalTheorie();
    private MinimalTheorieSet set = new MinimalTheorieSet();
    

    public RandomMTSetGenerator(int bundles, int totalFactors, int bundleSize) {
	this.bundles = 0;
	this.totalFactors = totalFactors;
	this.bundleSize = 0;

	nodeGenerator();
	createMT();
    }

    /** Generates the names of the nodes and the CustomTreeNodes. */
    private void nodeGenerator() {
	// i = 65 because char 65 = A
	for (int i = 65; i <= (65 + totalFactors); i++) {
	    String curFactorLetter = "" + (char) i;
	    String curFactorLetterNegative = "¬" + (char) i;
	    nodeNames.add(curFactorLetter);
	    nodeNames.add(curFactorLetterNegative);
	}
    }
    
    private void createMT(){
	for(int i = 0; i < totalFactors-1 ; i++){
	    int random = (int) (Math.random()*nodeNames.size()-2);
	    mt.addFactor(nodeNames.get(random));
	}
	int random = (int) (Math.random()*nodeNames.size()-2);
	mt.setEffect(nodeNames.get(random));
	set.add(mt);
	System.out.println("MTTOString" + mt);
    }
    
    public MinimalTheorieSet getMTSet() {
	return set;
    }

    public String[][] getTable() {
	// TODO Auto-generated method stub
	return null;
    }
}
