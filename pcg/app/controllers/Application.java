package controllers;

import models.Plotter;
import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {
		Plotter plotter = new Plotter();
		render();
	}

}