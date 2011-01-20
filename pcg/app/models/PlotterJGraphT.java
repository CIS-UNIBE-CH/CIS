package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/** This class plots a JgraphT graph with an adapterr with JGraph */
public class PlotterJGraphT extends JApplet {

	private static final long serialVersionUID = 1L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(400, 400);
	private JGraphModelAdapter<String, DefaultEdge> jgAdapter;

	public static void main(String[] args) {
		PlotterJGraphT applet = new PlotterJGraphT();
		applet.init();

		JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public void init() {
		// create a JGraphT graph
		DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);

		// create a visualization using JGraph, via an adapter
		String u1 = "U1";
		String u2 = "U2";
		String u3 = "U3";
		String u4 = "U4";
		String u5 = "U5";
		String u6 = "U6";
		String w1 = "W1";
		String w2 = "W2";

		// creating nodes and adding them to graph
		graph.addVertex(u1);
		graph.addVertex(u2);
		graph.addVertex(u3);
		graph.addVertex(u4);
		graph.addVertex(u5);
		graph.addVertex(u6);
		graph.addVertex(w1);
		graph.addVertex(w2);

		// creating edges with numbering
		CustomEdge e1 = new CustomEdge();
		CustomEdge e2 = new CustomEdge();
		e2.setNumber("1");
		CustomEdge e3 = new CustomEdge();
		e3.setNumber("1");
		CustomEdge e4 = new CustomEdge();
		e4.setNumber("1");
		CustomEdge e5 = new CustomEdge();
		CustomEdge e6 = new CustomEdge();
		CustomEdge e7 = new CustomEdge();

		// set directions of edges and add them to graph
		graph.addEdge(u1, w1, e1);
		graph.addEdge(u2, w1, e2);
		graph.addEdge(u3, w1, e3);
		graph.addEdge(u4, w1, e4);
		graph.addEdge(u5, w2, e5);
		graph.addEdge(u6, w2, e6);
		graph.addEdge(w1, w2, e7);

		// Initialize Adapter
		jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(graph);

		// position nodes nicely within JGraph component
		positionNode(u1, 50, 50);
		positionNode(u2, 100, 50);
		positionNode(u3, 150, 50);
		positionNode(u4, 200, 50);
		positionNode(u5, 175, 200);
		positionNode(u6, 225, 200);
		positionNode(w1, 125, 200);
		positionNode(w2, 175, 350);

		JGraph jgraph = new JGraph(jgAdapter);

		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);
	}

	private void positionNode(Object node, int x, int y) {

		DefaultGraphCell cell = jgAdapter.getVertexCell(node);

		AttributeMap attr = cell.getAttributes();
		Rectangle2D bounds = GraphConstants.getBounds(attr);

		int width = 40;
		int height = 30;
		Rectangle2D newBounds = new Rectangle2D.Double(x, y, width, height);

		GraphConstants.setBounds(attr, newBounds);
		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		jgAdapter.edit(cellAttr, null, null, null);
	}

	/** Will only be used for displaying applett */
	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(DEFAULT_SIZE);

		Color c = DEFAULT_BG_COLOR;
		String colorStr = null;

		try {
			colorStr = getParameter("bgcolor");
		} catch (Exception e) {
		}

		if (colorStr != null) {
			c = Color.decode(colorStr);
		}

		jg.setBackground(c);
	}
}
