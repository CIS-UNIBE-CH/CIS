package trees;

import java.util.ArrayList;

public class CNAList extends ArrayList<String> {

    public CNAList() {
	super();
    }

    public CNAList(String regex, String string) {
	String[] array = string.split(regex);
	for (int i = 0; i < array.length; i++) {
	    this.add(array[i]);
	}

    }

    public void negate() {
	for (int k = 0; k < this.size(); k++) {
	    this.set(k, this.get(k).replace("1", "x"));
	    this.set(k, this.get(k).replace("0", "1"));
	    this.set(k, this.get(k).replace("x", "0"));
	}
    }

    public void removeLastElement() {
	this.remove(this.getLastElement());
    }

    // Getters and Setters

    public String getLastElement() {
	return this.get(this.size() - 1);
    }

}
