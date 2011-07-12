package controllers;

import helpers.Timer;
import models.RandomGraphGenerator;
import models.Renderer;
import parsers.StringToTree;
import parsers.TreeToGraph;
import play.mvc.Controller;
import algorithms.CNAlgorithm;
import datastructures.CNATable;

public class Input extends Controller {

    private static Renderer renderer;
    private static RandomGraphGenerator generator;
    private static Timer timer;
    private static boolean showColourRenderer;
    private static boolean showBundleNumRenderer;

    public static void setup() {
	render();
    }

    public static void calcCNAGraph(String graph) {
	timer = new Timer();
	renderer = new Renderer();
	CNATable table = new CNATable("\n", ":", graph);
	System.out.println(table);
	CNAlgorithm cnaAlgorithm = new CNAlgorithm(table);

	StringToTree stringToTree = new StringToTree(cnaAlgorithm.getFmt().get(
		0));
	Long time = timer.timeElapsed();
	TreeToGraph treeToGraph = new TreeToGraph(stringToTree.getTree());
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

	render(elapsedTime, originalTable, calculatedGraph,
		calculatedGraphPath, effects, sufTable, msufTable, necList,
		mnecTable, fmt);
    }
}
