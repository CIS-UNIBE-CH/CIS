package datastructures.random;

import java.util.ArrayList;

public class RandomGraphInput {
    ArrayList<ArrayList<Object>> levels = new ArrayList<ArrayList<Object>>();
    private ArrayList<ArrayList<Integer>> bundleSizes;
    private ArrayList<Integer> noOfAlterFactors;

    public RandomGraphInput(ArrayList<ArrayList<Integer>> bundleSizes,
	    ArrayList<Integer> noOfAlterFactors) {
	this.bundleSizes = bundleSizes;
	this.noOfAlterFactors = noOfAlterFactors;

	createLevelsList();
    }
    
    public void createLevelsList(){
	for(int i = 0; i < bundleSizes.size(); i++){
	    ArrayList<Object> level = new ArrayList<Object>();
	    level.add(bundleSizes.get(i));
	    level.add(noOfAlterFactors.get(i));
	    levels.add(level);
	}
	System.out.println("Levels" + levels);
    }

    public ArrayList<ArrayList<Integer>> getBundleSizes() {
	return bundleSizes;
    }

    public ArrayList<Integer> getNoOfAlterFactors() {
	return noOfAlterFactors;
    }

    public ArrayList<ArrayList<Object>> getLevels() {
        return levels;
    }

    public void setBundleSizes(ArrayList<ArrayList<Integer>> bundleSizes) {
	this.bundleSizes = bundleSizes;
    }

    public void setNoOfAlterFactors(ArrayList<Integer> noOfAlterFactors) {
	this.noOfAlterFactors = noOfAlterFactors;
    }
}
