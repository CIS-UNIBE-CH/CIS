package datastructures.graph;

import java.util.Comparator;

public class NodeLevelComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
	return o2.getLevel() - o1.getLevel();
    }
}
