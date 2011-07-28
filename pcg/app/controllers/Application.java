package controllers;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <pcg.unibe.ch@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
	render();
    }

    public static void disclaimer() {
	render();
    }

    public static void contact() {
	render();
    }
}