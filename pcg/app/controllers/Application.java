package controllers;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import helper.Timer;

import java.util.ArrayList;

import models.RandomGraphGenerator;
import models.Renderer;
import parser.TreeToGraph;
import play.mvc.Controller;
import trees.CustomTree;
import algorithms.BinaryTest;
import algorithms.BooleanTest;
import algorithms.QuadroTest;

public class Application extends Controller {

    private static CustomTree tree = null;
    private static Renderer renderer;
    private static String generatedGraphPath = "";
    private static String generatedGraph = "";
    private static String calculatedGraphPath = "";
    private static String calculatedGraph = "";
    private static ArrayList<String> table;
    private static RandomGraphGenerator generator;
    private static Timer timer;

    public static void index() {
	render();
    }

    public static void calcBooleanGraph() {
	timer = new Timer();
	renderer = new Renderer();
	BooleanTest booleanTest = new BooleanTest(generator.getTableAsArray());
	tree = booleanTest.getCnaTree();
	Long time = timer.timeElapsed();
	renderer.config(new TreeToGraph(tree));
	calculatedGraph = tree.toString();
	calculatedGraphPath = renderer.getImageSource();
	String elapsedTime = time.toString() + " ms";
	complexTesting(2, false, null, generatedGraphPath, generatedGraph,
		calculatedGraphPath, calculatedGraph, "Boolean Test",
		elapsedTime);
    }

    public static void quadroTest(int step, String f1, String f2) {
	render(step, f1, f2);
    }

    public static void calcBinGraph() {
	timer = new Timer();
	renderer = new Renderer();
	BinaryTest binaryTest = new BinaryTest(generator.getTableAsArray());
	tree = binaryTest.createTree();
	Long time = timer.timeElapsed();
	renderer.config(new TreeToGraph(tree));
	calculatedGraph = tree.toString();
	calculatedGraphPath = renderer.getImageSource();
	String elapsedTime = time.toString() + " ms";
	complexTesting(2, false, null, generatedGraphPath, generatedGraph,
		calculatedGraphPath, calculatedGraph, "Binary Test",
		elapsedTime);
    }

    public static void complexTesting(int step, boolean showTable,
	    ArrayList<String> table, String generatedGraphPath,
	    String generatedGraph, String calculatedGraphPath,
	    String calculatedGraph, String algorithmName, String time) {

	render(step, showTable, table, generatedGraphPath, generatedGraph,
		calculatedGraphPath, calculatedGraph, time, algorithmName);
    }

    public static void generateGraph(String numberOfFactors,
	    String numberOfBundles, String sizeOfBundles, String showBundleNum,
	    String showColour) {

	// Generate a Graph with n bundles and a total of k factors
	int numBundles = Integer.parseInt(numberOfBundles);
	int numFactors = Integer.parseInt(numberOfFactors);
	int sizeBundles = Integer.parseInt(sizeOfBundles);
	renderer = new Renderer();
	table = new ArrayList<String>();

	renderer.setEdgeLabels(showBundleNum != null);
	renderer.setChangingVertexColors(showColour != null);

	if (numFactors >= (2 * numBundles) && numFactors <= 12) {
	    timer = new Timer();
	    generator = new RandomGraphGenerator(numBundles, numFactors,
		    sizeBundles);
	    generatedGraph = generator.getTree().toString();
	    Long time = timer.timeElapsed();
	    table = generator.getTable();
	    renderer.config(new TreeToGraph(generator.getTree()));
	    generatedGraphPath = renderer.getImageSource();

	    String elapsedTime = time.toString() + " ms";

	    if (numFactors <= 5) {
		complexTesting(1, true, table, generatedGraphPath,
			generatedGraph, " ", " ", " ", elapsedTime);

	    } else {
		complexTesting(1, false, null, generatedGraphPath,
			generatedGraph, "", "", "", elapsedTime);
	    }

	} else {
	    flash.error("Sorry it was not posible to generate a graph, the numbers of factros must be grater than twice as much of the number of bundls.");
	    params.flash();
	    complexTesting(0, false, null, generatedGraphPath, generatedGraph,
		    "", "", "", "");
	}
    }

    public static void calccalcEQuadroGraph() {
    }

    public static void calcEQuadroGraph() {
    }

    public static void calcAllGraph() {
    }

    public static void calcQuadroGraph(String f1, String f2, String i,
	    String ii, String l, String ll) {
	int[][] field = new int[2][2];
	int[] numbers = new int[4];

	try {
	    numbers[0] = Integer.parseInt(i);
	    numbers[1] = Integer.parseInt(ii);
	    numbers[2] = Integer.parseInt(l);
	    numbers[3] = Integer.parseInt(ll);
	} catch (Exception e) {
	    flash.error(e.toString());
	    params.flash();
	}
	if (numbers[0] < 0 || numbers[0] > 1 || numbers[1] < 0
		|| numbers[1] > 1 || numbers[2] < 0 || numbers[2] > 1
		|| numbers[3] < 0 || numbers[3] > 1) {
	    flash.error("Please insert only 1 or 0.");
	    params.flash();
	    quadroTest(1, f1, f2);
	}

	field[0][0] = numbers[0];
	field[0][1] = numbers[1];
	field[1][0] = numbers[2];
	field[1][1] = numbers[3];

	QuadroTest quadroTest = new QuadroTest(field, f1, f2);
	tree = quadroTest.creatGraph();

	if (tree == null) {
	    flash.error("Sorry it was not posible to calculate a graph with your data. For more information (click here)");
	    params.flash();
	    quadroTest(1, f1, f2);
	}
	Renderer renderer = new Renderer();

	renderer.config(new TreeToGraph(tree));
	String graphPath = renderer.getImageSource();
	showGraph(graphPath);
    }

    public static void showGraph(String graphPath) {
	String stringGraph = tree.toString();
	render(graphPath, stringGraph);
    }
}