package controllers;

import models.Plotter;
import play.mvc.*;

public class Application extends Controller {

    public static void index() {
    	Plotter plotter = new Plotter();
        render(plotter);
    }

}