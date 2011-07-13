package controllers;

import helpers.Timer;

import java.util.ArrayList;

import models.Renderer;
import parsers.StringToTree;
import parsers.TreeToGraph;
import play.mvc.Controller;
import algorithms.CNAlgorithm;
import datastructures.CNAList;
import datastructures.CNATable;

public class Input extends Controller {

    private static Renderer renderer;
    private static Timer timer;

    public static void setup() {
	render();
    }

    public static void calcCNAGraph(String graph) {
	timer = new Timer();
	renderer = new Renderer();
	CNATable table = new CNATable("\n", ":", graph);
	System.out.println(table);
	CNAlgorithm cnaAlgorithm = new CNAlgorithm(table);

	CNATable fmtTable = cnaAlgorithm.getFmtTable();
	System.out.println("fmtTable " + fmtTable);
	ArrayList<String> graphPaths = new ArrayList<String>();
	ArrayList<String> stringGraphs = new ArrayList<String>();
	for (CNAList list : fmtTable) {
	    System.out.println("list" + list);
	    StringToTree stringToTree = new StringToTree(list);
	    TreeToGraph treeToGraph = new TreeToGraph(stringToTree.getTree(),
		    stringToTree.getNumOfEffects(),
		    stringToTree.getTotalFactors());
	    renderer = new Renderer();
	    renderer.setEdgeLabels(true);
	    renderer.setChangingVertexColors(true);
	    renderer.config(treeToGraph);
	    graphPaths.add(renderer.getImageSource());
	    stringGraphs.add(stringToTree.getTree().toString());
	}
	Long time = timer.timeElapsed();

	String originalTable = cnaAlgorithm.getOriginalTable().toString();
	String elapsedTime = time.toString() + " ms";
	String effects = cnaAlgorithm.getEffects().toString();
	String sufTable = cnaAlgorithm.getSufTable().toString();
	String msufTable = cnaAlgorithm.getMsufTable().toString();
	String necList = cnaAlgorithm.getNecList().toString();
	String mnecTable = cnaAlgorithm.getMnecTable().toString();
	String fmt = cnaAlgorithm.getFmtTable().toString();

	render(elapsedTime, originalTable, graphPaths, stringGraphs, effects,
		sufTable, msufTable, necList, mnecTable, fmt);
    }
}
