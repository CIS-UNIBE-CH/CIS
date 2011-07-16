package controllers;

import helpers.BaumgartnerSampleTable;
import helpers.Timer;

import java.util.ArrayList;

import models.RandomMTSetGenerator;
import models.Renderer;
import parsers.MTSetToGraph;
import play.mvc.Controller;
import algorithms.cna.CNAlgorithm;
import algorithms.cna.NecException;
import datastructures.mt.MinimalTheorieSet;

public class CNAController extends Controller {

    private static Renderer renderer;
    private static RandomMTSetGenerator generator;
    private static Timer timer;
    private static boolean showBundleNumRenderer;

    public static void setup() {
	render();
    }

    public static void generateGraph(String numberOfAlterFactors,
	    String numberOfBundles, String sizeOfBundles, String showBundleNum,
	    String lines) {

	showBundleNumRenderer = (showBundleNum != null);

	// Generate a Graph with n bundles and a total of k factors
	int numBundles = Integer.parseInt(numberOfBundles);
	int numOfAlterFactors = Integer.parseInt(numberOfAlterFactors);
	int sizeBundles = Integer.parseInt(sizeOfBundles);
	int numFactors = numOfAlterFactors + (numBundles * sizeBundles);
	renderer = new Renderer();
	ArrayList<String> table = new ArrayList<String>();

	renderer.setEdgeLabels(showBundleNumRenderer);

	if (numFactors >= (2 * numBundles) && numFactors <= 12) {
	    timer = new Timer();
	    generator = new RandomMTSetGenerator(numBundles, numFactors,
		    sizeBundles);
	    Long time = timer.timeElapsed();
	    MTSetToGraph mtSetToGraph = new MTSetToGraph(generator.getMTSet());
	    // renderer.config(mtSetToGraph.getGraph());

	    String generatedGraphPath = renderer.getImageSource();
	    String elapsedTime = time.toString() + " ms";
	    // TODO FL
	    String generatedGraph = "TODO";

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
	try {
	    // TODO Make GraphToTable Parser change this
	    CNAlgorithm cnaAlgorithm = new CNAlgorithm(generator.getTable(),
		    randomLines);

	    MinimalTheorieSet theories = cnaAlgorithm.getMinimalTheorieSet();
	    ArrayList<String> graphPaths = new ArrayList<String>();
	    ArrayList<String> stringGraphs = new ArrayList<String>();

	    // TODO Make rendering correct.
	    renderer = new Renderer();
	    renderer.setEdgeLabels(true);
	    renderer.setChangingVertexColors(true);
	    // renderer.config(treeToGraph);
	    graphPaths.add(renderer.getImageSource());
	    // stringGraphs.add(stringToTree.getTree().toString());

	    Long time = timer.timeElapsed();

	    String originalTable = cnaAlgorithm.getOriginalTable().toString();
	    String elapsedTime = time.toString() + " ms";
	    String effects = cnaAlgorithm.getEffects().toString();
	    String sufTable = cnaAlgorithm.getSufTable().toString();
	    String msufTable = cnaAlgorithm.getMsufTable().toString();
	    String necList = cnaAlgorithm.getNecList().toString();
	    String mnecTable = cnaAlgorithm.getMnecTable().toString();
	    String deleted = cnaAlgorithm.getDeleted().toString();
	    String fmt = cnaAlgorithm.getMinimalTheorieSet().toString();

	    render(elapsedTime, originalTable, graphPaths, stringGraphs,
		    generatedGraphPath, generatedGraph, effects, sufTable,
		    msufTable, necList, mnecTable, fmt, deleted);
	} catch (NecException e) {
	    flash.error("NEC error.");
	    params.flash();
	    setup();
	}
    }

    public static void baumgartnerSample() {
	timer = new Timer();
	CNAlgorithm cnaAlgorithm;
	try {
	    cnaAlgorithm = new CNAlgorithm(
		    new BaumgartnerSampleTable().getSampleTable());
	    MinimalTheorieSet theories = cnaAlgorithm.getMinimalTheorieSet();
	    ArrayList<String> graphPaths = new ArrayList<String>();
	    ArrayList<String> stringGraphs = new ArrayList<String>();
	    // TODO
	    // for (MinimalTheorie theorie : theories) {
	    // CNAList list = new CNAList();
	    // list.add(theorie.toString());
	    // StringToTree stringToTree = new StringToTree(list);
	    // TreeToGraph treeToGraph = new TreeToGraph(
	    // stringToTree.getTree(), stringToTree.getNumOfEffects(),
	    // stringToTree.getTotalFactors());
	    // renderer = new Renderer();
	    // renderer.setEdgeLabels(true);
	    // renderer.setChangingVertexColors(true);
	    // renderer.config(treeToGraph);
	    // graphPaths.add(renderer.getImageSource());
	    // stringGraphs.add(stringToTree.getTree().toString());
	    // }
	    Long time = timer.timeElapsed();
	    // String calculatedGraph =
	    String elapsedTime = time.toString() + " ms";
	    String effects = cnaAlgorithm.getEffects().toString();
	    String sufTable = cnaAlgorithm.getSufTable().toString();
	    String msufTable = cnaAlgorithm.getMsufTable().toString();
	    String necList = cnaAlgorithm.getNecList().toString();
	    String mnecTable = cnaAlgorithm.getMnecTable().toString();
	    render(elapsedTime, graphPaths, stringGraphs, effects, sufTable,
		    msufTable, necList, mnecTable);
	} catch (NecException e) {
	    flash.error("NEC error.");
	    params.flash();
	    setup();
	    e.printStackTrace();
	}

    }
}
