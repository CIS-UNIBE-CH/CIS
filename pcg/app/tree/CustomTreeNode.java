package tree;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.Random;

import javax.swing.tree.DefaultMutableTreeNode;

/** Custom Tree Node inherits from DefaultMutableTreeNode */
public class CustomTreeNode extends DefaultMutableTreeNode {
	private String bundle;
	Random randomGenerator = new Random();
	private int test = randomGenerator.nextInt(10);

	public CustomTreeNode(Object o) {
		super(o);
	}

	public String getBundle() {
		return bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public int getTest() {
		return test;
	}

	/** Checks if the tree node is part of cause bundle */
	public boolean isPartOfBundle() {
		if (bundle != null) {
			return true;
		} else {
			return false;
		}
	}
}
