package datastructures;

import org.junit.BeforeClass;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.cna.CNAList;

public class CNAListTests extends UnitTest {

    private static CNAList list;

    @BeforeClass
    public static void setup() {
	list = new CNAList();
	list.add("1");
	list.add("2");
	list.add("3");
    }

    @Test
    public void shouldTestRegex() {
	CNAList list2 = new CNAList(',', "1,2,3");
	assertEquals(list.toString(), list2.toString());
    }

    @Test
    public void shouldNegate() {
	list.negate();
	assertEquals("0 2 3 ", list.toString());
    }

    @Test
    public void shouldRemoveLastElement() {
	int newSize = list.size() - 1;
	list.removeLastElement();
	assertEquals(newSize, list.size());
	assertEquals("0 2 ", list.toString());
    }

    @Test
    public void shouldSwap() {
	CNAList list = new CNAList(',', "1,2,3");
	list.swap(1, 2);
	assertEquals("1 3 2 ", list.toString());
	list.swap(0, 2);
	assertEquals("2 3 1 ", list.toString());
    }
}
