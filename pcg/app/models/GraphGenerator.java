package models;

import java.util.ArrayList;
import java.util.Random;

import parser.TreeToJgraph;
import parser.TreeToTable;
import tree.CustomTree;
import tree.CustomTreeNode;

/**
 * Generates random Graph out of a pre given number of factors and bundles.
 * 
 * TODO: Die möglichkeit, dass ein Faktor in mehren Bündeln vorkommt ist noch
 * nicht abgedeckt. (Siehe Kommentar Zeile: 86)
 * 
 */
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

	/** Generates the letters for the nodes and the CustomTreeNodes */
	private void nodeGenerator() {
		for (int i = 65; i <= (65 + numberOfFactors); i++) {
			String curFactorLetter = "" + (char) i;
			String curFactorLetterNegative = "-" + (char) i;

			CustomTreeNode curNode = new CustomTreeNode(curFactorLetter);
			nodes.add(curNode);

			CustomTreeNode curNodeNegative = new CustomTreeNode(
					curFactorLetterNegative);
			nodes.add(curNodeNegative);
		}
	}

	/** Generates the the bundles, the co-factors and adds them to the tree */
	private void randomTreeGenerator() {
		// Create Tree and Root Node
		tree = new CustomTree();
		CustomTreeNode root = new CustomTreeNode("W");
		tree.setRoot(root);

		int factorCounter = 0;
		int xfactorNumber = 1;
		Integer bundleNumber = 1;

		// Generate Bundles
		for (int i = 0; i < numberOfBundles; i++) {
			ArrayList<CustomTreeNode> curBundle = new ArrayList<CustomTreeNode>();
			int factorsInBundle = 2; // X1 factors will not be counted.

			for (int j = 0; j < factorsInBundle; j++) {
				Random generator = new Random();
				int randomIndex = generator.nextInt(nodes.size());

				CustomTreeNode curNode = nodes.get(randomIndex);

				if (avoidSameNegativeAndPositiveFactorInBundle(curBundle,
						curNode)) {
					// When Positive or Negative of Factor is already in
					// Bundle, then decrement j, so a new attemp can be
					// made.
					j = j - 1;
				} else {
					curNode.setBundle(bundleNumber.toString());
					curBundle.add(curNode);
					tree.addChildtoRootX(curNode, root);

					// Remove entfernen, fall gleicher Faktor in meheren Bündlen
					// vorkommen kann.
					nodes.remove(randomIndex);
					factorCounter = factorCounter + 1;
				}
			}
			CustomTreeNode x = new CustomTreeNode("X" + xfactorNumber);
			xfactorNumber = xfactorNumber + 1;
			x.setBundle(bundleNumber.toString());
			tree.addChildtoRootX(x, root);
			bundleNumber = bundleNumber + 1;
		}

		// Generate Co-Factors
		for (; factorCounter <= numberOfFactors; factorCounter++) {
			Random generator = new Random();
			int randomIndex1 = generator.nextInt(nodes.size());

			CustomTreeNode curNode = nodes.get(randomIndex1);
			tree.addChildtoRootX(curNode, root);
			nodes.remove(randomIndex1);
		}
		CustomTreeNode y = new CustomTreeNode("Y");
		tree.addChildtoRootX(y, root);

		Plotter plotter = new Plotter();
		plotter.setPath("./pcg/public/images/generated-graphs/");
		plotter.plot(new TreeToJgraph(tree));

		TreeToTable parser = new TreeToTable(tree);

	}

	private boolean avoidSameNegativeAndPositiveFactorInBundle(
			ArrayList<CustomTreeNode> curBundle, CustomTreeNode checkNode) {
		String checkNodeName = checkNode.toString();
		if (checkNodeName.length() == 2) {
			checkNodeName = checkNodeName.substring(1, checkNodeName.length());
		}

		for (int j = 0; j < curBundle.size(); j++) {
			String curNodeName = curBundle.get(j).toString();
			if (curNodeName.length() == 2) {
				curNodeName = curNodeName.substring(1, curNodeName.length());
			}

			if (curNodeName.equals(checkNodeName)) {
				return true;
			}

		}
		return false;

	}
}
