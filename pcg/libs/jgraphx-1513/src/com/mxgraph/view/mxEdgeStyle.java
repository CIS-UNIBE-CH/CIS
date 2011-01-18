/**
 * $Id: mxEdgeStyle.java,v 1.36 2010-08-02 13:43:11 david Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package com.mxgraph.view;

import java.util.List;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxUtils;

/**
 * Provides various edge styles to be used as the values for
 * mxConstants.STYLE_EDGE in a cell style. Alternatevly, the mxConstants.
 * EDGESTYLE_* constants can be used to reference an edge style via the
 * mxStyleRegistry.
 */
public class mxEdgeStyle
{

	/**
	 * Defines the requirements for an edge style function.
	 */
	public interface mxEdgeStyleFunction
	{

		/**
		 * Implements an edge style function. At the time the function is called, the result
		 * array contains a placeholder (null) for the first absolute point,
		 * that is, the point where the edge and source terminal are connected.
		 * The implementation of the style then adds all intermediate waypoints
		 * except for the last point, that is, the connection point between the
		 * edge and the target terminal. The first ant the last point in the
		 * result array are then replaced with mxPoints that take into account
		 * the terminal's perimeter and next point on the edge.
		 * 
		 * @param state Cell state that represents the edge to be updated.
		 * @param source Cell state that represents the source terminal.
		 * @param target Cell state that represents the target terminal.
		 * @param points List of relative control points.
		 * @param result Array of points that represent the actual points of the
		 * edge.
		 */
		void apply(mxCellState state, mxCellState source, mxCellState target,
				List<mxPoint> points, List<mxPoint> result);

	}

	/**
	 * Provides an entity relation style for edges (as used in database
	 * schema diagrams).
	 */
	public static mxEdgeStyleFunction EntityRelation = new mxEdgeStyleFunction()
	{

		/* (non-Javadoc)
		 * @see com.mxgraph.view.mxEdgeStyle.mxEdgeStyleFunction#apply(com.mxgraph.view.mxCellState, com.mxgraph.view.mxCellState, com.mxgraph.view.mxCellState, java.util.List, java.util.List)
		 */
		public void apply(mxCellState state, mxCellState source,
				mxCellState target, List<mxPoint> points, List<mxPoint> result)
		{
			mxGraphView view = state.getView();
			mxIGraphModel model = view.getGraph().getModel();
			double segment = mxUtils.getDouble(state.getStyle(),
					mxConstants.STYLE_SEGMENT, mxConstants.ENTITY_SEGMENT)
					* state.view.getScale();

			mxPoint p0 = state.getAbsolutePoint(0);
			mxPoint pe = state
					.getAbsolutePoint(state.getAbsolutePointCount() - 1);

			boolean isSourceLeft = false;

			if (p0 != null)
			{
				source = new mxCellState();
				source.setX(p0.getX());
				source.setY(p0.getY());
			}
			else if (source != null)
			{
				mxGeometry sourceGeometry = model.getGeometry(source.cell);

				if (sourceGeometry.isRelative())
				{
					isSourceLeft = sourceGeometry.getX() <= 0.5;
				}
				else if (target != null)
				{
					isSourceLeft = target.getX() + target.getWidth() < source
							.getX();
				}
			}

			boolean isTargetLeft = true;

			if (pe != null)
			{
				target = new mxCellState();
				target.setX(pe.getX());
				target.setY(pe.getY());
			}
			else if (target != null)
			{
				mxGeometry targetGeometry = model.getGeometry(target.cell);

				if (targetGeometry.isRelative())
				{
					isTargetLeft = targetGeometry.getX() <= 0.5;
				}
				else if (source != null)
				{
					isTargetLeft = source.getX() + source.getWidth() < target
							.getX();
				}
			}

			if (source != null && target != null)
			{
				double x0 = (isSourceLeft) ? source.getX() : source.getX()
						+ source.getWidth();
				double y0 = view.getRoutingCenterY(source);

				double xe = (isTargetLeft) ? target.getX() : target.getX()
						+ target.getWidth();
				double ye = view.getRoutingCenterY(target);

				double seg = segment;

				double dx = (isSourceLeft) ? -seg : seg;
				mxPoint dep = new mxPoint(x0 + dx, y0);
				result.add(dep);

				dx = (isTargetLeft) ? -seg : seg;
				mxPoint arr = new mxPoint(xe + dx, ye);

				// Adds intermediate points if both go out on same side
				if (isSourceLeft == isTargetLeft)
				{
					double x = (isSourceLeft) ? Math.min(x0, xe) - segment
							: Math.max(x0, xe) + segment;
					result.add(new mxPoint(x, y0));
					result.add(new mxPoint(x, ye));
				}
				else if ((dep.getX() < arr.getX()) == isSourceLeft)
				{
					double midY = y0 + (ye - y0) / 2;
					result.add(new mxPoint(dep.getX(), midY));
					result.add(new mxPoint(arr.getX(), midY));
				}

				result.add(arr);
			}
		}
	};

