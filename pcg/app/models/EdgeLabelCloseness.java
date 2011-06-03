package models;
import org.apache.commons.collections15.Transformer;

import tree.CustomTreeNode;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.Context;


public class EdgeLabelCloseness implements Transformer<Context<Graph<CustomTreeNode, CustomEdge>, CustomEdge>, Number> {
    @Override
    public Number transform(
	    Context<Graph<CustomTreeNode, CustomEdge>, CustomEdge> arg0) {
	// TODO Auto-generated method stub
	CustomEdge edge = arg0.element;
	OrderedSparseMultigraph<CustomTreeNode, CustomEdge> mygraph = (OrderedSparseMultigraph<CustomTreeNode, CustomEdge>) arg0.graph;
	return 0.3; 

    }

}

