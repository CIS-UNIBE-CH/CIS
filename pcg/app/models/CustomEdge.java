package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

/**
 * This class provides labeling of cause bundles with a number for every cause
 * in a bundle. Inherited from: org.jgrapht.graph.DefaultEdge
 */
public class CustomEdge {

    String bundleLabel;

    public void setBundleNumber(String bundleLabel) {
	this.bundleLabel = bundleLabel;
    }

    public int getBundleNumber(){
	if (bundleLabel != null) {
	    System.out.println("bundle" + Integer.parseInt(bundleLabel));
	    return Integer.parseInt(bundleLabel);
	}
	else{
	    return 1;
	}
    }

    @Override
    public String toString() {
	if (bundleLabel != null) {
	    return bundleLabel;
	} else {
	    return "";
	}
    }
}
