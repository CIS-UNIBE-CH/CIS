package controllers;

import helpers.BaumgartnerSampleTable;
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
	    String showBundleNum, String lines) {
	System.out.println(lines);
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
	    renderer.config(new TreeToGraph(generator.getTree(), 1, generator.getTotalFactors()));
	    String generatedGraphPath = renderer.getImageSource();

	    String elapsedTime = time.toString() + " ms";

	    if (numFactors <= 5) {
		render(elapsedTime, generatedGraphPath, generatedGraph, table,
			lines);

	    } else {
		render(elapsedTime, generatedGraphPath, generatedGraph, lines);
	    }

	} else {
	    flash.error("Sorry it was not posible to generate a graph, the numbers of factros must be grater than twice as much of the number of bundls.");
	    params.flash();
	    setup();
	}
    }

    public static void calcCNAGraph(String generatedGraphPath,
	    String generatedGraph, String lines) {
	timer = new Timer();
	renderer = new Renderer();
	int randomLines = Integer.parseInt(lines);
	renderer.setEdgeLabels(showBundleNumRenderer);
	renderer.setChangingVertexColors(showColourRenderer);
	CNAlgorithm cnaAlgorithm = new CNAlgorithm(generator.getTableAsArray(),
		randomLines);

	StringToTree stringToTree = new StringToTree(cnaAlgorithm.getFmt());
	Long time = timer.timeElapsed();
	TreeToGraph treeToGraph = new TreeToGraph(stringToTree.getTree(), stringToTree.getNumOfEffects(), stringToTree.getTotalFactors());
	renderer.config(treeToGraph);

	String originalTable = cnaAlgorithm.getOriginalTable().toString();
	String calculatedGraph = stringToTree.getTree().toString();
	String calculatedGraphPath = renderer.getImageSource();
	String elapsedTime = time.toString() + " ms";
	String effects = cnaAlgorithm.getEffects().toString();
	String sufTable = cnaAlgorithm.getSufTable().toString();
	String msufTable = cnaAlgorithm.getMsufTable().toString();
	String necList = cnaAlgorithm.getNecList().toString();
	String mnecTable = cnaAlgorithm.getMnecTable().toString();
	String fmt = cnaAlgorithm.getFmt().toString();
	String deleted = cnaAlgorithm.getDeleted().toString();

	render(elapsedTime, originalTable, calculatedGraph,
		calculatedGraphPath, generatedGraphPath, generatedGraph,
		effects, sufTable, msufTable, necList, mnecTable, fmt, deleted);
    }

    public static void baumgartnerSample() {
	timer = new Timer();
	renderer = new Renderer();
	renderer.setEdgeLabels(true);
	renderer.setChangingVertexColors(true);
	CNAlgorithm cnaAlgorithm = new CNAlgorithm(
		new BaumgartnerSampleTable().getSampleTable());
	StringToTree stringToTree = new StringToTree(cnaAlgorithm.getFmt());
	Long time = timer.timeElapsed();
	TreeToGraph treeToGraph = new TreeToGraph(stringToTree.getTree(), stringToTree.getNumOfEffects(), stringToTree.getTotalFactors());
	renderer.config(treeToGraph);

	String calculatedGraph = stringToTree.getTree().toString();
	String calculatedGraphPath = renderer.getImageSource();
	String elapsedTime = time.toString() + " ms";
	String effects = cnaAlgorithm.getEffects().toString();
	String sufTable = cnaAlgorithm.getSufTable().toString();
	String msufTable = cnaAlgorithm.getMsufTable().toString();
	String necList = cnaAlgorithm.getNecList().toString();
	String mnecTable = cnaAlgorithm.getMnecTable().toString();
	String fmt = cnaAlgorithm.getFmt().toString();

	render(elapsedTime, calculatedGraph, calculatedGraphPath, effects,
		sufTable, msufTable, necList, mnecTable, fmt);
    }
}
