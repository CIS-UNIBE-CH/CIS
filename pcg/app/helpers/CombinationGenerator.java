package helpers;

import java.util.Iterator;

/**
 * A class providing an iterator over all combinations of a certain number of
 * elements of a given set. For a set S with n = |S|, there are are n^k
 * combinations of k elements of the set. This is the number of possible samples
 * when doing sampling with replacement. Example:<br />
 * 
 * <pre>
 * S = { A,B,C }, n = |S| = 3
 * k = 2
 * m = n^k = 9
 * 
 * Combinations:
 * [A, A]
 * [A, B]
 * [A, C]
 * [B, A]
 * [B, B]
 * [B, C]
 * [C, A]
 * [C, B]
 * [C, C]
 * </pre>
 * 
 * @author Marco13 (http://www.java-forum.org/members/8636.html)
 */
public class CombinationGenerator<T> implements Iterable<T[]> {
    private T input[];
    private int sampleSize;
    private int numElements;

    /**
     * Creates an iterable over all multisets of 'sampleSize' elements of the
     * given array.
     * 
     * @param sampleSize
     * @param input
     */
    public CombinationGenerator(int sampleSize, T... input) {
	this.sampleSize = sampleSize;
	this.input = input.clone();
	numElements = (int) Math.pow(input.length, sampleSize);
    }

    @Override
    public Iterator<T[]> iterator() {
	return new Iterator<T[]>() {
	    private int current = 0;
	    private int chosen[] = new int[sampleSize];

	    @Override
	    public boolean hasNext() {
		return current < numElements;
	    }

	    @Override
	    public T[] next() {
		@SuppressWarnings("unchecked")
		T result[] = (T[]) java.lang.reflect.Array.newInstance(input
			.getClass().getComponentType(), sampleSize);
		for (int i = 0; i < sampleSize; i++) {
		    result[i] = input[chosen[i]];
		}
		increase();
		current++;
		return result;
	    }

	    private void increase() {
		// The array of 'chosen' elements for a set of size n
		// effectively is a number represented in k-ary form,
		// and thus, this method does nothing else than count.
		// For example, when choosing 2 elements of a set with
		// n=10, the contents of 'chosen' would represent all
		// values
		// 00, 01, 02,... 09,
		// 10, 11, 12,... 19,
		// ...
		// 90, 91, 92, ...99
		// with each digit indicating the index of the element
		// of the input array that should be placed at the
		// respective position of the output array.
		int index = chosen.length - 1;
		while (index >= 0) {
		    if (chosen[index] < input.length - 1) {
			chosen[index]++;
			return;
		    } else {
			chosen[index] = 0;
			index--;
		    }
		}
	    }

	    @Override
	    public void remove() {
		throw new UnsupportedOperationException(
			"May not remove elements from a combination");
	    }
	};
    }
}
