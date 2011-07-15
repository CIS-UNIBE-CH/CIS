//package algorithms;
//
///** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */
//
//import datastructures.CustomTree;
//import datastructures.Node;
//
//public class QuadroTest {
//
//    private int[][] field = new int[2][2];
//    private String f1, f2;
//
//    public QuadroTest(int[][] field, String f1, String f2) {
//	this.field = field;
//	this.f1 = f1;
//	this.f2 = f2;
//
//    }
//
//    public CustomTree creatGraph() {
//
//	CustomTree tree = new CustomTree();
//	Node root = new Node("W");
//	Node a = new Node(f1);
//	Node na = new Node("¬" + f1);
//	Node b = new Node(f2);
//	Node nb = new Node("¬" + f2);
//
//	Node x1 = new Node("X1");
//	Node x2 = new Node("X2");
//
//	int sum = calcSumAll();
//	switch (sum) {
//	case 0:
//	    System.out.println("error: 0");
//	    return null;
//
//	case 1:
//	    tree.addChildtoParentX(a, root);
//	    a.setBundle("1");
//	    if (calcRowSum(0) == 1) {
//		if (field[0][0] == 1) {
//		    tree.addChildtoParentX(b, root);
//		    b.setBundle("1");
//		} else {
//		    tree.addChildtoParentX(nb, root);
//		    nb.setBundle("1");
//		}
//		tree.addChildtoParentX(x1, root);
//		x1.setBundle("1");
//	    } else {
//		tree.addChildtoParentX(x1, root);
//		x1.setBundle("1");
//		tree.addChildtoParentX(na, root);
//		na.setBundle("2");
//		if (field[1][0] == 1) {
//		    tree.addChildtoParentX(b, root);
//		    b.setBundle("2");
//		} else {
//		    tree.addChildtoParentX(nb, root);
//		    nb.setBundle("2");
//		}
//		tree.addChildtoParentX(x2, root);
//		x2.setBundle("2");
//	    }
//	    break;
//
//	case 2:
//	    tree.addChildtoParentX(a, root);
//	    a.setBundle("1");
//	    tree.addChildtoParentX(x1, root);
//	    x1.setBundle("1");
//	    if (calcRowSum(0) == 2) {
//		System.out.println("error: 2");
//		return null;
//	    } else if (calcRowSum(1) == 2) {
//		tree.addChildtoParentX(na, root);
//		na.setBundle("2");
//	    } else if (calcColSum(0) == 2) {
//		tree.addChildtoParentX(b, root);
//		b.setBundle("2");
//	    } else if (calcColSum(1) == 2) {
//		tree.addChildtoParentX(nb, root);
//		nb.setBundle("2");
//	    } else {
//		if (field[0][0] == 0) {
//		    tree.addChildtoParentX(nb, root);
//		    nb.setBundle("1");
//		    tree.addChildtoParentX(b, root);
//		    b.setBundle("2");
//		    tree.addChildtoParentX(na, root);
//		    na.setBundle("2");
//		} else {
//		    tree.addChildtoParentX(b, root);
//		    b.setBundle("1");
//		    tree.addChildtoParentX(nb, root);
//		    nb.setBundle("2");
//		    tree.addChildtoParentX(na, root);
//		    na.setBundle("2");
//
//		}
//	    }
//	    tree.addChildtoParentX(x2, root);
//	    x2.setBundle("2");
//	    break;
//	case 3:
//	    tree.addChildtoParentX(a, root);
//	    a.setBundle("1");
//	    tree.addChildtoParentX(x1, root);
//	    x1.setBundle("1");
//	    if (calcRowSum(1) == 2) {
//		tree.addChildtoParentX(na, root);
//		na.setBundle("2");
//		tree.addChildtoParentX(x2, root);
//		x2.setBundle("2");
//		break;
//	    }
//	    if (field[1][0] == 0) {
//		tree.addChildtoParentX(nb, root);
//		nb.setBundle("2");
//		tree.addChildtoParentX(x2, root);
//		x2.setBundle("2");
//	    } else {
//		tree.addChildtoParentX(b, root);
//		b.setBundle("2");
//		tree.addChildtoParentX(x2, root);
//		x2.setBundle("2");
//	    }
//	    break;
//
//	case 4:
//	    System.out.println("error: 4");
//	    return null;
//
//	default:
//	    break;
//	}
//
//	Node y = new Node("Y");
//
//	tree.setRoot(root);
//	tree.addChildtoParentX(y, root);
//	System.out.println(tree.toString());
//	return tree;
//
//    }
//
//    private int calcRowSum(int row) {
//	int sum = 0;
//	for (int i = 0; i < field[row].length; i++)
//	    sum += field[row][i];
//	return sum;
//    }
//
//    private int calcColSum(int col) {
//	int sum = 0;
//	for (int i = 0; i < field[col].length; i++)
//	    sum += field[i][col];
//	return sum;
//    }
//
//    private int calcSumAll() {
//	int sum = 0;
//	for (int i = 0; i < field.length; i++)
//	    sum += calcRowSum(i);
//	return sum;
//    }
//
// }
