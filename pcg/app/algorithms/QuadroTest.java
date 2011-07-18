package algorithms;

import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

/** Hardcoded 16 possibilities of quadro test */
public class QuadroTest {

    private String coincidence;
    private String name1, name2, name1Nec, name2Nec;

    public QuadroTest(String coincidence, String name1, String name2) {
	this.coincidence = coincidence;
	this.name1 = name1;
	this.name2 = name2;
	this.name1Nec = "¬" + name1;
	this.name2Nec = "¬" + name2;

	createMTTheorySet();
    }

    private MinimalTheorySet createMTTheorySet() {
	MinimalTheorySet set = new MinimalTheorySet();
	MinimalTheory theory = new MinimalTheory();
	theory.setEffect("W");
	set.add(theory);

	if (coincidence.equals("1000")) {
	    theory.addBundle(name1 + name2);
	    return set;
	} else if (coincidence.equals("0100")) {
	    theory.addBundle(name1 + name2Nec);
	    return set;
	} else if (coincidence.equals("0010")) {
	    theory.addBundle(name1);
	    theory.addBundle(name1Nec + name2);
	    return set;
	} else if (coincidence.equals("0001")) {
	    theory.addBundle(name1);
	    theory.addBundle(name1Nec + name2Nec);
	    return set;
	} else if (coincidence.equals("1100")) {
	    // TODO Spezifalfall Buch S.219, felix fragen wie machen
	    System.out.println("error: 2");
	    return null;
	} else if (coincidence.equals("1010")) {
	    theory.addBundle(name1);
	    theory.addBundle(name2);
	    return set;
	} else if (coincidence.equals("0101")) {
	    theory.addBundle(name1);
	    theory.addBundle(name2Nec);
	    return set;
	} else if (coincidence.equals("1001")) {
	    theory.addBundle(name1 + name2);
	    theory.addBundle(name1Nec + name2Nec);
	    return set;
	} else if (coincidence.equals("0110")) {
	    theory.addBundle(name1 + name2Nec);
	    theory.addBundle(name1Nec + name2);
	    return set;
	} else if (coincidence.equals("0011")) {
	    theory.addBundle(name1);
	    theory.addBundle(name1Nec);
	    return set;
	} else if (coincidence.equals("1110")) {
	    theory.addBundle(name1);
	    theory.addBundle(name2);
	    return set;
	} else if (coincidence.equals("1101")) {
	    theory.addBundle(name1);
	    theory.addBundle(name2Nec);
	    return set;
	} else if (coincidence.equals("1011")) {
	    // TODO Spezialfall, scholl fragen, ob erwähnen.
	    theory.addBundle(name1);
	    theory.addBundle(name1Nec);
	    return set;
	} else if (coincidence.equals("0111")) {
	    theory.addBundle(name1);
	    theory.addBundle(name1Nec);
	    return set;
	} else if (coincidence.equals("0000")) {
	    // TODO Felix fragen ob so korrekt
	    System.out.println("error: 0");
	    return null;
	} else {
	    // TODO Felix fragen ob so korrekt
	    System.out.println("error: 4");
	    return null;
	}
    }
}
