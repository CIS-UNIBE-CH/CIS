package datastructures.mt;

import datastructures.cna.CNAList;

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

    // To String
    @Override
    public String toString() {
	assert (bundles.size() != 0 && effect.length() != 0);
	String output = new String();
	for (int i = 0; i < bundles.size() - 1; i++) {
	    output += bundles.get(i) + "X" + (i + 1) + " ∨ ";
	}
	output += bundles.get(bundles.size() - 1) + "X" + bundles.size()
		+ " => " + effect;
	return output;
    }

    // Getters and Setters
    public CNAList getBundles() {
	return bundles;
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
