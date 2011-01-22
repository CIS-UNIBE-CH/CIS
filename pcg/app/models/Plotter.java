package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Timestamp;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgrapht.ext.JGraphModelAdapter;

import play.libs.Time;

import tree.CustomTree;
import tree.CustomTreeNode;

public class Plotter {

	private static final Color DEFAULT_BG_COLOR = Color.decode("#ffffff");
	private static JGraphModelAdapter<CustomTreeNode, CustomEdge> jgAdapter;
	private static Dimension appletSize;
	private static JFrame frame;
	private static java.sql.Timestamp timestamp;

	public Plotter() {
		frame = new JFrame();
		frame.getContentPane();
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(false);
		init();
	}

	public static void init() {

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
		

		frame.getContentPane().add(jgraph);
		frame.setSize(appletSize);

		BufferedImage img = jgraph.getImage(jgraph.getBackground(), 10);
		
		timestamp = new java.sql.Timestamp(new GregorianCalendar().getTimeInMillis());
		String imagePath = "./pcg/public/images/graphs/" + timestamp.toString() + ".png";
		
		try {
			ImageIO.write(img, "png", new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Will only be used for displaying applet */
	private static void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(appletSize);

		Color c = DEFAULT_BG_COLOR;
		String colorStr = null;

		if (colorStr != null) {
			c = Color.decode(colorStr);
		}

		jg.setBackground(c);
	}
	
	public String getImageSource() {
		return "/public/images/graphs/" + timestamp.toString() + ".png";
	}
}