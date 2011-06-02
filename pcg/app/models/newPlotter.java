package models;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import parser.TreeToGraph;
import tree.CustomTree;
import tree.CustomTreeNode;
import util.BinaryTest;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.screencap.PNGDump;

public class newPlotter {
	private static CustomTree tree = null;

	public static void main(String[] args) {

		int n = 1;
		int k = 3;
		// String generatedGraphPath = "";
		// String calculatedGraphPath = "";
		// String generatedGraph = "";
		ArrayList<String> table = new ArrayList<String>();
		BinaryTest complexTest;
		GraphGenerator generator = new GraphGenerator(n, k);
		table = generator.getTable();
		complexTest = new BinaryTest(generator.getTableAsArray());
		tree = complexTest.createTree();
		TreeToGraph graphing = new TreeToGraph(tree);

		Transformer locationTransformer = new Transformer(); // {

		// Need a static layout so nodes will positioned evertime the same
		StaticLayout<CustomTreeNode, CustomEdge> layout = new StaticLayout<CustomTreeNode, CustomEdge>(
				graphing.getGraph(), locationTransformer);
		layout.setSize(new Dimension(350, 350));

		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<CustomTreeNode, CustomEdge> vv = new BasicVisualizationServer<CustomTreeNode, CustomEdge>(
				layout);

		// Labels for nodes
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		// Labels for edges
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		// Position the labels in the center of node
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		vv.getRenderer().setVertexRenderer(new Renderer());
		// Size a the picture which will be dumped.
		vv.setSize(600, 600);

		PNGDump dumper = new PNGDump();
		try {
			dumper.dumpComponent(new File("public/images/test.png"), vv);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 350);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);

	}
}
