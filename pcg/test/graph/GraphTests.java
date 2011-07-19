package graph;

import helpers.BaumgartnerSampleTable;
import models.Renderer;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import algorithms.cna.CNAlgorithm;
import algorithms.cna.NecException;
import datastructures.cna.CNAList;
import datastructures.graph.Graph;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;

public class GraphTests extends UnitTest {

    private MinimalTheorySet theories;

    @Before
    public void setup() {
	BaumgartnerSampleTable sample = new BaumgartnerSampleTable();
	try {
	    CNAlgorithm cnaAlgorithm = new CNAlgorithm(sample.getSampleTable());
	    theories = cnaAlgorithm.getMinimalTheorySet();
	} catch (NecException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void shouldAddMTSet() {
	MinimalTheorySet theories = new MinimalTheorySet();

	CNAList factors = new CNAList(',', "C,B,L");
	MinimalTheory theorie = new MinimalTheory(factors, "R");
	theories.add(theorie);

	factors = new CNAList(',', "R,S,THEL");
	theorie = new MinimalTheory(factors, "W");
	theories.add(theorie);

	factors = new CNAList(',', "S,J");
	theorie = new MinimalTheory(factors, "I");
	theories.add(theorie);

	// CNAList factors = new CNAList(',', "C,B,L");
	// MinimalTheory theorie = new MinimalTheory(factors, "R");
	// theories.add(theorie);

	//
	// factors = new CNAList(',', "R,S,TH");
	// theorie = new MinimalTheory(factors, "W");
	// theories.add(theorie);
	//
	// factors = new CNAList(',', "S,J");
	// theorie = new MinimalTheory(factors, "I");
	// theories.add(theorie);

	// System.out.println(theories);
	// CNAList factors = new CNAList(',', "A,1,11");
	// MinimalTheory theorie = new MinimalTheory(factors, "B");
	// theories.add(theorie);
	//
	// factors = new CNAList(',', "B,2,22");
	// theorie = new MinimalTheory(factors, "C");
	// theories.add(theorie);
	//
	// factors = new CNAList(',', "C,3,33");
	// theorie = new MinimalTheory(factors, "D");
	// theories.add(theorie);
	//
	// factors = new CNAList(',', "D,4,44");
	// theorie = new MinimalTheory(factors, "E");
	// theories.add(theorie);

	System.out.println(theories);
	Graph matrix = new Graph(theories);
	System.out.println(matrix);
	Renderer renderer = new Renderer();
	assert (matrix != null);
	renderer.config(matrix);
    }
}
