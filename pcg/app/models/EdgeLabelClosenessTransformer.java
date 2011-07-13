package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import org.apache.commons.collections15.Transformer;

import datastructures.CustomTreeNode;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;

/** Does position EdgeLabels according to Edge length. */
public class EdgeLabelClosenessTransformer
	implements
	Transformer<Context<Graph<CustomTreeNode, CustomGraphEdge>, CustomGraphEdge>, Number> {
    @Override
    public Number transform(
	    Context<Graph<CustomTreeNode, CustomGraphEdge>, CustomGraphEdge> edge) {
	CustomGraphEdge curEdge = edge.element;
	Double length = curEdge.calcEdgeLength();
	return ((length / 1000) * 3);
    }
}
