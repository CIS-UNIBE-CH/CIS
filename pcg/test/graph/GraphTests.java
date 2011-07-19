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
import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;

public class GraphTests extends UnitTest {

    private MinimalTheorieSet theories;

    @Before
    public void setup() {
	BaumgartnerSampleTable sample = new BaumgartnerSampleTable();
	try {
	    CNAlgorithm cnaAlgorithm = new CNAlgorithm(sample.getSampleTable());
	    theories = cnaAlgorithm.getMinimalTheorieSet();
	} catch (NecException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void shouldAddMTSet() {
	MinimalTheorieSet theories = new MinimalTheorieSet();

	CNAList factors = new CNAList(',', "C,B,L");
	MinimalTheorie theorie = new MinimalTheorie(factors, "R");
	theories.add(theorie);

	factors = new CNAList(',', "R,S,TH");
	theorie = new MinimalTheorie(factors, "W");
	theories.add(theorie);

	factors = new CNAList(',', "S,J");
	theorie = new MinimalTheorie(factors, "I");
	theories.add(theorie);

	// CNAList factors = new CNAList(',', "C,B,L");
	// MinimalTheorie theorie = new MinimalTheorie(factors, "R");
	// theories.add(theorie);

	//
	// factors = new CNAList(',', "R,S,TH");
	// theorie = new MinimalTheorie(factors, "W");
	// theories.add(theorie);
	//
	// factors = new CNAList(',', "S,J");
	// theorie = new MinimalTheorie(factors, "I");
	// theories.add(theorie);

	// System.out.println(theories);
	// CNAList factors = new CNAList(',', "A,1,11");
	// MinimalTheorie theorie = new MinimalTheorie(factors, "B");
	// theories.add(theorie);
	//
	// factors = new CNAList(',', "B,2,22");
	// theorie = new MinimalTheorie(factors, "C");
	// theories.add(theorie);
	//
	// factors = new CNAList(',', "C,3,33");
	// theorie = new MinimalTheorie(factors, "D");
	// theories.add(theorie);
	//
	// factors = new CNAList(',', "D,4,44");
	// theorie = new MinimalTheorie(factors, "E");
	// theories.add(theorie);

	System.out.println(theories);
	Graph matrix = new Graph(theories);
	// System.out.println(matrix);
	Renderer renderer = new Renderer();
	assert (matrix != null);
	renderer.config(matrix);
    }
}
