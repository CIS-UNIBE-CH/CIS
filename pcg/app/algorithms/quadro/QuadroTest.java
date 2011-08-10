package algorithms.quadro;

/** 
 * Copyright (C) <2011> 
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe@gmail.com>
 * @license GPLv3, see Readme.mdown 
 */

import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

/** Hardcoded 16 possibilities of quadro test */
public class QuadroTest {

    private String coincidence;
    private String name1, name2, name1Nec, name2Nec;
    private String specialCase;

    public QuadroTest(String coincidence, String name1, String name2)
	    throws QuadroException {
	this.coincidence = coincidence;
	this.name1 = name1;
	this.name2 = name2;
	this.name1Nec = "¬" + name1;
	this.name2Nec = "¬" + name2;
	specialCase = "";

	createMTTheorySet();
    }

    public MinimalTheorySet createMTTheorySet() throws QuadroException {
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
	    throw new QuadroException(
		    "The factor "
			    + name2
			    + " is not part of at least one minimal sufficient condition which contains the first factor.");
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
	    theory.addBundle(name1);
	    theory.addBundle(name1Nec);
	    specialCase = "Note: The factor " + name2
		    + " can be localized in two ways: (1) " + name1 + name2
		    + "X1 v " + name1Nec + "X2 v YW => W (2) " + name1
		    + "X1 v " + name1Nec + "X2 v " + name2 + "X3 v YW => W";
	    return set;
	} else if (coincidence.equals("0111")) {
	    theory.addBundle(name1);
	    theory.addBundle(name1Nec);
	    return set;
	} else if (coincidence.equals("0000")) {
	    throw new QuadroException("No causal inference is possible.");
	} else {
	    throw new QuadroException("No causal inference is possible.");
	}
    }

    public String getSpecialCase() {
	return specialCase;
    }
}
