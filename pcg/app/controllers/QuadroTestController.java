package controllers;

import models.Renderer;
import play.mvc.Controller;
import algorithms.QuadroError;
import algorithms.QuadroTest;
import datastructures.graph.Graph;
import datastructures.mt.MinimalTheorySet;

public class QuadroTestController extends Controller {

    private static boolean showBundleNumRenderer;

    public static void setup() {
	render();
    }

    public static void setTable(String f1, String f2, String showBundleNum) {
	showBundleNumRenderer = (showBundleNum != null);
	render(f1, f2);
    }

    public static void calcQuadroGraph(String f1, String f2, String i,
	    String ii, String l, String ll, String showBundleNum) {

	int[] numbers = new int[4];
	String coincidence = i + ii + l + ll;

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
	}

	try {
	    QuadroTest quadroTest = new QuadroTest(coincidence, f1, f2);
	    MinimalTheorySet theories = quadroTest.createMTTheorySet();
	    Graph graph = new Graph(theories);
	    Renderer renderer = new Renderer();
	    renderer.setShowEdgeLabels(showBundleNumRenderer);
	    renderer.config(graph);

	    String graphPath = renderer.getImageSource();
	    String stringGraph = theories.toString();
	    render(graphPath, stringGraph);
	} catch (QuadroError e) {
	    flash.error(e.toString());
	    params.flash();
	    setup();
	}

    }
}
