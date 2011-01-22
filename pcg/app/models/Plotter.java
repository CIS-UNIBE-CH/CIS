package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgrapht.ext.JGraphModelAdapter;

import tree.CustomTree;
import tree.CustomTreeNode;

/**
 * Plots form a given JGraphModelAdapter a JGraph in a Jframe and generates a
 * image file out of the Jgraph.
 */
public class Plotter {

	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static JGraphModelAdapter<CustomTreeNode, CustomEdge> jgAdapter;
	private static Dimension appletSize;
	private static JFrame frame;

	public Plotter() {
		frame = new JFrame();
		frame.getContentPane();
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(false);
		init();
	}

	// TODO When algorithm implemented remove example data and move init method
	// to constructor
	public static void init() {
		// ***** Sample data *****
		CustomTree tree = CustomTree.getInstance();
		CustomTreeNode root = new CustomTreeNode("root");
		CustomTreeNode child1 = new CustomTreeNode("child1");
		child1.setBundle("1");
		CustomTreeNode child2 = new CustomTreeNode("child2");
		child2.setBundle("1");
		CustomTreeNode child3 = new CustomTreeNode("child3");
		CustomTreeNode child4 = new CustomTreeNode("child4");
		child4.setBundle("2");
		CustomTreeNode child5 = new CustomTreeNode("child5");
		child5.setBundle("2");
		CustomTreeNode child6 = new CustomTreeNode("child6");
		tree.setRoot(root);
		tree.addChildtoRootX(child1, root);
		tree.addChildtoRootX(child2, root);
		tree.addChildtoRootX(child3, root);
		tree.addChildtoRootX(child4, root);
		tree.addChildtoRootX(child5, root);
		tree.addChildtoRootX(child6, root);
		// ***** Sample data *****

		Parser parser = new Parser();

		// get calculated applet size from parser
		appletSize = new Dimension(parser.getSizeAppletx(),
				parser.getSizeApplety());

		jgAdapter = parser.getJgAdapter();

		// Add JgraphModelAdapter to Jgraph
		JGraph jgraph = new JGraph(jgAdapter);

		adjustImageSettings(jgraph);
		frame.getContentPane().add(jgraph);
		frame.setSize(appletSize);

		// Write image to file
		BufferedImage img = jgraph.getImage(jgraph.getBackground(), 10);
		try {
			ImageIO.write(img, "png", new File("picture9000.png"));
		} catch (IOException e) {
			System.out.println("Sorry image couldn't be generated!");
			e.printStackTrace();
		}
	}

	/**
	 * Will be used to set background color of generated image
	 * 
	 * @param jgraph
	 *            the generated JGraph
	 */
	private static void adjustImageSettings(JGraph jgraph) {
		jgraph.setPreferredSize(appletSize);
		Color c = DEFAULT_BG_COLOR;
		jgraph.setBackground(c);
	}
}