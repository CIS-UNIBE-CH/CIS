package controllers;

import helpers.Timer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import models.Renderer;
import parsers.StringToTree;
import parsers.TreeToGraph;
import play.mvc.Controller;
import algorithms.cna.CNAlgorithm;
import algorithms.cna.NecException;
import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.mt.MinimalTheorie;
import datastructures.mt.MinimalTheorieSet;

public class Input extends Controller {

    private static Renderer renderer;
    private static Timer timer;

    public static void setup() {
	render();
    }

    public static void calcCNAGraph(String table)
	    throws InvocationTargetException {
	timer = new Timer();
	renderer = new Renderer();
	// CNATable cnaTable = new CNATable(";", ",",
	// "a,b,c;1,0,1;0,0,1;1,1,1");
	CNATable cnaTable = new CNATable('\n', ',', table);
	//System.out.println(cnaTable.size());
	if (cnaTable.size() < 3) {
	    flash.error("Please add more lines (min. 3 lines");
	}
	// cnaTable.addOneLine();
	CNAlgorithm cnaAlgorithm;
	try {
	    cnaAlgorithm = new CNAlgorithm(cnaTable);
	    MinimalTheorieSet theories = cnaAlgorithm.getMinimalTheorieSet();
	    ArrayList<String> graphPaths = new ArrayList<String>();
	    ArrayList<String> stringGraphs = new ArrayList<String>();
	    for (MinimalTheorie theorie : theories) {
		CNAList list = new CNAList();
		list.add(theorie.toString());
		StringToTree stringToTree = new StringToTree(list);
		TreeToGraph treeToGraph = new TreeToGraph(
			stringToTree.getTree(), stringToTree.getNumOfEffects(),
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

	    render(elapsedTime, originalTable, graphPaths, stringGraphs,
		    effects, sufTable, msufTable, necList, mnecTable);
	} catch (NecException e) {
	    flash.error("NEC error.");
	    params.flash();
	    setup();
	    e.printStackTrace();
	}

    }
}
