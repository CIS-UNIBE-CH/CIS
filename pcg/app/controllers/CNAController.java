package controllers;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <pcg.unibe.ch@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import helpers.BaumgartnerSampleTable;
import helpers.Timer;

import java.util.ArrayList;

import models.Renderer;
import play.mvc.Controller;
import algorithms.cna.CNAlgorithm;
import algorithms.cna.NecException;
import datastructures.cna.CNAList;
import datastructures.cna.CNATable;
import datastructures.graph.Graph;
import datastructures.mt.MinimalTheory;
import datastructures.mt.MinimalTheorySet;
import datastructures.parser.MTSetToTable;
import datastructures.random.RandomMTGeneratorHelper;
import datastructures.random.RandomMTSetGenerator;

public class CNAController extends Controller {

    private static Timer timer;
    private static boolean showBundleNumRenderer;
    private static MinimalTheorySet theories;
    private static boolean makeEpi;

    public static void setup() {
	render();
    }

    public static void prepare(String layers) {
	int i = Integer.parseInt(layers);
	render(i);
    }

    public static void generateGraph(ArrayList<Integer> bundles1,
	    ArrayList<Integer> bundles2, ArrayList<Integer> bundles3,
	    ArrayList<Integer> alterFactors, String epi, String showBundleNum)
	    throws NecException {
	try {
	    showBundleNumRenderer = (showBundleNum != null);
	    makeEpi = (epi != null);
	    RandomMTSetGenerator generator;
	    ArrayList<ArrayList<Integer>> list;
	    RandomMTGeneratorHelper input;

	    list = new ArrayList<ArrayList<Integer>>();
	    list.add(bundles1);
	    list.add(bundles2);
	    list.add(bundles3);

	    input = new RandomMTGeneratorHelper(list, alterFactors, makeEpi);
	    generator = new RandomMTSetGenerator(input.getCompleteList(),
		    makeEpi);
	    theories = generator.getMTSet();

	    Graph graph = new Graph(theories);
	    Renderer renderer = new Renderer();
	    renderer.setShowEdgeLabels(showBundleNumRenderer);
	    renderer.config(graph);

	    String generatedGraphSource = renderer.getImageSource();
	    String generatedGraphString = theories.toString();
	    boolean calc = (theories.getAllNames().size() <= 10);
	    if (!calc) {
		flash.error("Only up to 10 factors allowed.");
		params.flash();
	    }

	    render(calc, generatedGraphSource, generatedGraphString);
	} catch (NecException e) {
	    flash.error(e.toString());
	    params.flash();
	    setup();
	} catch (OutOfMemoryError e) {
	    flash.error("Server is out of memory, please wait a minute.");
	    params.flash();
	    setup();
	} catch (IllegalArgumentException e) {
	    flash.error("All minimal theories have zero factors. Please specifiy the number of factors and bundles.");
	    params.flash();
	    setup();
	    // TODO Mail to us.
	} catch (IndexOutOfBoundsException e) {
	    flash.error("Too much data!");
	    params.flash();
	    setup();
	}
    }

    public static void calcCNAGraph(String generatedGraphSource,
	    String generatedGraphString) {
	try {
	    timer = new Timer();
	    MTSetToTable parser = new MTSetToTable(theories, makeEpi);
	    CNATable table = parser.getCoincTable();
	    CNAlgorithm cnaAlgorithm = new CNAlgorithm(table);
	    ArrayList<String> graphsView = new ArrayList<String>();

	    for (MinimalTheorySet set : cnaAlgorithm.getSets()) {
		Graph graph = new Graph(set);
		Renderer renderer = new Renderer();
		renderer.setShowEdgeLabels(showBundleNumRenderer);
		renderer.config(graph);
		graphsView.add(renderer.getImageSource());
		graphsView.add(set.toString());
	    }

	    String elapsedTime = timer.timeElapsed() + " ms";
	    String effects = cnaAlgorithm.getEffects().toString();
	    String sufTable = cnaAlgorithm.getSufTable().toString();
	    String msufTable = cnaAlgorithm.getMsufTable().toString();
	    String necList = cnaAlgorithm.getNecList().toString();
	    String mnecTable = cnaAlgorithm.getMnecTable().toString();
	    String coincTable = table.toString();

	    render(elapsedTime, graphsView, generatedGraphSource,
		    generatedGraphString, effects, sufTable, msufTable,
		    necList, mnecTable, coincTable);
	} catch (NecException e) {
	    flash.error(e.toString());
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
	    MinimalTheorySet theories = cnaAlgorithm.getSets().get(0);

	    Graph graph = new Graph(theories);
	    Renderer renderer = new Renderer();
	    renderer.setShowEdgeLabels(showBundleNumRenderer);
	    renderer.config(graph);
	    String graphPath = renderer.getImageSource();
	    String graphString = theories.toString();

	    String elapsedTime = timer.timeElapsed() + " ms";
	    String effects = cnaAlgorithm.getEffects().toString();
	    String sufTable = cnaAlgorithm.getSufTable().toString();
	    String msufTable = cnaAlgorithm.getMsufTable().toString();
	    String necList = cnaAlgorithm.getNecList().toString();
	    String mnecTable = cnaAlgorithm.getMnecTable().toString();

	    render(elapsedTime, graphPath, graphString, effects, sufTable,
		    msufTable, necList, mnecTable);
	} catch (NecException e) {
	    flash.error(e.toString());
	    params.flash();
	    setup();
	}
    }