	/**
	 * Provides a self-reference, aka. loop.
	 */
	public static mxEdgeStyleFunction Loop = new mxEdgeStyleFunction()
	{

		/* (non-Javadoc)
		 * @see com.mxgraph.view.mxEdgeStyle.mxEdgeStyleFunction#apply(com.mxgraph.view.mxCellState, com.mxgraph.view.mxCellState, com.mxgraph.view.mxCellState, java.util.List, java.util.List)
		 */
		public void apply(mxCellState state, mxCellState source,
				mxCellState target, List<mxPoint> points, List<mxPoint> result)
		{
			if (source != null)
			{
				mxGraphView view = state.getView();
				mxGraph graph = view.getGraph();
				mxPoint pt = (points != null && points.size() > 0) ? points
						.get(0) : null;

				if (pt != null)
				{
					pt = view.transformControlPoint(state, pt);

					if (source.contains(pt.getX(), pt.getY()))
					{
						pt = null;
					}
				}

				double x = 0;
				double dx = 0;
				double y = 0;
				double dy = 0;

				double seg = mxUtils.getDouble(state.getStyle(),
						mxConstants.STYLE_SEGMENT, graph.getGridSize())
						* view.getScale();
				String dir = mxUtils
						.getString(state.getStyle(),
								mxConstants.STYLE_DIRECTION,
								mxConstants.DIRECTION_WEST);

				if (dir.equals(mxConstants.DIRECTION_NORTH)
						|| dir.equals(mxConstants.DIRECTION_SOUTH))
				{
					x = view.getRoutingCenterX(source);
					dx = seg;
				}
				else
				{
					y = view.getRoutingCenterY(source);
					dy = seg;
				}

				if (pt == null || pt.getX() < source.getX()
						|| pt.getX() > source.getX() + source.getWidth())
				{
					if (pt != null)
					{
						x = pt.getX();
						dy = Math.max(Math.abs(y - pt.getY()), dy);
					}
					else
					{
						if (dir.equals(mxConstants.DIRECTION_NORTH))
						{
							y = source.getY() - 2 * dx;
						}
						else if (dir.equals(mxConstants.DIRECTION_SOUTH))
						{
							y = source.getY() + source.getHeight() + 2 * dx;
						}
						else if (dir.equals(mxConstants.DIRECTION_EAST))
						{
							x = source.getX() - 2 * dy;
						}
						else
						{
							x = source.getX() + source.getWidth() + 2 * dy;
						}
					}
				}
				else
				{
					// pt != null
					x = view.getRoutingCenterX(source);
					dx = Math.max(Math.abs(x - pt.getX()), dy);
					y = pt.getY();
					dy = 0;
				}

				result.add(new mxPoint(x - dx, y - dy));
				result.add(new mxPoint(x + dx, y + dy));
			}
		}
	};

	/**
	 * Uses either SideToSide or TopToBottom depending on the horizontal
	 * flag in the cell style. SideToSide is used if horizontal is true or
	 * unspecified.
	 */
	public static mxEdgeStyleFunction ElbowConnector = new mxEdgeStyleFunction()
	{

		/* (non-Javadoc)
		 * @see com.mxgraph.view.mxEdgeStyle.mxEdgeStyleFunction#apply(com.mxgraph.view.mxCellState, com.mxgraph.view.mxCellState, com.mxgraph.view.mxCellState, java.util.List, java.util.List)
		 */
		public void apply(mxCellState state, mxCellState source,
				mxCellState target, List<mxPoint> points, List<mxPoint> result)
		{
			mxPoint pt = (points != null && points.size() > 0) ? points.get(0)
					: null;

			boolean vertical = false;
			boolean horizontal = false;

			if (source != null && target != null)
			{
				if (pt != null)
				{
					double left = Math.min(source.getX(), target.getX());
					double right = Math.max(source.getX() + source.getWidth(),
							target.getX() + target.getWidth());

					double top = Math.min(source.getY(), target.getY());
					double bottom = Math.max(
							source.getY() + source.getHeight(), target.getY()
									+ target.getHeight());

					pt = state.getView().transformControlPoint(state, pt);

					vertical = pt.getY() < top || pt.getY() > bottom;
					horizontal = pt.getX() < left || pt.getX() > right;
				}
				else
				{
					double left = Math.max(source.getX(), target.getX());
					double right = Math.min(source.getX() + source.getWidth(),
							target.getX() + target.getWidth());

					vertical = left == right;

					if (!vertical)
					{
						double top = Math.max(source.getY(), target.getY());
						double bottom = Math.min(source.getY()
								+ source.getHeight(), target.getY()
								+ target.getHeight());

						horizontal = top == bottom;
					}
				}
			}

			if (!horizontal
					&& (vertical || mxUtils.getString(state.getStyle(),
							mxConstants.STYLE_ELBOW, "").equals(
							mxConstants.ELBOW_VERTICAL)))
			{
				mxEdgeStyle.TopToBottom.apply(state, source, target, points,
						result);
			}
			else
			{
				mxEdgeStyle.SideToSide.apply(state, source, target, points,
						result);
			}
		}
	};

