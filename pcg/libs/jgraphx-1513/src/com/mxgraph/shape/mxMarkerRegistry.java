package com.mxgraph.shape;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Hashtable;
import java.util.Map;

import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.view.mxCellState;

public class mxMarkerRegistry
{
	/**
	 * 
	 */
	protected static Map<String, mxIMarker> markers = new Hashtable<String, mxIMarker>();

	static
	{
		mxIMarker tmp = new mxIMarker()
		{
			public mxPoint paintMarker(mxGraphics2DCanvas canvas,
					mxCellState state, String type, mxPoint pe, double nx,
					double ny, double size)
			{
				Polygon poly = new Polygon();
				poly.addPoint((int) Math.round(pe.getX()),
						(int) Math.round(pe.getY()));
				poly.addPoint((int) Math.round(pe.getX() - nx - ny / 2),
						(int) Math.round(pe.getY() - ny + nx / 2));

				if (type.equals(mxConstants.ARROW_CLASSIC))
				{
					poly.addPoint((int) Math.round(pe.getX() - nx * 3 / 4),
							(int) Math.round(pe.getY() - ny * 3 / 4));
				}

				poly.addPoint((int) Math.round(pe.getX() + ny / 2 - nx),
						(int) Math.round(pe.getY() - ny - nx / 2));

				canvas.fillShape(poly);
				canvas.getGraphics().draw(poly);

				return new mxPoint(-nx, -ny);
			}
		};

		registerMarker(mxConstants.ARROW_CLASSIC, tmp);
		registerMarker(mxConstants.ARROW_BLOCK, tmp);

		registerMarker(mxConstants.ARROW_OPEN, new mxIMarker()
		{
			public mxPoint paintMarker(mxGraphics2DCanvas canvas,
					mxCellState state, String type, mxPoint pe, double nx,
					double ny, double size)
			{
				nx *= 1.2;
				ny *= 1.2;

				canvas.getGraphics().draw(
						new Line2D.Float((int) Math.round(pe.getX() - nx - ny
								/ 2),
								(int) Math.round(pe.getY() - ny + nx / 2),
								(int) Math.round(pe.getX() - nx / 6),
								(int) Math.round(pe.getY() - ny / 6)));
				canvas.getGraphics().draw(
						new Line2D.Float((int) Math.round(pe.getX() - nx / 6),
								(int) Math.round(pe.getY() - ny / 6),
								(int) Math.round(pe.getX() + ny / 2 - nx),
								(int) Math.round(pe.getY() - ny - nx / 2)));

				return new mxPoint(-nx / 2, -ny / 2);
			}
		});
		
		registerMarker(mxConstants.ARROW_OVAL, new mxIMarker()
		{
			public mxPoint paintMarker(mxGraphics2DCanvas canvas,
					mxCellState state, String type, mxPoint pe, double nx,
					double ny, double size)
			{
				nx *= 1.2;
				ny *= 1.2;
				size *= 1.2;

				int cx = (int) Math.round(pe.getX() - nx / 2);
				int cy = (int) Math.round(pe.getY() - ny / 2);
				int a = (int) Math.round(size / 2);
				int a2 = (int) Math.round(size);
				Shape shape = new Ellipse2D.Float(cx - a, cy - a, a2, a2);

				canvas.fillShape(shape);
				canvas.getGraphics().draw(shape);

				return new mxPoint(-nx / 2, -ny / 2);
			}
		});
		
		
		registerMarker(mxConstants.ARROW_DIAMOND, new mxIMarker()
		{
			public mxPoint paintMarker(mxGraphics2DCanvas canvas,
					mxCellState state, String type, mxPoint pe, double nx,
					double ny, double size)
			{
				nx *= 1.2;
				ny *= 1.2;

				Polygon poly = new Polygon();
				poly.addPoint((int) Math.round(pe.getX()),
						(int) Math.round(pe.getY()));
				poly.addPoint((int) Math.round(pe.getX() - nx / 2 - ny / 2),
						(int) Math.round(pe.getY() + nx / 2 - ny / 2));
				poly.addPoint((int) Math.round(pe.getX() - nx),
						(int) Math.round(pe.getY() - ny));
				poly.addPoint((int) Math.round(pe.getX() - nx / 2 + ny / 2),
						(int) Math.round(pe.getY() - ny / 2 - nx / 2));

				canvas.fillShape(poly);
				canvas.getGraphics().draw(poly);

				return new mxPoint(-nx / 2, -ny / 2);
			}
		});
	}

	/**
	 * 
	 */
	public static mxIMarker getMarker(String name)
	{
		return markers.get(name);
	}

	/**
	 * 
	 */
	public static void registerMarker(String name, mxIMarker marker)
	{
		markers.put(name, marker);
	}

}
