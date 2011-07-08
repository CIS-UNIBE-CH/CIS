package trees;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class CNATreeNode extends DefaultMutableTreeNode {
    private CNAList coincLine;

    public CNATreeNode(CNAList coincLine) {
	this.coincLine = coincLine;
    }

    /**
     * A care is digit in coincLine where a 1 or 0 is. A dont't care is a digit
     * with a $.
     */
    public boolean hasOneCare() {
	int counter = 0;
	for (int i = 0; i < coincLine.size(); i++) {
	    if (coincLine.get(i).equals("1") || coincLine.get(i).equals("0")
		    || (coincLine.get(i).length() > 1))
		counter++;
	}
	return counter == 1;
    }

    /**
     * A care is digit in coincLine where a 1 or 0 is. A dont't care is a digit
     * with a $.
     */
    public ArrayList<Integer> getCareIndexes() {
	ArrayList<Integer> places = new ArrayList<Integer>();
	for (int i = 0; i < coincLine.size(); i++) {
	    if (coincLine.get(i).equals("1") || coincLine.get(i).equals("0")
		    || (coincLine.get(i).length() > 1))
		places.add(i);
	}
	return places;
    }

    public CNAList getCoincLine() {
	return coincLine;
    }

    public void setEffectValue(String effectValue) {
	coincLine.add(effectValue);
    }

    @Override
    public String toString() {
	return coincLine.toString();
    }
}
