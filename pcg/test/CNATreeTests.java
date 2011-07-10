import static org.junit.Assert.assertEquals;
import helper.BaumgartnerSampleTable;

import org.junit.BeforeClass;
import org.junit.Test;

import trees.CNAList;
import trees.CNATable;
import trees.CNATreeNode;
import trees.MsufTree;
import algorithms.CNAlgorithm;

public class CNATreeTests {

    private static MsufTree msufTree;
    private static CNAlgorithm cnaAlgorithm;
    private static CNATreeNode node;
    private static CNATable originalTable;

    @BeforeClass
    public static void setup() {
	originalTable = new BaumgartnerSampleTable().getSampleTable();
	cnaAlgorithm = new CNAlgorithm(originalTable);
	// Second Line of Suftable without Effects
	CNAList list = cnaAlgorithm.getSufTable().get(1);
	list.removeLastElement();
	node = new CNATreeNode(list);

	msufTree = new MsufTree(node);
    }

    @Test
    public void shouldTestTreeFillUp() {
	msufTree.fillUpTree(node);
	assertEquals("[$, 1, 1, 1][$, $, 1, 1]" + "[$, $, $, 1][$, $, 1, $]"
		+ "[$, 1, $, 1][$, $, $, 1]" + "[$, 1, $, $][$, 1, 1, $]"
		+ "[$, $, 1, $][$, 1, $, $]" + "[1, $, 1, 1][$, $, 1, 1]"
		+ "[$, $, $, 1][$, $, 1, $]" + "[1, $, $, 1][$, $, $, 1]"
		+ "[1, $, $, $][1, $, 1, $]" + "[$, $, 1, $][1, $, $, $]"
		+ "[1, 1, $, 1][$, 1, $, 1]" + "[$, $, $, 1][$, 1, $, $]"
		+ "[1, $, $, 1][$, $, $, 1]" + "[1, $, $, $][1, 1, $, $]"
		+ "[$, 1, $, $][1, $, $, $]" + "[1, 1, 1, $][$, 1, 1, $]"
		+ "[$, $, 1, $][$, 1, $, $]" + "[1, $, 1, $][$, $, 1, $]"
		+ "[1, $, $, $][1, 1, $, $]" + "[$, 1, $, $][1, $, $, $]",
		msufTree.toString(node));
	assertEquals("[1, 1, 1, 1]", msufTree.getRoot().toString());
    }
}
