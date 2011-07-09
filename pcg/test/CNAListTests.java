import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import trees.CNAList;

public class CNAListTests {

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
	CNAList list2 = new CNAList(",", "1,2,3");
	assertEquals(list.toString(), list2.toString());
    }

    @Test
    public void shouldNegate() {
	list.negate();
	assertEquals("[0, 2, 3]", list.toString());
    }

    @Test
    public void shouldRemoveLastElement() {
	int newSize = list.size() - 1;
	list.removeLastElement();
	assertEquals(newSize, list.size());
	assertEquals("[0, 2]", list.toString());
    }
}
