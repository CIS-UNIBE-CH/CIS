package graph;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.graph.Node;

public class NodeTest extends UnitTest {
    private Node node;

    @Before
    public void setup() {
	node = new Node("A", false);
    }

    @Test
    public void shouldEqual() {
	Node testNode = new Node("A", false);
	assertTrue(node.equals(testNode));
	testNode = new Node("B", false);
	assertFalse(node.equals(testNode));
	testNode = new Node("A", true);
    }

}
