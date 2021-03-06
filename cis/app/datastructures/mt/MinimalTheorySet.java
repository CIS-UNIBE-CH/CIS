package datastructures.mt;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe.ch@gmail.com>
 * @license GPLv3, for more informations see Readme.mdown
 */

import java.util.ArrayList;
import java.util.HashSet;

import datastructures.cna.CNAList;

public class MinimalTheorySet extends ArrayList<MinimalTheory> {

    private ArrayList<MinimalTheory> theories;

    public MinimalTheorySet() {
	theories = new ArrayList<MinimalTheory>();
    }

    @Override
    public boolean add(MinimalTheory theorie) {
	return theories.add(theorie);
    }

    @Override
    public int size() {
	return theories.size();
    }

    @Override
    public MinimalTheory remove(int arg0) {
	return theories.remove(arg0);
    }

    // Getters and Setters

    @Override
    public MinimalTheory get(int arg0) {
	return theories.get(arg0);
    }
    
    public MinimalTheorySet duplicate(MinimalTheorySet set){
	MinimalTheorySet clone = new MinimalTheorySet();
	for(MinimalTheory theory : set){
	    clone.add(theory);
	}
	return clone;
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
	for (MinimalTheory theorie : theories) {
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
	for (MinimalTheory theorie : theories) {
	    set.add(theorie.getEffect());
	}
	for (String string : set)
	    list.add(string);
	return list;
    }

    public CNAList getAllNames() {
	CNAList list = new CNAList();
	HashSet<String> set = new HashSet<String>();
	set.addAll(getAllEffects());
	set.addAll(getAllFactors());
	list.addAll(set);
	return list;
    }
}
