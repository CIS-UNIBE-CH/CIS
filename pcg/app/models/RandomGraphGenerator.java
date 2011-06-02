package models;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

import java.util.ArrayList;
import java.util.Random;

import parser.TreeToTable;
import tree.CustomTree;
import tree.CustomTreeNode;

/**
 * Generates a random graph out of a given number of factors and bundles.
 * 
 * TODO: Die möglichkeit, dass der gleiche Faktor in mehreren Bündeln vorkommt
 * ist noch nicht abgedeckt. (Siehe Kommentar Zeile: 86)
 * 
 */
public class RandomGraphGenerator {
	private int numberOfBundles;
	private int numberOfFactors;
	private CustomTree tree;
	private ArrayList<CustomTreeNode> nodes;
	private String imageSource;
	private TreeToTable parser;

	public RandomGraphGenerator(int numberOfBundles, int numberOfFactors) {
		this.numberOfBundles = numberOfBundles;
		this.numberOfFactors = numberOfFactors;
		this.nodes = new ArrayList<CustomTreeNode>();

		// Init process
		nodeGenerator();
		randomTreeGenerator();

		// this.imageSource = plotter.getImageSource();
		this.parser = new TreeToTable(tree, numberOfFactors, numberOfBundles);
	}

	/** Generates the names of the nodes and the CustomTreeNodes. */
	private void nodeGenerator() {
		// i = 65 because char 65 = A
		for (int i = 65; i <= (65 + numberOfFactors); i++) {
			String curFactorLetter = "" + (char) i;
			String curFactorLetterNegative = "¬" + (char) i;

			CustomTreeNode curNode = new CustomTreeNode(curFactorLetter);
			nodes.add(curNode);

			CustomTreeNode curNodeNegative = new CustomTreeNode(
					curFactorLetterNegative);
			nodes.add(curNodeNegative);
		}
	}

	/** Generates the the bundles with their factors and adds them to the tree. */
	private void randomTreeGenerator() {
		// Create Tree and Root Node
		tree = new CustomTree();
		CustomTreeNode root = new CustomTreeNode("W");
		tree.setRoot(root);

		int factorCounter = 1;
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

				if (avoidPositiveNegativeFactorInBundle(curBundle, curNode)) {
					// When positive or negative of a factor is already in
					// bundle, then decrement j, so a new attempt of adding a
					// factor can be made.
					j = j - 1;
				} else {
					curNode.setBundle(bundleNumber.toString());
					curBundle.add(curNode);
					tree.addChildtoRootX(curNode, root);

					// Falls ein gleicher ein bestimmter Faktor in mehreren
					// Bündeln vorkommen können soll muss das die nächste Zeile
					// entfernt werden.
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

		// Generate alternative factors, take factor counter value from above.
		for (; factorCounter <= numberOfFactors; factorCounter++) {
			Random generator = new Random();
			int randomIndex1 = generator.nextInt(nodes.size());

			CustomTreeNode curNode = nodes.get(randomIndex1);
			tree.addChildtoRootX(curNode, root);
			nodes.remove(randomIndex1);
		}
		CustomTreeNode y = new CustomTreeNode("Y");
		tree.addChildtoRootX(y, root);
	}

	/**
	 * Avoids that in one bundle is the negative and positive of the same factor
	 */
	private boolean avoidPositiveNegativeFactorInBundle(
			ArrayList<CustomTreeNode> curBundle, CustomTreeNode node) {

		String curNode = node.toString();
		// Remove the negative symbol
		if (curNode.length() == 2) {
			curNode = curNode.substring(1, curNode.length());
		}

		for (int j = 0; j < curBundle.size(); j++) {
			String verificationNode = curBundle.get(j).toString();

			// Remove the negative symbol
			if (verificationNode.length() == 2) {
				verificationNode = verificationNode.substring(1,
						verificationNode.length());
			}

			if (verificationNode.equals(curNode)) {
				return true;
			}
		}
		return false;
	}

	public String getGraphicSource() {
		return this.imageSource;
	}

	public String toString() {
		return parser.toString();
	}

	public CustomTree getTree() {
		return tree;
	}

	public ArrayList<String> getTable() {
		return parser.getTable();
	}

	public String[][] getTableAsArray() {
		return parser.getTableAsArray();
	}
}
