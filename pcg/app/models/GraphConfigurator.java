package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;

import org.apache.commons.collections15.functors.ConstantTransformer;

import parser.TreeToGraph;
import tree.CustomTree;
import tree.CustomTreeNode;
import algorithms.BinaryTest;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.screencap.PNGDump;

/**
 * Holds all the configurations for the JUNG Graph rendering. Furthermore all
 * configs which are in the Transformer will here be called and applyed to the
 * graphs config (For example the vertex positions will be applied from
 * VertexLocationTransformer). Here you can configure vetex color, in the
 * VetexLookTransformer you can config shape, apsect ratio and size of vertex.
 */
public class GraphConfigurator {
	private static CustomTree tree = null;

	public static void main(String[] args) {

		// ********* REMOVE
		int n = 1;
		int k = 3;

		BinaryTest complexTest;
		RandomGraphGenerator generator = new RandomGraphGenerator(n, k);
		complexTest = new BinaryTest(generator.getTableAsArray());
		tree = complexTest.createTree();
		TreeToGraph graphing = new TreeToGraph(tree);
		Collection<CustomEdge> typedEdges = new ArrayList<CustomEdge>();
		typedEdges = graphing.getGraph().getEdges();

		// ********* REMOVE

		// Transformer which will set node positions
		VertexLocationTransformer locationTransformer = new VertexLocationTransformer();

		// Use a static layout so vertexes will positioned ever time at the same
		// place
		StaticLayout<CustomTreeNode, CustomEdge> layout = new StaticLayout<CustomTreeNode, CustomEdge>(
				graphing.getGraph(), locationTransformer);
		layout.setSize(new Dimension(350, 350));

		// Transformer which will set shape, size, aspect ratio of vertexes
		VertexLookTransformer<CustomTreeNode, Shape> vertexLookTransformer = new VertexLookTransformer<CustomTreeNode, Shape>();

		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<CustomTreeNode, CustomEdge> visServer = new BasicVisualizationServer<CustomTreeNode, CustomEdge>(
				layout);

		// ***Configure BasicVisualizationServer***
		// Labels for Vertexes
		visServer.getRenderContext().setVertexLabelTransformer(
				new ToStringLabeller());
		// Labels for edges
		visServer.getRenderContext().setEdgeLabelTransformer(
				new ToStringLabeller());
		// Position the labels in the center of vertex
		visServer.getRenderer().getVertexLabelRenderer()
				.setPosition(Position.CNTR);
		// Edge shape as line
		visServer.getRenderContext().setEdgeShapeTransformer(
				new EdgeShape.Line<CustomTreeNode, CustomEdge>());
		// Set VertexLookTranformer changes
		visServer.getRenderContext().setVertexShapeTransformer(
				vertexLookTransformer);
		// Change vertex color
		Color color = new Color(184, 0, 138);
		visServer.getRenderContext().setVertexFillPaintTransformer(
				new ConstantTransformer(color));

		// Size the PNG picture will have which will be dumped.
		visServer.setSize(600, 600);

		PNGDump dumper = new PNGDump();
		try {
			dumper.dumpComponent(new File("public/images/test.png"), visServer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ******** REMOVE

		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 350);
		frame.getContentPane().add(visServer);
		frame.pack();
		frame.setVisible(true);

		// ********* REMOVE
	}
}
