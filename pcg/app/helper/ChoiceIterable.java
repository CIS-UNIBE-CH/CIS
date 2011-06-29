package helper;

import java.util.Iterator;

/**
 * A class providing an iterator over all choices of a certain number of
 * elements from a given set. For a set S with n = |S|, there are are
 * n!/(k!*(n-k)!) ways of choosing k elements from the set. This is the number
 * of possible samples when doing sampling without replacement. Example:<br />
 * 
 * <pre>
 * S = { A,B,C,D }, n = |S| = 4
 * k = 2
 * m = n!/(k!*(n-k)!) = 6
 * 
 * Choices:
 * [A, B]
 * [A, C]
 * [A, D]
 * [B, C]
 * [B, D]
 * [C, D]
 * </pre>
 * 
 * @author Marco13 (http://www.java-forum.org/members/8636.html)
 */
public class ChoiceIterable<String> implements Iterable<String[]> {
    private String input[];
    private int sampleSize;
    private int numElements;

    /**
     * Creates an iterable over all choices of 'sampleSize' elements taken from
     * the given array.
     * 
     * @param sampleSize
     * @param input
     */
    public ChoiceIterable(int sampleSize, String... input) {
	this.sampleSize = sampleSize;
	this.input = input.clone();

	numElements = factorial(input.length)
		/ (factorial(sampleSize) * factorial(input.length - sampleSize));
    }

    /**
     * Utility method for computing the factorial n! of a number n. The
     * factorial of a number n is n*(n-1)*(n-2)*...*1, or more formally:<br />
     * 0! = 1 <br />
     * 1! = 1 <br />
     * n! = n*(n-1)!<br />
     * 
     * @param n
     *            The number of which the factorial should be computed
     * @return The factorial, i.e. n!
     */
    public static int factorial(int n) {
	int f = 1;
	for (int i = 2; i <= n; i++) {
	    f *= i;
	}
	return f;
    }

    @Override
    public Iterator<String[]> iterator() {
	return new Iterator<String[]>() {
	    private int current = 0;
	    private int chosen[] = new int[sampleSize];

	    // Initialization of first choice
	    {
		for (int i = 0; i < sampleSize; i++) {
		    chosen[i] = i;
		}
	    }

	    @Override
	    public boolean hasNext() {
		return current < numElements;
	    }

	    @Override
	    public String[] next() {
		@SuppressWarnings("unchecked")
		String result[] = (String[]) java.lang.reflect.Array
			.newInstance(input.getClass().getComponentType(),
				sampleSize);
		for (int i = 0; i < sampleSize; i++) {
		    result[i] = input[chosen[i]];
		}
		current++;
		if (current < numElements) {
		    increase(sampleSize - 1, input.length - 1);
		}
		for (int i = 0; i < result.length; i++) {
		    System.out.println("********" + result[0]);
		}
		return result;
	    }

	    private void increase(int n, int max) {
		// The fist choice when choosing 3 of 5 elements consists
		// of 0,1,2. Subsequent choices are created by increasing
		// the last element of this sequence:
		// 0,1,3
		// 0,1,4
		// until the last element of the choice has reached the
		// maximum value. Then, the earlier elements of the
		// sequence are increased recursively, while obeying the
		// maximum value each element may have so that there may
		// still be values assigned to the subsequent elements.
		// For the example:
		// - The element with index 2 may have maximum value 4.
		// - The element with index 1 may have maximum value 3.
		// - The element with index 0 may have maximum value 2.
		// Each time that the value of one of these elements is
		// increased, the subsequent elements will simply receive
		// the subsequent values.
		if (chosen[n] < max) {
		    chosen[n]++;
		    for (int i = n + 1; i < sampleSize; i++) {
			chosen[i] = chosen[i - 1] + 1;
		    }
		} else {
		    increase(n - 1, max - 1);
		}
	    }

	    @Override
	    public void remove() {
		throw new UnsupportedOperationException(
			"May not remove elements from a choice");
	    }
	};
    }
}
