package models;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;

import tree.CustomTree;
import tree.CustomTreeNode;


/** Plots a JGraphT Graph in a Java Applet */
public class Plotter extends JApplet {

	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(600, 800);
	private JGraphModelAdapter<CustomTreeNode, DefaultEdge> jgAdapter;

	public static void main(String[] args) {
		Plotter applet = new Plotter();
		applet.init();

		JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public void init() {

		CustomTree tree = CustomTree.getInstance();
		CustomTreeNode root = new CustomTreeNode("root");
		CustomTreeNode child1 = new CustomTreeNode("child1");
		CustomTreeNode child2 = new CustomTreeNode("child2");
		tree.setRoot(root);
		tree.addChildtoRootX(child1, root);
		tree.addChildtoRootX(child2, root);
		tree.getChildren();

		Parser parser = new Parser();
		jgAdapter = parser.getJgAdapter();

		JGraph jgraph = new JGraph(jgAdapter);

		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);
	}

	/** Will only be used for displaying applet */
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
