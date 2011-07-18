
import helpers.BaumgartnerSampleTable;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import algorithms.cna.CNAlgorithm;
import algorithms.cna.NecException;
import datastructures.graph.Edge;
import datastructures.graph.Graph;
import datastructures.graph.Node;
import datastructures.mt.MinimalTheorySet;

public class GraphTests extends UnitTest {

    private Graph<Node, Edge> graph;
    private MinimalTheorySet theories;

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
	graph = new Graph<Node, Edge>(theories);
	System.out.println(graph);
    }

}