    public static void inputTable(String table) {

	CNATable cnatable = new CNATable("\r\n", ",", table);
	if (cnatable.get(0).size() > 10) {
	    flash.error("Only up to 10 are factors allowed.");
	    params.flash();
	    setup();
	} else if (cnatable.get(0).size() < 2) {
	    flash.error("There must be at least two factors.");
	    params.flash();
	    setup();
	}
	try {
	    ArrayList<String> graphsView = new ArrayList<String>();
	    timer = new Timer();
	    CNAlgorithm cnaAlgorithm = new CNAlgorithm(cnatable);
	    for (MinimalTheorySet set : cnaAlgorithm.getSets()) {
		Graph graph = new Graph(set);
		Renderer renderer = new Renderer();
		renderer.setShowEdgeLabels(showBundleNumRenderer);
		renderer.config(graph);
		graphsView.add(renderer.getImageSource());
		graphsView.add(set.toString());
	    }

	    String elapsedTime = timer.timeElapsed() + " ms";
	    String effects = cnaAlgorithm.getEffects().toString();
	    String sufTable = cnaAlgorithm.getSufTable().toString();
	    String msufTable = cnaAlgorithm.getMsufTable().toString();
	    String necList = cnaAlgorithm.getNecList().toString();
	    String mnecTable = cnaAlgorithm.getMnecTable().toString();

	    render(elapsedTime, graphsView, effects, sufTable, msufTable,
		    necList, mnecTable);
	} catch (NecException e) {
	    flash.error(e.toString());
	    params.flash();
	    setup();
	    // TODO Mail to us
	} catch (ArrayIndexOutOfBoundsException e) {
	    flash.error("Please give us more data!");
	    params.flash();
	    setup();
	    // TODO Mail to us
	} catch (IndexOutOfBoundsException e) {
	    flash.error("Please give us more data!");
	    params.flash();
	    setup();
	    // TODO Mail to us
	} catch (IllegalArgumentException e) {
	    flash.error("Sorry, something went very wrong!");
	    params.flash();
	    setup();
	}
    }

    public static void inputMT(String mtset) {
	try {
	    CNAList list = new CNAList("\r\n", mtset);
	    CNAList factors;
	    theories = new MinimalTheorySet();
	    MinimalTheory theorie;
	    for (String str : list) {
		factors = new CNAList();
		String[] array = str.split("=>");
		String[] fac = array[0].split("v");
		for (int i = 0; i < fac.length; i++) {
		    factors.add(fac[i]);
		}
		theorie = new MinimalTheory(factors, array[1]);
		theories.add(theorie);
	    }
	    Graph graph = new Graph(theories);
	    Renderer renderer = new Renderer();
	    renderer.setShowEdgeLabels(showBundleNumRenderer);
	    renderer.config(graph);

	    String generatedGraphSource = renderer.getImageSource();
	    String generatedGraphString = theories.toString();
	    boolean calc = (theories.getAllNames().size() < 11);
	    if (!calc) {
		flash.error("Only up to 10 factors allowed.");
		params.flash();

	    }

	    render(generatedGraphSource, generatedGraphString, calc);
	} catch (ArrayIndexOutOfBoundsException e) {
	    flash.error("You're input is not according to our syntax. Please correct it.");
	    params.flash();
	    setup();
	    // TODO mail us.
	} catch (IndexOutOfBoundsException e) {
	    flash.error("Please give us more data!");
	    params.flash();
	    setup();
	    // TODO Mail us.
	} catch (IllegalArgumentException e) {
	    flash.error("Sorry, something went very wrong!");
	    params.flash();
	    setup();
	}
    }
}
