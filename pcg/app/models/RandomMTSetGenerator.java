package models;


/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

/**
 * Generates a random graph out of a given number of factors and bundles.
 * 
 * TODO: Die möglichkeit, dass der gleiche Faktor in mehreren Bündeln vorkommt
 * ist noch nicht abgedeckt. (Siehe Kommentar Zeile: 90)
 * 
 */
public class RandomMTSetGenerator {
    private int numberOfBundles;
    private int numberOfFactors;
    private int totalFactors;

    public RandomMTSetGenerator(int numberOfBundles, int numberOfFactors,
	    int bundleSize) {
	this.numberOfBundles = numberOfBundles;
	this.numberOfFactors = numberOfFactors;
    }
}
