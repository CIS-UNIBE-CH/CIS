package datastructures.mt;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe.ch@gmail.com>
 * @license GPLv3, for more informations see Readme.mdown
 */

import datastructures.cna.CNAList;
import datastructures.cna.CNATable;

public class MinimalTheory {

    private CNAList bundles;
    private String effect;

    public MinimalTheory() {
	bundles = new CNAList();
	effect = new String();
    }

    public MinimalTheory(CNAList factors, String effect) {
	this.bundles = factors;
	this.effect = effect;
    }

    @Override
    public String toString() {
	assert (bundles.size() != 0 && effect.length() != 0);
	String output = new String();

	for (int i = 0; i < bundles.size() - 1; i++) {
	    output += bundles.get(i) + " ∨ ";
	}
	output += bundles.get(bundles.size() - 1) + " <=> " + effect;
	return output;
    }

    // Getters and Setters
    public CNAList getBundles() {
	return bundles;
    }

    public CNAList getFactors() {
	CNAList factors = new CNAList();
	for (int i = 0; i < bundles.size(); i++) {
	    for (int j = 0; j < bundles.get(i).length(); j++) {
		String cur = bundles.get(i);
		if (cur.charAt(j) == '¬') {
		    factors.add("" + cur.charAt(j) + cur.charAt(j + 1));
		    j++;
		} else {
		    factors.add("" + cur.charAt(j));
		}
	    }
	}
	return factors;
    }

    public CNATable getBundleFactors() {
	CNATable table = new CNATable();
	for (int i = 0; i < bundles.size(); i++) {
	    CNAList factors = new CNAList();
	    for (int j = 0; j < bundles.get(i).length(); j++) {
		String cur = bundles.get(i);
		if (cur.charAt(j) == '¬') {
		    factors.add("" + cur.charAt(j) + cur.charAt(j + 1));
		    j++;
		} else {
		    factors.add("" + cur.charAt(j));
		}
	    }
	    table.add(factors);
	}
	return table;
    }

    public void setBundleFactors(CNATable bundleFactors) {
	bundles.clear();
	for (CNAList list : bundleFactors) {
	    String bundle = "";
	    for (String cur : list) {
		bundle += cur;
	    }
	    bundles.add(bundle);
	}
    }

    public void addBundle(String bundle) {
	bundles.add(bundle);
    }

    public void setBundles(CNAList bundles) {
	this.bundles = bundles;
    }

    public String getEffect() {
	return effect;
    }

    public void setEffect(String effect) {
	this.effect = effect;
    }
}
