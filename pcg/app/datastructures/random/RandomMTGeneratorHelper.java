package datastructures.random;

import java.util.ArrayList;

public class RandomMTGeneratorHelper {
    ArrayList<ArrayList<Object>> completeList = new ArrayList<ArrayList<Object>>();
    private ArrayList<ArrayList<Integer>> bundleSizesLevels;
    private ArrayList<Integer> alterFactors;

    public RandomMTGeneratorHelper(ArrayList<ArrayList<Integer>> bundleSizesLeveled,
	    ArrayList<Integer> alterFactors) {
	this.bundleSizesLevels = bundleSizesLeveled;
	this.alterFactors = alterFactors;

	removeZerosInBundles();
	createLevelsList();
    }
    
    //For Testing only.
    public RandomMTGeneratorHelper(){
    }

    public void removeZerosInBundles() {
	for (int i = bundleSizesLevels.size() - 1; i >= 0; i--) {
	    ArrayList<Integer> list = bundleSizesLevels.get(i);
	    for (int j = list.size() - 1; j >= 0; j--) {
		Integer cur = list.get(j);
		if (cur == 0) {
		    list.remove(j);
		}
	    }
	}
    }

    public void createLevelsList() {
	for (int i = 0; i < bundleSizesLevels.size(); i++) {
	    ArrayList<Object> level = new ArrayList<Object>();
	    level.add(bundleSizesLevels.get(i));
	    level.add(alterFactors.get(i));
	    completeList.add(level);
	}
    }

    public ArrayList<ArrayList<Integer>> getBundleSizesLevels() {
	return bundleSizesLevels;
    }
    
    public ArrayList<Integer> getAlterFactors() {
        return alterFactors;
    }

    public ArrayList<ArrayList<Object>> getCompleteList() {
	return completeList;
    }

    public void setBundleSizesLevels(
    	ArrayList<ArrayList<Integer>> bundleSizesLevels) {
        this.bundleSizesLevels = bundleSizesLevels;
    }

    public void setAlterFactors(ArrayList<Integer> alterFactors) {
        this.alterFactors = alterFactors;
    }
}
