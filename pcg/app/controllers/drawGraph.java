package controllers;

import models.Renderer;
import parsers.StringToTree;
import parsers.TreeToGraph;
import play.mvc.Controller;

public class drawGraph extends Controller {

    private static Renderer renderer;

    public static void setup() {
	render();
    }

    public static void drawGraph(String graph) {
	graph = graph.replace("v", " ∨ ");
	System.out.println(" -- " + graph);
	renderer = new Renderer();
	StringToTree StringParser = new StringToTree(graph);
	TreeToGraph TreeParser = new TreeToGraph(StringParser.getTree());
	renderer.config(TreeParser);
	String GraphPath = renderer.getImageSource();
	render(GraphPath);
    }
}
