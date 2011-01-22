package models;

import org.jgraph.graph.DefaultEdge;

/**
 * This class provides labeling of cause bundles with a number for every cause
 * in a bundle. Inherited from: org.jgrapht.graph.DefaultEdge
 */
public class CustomEdge extends DefaultEdge {

	String bundleLabel;

	public void setBundleNumber(String bundleLabel) {
		this.bundleLabel = bundleLabel;
	}

	public String toString() {
		if (bundleLabel != null) {
			return bundleLabel;
		} else {

			return "";
		}
	}
}
