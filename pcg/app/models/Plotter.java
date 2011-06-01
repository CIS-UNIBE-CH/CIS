package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */


/**
 * Plots form a given JGraphModelAdapter a JGraph in a Jframe and generates a
 * image file out of the JGraph.
 */
public class Plotter {

	/*
	 * private static final Color DEFAULT_BG_COLOR = Color.decode("#ffffff"); //
	 * private static JGraphModelAdapter<CustomTreeNode, CustomEdge> jgAdapter;
	 * private static Dimension appletSize; private static JFrame frame; private
	 * static Date now; private static SimpleDateFormat dateFormat; private
	 * static String path;
	 * 
	 * public Plotter() { frame = new JFrame(); frame.getContentPane();
	 * frame.setTitle("JGraphT Adapter to JGraph Demo");
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.pack();
	 * frame.setVisible(false); now = new Date(); dateFormat = new
	 * SimpleDateFormat("ddMMyyyy-HHmmssS");
	 * 
	 * this.path = "./pcg/public/images/graphs/"; }
	 * 
	 * public static void plot(TreeToJgraph parser) {
	 * 
	 * // get calculated applet size from parser appletSize = new Dimension(600,
	 * 600);
	 * 
	 * // jgAdapter = parser.getJgAdapter();
	 * 
	 * // Add JgraphModelAdapter to Jgraph // JGraph jgraph = new
	 * JGraph(jgAdapter);
	 * 
	 * adjustImageSettings(jgraph);
	 * 
	 * frame.getContentPane().add(jgraph); frame.setSize(appletSize);
	 * 
	 * // Write image to file BufferedImage img =
	 * jgraph.getImage(jgraph.getBackground(), 10);
	 * 
	 * try { ImageIO.write(img, "png", new File(generateFileName())); } catch
	 * (IOException e) {
	 * System.out.println("Sorry image couldn't be generated!");
	 * e.printStackTrace(); } }
	 * 
	 * /** Will be used to set background color of generated image
	 * 
	 * @param jgraph the generated JGraph
	 */
	/*
	 * private static void adjustImageSettings(JGraph jgraph) {
	 * jgraph.setPreferredSize(appletSize); Color c = DEFAULT_BG_COLOR;
	 * jgraph.setBackground(c); }
	 * 
	 * public String getImageSource() { return "/public/images/graphs/" +
	 * dateFormat.format(now).toString() + ".png"; }
	 * 
	 * public void setPath(String path) { this.path = path; }
	 * 
	 * public static String generateFileName() { return path +
	 * dateFormat.format(now).toString() + ".png"; }
	 */
}
