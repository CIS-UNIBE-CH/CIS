import helper.BaumgartnerSampleTable;

import org.junit.BeforeClass;
import org.junit.Test;

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
	node = new CNATreeNode(originalTable.g)
    }

    @Test
    public void shouldTestTreeFillUp(){
	msufTree = new 
	
    }
}
