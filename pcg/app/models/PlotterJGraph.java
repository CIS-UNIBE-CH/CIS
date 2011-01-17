package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

/** This class plots graphs with JGpraph */
public class PlotterJGraph extends JApplet {

	private static final long serialVersionUID = 1L;
	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(400, 400);

	public static void main(String[] args) {
		PlotterJGraph applet = new PlotterJGraph();
		applet.init();

		JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public void init() {
		// Create nodes
		DefaultGraphCell cell1 = new DefaultGraphCell("U1");
		DefaultGraphCell cell2 = new DefaultGraphCell("U2");
		DefaultGraphCell cell3 = new DefaultGraphCell("W");

		// Create empty nodes for edges
		DefaultGraphCell cell4 = new DefaultGraphCell();
		DefaultGraphCell cell5 = new DefaultGraphCell();

		// Create Edges
		DefaultEdge edge = new DefaultEdge();
		DefaultEdge edge1 = new DefaultEdge();

		// Set direction of edges
		edge.setSource(cell1);
		edge.setTarget(cell3);
		cell4 = edge;

		edge1.setSource(cell2);
		edge1.setTarget(cell3);
		cell5 = edge1;

		// Set Arrow Style for edge
		int arrow = GraphConstants.ARROW_CLASSIC;
		GraphConstants.setLineEnd(edge.getAttributes(), arrow);
		GraphConstants.setEndFill(edge.getAttributes(), true);

		GraphModel model = new DefaultGraphModel();
		JGraph jgraph = new JGraph(model);

		// plot nodes as circles
		jgraph.getGraphLayoutCache().setFactory(new GPCellViewFactory());

		jgraph.getGraphLayoutCache().insert(positionNode(cell1, 75, 50));
		jgraph.getGraphLayoutCache().insert(positionNode(cell2, 175, 50));
		jgraph.getGraphLayoutCache().insert(positionNode(cell3, 125, 350));
		jgraph.getGraphLayoutCache().insert(cell4);
		jgraph.getGraphLayoutCache().insert(cell5);

		// Add stuff to applet
		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);
		resize(DEFAULT_SIZE);
	}

	private DefaultGraphCell positionNode(DefaultGraphCell cell, int x, int y) {
		DefaultGraphCell finalCell = createVertex(x, y, 30, 30, null, false,
				cell, "models.JGraphEllipseView");

		return finalCell;
	}

	/** Used to manipulate cell, to make shape elliptic, change bg color etc */
	public static DefaultGraphCell createVertex(double x, double y, double w,
			double h, Color bg, boolean raised, DefaultGraphCell cell,
			String viewClass) {

		// set the view class (indirection for the renderer and the editor)
		if (viewClass != null)
			GPCellViewFactory.setViewClass(cell.getAttributes(), viewClass);

		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(
				x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(),
					BorderFactory.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);

		// Add a Floating Port
		cell.addPort();

		return cell;
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
