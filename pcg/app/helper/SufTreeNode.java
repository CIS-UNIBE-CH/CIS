package helper;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

public class SufTreeNode extends DefaultMutableTreeNode {

    private ArrayList<String> data;

    public SufTreeNode(ArrayList<String> data) {
	this.data = data;
    }

    public boolean hasOneCare() {
	int counter = 0;
	for (int i = 0; i < data.size(); i++) {
	    if (data.get(i).equals("1") || data.get(i).equals("0"))
		counter++;
	}
	return counter == 1;
    }

    public ArrayList<String> getData() {
	return data;
    }

    public ArrayList<Integer> getCarePlace() {
	ArrayList<Integer> places = new ArrayList<Integer>();
	for (int i = 0; i < data.size(); i++) {
	    if (data.get(i).equals("1") || data.get(i).equals("0"))
		places.add(i);
	}
	return places;
    }

    public String toString() {
	return data.toString();
    }
}
