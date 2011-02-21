package tree;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import javax.swing.tree.DefaultMutableTreeNode;

/** Custom Tree Node inherits from DefaultMutableTreeNode */
public class CustomTreeNode extends DefaultMutableTreeNode {
	private String bundle;

	public CustomTreeNode(Object o) {
		super(o);
	}

	public String getBundle() {
		return bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
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
