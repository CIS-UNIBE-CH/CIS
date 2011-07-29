package datastructures;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <pcg.unibe.ch@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import helpers.BaumgartnerSampleTable;

import org.junit.BeforeClass;
import org.junit.Test;

import play.test.UnitTest;
import algorithms.cna.CNAlgorithm;
import algorithms.cna.NecException;
import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.tree.CNATreeNode;
import datastructures.tree.MsufTree;

public class CNATreeTests extends UnitTest {

    private static MsufTree msufTree;
    private static CNAlgorithm cnaAlgorithm;
    private static CNATreeNode node;
    private static CNATable originalTable;

    @BeforeClass
    public static void setup() throws NecException {
	originalTable = new BaumgartnerSampleTable().getSampleTable();
	cnaAlgorithm = new CNAlgorithm(originalTable);
	// Second Line of Suftable without Effects
	CNAList list = cnaAlgorithm.getSufTable().get(1);
	list.remove(list.size() - 1);
	node = new CNATreeNode(list);

	msufTree = new MsufTree(node);
    }

    @Test
    public void shouldTestTreeFillUp() {
	msufTree.fillUpTree(node);
	assertEquals("" + "$ 1 1 1 \n" + "$ $ 1 1 \n" + "$ $ $ 1 \n"
		+ "$ $ 1 $ \n" + "$ 1 $ 1 \n" + "$ $ $ 1 \n" + "$ 1 $ $ \n"
		+ "$ 1 1 $ \n" + "$ $ 1 $ \n" + "$ 1 $ $ \n" + "1 $ 1 1 \n"
		+ "$ $ 1 1 \n" + "$ $ $ 1 \n" + "$ $ 1 $ \n" + "1 $ $ 1 \n"
		+ "$ $ $ 1 \n" + "1 $ $ $ \n" + "1 $ 1 $ \n" + "$ $ 1 $ \n"
		+ "1 $ $ $ \n" + "1 1 $ 1 \n" + "$ 1 $ 1 \n" + "$ $ $ 1 \n"
		+ "$ 1 $ $ \n" + "1 $ $ 1 \n" + "$ $ $ 1 \n" + "1 $ $ $ \n"
		+ "1 1 $ $ \n" + "$ 1 $ $ \n" + "1 $ $ $ \n" + "1 1 1 $ \n"
		+ "$ 1 1 $ \n" + "$ $ 1 $ \n" + "$ 1 $ $ \n" + "1 $ 1 $ \n"
		+ "$ $ 1 $ \n" + "1 $ $ $ \n" + "1 1 $ $ \n" + "$ 1 $ $ \n"
		+ "1 $ $ $ \n", msufTree.toString(node));
	assertEquals("1 1 1 1 ", msufTree.getRoot().toString());
    }
}
