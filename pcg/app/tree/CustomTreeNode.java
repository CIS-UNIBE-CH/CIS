package tree;

import javax.swing.tree.DefaultMutableTreeNode;

/** A custom Tree Node inherits from DefaultMutableTreeNode */
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

	public boolean isPartOfBundle() {
		if (bundle != null) {
			return true;
		} else {
			return false;
		}
	}
}
