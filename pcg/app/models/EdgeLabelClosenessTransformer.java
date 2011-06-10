package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import org.apache.commons.collections15.Transformer;

import tree.CustomTreeNode;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;

/** Does position EdgeLabels according to Edge length. */
public class EdgeLabelClosenessTransformer
	implements
	Transformer<Context<Graph<CustomTreeNode, CustomEdge>, CustomEdge>, Number> {
    @Override
    public Number transform(
	    Context<Graph<CustomTreeNode, CustomEdge>, CustomEdge> edge) {
	CustomEdge curEdge = edge.element;
	Double length = curEdge.calcEdgeLength();
	return ((length / 1000) * 1.5);
    }
}
