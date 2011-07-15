package datastructures.mt;

import datastructures.cna.CNAList;

public class MinimalTheorie {

    private CNAList factors;
    private String effect;

    public MinimalTheorie() {
	factors = new CNAList();
	effect = new String();
    }

    public MinimalTheorie(CNAList factors, String effect) {
	this.factors = factors;
	this.effect = effect;
    }

    // To String

    public String toString() {
	assert (factors.size() != 0 && effect.length() != 0);
	String output = new String();
	for (int i = 0; i < factors.size() - 1; i++) {
	    output += factors.get(i) + "X" + (i + 1) + " ∨ ";
	}
	output += factors.get(factors.size() - 1) + "X" + factors.size()
		+ " => " + effect;
	return output;
    }

    // Getters and Setters
    public CNAList getFactors() {
	return factors;
    }

    public void setFactors(CNAList factors) {
	this.factors = factors;
    }

    public String getEffect() {
	return effect;
    }

    public void setEffect(String effect) {
	this.effect = effect;
    }
}
