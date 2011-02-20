package models;

import java.util.ArrayList;
import java.util.Random;

import parser.TreeToJgraph;
import tree.CustomTree;
import tree.CustomTreeNode;

public class GraphGenerator {
	private int numberOfBundles;
	private int numberOfFactors;
	private CustomTree tree;
	private ArrayList<CustomTreeNode> nodes;

	public GraphGenerator(int numberOfBundles, int numberOfFactors) {
		this.numberOfBundles = numberOfBundles;
		this.numberOfFactors = numberOfFactors;
		nodes = new ArrayList<CustomTreeNode>();

		// TODO Abfangen, dass nicht mehr Bündel als Faktoren vorgegeben
		// werden können.

		nodeGenerator();
		randomTreeGenerator();
	}

	private void nodeGenerator() {
		for (int i = 65; i <= (65 + numberOfFactors); i++) {
			String curFactorLetter = "" + (char) i;
			String curFactorLetterNegative = "-" + (char) i;
			System.out.println("curFactorLetter" + curFactorLetter);
			System.out.println("curFactorLetterNegative"
					+ curFactorLetterNegative);

			CustomTreeNode curNode = new CustomTreeNode(curFactorLetter);
			nodes.add(curNode);

			CustomTreeNode curNodeNegative = new CustomTreeNode(
					curFactorLetterNegative);
			nodes.add(curNodeNegative);
		}
	}

	private void randomTreeGenerator() {
		// Create Tree and Root Node
		tree = new CustomTree();
		CustomTreeNode root = new CustomTreeNode("W");
		tree.setRoot(root);
		int totalFactors = 0;
		int xfactorNumber = 1;
		Integer bundleNumber = 1;

		// Generate Bundles
		for (int i = 0; i < numberOfBundles; i++) {
			int factorsInBundle = 2; // X1 factors will be counted separate

			for (int j = 0; j < factorsInBundle; j++) {
				ArrayList<CustomTreeNode> curBundle = new ArrayList<CustomTreeNode>();
				Random generator = new Random();
				int randomIndex = generator.nextInt(nodes.size());
				CustomTreeNode curNode = nodes.get(randomIndex);
				curNode.setBundle(bundleNumber.toString());

				if (j > 0
						&& avoidNegativeAndPositiveFactorInBundle(curBundle,
								curNode)) {
					// When Positive or Negative of Factor is already in
					// Bundle, then decrement j, so a new attempt can be
					// made.
					j = j - 1;
				} else {
					curBundle.add(curNode);
					tree.addChildtoRootX(curNode, root);

					// TODO Stimmt das? Kann ein Faktor nicht in mehren Bündeln
					// vorkommen?
					nodes.remove(randomIndex);
					totalFactors = totalFactors + 1;
				}
			}
			CustomTreeNode x = new CustomTreeNode("X" + xfactorNumber);
			xfactorNumber = xfactorNumber + 1;
			x.setBundle(bundleNumber.toString());
			tree.addChildtoRootX(x, root);
			bundleNumber = bundleNumber + 1;
		}

		// Generate Co-Factors
		for (; totalFactors <= numberOfFactors; totalFactors++) {
			Random generator = new Random();
			int randomIndex = generator.nextInt(nodes.size());

			CustomTreeNode curNode = nodes.get(randomIndex);
			tree.addChildtoRootX(curNode, root);
			nodes.remove(randomIndex);
		}
		CustomTreeNode y = new CustomTreeNode("Y");
		tree.addChildtoRootX(y, root);

		Plotter plotter = new Plotter();
		plotter.setPath("./pcg/public/images/generated-graphs/");
		plotter.plot(new TreeToJgraph(tree));

	}

	private boolean avoidNegativeAndPositiveFactorInBundle(
			ArrayList<CustomTreeNode> curBundle, CustomTreeNode checkNode) {
		String checkNodeName = checkNode.toString();

		for (int j = 0; j < curBundle.size(); j++) {
			String curNodeName = curBundle.get(j).toString();
			if (curNodeName.equals(checkNodeName.substring(1,
					checkNodeName.length() - 1))) {
				return true;
			} else if (checkNodeName.equals(curNodeName.substring(1,
					curNodeName.length() - 1))) {
				return true;
			}
		}
		return false;

	}
}
