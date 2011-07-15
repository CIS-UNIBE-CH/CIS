package controllers;

import play.mvc.Controller;

public class SimpleQuadroController extends Controller {

    private static boolean showColourRenderer;
    private static boolean showBundleNumRenderer;

    public static void setup() {
	render();
    }

    public static void setTable(String f1, String f2, String showColour,
	    String showBundleNum) {
//	showColourRenderer = (showColour != null);
//	showBundleNumRenderer = (showBundleNum != null);
//	render(f1, f2);
    }

    public static void calcQuadroGraph(String f1, String f2, String i,
	    String ii, String l, String ll, String showBundleNum,
	    String showColour) {

//	int[][] field = new int[2][2];
//	int[] numbers = new int[4];
//
//	try {
//	    numbers[0] = Integer.parseInt(i);
//	    numbers[1] = Integer.parseInt(ii);
//	    numbers[2] = Integer.parseInt(l);
//	    numbers[3] = Integer.parseInt(ll);
//	} catch (Exception e) {
//	    flash.error(e.toString());
//	    params.flash();
//	}
//	if (numbers[0] < 0 || numbers[0] > 1 || numbers[1] < 0
//		|| numbers[1] > 1 || numbers[2] < 0 || numbers[2] > 1
//		|| numbers[3] < 0 || numbers[3] > 1) {
//	    flash.error("Please insert only 1 or 0.");
//	    params.flash();
//	}
//
//	field[0][0] = numbers[0];
//	field[0][1] = numbers[1];
//	field[1][0] = numbers[2];
//	field[1][1] = numbers[3];
//
//	QuadroTest quadroTest = new QuadroTest(field, f1, f2);
//	tree = quadroTest.creatGraph();
//
//	if (tree == null) {
//	    flash.error("Sorry it was not posible to calculate a graph with your data. For more information (click here)");
//	    params.flash();
//	    render();
//	}
//	Renderer renderer = new Renderer();
//	renderer.setEdgeLabels(showBundleNumRenderer);
//	renderer.setChangingVertexColors(showColourRenderer);
//
//	renderer.config(new TreeToGraph(tree, 1, 3));
//
//	String graphPath = renderer.getImageSource();
//	String stringGraph = tree.toString();
//	render(graphPath, stringGraph);
    }
}
