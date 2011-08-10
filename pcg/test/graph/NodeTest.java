package graph;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

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
