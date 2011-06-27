package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import parser.TreeToGraph;
import tree.CustomTreeNode;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.screencap.PNGDump;

/**
 * 1. Holds all the configurations for the JUNG Graph rendering. Furthermore all
 * configs which are in the Transformer will here be called and applyed to the
 * graphs config (For example the vertex positions will be applied from
 * VertexLocationTransformer). Here you can configure vertex color, in the
 * VetexLookTransformer you can config shape, apsect ratio and size of vertex.
 * 2. Makes dumping of graph in a PNG
 */
public class Renderer {
	private static Date now;
	private static SimpleDateFormat dateFormat;
	private static String path;
	// X and Y Size of picture which will be dumped
	private static int xPicSize;
	private static int yPicSize;
	// Transformer which will set node positions
	private VertexLocationTransformer locationTransformer;
	private TreeToGraph parser;

	public Renderer() {
		now = new Date();
		dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmssS");
		this.path = "./pcg/public/images/graphs/";
		xPicSize = 0;
		yPicSize = 0;
		locationTransformer = new VertexLocationTransformer();
	}

	public void config(TreeToGraph parser) {
		this.parser = parser;

		// Calculate Size of Pictures according to number of vertexes.
		calculatePictureSize();

		// Set x Coordinate of Location of the Wirkung
		locationTransformer
				.setxRootPosition(parser.getGraph().getVertexCount());

		// Use a static layout so vertexes will positioned ever time at the same
		// place
		StaticLayout<CustomTreeNode, CustomEdge> layout = new StaticLayout<CustomTreeNode, CustomEdge>(
				this.parser.getGraph(), locationTransformer);
		layout.setSize(new Dimension(xPicSize, yPicSize));

		// Do a reset of vertex location transformation after every graph, else
		// transformation WON'T work properly.
		locationTransformer.reset();

		// Print out the graph in console for development only used
		System.out.println("*****************");
		System.out.println(this.parser.getGraph().toString());
		System.out.println("*****************");

		// Transformer which will set shape, size, aspect ratio of vertexes
		VertexLookTransformer<CustomTreeNode, Shape> vertexLookTransformer = new VertexLookTransformer<CustomTreeNode, Shape>();

		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<CustomTreeNode, CustomEdge> visServer = new BasicVisualizationServer<CustomTreeNode, CustomEdge>(
				layout);

		// *************Configure BasicVisualizationServer*********

		// Offset of Edge Labels to Edge
		visServer.getRenderContext().setLabelOffset(15);
		// Position Edge Labels according to Edge Length
		visServer.getRenderContext().setEdgeLabelClosenessTransformer(
				new EdgeLabelClosenessTransformer());
		// Edge Labels will be displayed in right orientation. true = parallel
		// to Edge, false = horizontal
		EdgeLabelRenderer edgeLabelRenderer = visServer.getRenderContext()
				.getEdgeLabelRenderer();
		edgeLabelRenderer.setRotateEdgeLabels(false);
		// Labels for Vertexes
		visServer.getRenderContext().setVertexLabelTransformer(
				new ToStringLabeller());
		// Labels for edges
		visServer.getRenderContext().setEdgeLabelTransformer(
				new ToStringLabeller());
		// Position the labels in the center of vertex
		visServer.getRenderer().getVertexLabelRenderer().setPosition(
				Position.CNTR);
		// Edge shape as line
		visServer.getRenderContext().setEdgeShapeTransformer(
				new EdgeShape.Line<CustomTreeNode, CustomEdge>());
		// Set VertexLookTranformer changes
		visServer.getRenderContext().setVertexShapeTransformer(
				vertexLookTransformer);
		// Set vertex color
		visServer.getRenderContext().setVertexFillPaintTransformer(new VertexColorTransformer());
		// Set background color
		Color backgroundColor = new Color(255, 255, 255);
		visServer.setBackground(backgroundColor);

		// ************ PNG Dumping ****************
		// Size the PNG picture will have which will be dumped.
		visServer.setSize(xPicSize, yPicSize);

		PNGDump dumper = new PNGDump();
		try {
			dumper.dumpComponent(new File(generateFileName()), visServer);
		} catch (IOException e) {
			System.out.println("Image couldn't be generated!");
			e.printStackTrace();
		}
	}

	private void calculatePictureSize() {
		// Get Number of nodes -1 (Wirkung)
		int numberOfNodes = this.parser.getGraph().getVertexCount() - 1;
		xPicSize = numberOfNodes * locationTransformer.getSpace()
				+ locationTransformer.getSpace();
		yPicSize = 30 + locationTransformer.getyRoot();
	}

	public String getImageSource() {
		return "/public/images/graphs/" + dateFormat.format(now).toString()
				+ ".png";
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static String generateFileName() {
		return path + dateFormat.format(now).toString() + ".png";
	}
}
