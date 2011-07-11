package controllers;

import helpers.Timer;

import java.util.ArrayList;

import models.RandomGraphGenerator;
import models.Renderer;
import parsers.StringToTree;
import parsers.TreeToGraph;
import play.mvc.Controller;
import algorithms.CNAlgorithm;

public class CNAController extends Controller {

    private static Renderer renderer;
    private static RandomGraphGenerator generator;
    private static Timer timer;
    private static boolean showColourRenderer;
    private static boolean showBundleNumRenderer;

    public static void setup() {

	render();
    }

    public static void generateGraph(String numberOfAlterFactors,
	    String numberOfBundles, String sizeOfBundles, String showColour,
	    String showBundleNum) {
	showColourRenderer = (showColour != null);
	showBundleNumRenderer = (showBundleNum != null);

	// Generate a Graph with n bundles and a total of k factors
	int numBundles = Integer.parseInt(numberOfBundles);
	int numOfAlterFactors = Integer.parseInt(numberOfAlterFactors);
	int sizeBundles = Integer.parseInt(sizeOfBundles);
	int numFactors = numOfAlterFactors + (numBundles * sizeBundles);
	renderer = new Renderer();
	ArrayList<String> table = new ArrayList<String>();

	renderer.setEdgeLabels(showBundleNumRenderer);
	renderer.setChangingVertexColors(showColourRenderer);

	if (numFactors >= (2 * numBundles) && numFactors <= 12) {
	    timer = new Timer();
	    generator = new RandomGraphGenerator(numBundles, numFactors,
		    sizeBundles);
	    String generatedGraph = generator.getTree().toString();
	    Long time = timer.timeElapsed();
	    table = generator.getTable();
	    renderer.config(new TreeToGraph(generator.getTree()));
	    String generatedGraphPath = renderer.getImageSource();

	    String elapsedTime = time.toString() + " ms";

	    if (numFactors <= 5) {
		render(elapsedTime, generatedGraphPath, generatedGraph, table);

	    } else {
		render(elapsedTime, generatedGraphPath, generatedGraph);
	    }

	} else {
	    flash.error("Sorry it was not posible to generate a graph, the numbers of factros must be grater than twice as much of the number of bundls.");
	    params.flash();
	    setup();
	}
    }

    public static void calcCNAGraph(String generatedGraphPath,
	    String generatedGraph) {
	timer = new Timer();
	renderer = new Renderer();
	renderer.setEdgeLabels(showBundleNumRenderer);
	renderer.setChangingVertexColors(showColourRenderer);
	CNAlgorithm cnaAlgorithm = new CNAlgorithm(generator.getTableAsArray());
	StringToTree stringToTree = new StringToTree(cnaAlgorithm.getFmt().get(
		0));
	Long time = timer.timeElapsed();
	TreeToGraph treeToGraph = new TreeToGraph(stringToTree.getTree());
	renderer.config(treeToGraph);
	String calculatedGraph = stringToTree.getTree().toString();
	String calculatedGraphPath = renderer.getImageSource();
	String elapsedTime = time.toString() + " ms";
	render(elapsedTime, calculatedGraph, calculatedGraphPath,
		generatedGraphPath, generatedGraph);
    }
}
