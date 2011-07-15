package datastructures.mt;

import java.util.ArrayList;

public class MinimalTheorieSet extends ArrayList<MinimalTheorie> {

    private ArrayList<MinimalTheorie> theories;

    public MinimalTheorieSet() {
	theories = new ArrayList<MinimalTheorie>();
    }

    @Override
    public boolean add(MinimalTheorie theorie) {
	return theories.add(theorie);
    }

    @Override
    public int size() {
	return theories.size();
    }

    @Override
    public MinimalTheorie remove(int arg0) {
	return theories.remove(arg0);
    }

    // Getters and Setters

    @Override
    public MinimalTheorie get(int arg0) {
	return theories.get(arg0);
    }

}
