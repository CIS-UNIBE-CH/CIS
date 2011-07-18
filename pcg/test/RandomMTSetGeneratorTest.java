/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;

import models.RandomMTSetGenerator;

import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;
import datastructures.mt.MinimalTheorySet;

public class RandomMTSetGeneratorTest extends UnitTest {
    private RandomMTSetGenerator generator;
    ArrayList<ArrayList<Object>> test;

    @Before
    public void setup() {
	test = new ArrayList<ArrayList<Object>>();
	ArrayList<Integer> bundleSizes = new ArrayList<Integer>();
	for (int i = 0; i < 2; i++) {
	    bundleSizes.add(2);
	}
	Integer alterfactNumber = 2;
	ArrayList<Object> level = new ArrayList<Object>();
	level.add(bundleSizes);
	level.add(alterfactNumber);
	ArrayList<Object> level1 = new ArrayList<Object>();
	level1.add(bundleSizes);
	level1.add(alterfactNumber);
	test.add(level);
	test.add(level1);
	// System.out.println("Test" + test);
    }

    @Test
    public void shouldCreateFactorNames() {
	generator = new RandomMTSetGenerator();
	generator.generateFactorNames();

	assertEquals("[A, ¬A]", generator.getAllNames().get(0).toString());
	assertEquals("[W, ¬W]",
		generator.getAllNames().get(generator.getAllNames().size() - 1)
			.toString());
	assertEquals(23, generator.getAllNames().size());
    }

    @Test
    public void shouldCreateRandomNumbers() {
	generator = new RandomMTSetGenerator();

	for (int i = 0; i < 300; i++) {
	    int random = generator.randomSecond();
	    assertTrue(random == 0 || random == 1);
	}
	for (int j = 0; j < 50 * 10; j++) {
	    int random = generator.random(50);
	    assertTrue(random >= 0 && random < 50);
	}
    }

    @Test
    public void shouldCreateMTTheorie() {
	for (int i = 0; i < 300; i++) {
	    RandomMTSetGenerator generator = new RandomMTSetGenerator(test);
	    MinimalTheorySet set = generator.getSet();

	    ArrayList<Integer> list = (ArrayList<Integer>) test.get(0).get(0);
	    int k = (Integer) test.get(0).get(1);
	    assertEquals(list.size() + k, set.get(0).getFactors().size());

	    ArrayList<Integer> list1 = (ArrayList<Integer>) test.get(0).get(0);
	    int k1 = (Integer) test.get(0).get(1);
	    assertEquals(list1.size() + k1, set.get(0).getFactors().size());
	}
    }

    public void shouldMakeChain() {
	for (int k = 0; k < 5; k++) {
	    ArrayList<Integer> bundleSizes = new ArrayList<Integer>();
	    for (int i = 0; i < 2; i++) {
		bundleSizes.add(2);
	    }
	    Integer alterfactNumber = 2;
	    ArrayList<Object> level = new ArrayList<Object>();
	    level.add(bundleSizes);
	    level.add(alterfactNumber);
	    test.clear();
	    test.add(level);
	}
	RandomMTSetGenerator generator = new RandomMTSetGenerator(test);
	MinimalTheorySet set = generator.getSet();
	
	assertEquals(5, set.size());
	
	for (int i = 0; i < set.size(); i++) {
	    String effectPrev = set.get(i).getEffect();
	    if (i < set.size() - 1) {
		String current = set.get(i + 1).toString();
		assertTrue(current.contains(effectPrev));
	    }
	}
    }

}
