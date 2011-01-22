package controllers;


import models.Plotter;

import play.mvc.Controller;
import util.QuadroTest;

public class Application extends Controller {

	public static void index() {
		Plotter plotter = new Plotter();
		
		render(plotter);
	}
	
	public static void calcQuadroTestGraph(String i, String ii, String l, String ll) {
		int[][] field = new int[2][2];
		field[0][0] = Integer.parseInt(i);
		field[0][1] = Integer.parseInt(ii);
		field[1][0] = Integer.parseInt(l);
		field[1][1] = Integer.parseInt(ll);
		
		QuadroTest quadroTest = new QuadroTest(field);
		
		showGraph();
	}
	
	public static void showGraph() {
		
	}
}