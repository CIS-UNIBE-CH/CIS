import helpers.BaumgartnerSampleTable;
import models.Renderer;

import org.junit.Before;
import org.junit.Test;

import parsers.MTSetToGraph;
import play.test.UnitTest;
import algorithms.cna.CNAlgorithm;
import algorithms.cna.NecException;
import datastructures.graph.Edge;
import datastructures.graph.Graph;
import datastructures.graph.Node;
import datastructures.mt.MinimalTheorieSet;

public class GraphTests extends UnitTest {

    private Graph<Node, Edge> graph;
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
	Renderer renderer = new Renderer();
	MTSetToGraph mtSetToGraph = new MTSetToGraph(theories);
	renderer.config(mtSetToGraph.getGraph());
    }

}
