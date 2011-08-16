package controllers;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import models.Renderer;
import play.mvc.Controller;
import algorithms.quadro.QuadroException;
import algorithms.quadro.QuadroTest;
import datastructures.graph.Graph;
import datastructures.mt.MinimalTheorySet;

public class QuadroTestController extends Controller {

    private static boolean showBundleNumRenderer;

    public static void setup() {
	render();
    }

    public static void setTable(String f1, String f2) {
	if (f1.equals("") || f2.equals("")) {
	    flash.error("Please insert in the fields two factor names.");
	    setup();
	}
	render(f1, f2);
    }

    public static void calcQuadroGraph(String f1, String f2, String i,
	    String ii, String l, String ll, String showBundleNum) {
	showBundleNumRenderer = (showBundleNum != null);
	int[] numbers = new int[4];
	String coincidence = i + ii + l + ll;

	try {
	    numbers[0] = Integer.parseInt(i);
	    numbers[1] = Integer.parseInt(ii);
	    numbers[2] = Integer.parseInt(l);
	    numbers[3] = Integer.parseInt(ll);
	} catch (NumberFormatException e) {
	    flash.error("Please insert in the fields only ones or zeros.");
	    params.flash();
	    setup();
	}
	if (numbers[0] < 0 || numbers[0] > 1 || numbers[1] < 0
		|| numbers[1] > 1 || numbers[2] < 0 || numbers[2] > 1
		|| numbers[3] < 0 || numbers[3] > 1) {
	    flash.error("Please insert in the fields only ones or zeros.");
	    params.flash();
	    setup();
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
	    String specialCase = quadroTest.getSpecialCase();

	    render(graphPath, stringGraph, specialCase);
	} catch (QuadroException e) {
	    flash.error(e.toString());
	    params.flash();
	    setup();
	} catch (OutOfMemoryError e) {
	    flash.error("Server is out of memory, please wait a minute.");
	    params.flash();
	    setup();
	}
    }
}
