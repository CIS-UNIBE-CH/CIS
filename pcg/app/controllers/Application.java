package controllers;

import java.util.ArrayList;

import models.GraphGenerator;
import models.News;
import models.Plotter;
import parser.TreeToJgraph;
import play.mvc.Controller;
import tree.CustomTree;
import util.ComplexTest;
import util.QuadroTest;

public class Application extends Controller {

	private static CustomTree tree = null;

	public static void index() {
		/* Move This to new Method for generating Graph out of tables */
		int n = 1;
		int k = 3;
		GraphGenerator generator = new GraphGenerator(n, k);
		ComplexTest complexTest = new ComplexTest(generator.getTableAsArray());
		/* Move This to new Method for generating Graph out of tables */

		News news = new News(
				"The first algorithmen to pursuit of a causal graph is implemented. Now you can do simple tests with two factors. Have fun!",
				"@{Application.quadroTest(0)}", "Quadro Test implemented");

		render(news);
	}

	public static void quadroTest(int step, String f1, String f2) {
		render(step, f1, f2);
	}

	public static void complexTest(int step, String f1,
			ArrayList<String> table, String tree) {
		render(step, f1, table, tree);
	}

	public static void generateGraph(String numberOfFactors,
			String numberOfBundls) {

		// Generate a Graph with n bundles and a total of k factors
		int n = Integer.parseInt(numberOfBundls);
		int k = Integer.parseInt(numberOfFactors);
		GraphGenerator generator = new GraphGenerator(n, k);
		String source = generator.getGraphicSource();
		String tree = generator.getTree().toString();
		ArrayList<String> table = generator.getTable();
		if (k >= 2 * n) {
			complexTest(1, source, table, tree);
		} else {
			flash.error("Sorry it was not posible to generate a graph, the numbers of factros must be grater than twice as much of the number of bundls.");
			params.flash();
			complexTest(0, source, table, tree);
		}
	}

	public static void calcQuadroTestGraph(String f1, String f2, String i,
			String ii, String l, String ll) {
		int[][] field = new int[2][2];
		int[] numbers = new int[4];

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
			quadroTest(1, f1, f2);
		}

		field[0][0] = numbers[0];
		field[0][1] = numbers[1];
		field[1][0] = numbers[2];
		field[1][1] = numbers[3];

		QuadroTest quadroTest = new QuadroTest(field, f1, f2);
		tree = quadroTest.creatGraph();

		if (tree == null) {
			flash.error("Sorry it was not posible to calculate a graph with your data. For more information (click here)");
			params.flash();
			quadroTest(1, f1, f2);
		}
		Plotter plotter = new Plotter();

		plotter.plot(new TreeToJgraph(tree));
		String graphPath = plotter.getImageSource();
		showGraph(graphPath);
	}

	public static void showGraph(String graphPath) {
		String stringGraph = tree.toString();
		render(graphPath, stringGraph);
	}
}