package models;

import org.jgraph.graph.DefaultEdge;

/**
 * This class provides labeling of cause bundles with a number for every cause
 * in a bundle. Inherited from: org.jgrapht.graph.DefaultEdge
 */
public class CustomEdge extends DefaultEdge {

	String number;

	public void setBundleNumber(String number) {
		this.number = number;
	}

	public String toString() {
		if (number != null) {
			return number;
		} else {

			return "";
		}
	}
}
