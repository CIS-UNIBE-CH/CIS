package util;

import tree.CustomTree;
import tree.CustomTreeNode;

public class QuadroTest {

	int[][] field = new int[2][2];
	String f1, f2;

	public QuadroTest(int[][] field, String f1, String f2) {
		this.field = field;
		this.f1 = f1;
		this.f2 = f2;

	}

	public CustomTree creatGraph() {

		CustomTree tree = new CustomTree();
		CustomTreeNode root = new CustomTreeNode("W");
		CustomTreeNode a = new CustomTreeNode(f1);
		CustomTreeNode na = new CustomTreeNode("¬" + f1);
		CustomTreeNode b = new CustomTreeNode(f2);
		CustomTreeNode nb = new CustomTreeNode("¬" + f2);

		CustomTreeNode x1 = new CustomTreeNode("X1");
		CustomTreeNode x2 = new CustomTreeNode("X2");

		int sum = calcSumAll();
		switch (sum) {
		case 0:
			System.out.println("error: 0");
			return null;

		case 1:
			tree.addChildtoRootX(a, root);
			a.setBundle("1");
			if (calcRowSum(0) == 1) {
				if (field[0][0] == 1) {
					tree.addChildtoRootX(b, root);
					b.setBundle("1");
				} else {
					tree.addChildtoRootX(nb, root);
					nb.setBundle("1");
				}
				tree.addChildtoRootX(x1, root);
				x1.setBundle("1");
			} else {
				tree.addChildtoRootX(x1, root);
				x1.setBundle("1");
				tree.addChildtoRootX(na, root);
				na.setBundle("2");
				if (field[1][0] == 1) {
					tree.addChildtoRootX(b, root);
					b.setBundle("2");
				} else {
					tree.addChildtoRootX(nb, root);
					nb.setBundle("2");
				}
				tree.addChildtoRootX(x2, root);
				x2.setBundle("2");
			}
			break;

		case 2:
			tree.addChildtoRootX(a, root);
			a.setBundle("1");
			tree.addChildtoRootX(x1, root);
			x1.setBundle("1");
			if (calcRowSum(0) == 2) {
				System.out.println("error: 2");
				return null;
			} else if (calcRowSum(1) == 2) {
				tree.addChildtoRootX(na, root);
				na.setBundle("2");
			} else if (calcColSum(0) == 2) {
				tree.addChildtoRootX(b, root);
				b.setBundle("2");
			} else if (calcColSum(1) == 2) {
				tree.addChildtoRootX(nb, root);
				nb.setBundle("2");
			} else {
				if (field[0][0] == 0) {
					tree.addChildtoRootX(nb, root);
					nb.setBundle("1");
					tree.addChildtoRootX(b, root);
					b.setBundle("2");
					tree.addChildtoRootX(na, root);
					na.setBundle("2");
				} else {
					tree.addChildtoRootX(b, root);
					b.setBundle("1");
					tree.addChildtoRootX(nb, root);
					nb.setBundle("2");
					tree.addChildtoRootX(na, root);
					na.setBundle("2");

				}
			}
			tree.addChildtoRootX(x2, root);
			x2.setBundle("2");
			break;
		case 3:
			tree.addChildtoRootX(a, root);
			a.setBundle("1");
			tree.addChildtoRootX(x1, root);
			x1.setBundle("1");
			if (calcRowSum(1) == 2) {
				tree.addChildtoRootX(na, root);
				na.setBundle("2");
				tree.addChildtoRootX(x2, root);
				x2.setBundle("2");
				break;
			}
			if (field[1][0] == 0) {
				tree.addChildtoRootX(nb, root);
				nb.setBundle("2");
				tree.addChildtoRootX(x2, root);
				x2.setBundle("2");
			} else {
				tree.addChildtoRootX(b, root);
				b.setBundle("2");
				tree.addChildtoRootX(x2, root);
				x2.setBundle("2");
			}
			break;

		case 4:
			System.out.println("error: 4");
			return null;

		default:
			break;
		}

		CustomTreeNode y = new CustomTreeNode("Y");

		tree.setRoot(root);
		tree.addChildtoRootX(y, root);
		return tree;

	}

	private int calcRowSum(int row) {
		int sum = 0;
		for (int i = 0; i < field[row].length; i++)
			sum += field[row][i];
		return sum;
	}

	private int calcColSum(int col) {
		int sum = 0;
		for (int i = 0; i < field[col].length; i++)
			sum += field[i][col];
		return sum;
	}

	private int calcSumAll() {
		int sum = 0;
		for (int i = 0; i < field.length; i++)
			sum += calcRowSum(i);
		return sum;
	}

}
