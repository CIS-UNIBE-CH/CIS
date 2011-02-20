package util;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Some frequently requested utilities for combinatorics.
 * 
 * @author Marco13 (http://www.java-forum.org/members/8636.html)
 */
public class Combinatorics {
	public static void main(String args[]) {
		testPermutations(3);
		testSequenceWithoutRepetitions(4, 2);
		testChoices(4, 2);
		testCombinations(2, 2);
		testUnorderedCombinations(4, 2);
		testPowerSet(3);
	}

	/**
	 * Creates the sample input for the tests
	 * 
	 * @param n
	 *            The size of the input
	 * @return The input for the tests
	 */
	private static String[] createInput(int n) {
		String input[] = new String[n];
		for (int i = 0; i < n; i++) {
			input[i] = String.valueOf((char) ('A' + i));
		}
		return input;
	}

	private static void testPermutations(int n) {
		String input[] = createInput(n);
		PermutationIterable<String> pi = new PermutationIterable<String>(input);
		System.out.println("Permutations of " + n + " elements:");
		for (String s[] : pi) {
			System.out.println(Arrays.toString(s));
		}
	}

	private static void testSequenceWithoutRepetitions(int n, int k) {
		String input[] = createInput(n);
		SequenceWithoutRepetitionIterable<String> si = new SequenceWithoutRepetitionIterable<String>(
				k, input);
		System.out.println("Sequences without repetitions of length " + k
				+ " taken from a set of " + n + " elements");
		for (String s[] : si) {
			System.out.println(Arrays.toString(s));
		}
	}

	private static void testChoices(int n, int k) {
		String input[] = createInput(n);
		ChoiceIterable<String> chi = new ChoiceIterable<String>(k, input);
		System.out.println("Choices of " + k + " elements taken "
				+ "from a set of " + n + " elements");
		for (String s[] : chi) {
			System.out.println(Arrays.toString(s));
		}
	}

	private static void testCombinations(int n, int k) {
		String input[] = createInput(n);
		CombinationIterable<String> ci = new CombinationIterable<String>(k,
				input);
		System.out.println("Combinations of " + k + " elements taken "
				+ "from a set of " + n + " elements");
		for (String s[] : ci) {
			System.out.println(Arrays.toString(s));
		}
	}

	private static void testUnorderedCombinations(int n, int k) {
		String input[] = createInput(n);
		UnorderedCombinationIterable<String> uci = new UnorderedCombinationIterable<String>(
				k, input);
		System.out.println("Combinations of " + k + " elements taken "
				+ "from a set of " + n + " elements,");
		System.out.println("ignoring the order of the selected elements");
		for (String s[] : uci) {
			System.out.println(Arrays.toString(s));
		}
	}

	private static void testPowerSet(int n) {
		String input[] = createInput(n);
		PowerSetIterable<String> psi = new PowerSetIterable<String>(input);
		System.out.println("Power set of a set with " + n + " elements");
		for (String s[] : psi) {
			System.out.println(Arrays.toString(s));
		}

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

	/**
	 * A magic utility method that happens to return the number of bits that are
	 * set to '1' in the given number.
	 * 
	 * @param n
	 *            The number whose bits should be counted
	 * @return The number of bits that are '1' in n
	 */
	public static int countBits(int n) {
		int m = n - ((n >> 1) & 033333333333) - ((n >> 2) & 011111111111);
		return ((m + (m >> 3)) & 030707070707) % 63;
	}

}

/**
 * A class providing an iterator over all permutations of a set of elements. For
 * a set S with n=|S|, there are m=n! different permutations. Example: <br />
 * 
 * <pre>
 * S = { A,B,C }, n = |S| = 3
 * m = n! = 6
 * 
 * Permutations:
 * [A, B, C]
 * [A, C, B]
 * [B, A, C]
 * [B, C, A]
 * [C, A, B]
 * [C, B, A]
 * </pre>
 * 
 * @author Marco13 (http://www.java-forum.org/members/8636.html)
 */
class PermutationIterable<T> implements Iterable<T[]> {
	private T input[];
	private int numPermutations = 0;

	public PermutationIterable(T... input) {
		this.input = input.clone();
		numPermutations = Combinatorics.factorial(input.length);
	}

	public Iterator<T[]> iterator() {
		return new Iterator<T[]>() {
			int current = 0;

			public boolean hasNext() {
				return current < numPermutations;
			}

			// Adapted from
			// [url=http://en.wikipedia.org/wiki/Permutation]Permutation -
			// Wikipedia, the free encyclopedia[/url]
			public T[] next() {
				T result[] = input.clone();
				int factorial = numPermutations / input.length;
				for (int i = 0; i < result.length - 1; i++) {
					int tempIndex = (current / factorial) % (result.length - i);
					T temp = result[i + tempIndex];
					for (int j = i + tempIndex; j > i; j--) {
						result[j] = result[j - 1];
					}
					result[i] = temp;
					factorial /= (result.length - (i + 1));
				}
				current++;
				return result;
			}

			public void remove() {
				throw new UnsupportedOperationException(
						"May not remove elements from a permutation");
			}
		};
	}
}

/**
 * A class providing an iterator over all sequences without repetition of a
 * certain number of elements taken from a set. For a set S with n=|S|, there
 * are m=n!/(n-k)! different sequences of k elements without repetition.
 * Example: <br />
 * 
 * <pre>
 * S = { A,B,C,D }, n = |S| = 4
 * k = 2
 * m = n!/(n-k)! = 12
 * 
 * Sequences without repetition:
 * [A, B]
 * [B, A]
 * [A, C]
 * [C, A]
 * [A, D]
 * [D, A]
 * [B, C]
 * [C, B]
 * [B, D]
 * [D, B]
 * [C, D]
 * [D, C]
 * </pre>
 * 
 * @author Marco13 (http://www.java-forum.org/members/8636.html)
 */
class SequenceWithoutRepetitionIterable<T> implements Iterable<T[]> {
	private T input[];
	private int sampleSize;

	public SequenceWithoutRepetitionIterable(int sampleSize, T... input) {
		this.input = input.clone();
		this.sampleSize = sampleSize;
	}

	public Iterator<T[]> iterator() {
		return new Iterator<T[]>() {
			// Sequences without repetition may be constructed
			// by iterating over all permutations of all
			// choices of the given number of elements.

			Iterator<T[]> choiceIterator = new ChoiceIterable<T>(sampleSize,
					input).iterator();
			Iterator<T[]> permutationIterator = null;

			public boolean hasNext() {
				if (permutationIterator != null
						&& permutationIterator.hasNext()) {
					return true;
				}
				if (choiceIterator.hasNext()) {
					T nextChoice[] = choiceIterator.next();
					permutationIterator = new PermutationIterable<T>(nextChoice)
							.iterator();
				}
				return permutationIterator.hasNext();
			}

			public T[] next() {
				if (permutationIterator == null) {
					T nextChoice[] = choiceIterator.next();
					permutationIterator = new PermutationIterable<T>(nextChoice)
							.iterator();
				}
				return permutationIterator.next();
			}

			public void remove() {
				throw new UnsupportedOperationException(
						"May not remove elements from a "
								+ "SequenceWithoutRepetitionIterable");
			}
		};
	}
}

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
class ChoiceIterable<T> implements Iterable<T[]> {
	private T input[];
	private int sampleSize;
	private int numElements;

	/**
	 * Creates an iterable over all choices of 'sampleSize' elements taken from
	 * the given array.
	 * 
	 * @param sampleSize
	 * @param input
	 */
	public ChoiceIterable(int sampleSize, T... input) {
		this.sampleSize = sampleSize;
		this.input = input.clone();

		numElements = Combinatorics.factorial(input.length)
				/ (Combinatorics.factorial(sampleSize) * Combinatorics
						.factorial(input.length - sampleSize));
	}

	public Iterator<T[]> iterator() {
		return new Iterator<T[]>() {
			private int current = 0;
			private int chosen[] = new int[sampleSize];

			// Initialization of first choice
			{
				for (int i = 0; i < sampleSize; i++) {
					chosen[i] = i;
				}
			}

			public boolean hasNext() {
				return current < numElements;
			}

			public T[] next() {
				@SuppressWarnings("unchecked")
				T result[] = (T[]) java.lang.reflect.Array.newInstance(input
						.getClass().getComponentType(), sampleSize);
				for (int i = 0; i < sampleSize; i++) {
					result[i] = input[chosen[i]];
				}
				current++;
				if (current < numElements) {
					increase(sampleSize - 1, input.length - 1);
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

			public void remove() {
				throw new UnsupportedOperationException(
						"May not remove elements from a choice");
			}
		};
	}
}

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
class CombinationIterable<T> implements Iterable<T[]> {
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
	public CombinationIterable(int sampleSize, T... input) {
		this.sampleSize = sampleSize;
		this.input = input.clone();
		numElements = (int) Math.pow(input.length, sampleSize);
	}

	public Iterator<T[]> iterator() {
		return new Iterator<T[]>() {
			private int current = 0;
			private int chosen[] = new int[sampleSize];

			public boolean hasNext() {
				return current < numElements;
			}

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

			public void remove() {
				throw new UnsupportedOperationException(
						"May not remove elements from a combination");
			}
		};
	}
}

/**
 * A class providing an iterator over all combinations of a certain number of
 * elements from a given set, ignoring the order of the elements. For a set S
 * with n = |S|, there are are (n+k-1)/(k!*(n-1)!) ways of choosing k elements
 * from the set when the order of the elements in the resulting set should be
 * ignored. This is, for example, the number of distinct results when throwing k
 * dices with n sides. Example:<br />
 * 
 * <pre>
 * S = { A,B,C,D }, n = |S| = 4
 * k = 2
 * m = (n+k-1)/(k!*(n-1)!) = 10
 * 
 * Unordered combinations of length 2:
 * [A, A]
 * [A, B]
 * [A, C]
 * [A, D]
 * [B, B]
 * [B, C]
 * [B, D]
 * [C, C]
 * [C, D]
 * [D, D]
 * </pre>
 * 
 * @author Marco13 (http://www.java-forum.org/members/8636.html)
 */
class UnorderedCombinationIterable<T> implements Iterable<T[]> {
	private T input[];
	private int length;
	private int numElements;
	private Integer positions[];

	public UnorderedCombinationIterable(int length, T... input) {
		this.length = length;
		this.input = input.clone();

		numElements = Combinatorics.factorial(input.length + length - 1)
				/ (Combinatorics.factorial(length) * Combinatorics
						.factorial(input.length - 1));

		int numPositions = length + input.length - 1;
		positions = new Integer[numPositions];
		for (int i = 0; i < numPositions; i++) {
			positions[i] = i;
		}
	}

	public Iterator<T[]> iterator() {
		return new Iterator<T[]>() {
			private int current = 0;
			private Iterator<Integer[]> positionChoiceIterator;

			// Initialization of the positionChoiceIterator
			{
				ChoiceIterable<Integer> positionChoiceIterable = new ChoiceIterable<Integer>(
						length, positions);
				positionChoiceIterator = positionChoiceIterable.iterator();
			}

			public boolean hasNext() {
				return current < numElements;
			}

			public T[] next() {
				current++;
				return toSelection(positionChoiceIterator.next());
			}

			private T[] toSelection(Integer positionChoice[]) {
				// Selecting k elements from n elements, ignoring the
				// order of the selected elements, may be formulated
				// as selecting k elements from (n+k-1) elements
				// obeying the order.
				// The positionChoiceIterator provides choices of k
				// elements from (n+k-1) elements, and they are converted
				// to the corresponding selection of elements here.
				//
				// For example:
				// 4 elements should be selected from a set of 6 elements,
				// and the order of the selected elements is not relevant
				// (like when rolling 4 six-sided dices).
				// The positionChoiceIterator will be used for choosing
				// 4 elements from (6+4-1)=9 elements. If it provides the
				// choice 0,3,4,7 then this will be translated into a
				// selection in the following way:
				//
				// Positions array : 012345678
				// Choice done by positionChoiceIterator : 0 34 7
				// For interpreting this choice, place
				// asterisks at the respective positions
				// filling the remaining positions with
				// consecutive numbers : *12**34*5
				// This pattern can then be interpreted as...
				// - take 0 once
				// - take 2 twice
				// - take 4 once
				// resulting the the selection 0,2,2,4

				@SuppressWarnings("unchecked")
				T result[] = (T[]) java.lang.reflect.Array.newInstance(input
						.getClass().getComponentType(), length);

				int currentValue = 0;
				int currentIndex = 0;
				for (int x = 0; x < positions.length; x++) {
					if (x == positionChoice[currentIndex]) {
						result[currentIndex] = input[currentValue];
						currentIndex++;
					} else {
						currentValue++;
					}
					if (currentIndex >= positionChoice.length) {
						break;
					}
				}
				return result;
			}

			public void remove() {
				throw new UnsupportedOperationException(
						"May not remove elements from a combination");
			}
		};
	}
}

/**
 * A class providing an iterator over all elements of the power set of a given
 * set of elements. <br />
 * <br />
 * The power set of a set S is the set of all subsets of S. For a set with n=|S|
 * elements, the power set contains m=2^n elements, each being one possible
 * subset of S. These elements are computed from the bit patterns of the binary
 * representations of the numbers i=0 ... m-1: When the bit number b in the
 * binary representation of i is set, then the b'th element of the input array
 * is put into the respective subset. Example: <br />
 * 
 * <pre>
 * S = { A,B,C }, n = |S| = 3
 * m = 2^n = 8
 * 
 * Elements of the power set:
 * i = 0, binary: 000, element: {     }
 * i = 1, binary: 001, element: {A    }
 * i = 2, binary: 010, element: {  B  }
 * i = 3, binary: 011, element: {A,B  }
 * i = 4, binary: 100, element: {    C}
 * i = 5, binary: 101, element: {A,  C}
 * i = 6, binary: 110, element: {  B,C}
 * i = 7, binary: 111, element: {A,B,C}
 * </pre>
 * 
 * @author Marco13 (http://www.java-forum.org/members/8636.html)
 */
class PowerSetIterable<T> implements Iterable<T[]> {
	private T input[];
	private int numElements;

	public PowerSetIterable(T... input) {
		this.input = input.clone();
		numElements = 1 << input.length;
	}

	public Iterator<T[]> iterator() {
		return new Iterator<T[]>() {
			int current = 0;

			public boolean hasNext() {
				return current < numElements;
			}

			public T[] next() {
				int size = Combinatorics.countBits(current);

				@SuppressWarnings("unchecked")
				T element[] = (T[]) java.lang.reflect.Array.newInstance(input
						.getClass().getComponentType(), size);

				// Insert into the current power set element
				// all elements of the input set that are at
				// indices where the current counter value
				// has a '1' in its binary representation
				int n = 0;
				for (int i = 0; i < input.length; i++) {
					long b = 1 << i;
					if ((current & b) != 0) {
						element[n++] = input[i];
					}
				}
				current++;
				return element;
			}

			public void remove() {
				throw new UnsupportedOperationException(
						"May not remove elements from a power set");
			}
		};

	}
}