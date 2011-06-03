package tree;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import javax.swing.tree.DefaultMutableTreeNode;

/** Custom Tree Node inherits from DefaultMutableTreeNode */
public class CustomTreeNode extends DefaultMutableTreeNode {
    private String bundle;
    private String name;

    public CustomTreeNode(Object o) {
	super(o);
	name = (String) o;
    }

    public String getBundle() {
	return bundle;
    }

    public void setBundle(String bundle) {
	this.bundle = bundle;
    }

    public String getName() {
	return name;
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
