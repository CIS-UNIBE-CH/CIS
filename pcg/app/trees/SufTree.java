package trees;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import javax.swing.tree.DefaultTreeModel;

public class SufTree extends DefaultTreeModel {
    public SufTree(SufTreeNode node) {
	super(node);
    }
}