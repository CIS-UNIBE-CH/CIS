package algorithms;

import java.util.ArrayList;

import javax.swing.text.TabExpander;

import com.sun.tools.javac.code.Attribute.Array;

import tree.CustomTree;
import tree.CustomTreeNode;

/** Copyright 2011 (C) Felix Langenegger & Jonas Ruef */

public class BooleanTest {
	
	private CustomTree tree;
	private ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
	private ArrayList<String> msuf = new ArrayList<String>();
	private ArrayList<ArrayList<String>> sufTable;
	private ArrayList<ArrayList<String>> temp;
	
	public BooleanTest(String[][] table){
		this.table = ArrayToArrayList(table);
		
		identifySUF();
		identifyMSUF();
		
	}
	
	public CustomTree creatTree() {
		tree = new CustomTree();
		CustomTreeNode root = new CustomTreeNode("W");
		tree.setRoot(root);
		
		return tree;	
	}
	
	/** Step 2 **/
	public void identifySUF() {
		sufTable = clone2DArrayList(table);
		for(int r = 1; r < sufTable.size(); r++){
			if(sufTable.get(r).get(sufTable.get(r).size()-1).equals("0"))
				sufTable.remove(r);
		}
		System.out.println(tableToString(sufTable));
	}
	
	/** Step 3 **/
	public void identifyMSUF() {
		ArrayList<String> tester = new ArrayList<String>();
		temp = clone2DArrayList(table);
		Boolean found;
		for(int col = 0; col < table.get(0).size(); col++){
			for(int zeroPos = col; zeroPos < temp.get(0).size(); zeroPos++) {
				tester = calcTester(temp.get(0).size(), zeroPos);
				System.out.println(tester);
				System.out.println(tableToString(temp));
				found = true;
				for(int min = 0; min < temp.get(0).size() && found; min++){
					System.out.println("in");
					for(int row = 1; row < temp.size(); row++){
						if(tester.equals(temp.get(row))){
							removeCol(temp, zeroPos);
							System.out.println(tableToString(temp));
							tester.remove(zeroPos);
							System.out.println(tester);
							found = true;
							break;
						}
						found = false;
					}
					
				}
			}
			temp = clone2DArrayList(table);
		}
		System.out.println(msuf);
	}
	

	private ArrayList<String> calcTester(int size, int zeroPos) {
		ArrayList<String> tester = new ArrayList<String>();
		for(int i = 0; i < size; i++) {
			if(i == zeroPos)
				tester.add("0");
			else
				tester.add("1");
		}
		return tester;
	}

	private ArrayList<ArrayList<String>> clone2DArrayList(ArrayList<ArrayList<String>> toClone){
		ArrayList<ArrayList<String>> copy = new ArrayList<ArrayList<String>>();
		ArrayList<String> col;
		for(int i = 0; i < toClone.size(); i++) {
			col = (ArrayList<String>) toClone.get(i).clone();
			copy.add(col);
		}
		return copy;
	}
	
	private void removeCol(ArrayList<ArrayList<String>> tableColToDelet, int place) {
		for(int r = 0; r < tableColToDelet.size(); r ++){
			tableColToDelet.get(r).remove(place);
		}	
	}

	public ArrayList<ArrayList<String>> ArrayToArrayList(String[][] table){
		ArrayList<ArrayList<String>> tableList = new ArrayList<ArrayList<String>>();
		for (int r = 0; r < table.length; r++) {
			tableList.add(new ArrayList<String>());
			for (int c = 0; c < table[r].length; c++) {
				tableList.get(r).add(table[r][c]);
			}
		}
		return tableList;
	}
	
	public String tableToString(ArrayList<ArrayList<String>> tableList){
		String print = "";
		for(int r = 0; r < tableList.size(); r++){
			for(int c = 0; c < tableList.get(r).size(); c++){
				print += tableList.get(r).get(c) + " ";
			}
			print += "\n";
		}
		return print;
	}
}