	/**
	 * Provides a vertical elbow edge.
	 */
	public static mxEdgeStyleFunction SideToSide = new mxEdgeStyleFunction()
	{

		/* (non-Javadoc)
		 * @see com.mxgraph.view.mxEdgeStyle.mxEdgeStyleFunction#apply(com.mxgraph.view.mxCellState, com.mxgraph.view.mxCellState, com.mxgraph.view.mxCellState, java.util.List, java.util.List)
		 */
		public void apply(mxCellState state, mxCellState source,
				mxCellState target, List<mxPoint> points, List<mxPoint> result)
		{
			mxGraphView view = state.getView();
			mxPoint pt = ((points != null && points.size() > 0) ? points.get(0)
					: null);
			mxPoint p0 = state.getAbsolutePoint(0);
			mxPoint pe = state
					.getAbsolutePoint(state.getAbsolutePointCount() - 1);

			if (pt != null)
			{
				pt = view.transformControlPoint(state, pt);
			}

			if (p0 != null)
			{
				source = new mxCellState();
				source.setX(p0.getX());
				source.setY(p0.getY());
			}

			if (pe != null)
			{
				target = new mxCellState();
				target.setX(pe.getX());
				target.setY(pe.getY());
			}

			if (source != null && target != null)
			{
				double l = Math.max(source.getX(), target.getX());
				double r = Math.min(source.getX() + source.getWidth(), target
						.getX()
						+ target.getWidth());

				double x = (pt != null) ? pt.getX() : r + (l - r) / 2;

				double y1 = view.getRoutingCenterY(source);
				double y2 = view.getRoutingCenterY(target);

				if (pt != null)
				{
					if (pt.getY() >= source.getY()
							&& pt.getY() <= source.getY() + source.getHeight())
					{
						y1 = pt.getY();
					}

					if (pt.getY() >= target.getY()
							&& pt.getY() <= target.getY() + target.getHeight())
					{
						y2 = pt.getY();
					}
				}

				if (!target.contains(x, y1) && !source.contains(x, y1))
				{
					result.add(new mxPoint(x, y1));
				}

				if (!target.contains(x, y2) && !source.contains(x, y2))
				{
					result.add(new mxPoint(x, y2));
				}

				if (result.size() == 1)
				{
					if (pt != null)
					{
						result.add(new mxPoint(x, pt.getY()));
					}
					else
					{
						double t = Math.max(source.getY(), target.getY());
						double b = Math.min(source.getY() + source.getHeight(),
								target.getY() + target.getHeight());

						result.add(new mxPoint(x, t + (b - t) / 2));
					}
				}
			}
		}
	};

	/**
	 * Provides a horizontal elbow edge.
	 */
	public static mxEdgeStyleFunction TopToBottom = new mxEdgeStyleFunction()
	{

		/* (non-Javadoc)
		 * @see com.mxgraph.view.mxEdgeStyle.mxEdgeStyleFunction#apply(com.mxgraph.view.mxCellState, com.mxgraph.view.mxCellState, com.mxgraph.view.mxCellState, java.util.List, java.util.List)
		 */
		public void apply(mxCellState state, mxCellState source,
				mxCellState target, List<mxPoint> points, List<mxPoint> result)
		{
			mxGraphView view = state.getView();
			mxPoint pt = ((points != null && points.size() > 0) ? points.get(0)
					: null);
			mxPoint p0 = state.getAbsolutePoint(0);
			mxPoint pe = state
					.getAbsolutePoint(state.getAbsolutePointCount() - 1);

			if (pt != null)
			{
				pt = view.transformControlPoint(state, pt);
			}

			if (p0 != null)
			{
				source = new mxCellState();
				source.setX(p0.getX());
				source.setY(p0.getY());
			}

			if (pe != null)
			{
				target = new mxCellState();
				target.setX(pe.getX());
				target.setY(pe.getY());
			}

			if (source != null && target != null)
			{
				double t = Math.max(source.getY(), target.getY());
				double b = Math.min(source.getY() + source.getHeight(), target
						.getY()
						+ target.getHeight());

				double x = view.getRoutingCenterX(source);

				if (pt != null && pt.getX() >= source.getX()
						&& pt.getX() <= source.getX() + source.getWidth())
				{
					x = pt.getX();
				}

				double y = (pt != null) ? pt.getY() : b + (t - b) / 2;

				if (!target.contains(x, y) && !source.contains(x, y))
				{
					result.add(new mxPoint(x, y));
				}

				if (pt != null && pt.getX() >= target.getX()
						&& pt.getX() <= target.getX() + target.getWidth())
				{
					x = pt.getX();
				}
				else
				{
					x = view.getRoutingCenterX(target);
				}

				if (!target.contains(x, y) && !source.contains(x, y))
				{
					result.add(new mxPoint(x, y));
				}

				if (result.size() == 1)
				{
					if (pt != null)
					{
						result.add(new mxPoint(pt.getX(), y));
					}
					else
					{
						double l = Math.max(source.getX(), target.getX());
						double r = Math.min(source.getX() + source.getWidth(),
								target.getX() + target.getWidth());

						result.add(new mxPoint(l + (r - l) / 2, y));
					}
				}
			}
		}
	};

}
