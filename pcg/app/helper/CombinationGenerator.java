package helper;

/** Souce an (C) by: http://big-o.nl/demos/math/combinationgenerator/index.html*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CombinationGenerator<E> implements Iterator<ArrayList<E>>,
	Iterable<ArrayList<E>> {

    private final ArrayList<E> set;
    private int[] currentIdxs;
    private final int[] lastIdxs;

    public CombinationGenerator(ArrayList<E> set, int r) {
	if (r < 1 || r > set.size()) {
	    throw new IllegalArgumentException("r < 1 || r > set.size()");
	}
	this.set = new ArrayList<E>(set);
	this.currentIdxs = new int[r];
	this.lastIdxs = new int[r];
	for (int i = 0; i < r; i++) {
	    this.currentIdxs[i] = i;
	    this.lastIdxs[i] = set.size() - r + i;
	}
    }

    @Override
    public boolean hasNext() {
	return currentIdxs != null;
    }

    @Override
    public Iterator<ArrayList<E>> iterator() {
	return this;
    }

    @Override
    public ArrayList<E> next() {
	if (!hasNext()) {
	    throw new NoSuchElementException();
	}
	ArrayList<E> currentCombination = new ArrayList<E>();
	for (int i : currentIdxs) {
	    currentCombination.add(set.get(i));
	}
	setNextIndexes();
	return currentCombination;
    }

    @Override
    public void remove() {
	throw new UnsupportedOperationException();
    }

    private void setNextIndexes() {
	for (int i = currentIdxs.length - 1, j = set.size() - 1; i >= 0; i--, j--) {
	    if (currentIdxs[i] != j) {
		currentIdxs[i]++;
		for (int k = i + 1; k < currentIdxs.length; k++) {
		    currentIdxs[k] = currentIdxs[k - 1] + 1;
		}
		return;
	    }
	}
	currentIdxs = null;
    }
}