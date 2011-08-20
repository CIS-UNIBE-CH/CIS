package datastructures.cna;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe.ch@gmail.com>
 * @license GPLv3, for more informations see Readme.mdown
 */

import java.util.ArrayList;
import java.util.HashSet;

public class CNAList extends ArrayList<String> {

    public CNAList() {
	super();
    }

    public CNAList(String regex, String string) {
	String[] array = string.split("" + regex);
	for (int i = 0; i < array.length; i++) {
	    String temp = array[i];
	    temp = temp.replace("\n", "");
	    temp = temp.replace(" ", "");
	    this.add(temp);

	}
    }

    @Override
    public boolean add(String arg0) {
	return super.add(arg0);
    }

    public void negate() {
	for (int k = 0; k < this.size(); k++) {
	    this.set(k, this.get(k).replace("1", "x"));
	    this.set(k, this.get(k).replace("0", "1"));
	    this.set(k, this.get(k).replace("x", "0"));
	}
    }

    public void swap(int first, int second) {
	String firstStr = this.get(first);
	String secondStr = this.get(second);
	this.set(first, secondStr);
	this.set(second, firstStr);
    }

    public void removeDuplicated() {
	HashSet duplicate = new HashSet(this);
	this.clear();
	this.addAll(duplicate);
    }

    public String remove(Integer index) {
	int i = index;
	return super.remove(i);
    }

    public boolean lastElementIsZero() {
	if (this.getLastElement().equals("0")) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean lastElementIsOne() {
	if (this.getLastElement().equals("1")) {
	    return true;
	} else {
	    return false;
	}
    }

    public CNAList clone(CNAList list) {
	CNAList clone = new CNAList();
	for (String str : list) {
	    clone.add(str);
	}
	return clone;
    }

    // toString
    @Override
    public String toString() {
	String str = new String();
	for (String string : this) {
	    str += string + " ";
	}
	return str;
    }

    // Getters and Setters
    public String getLastElement() {
	assert (this.size() != 0);
	return this.get(this.size() - 1);
    }

    public int getIndex(String string) {
	for (int i = 0; i < this.size(); i++) {
	    if (string.equals(this.get(i)))
		return i;
	}
	return -1;
    }

    public boolean elementIsZero(int index) {
	if (this.get(index).equals("0")) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean elementIsOne(int index) {
	if (this.get(index).equals("1")) {
	    return true;
	} else {
	    return false;
	}
    }
}
