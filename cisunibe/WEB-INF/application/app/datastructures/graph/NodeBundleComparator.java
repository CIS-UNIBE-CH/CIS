package datastructures.graph;

import java.util.Comparator;

public class NodeBundleComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
	if (o1.getLevel() == o2.getLevel())
	    return o1.getBundle() - o2.getBundle();
	else if (o1.getLevel() > o2.getLevel())
	    return -1;
	else
	    return 1;
    }
}
