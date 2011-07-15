package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import org.apache.commons.collections15.Transformer;

import datastructures.graph.Edge;
import datastructures.graph.Node;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;

/** Does position EdgeLabels according to Edge length. */
public class EdgeLabelClosenessTransformer
	implements
	Transformer<Context<Graph<Node, Edge>, Edge>, Number> {
    @Override
    public Number transform(
	    Context<Graph<Node, Edge>, Edge> edge) {
	Edge curEdge = edge.element;
	Double length = curEdge.calcEdgeLength();
	return ((length / 1000) * 3);
    }
}
