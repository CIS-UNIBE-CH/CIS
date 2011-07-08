package trees;

import java.util.ArrayList;

public class CNAList extends ArrayList<String> {

    public CNAList() {
	super();
    }

    public void negate() {
	for (int k = 0; k < this.size(); k++) {
	    this.set(k, this.get(k).replace("1", "x"));
	    this.set(k, this.get(k).replace("0", "1"));
	    this.set(k, this.get(k).replace("x", "0"));
	}
    }

}
