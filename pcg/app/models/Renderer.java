package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.TransformerUtils;

import datastructures.graph.Edge;
import datastructures.graph.Graph;
import datastructures.graph.Node;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.EdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.screencap.PNGDump;

/**
 * 1. Holds all the configurations for the JUNG Graph rendering. Furthermore all
 * configs which are in the Transformer will here be called and applied to the
 * graphs config (For example the vertex positions will be applied from
 * VertexLocationTransformer). Here you can configure vertex color, in the
 * VetexLookTransformer you can config shape, aspect ratio and size of vertex.
 * 2. Dumps graph in a PNG image.
 */
public class Renderer {
    private static Date now;
    private static SimpleDateFormat dateFormat;
    private static String path;
    private static int xPicSize;
    private static int yPicSize;
    private boolean showLabels = true;

    public Renderer() {
	now = new Date();
	dateFormat = new SimpleDateFormat("ddMMyyyy-HHmmssS");
	this.path = "./pcg/public/images/graphs/";
	xPicSize = 0;
	yPicSize = 0;
    }

    public void config(Graph graph) {
	yPicSize = 1800;
	xPicSize = 1800;

	// Our own map of vertex locations
	Transformer<Node, Point2D> vertexLocations = TransformerUtils
		.mapTransformer(graph.getGraph());

	// Use a static layout so vertexes will positioned ever time at the same
	// place
	StaticLayout<Node, Edge> layout = new StaticLayout<Node, Edge>(graph,
		vertexLocations);

	layout.setSize(new Dimension(xPicSize, yPicSize));

	// Transformer which will set shape, size, aspect ratio of vertexes
	VertexLookTransformer<Node, Shape> vertexLookTransformer = new VertexLookTransformer<Node, Shape>();

	// The BasicVisualizationServer<V,E> is parameterized by the edge types
	BasicVisualizationServer<Node, Edge> visServer = new BasicVisualizationServer<Node, Edge>(
		layout);

	/** *************Configure BasicVisualizationServer********* */
	if (showLabels) {
	    // Offset of Edge Labels to Edge
	    visServer.getRenderContext().setLabelOffset(15);
	    // Position Edge Labels according to Edge Length
	    visServer.getRenderContext().setEdgeLabelClosenessTransformer(
		    new EdgeLabelClosenessTransformer());
	    // Edge Labels will be displayed in right orientation. true =
	    // parallel
	    // to Edge, false = horizontal
	    EdgeLabelRenderer edgeLabelRenderer = visServer.getRenderContext()
		    .getEdgeLabelRenderer();
	    edgeLabelRenderer.setRotateEdgeLabels(false);
	    // Labels for edges
	    visServer.getRenderContext().setEdgeLabelTransformer(
		    new ToStringLabeller());
	}
	// Labels for Vertexes
	visServer.getRenderContext().setVertexLabelTransformer(
		new ToStringLabeller());
	// Position the labels in the center of vertex
	visServer.getRenderer().getVertexLabelRenderer()
		.setPosition(Position.CNTR);
	// Edge shape as line
	visServer.getRenderContext().setEdgeShapeTransformer(
		new EdgeShape.Line<Node, Edge>());
	// Set VertexLookTranformer changes
	visServer.getRenderContext().setVertexShapeTransformer(
		vertexLookTransformer);
	// Set vertex color
	visServer.getRenderContext().setVertexFillPaintTransformer(
		new VertexColorTransformer());

	// Set background color
	Color backgroundColor = new Color(255, 255, 255);
	visServer.setBackground(backgroundColor);

	/** ************ PNG Dumping **************** */
	// Size the PNG picture will have which will be dumped.
	visServer.setSize(xPicSize, yPicSize);

	PNGDump dumper = new PNGDump();
	try {
	    System.out.println(generateFileName());
	    dumper.dumpComponent(new File(generateFileName()), visServer);
	} catch (IOException e) {
	    System.out.println("Image couldn't be generated!");
	    e.printStackTrace();
	}
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

    public void setShowEdgeLabels(boolean edgeLabels) {
	this.showLabels = edgeLabels;
    }
}
