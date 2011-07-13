package controllers;

import java.util.ArrayList;

import models.Renderer;
import parsers.StringToTree;
import parsers.TreeToGraph;
import play.mvc.Controller;
import datastructures.CNAList;
import datastructures.CNATable;

public class drawGraph extends Controller {

    private static Renderer renderer;

    public static void setup() {
	render();
    }

    public static void drawGraph(String graph) {
	CNATable table = new CNATable();
	graph = graph.replace("v", " ∨ ");
	String[] array = graph.split("\n");
	for (int i = 0; i < array.length; i++) {
	    CNAList list = new CNAList();
	    list.add(array[i]);
	    table.add(list);
	}
	ArrayList<String> graphPaths = new ArrayList<String>();
	ArrayList<String> stringGraphs = new ArrayList<String>();
	for (CNAList list : table) {
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
	render(graphPaths);
    }
}
