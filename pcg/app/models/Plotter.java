package models;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;

import tree.CustomTree;
import tree.CustomTreeNode;

/** Plots a JGraphT Graph in a Java Applet ajsdkflajsdflkajsföl */
public class Plotter extends JApplet {

	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private JGraphModelAdapter<CustomTreeNode, CustomEdge> jgAdapter;
	private Dimension appletSize;

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
		CustomTreeNode child3 = new CustomTreeNode("child3");
		CustomTreeNode child4 = new CustomTreeNode("child4");
		CustomTreeNode child5 = new CustomTreeNode("child5");
		CustomTreeNode child6 = new CustomTreeNode("child6");
		tree.setRoot(root);
		tree.addChildtoRootX(child1, root);
		tree.addChildtoRootX(child2, root);
		tree.addChildtoRootX(child3, root);
		tree.addChildtoRootX(child4, root);
		tree.addChildtoRootX(child5, root);
		tree.addChildtoRootX(child6, root);

		Parser parser = new Parser();
		appletSize = new Dimension(parser.getSizeAppletx(),
				parser.getSizeApplety());
		jgAdapter = parser.getJgAdapter();

		JGraph jgraph = new JGraph(jgAdapter);

		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(appletSize);
	}

	/** Will only be used for displaying applet */
	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(appletSize);

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
