package datastructures.mt;

import java.util.ArrayList;
import java.util.HashSet;

import datastructures.cna.CNAList;

public class MinimalTheorieSet extends ArrayList<MinimalTheorie> {

    private ArrayList<MinimalTheorie> theories;

    public MinimalTheorieSet() {
	theories = new ArrayList<MinimalTheorie>();
    }

    @Override
    public boolean add(MinimalTheorie theorie) {
	return theories.add(theorie);
    }

    @Override
    public int size() {
	return theories.size();
    }

    @Override
    public MinimalTheorie remove(int arg0) {
	return theories.remove(arg0);
    }

    // Getters and Setters

    @Override
    public MinimalTheorie get(int arg0) {
	return theories.get(arg0);
    }

    public int getNumberOfFactors() {
	return this.getAllFactors().size();
    }

    public int getNumberOfEffects() {
	return this.getAllEffects().size();
    }

    public CNAList getAllFactors() {
	CNAList list = new CNAList();
	HashSet<String> set = new HashSet<String>();
	for (MinimalTheorie theorie : theories) {
	    for (String string : theorie.getFactors()) {
		set.add(string);
	    }
	}
	for (String string : set)
	    list.add(string);
	return list;
    }

    public CNAList getAllEffects() {
	CNAList list = new CNAList();
	HashSet<String> set = new HashSet<String>();
	for (MinimalTheorie theorie : theories) {
	    set.add(theorie.getEffect());
	}
	for (String string : set)
	    list.add(string);
	return list;
    }

    public CNAList getAllNames() {
	CNAList list = new CNAList();
	HashSet<String> set = new HashSet<String>();
	for (MinimalTheorie theorie : theories) {
	    set.add(theorie.getEffect());
	}
	for (MinimalTheorie theorie : theories) {
	    for (String string : theorie.getFactors()) {
		set.add(string);
	    }
	}
	System.out.println(set);
	for (String string : set)
	    list.add(string);
	return list;
    }

}
