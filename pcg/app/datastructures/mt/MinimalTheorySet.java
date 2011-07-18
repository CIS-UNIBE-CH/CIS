package datastructures.mt;

import java.util.ArrayList;

public class MinimalTheorySet extends ArrayList<MinimalTheory> {

    private ArrayList<MinimalTheory> theories;

    public MinimalTheorySet() {
	theories = new ArrayList<MinimalTheory>();
    }

    @Override
    public boolean add(MinimalTheory theory) {
	return theories.add(theory);
    }

    @Override
    public int size() {
	return theories.size();
    }

    @Override
    public MinimalTheory remove(int index) {
	return theories.remove(index);
    }

    // Getters and Setters
    @Override
    public MinimalTheory get(int index) {
	return theories.get(index);
    }
}
