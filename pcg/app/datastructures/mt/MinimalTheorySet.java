package datastructures.mt;

import java.util.ArrayList;

public class MinimalTheorySet extends ArrayList<MinimalTheory> {

    private ArrayList<MinimalTheory> theories;

    public MinimalTheorySet() {
	theories = new ArrayList<MinimalTheory>();
    }

    @Override
    public boolean add(MinimalTheory theorie) {
	return theories.add(theorie);
    }

    @Override
    public int size() {
	return theories.size();
    }

    @Override
    public MinimalTheory remove(int arg0) {
	return theories.remove(arg0);
    }

    // Getters and Setters

    @Override
    public MinimalTheory get(int arg0) {
	return theories.get(arg0);
    }

}
