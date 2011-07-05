package trees;

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
	    if (data.get(i).equals("1") || data.get(i).equals("0")
		    || (data.get(i).length() > 1))
		counter++;
	}
	return counter == 1;
    }

    public ArrayList<String> getData() {
	return data;
    }

    public ArrayList<Integer> getCareIndexes() {
	ArrayList<Integer> places = new ArrayList<Integer>();
	for (int i = 0; i < data.size(); i++) {
	    if (data.get(i).equals("1") || data.get(i).equals("0")
		    || (data.get(i).length() > 1))
		places.add(i);
	}
	return places;
    }

    public void setEffectValue(String effectValue) {
	data.add(effectValue);
    }

    @Override
    public String toString() {
	return data.toString();
    }
}
