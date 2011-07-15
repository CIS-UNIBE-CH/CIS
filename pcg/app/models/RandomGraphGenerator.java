package models;

import java.util.ArrayList;

import parsers.TreeToTable;
import datastructures.Node;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

/**
 * Generates a random graph out of a given number of factors and bundles.
 * 
 * TODO: Die möglichkeit, dass der gleiche Faktor in mehreren Bündeln vorkommt
 * ist noch nicht abgedeckt. (Siehe Kommentar Zeile: 90)
 * 
 */
public class RandomGraphGenerator {
    private int numberOfBundles;
    private int numberOfFactors;
    private int factorsInBundle;
    // private CustomTree tree;
    private ArrayList<Node> nodes;
    private TreeToTable parser;
    private int totalFactors;

    // public RandomGraphGenerator(int numberOfBundles, int numberOfFactors,
    // int bundleSize) {
    // this.numberOfBundles = numberOfBundles;
    // this.numberOfFactors = numberOfFactors;
    // // X1,X2,etc factors will not be counted.
    // this.factorsInBundle = bundleSize;
    // this.nodes = new ArrayList<Node>();
    //
    // // Init process
    // nodeGenerator();
    // randomTreeGenerator();
    //
    // this.parser = new TreeToTable(tree, numberOfFactors, numberOfBundles,
    // bundleSize);
    // }
    //
    // /** Generates the names of the nodes and the CustomTreeNodes. */
    // private void nodeGenerator() {
    // // i = 65 because char 65 = A
    // for (int i = 65; i <= (65 + numberOfFactors); i++) {
    // String curFactorLetter = "" + (char) i;
    // String curFactorLetterNegative = "¬" + (char) i;
    //
    // Node curNode = new Node(curFactorLetter);
    // nodes.add(curNode);
    //
    // Node curNodeNegative = new Node(curFactorLetterNegative);
    // nodes.add(curNodeNegative);
    // }
    // }
    //
    // /**
    // * Generates the the bundles with their factors and adds them to the tree.
    // */
    // private void randomTreeGenerator() {
    // // Create Tree and Root Node
    // tree = new CustomTree();
    // Node root = new Node("W");
    // root.setIsEffect(true);
    // tree.setRoot(root);
    //
    // int factorCounter = 1;
    // int xfactorNumber = 1;
    // Integer bundleNumber = 1;
    //
    // // Generate Bundles
    // for (int i = 0; i < numberOfBundles; i++) {
    // ArrayList<Node> curBundle = new ArrayList<Node>();
    //
    // for (int j = 0; j < factorsInBundle; j++) {
    // Random generator = new Random();
    // int randomIndex = generator.nextInt(nodes.size());
    // Node curNode = nodes.get(randomIndex);
    // curNode.setIsEffect(false);
    // totalFactors++;
    //
    // if (avoidPositiveNegativeFactorInBundle(curBundle, curNode)) {
    // // When positive or negative of a factor is already in
    // // bundle, then decrement j, so a new attempt of adding a
    // // factor can be made.
    // j = j - 1;
    // } else {
    // curNode.setBundle(bundleNumber.toString());
    // curBundle.add(curNode);
    // tree.addChildtoParentX(curNode, root);
    //
    // // Falls ein gleicher ein bestimmter Faktor in mehreren
    // // Bündeln vorkommen können soll muss das die nächste Zeile
    // // entfernt werden.
    // nodes.remove(randomIndex);
    //
    // factorCounter = factorCounter + 1;
    // }
    // }
    // Node x = new Node("X" + xfactorNumber);
    // x.setIsEffect(false);
    // totalFactors++;
    // xfactorNumber = xfactorNumber + 1;
    // x.setBundle(bundleNumber.toString());
    // tree.addChildtoParentX(x, root);
    //
    // bundleNumber = bundleNumber + 1;
    // }
    //
    // // Generate alternative factors, take factor counter value from above.
    // for (; factorCounter <= numberOfFactors; factorCounter++) {
    // Random generator = new Random();
    // int randomIndex1 = generator.nextInt(nodes.size());
    //
    // Node curNode = nodes.get(randomIndex1);
    // curNode.setIsEffect(false);
    // totalFactors++;
    // tree.addChildtoParentX(curNode, root);
    // nodes.remove(randomIndex1);
    // }
    // Node y = new Node("Y");
    // tree.addChildtoParentX(y, root);
    // }
    //
    // /**
    // * Avoids that in one bundle is the negative and positive of the same
    // factor
    // */
    // private boolean avoidPositiveNegativeFactorInBundle(
    // ArrayList<Node> curBundle, Node node) {
    //
    // String curNode = node.toString();
    // // Remove the negative symbol
    // if (curNode.length() == 2) {
    // curNode = curNode.substring(1, curNode.length());
    // }
    //
    // for (int j = 0; j < curBundle.size(); j++) {
    // String verificationNode = curBundle.get(j).toString();
    //
    // // Remove the negative symbol
    // if (verificationNode.length() == 2) {
    // verificationNode = verificationNode.substring(1,
    // verificationNode.length());
    // }
    //
    // if (verificationNode.equals(curNode)) {
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // @Override
    // public String toString() {
    // return parser.toString();
    // }
    //
    // public CustomTree getTree() {
    // return tree;
    // }
    //
    // public ArrayList<String> getTable() {
    // return parser.getTable();
    // }
    //
    // public String[][] getTableAsArray() {
    // return parser.getTableAsArray();
    // }
    //
    // public int getTotalFactors() {
    // return totalFactors;
    // }
}
