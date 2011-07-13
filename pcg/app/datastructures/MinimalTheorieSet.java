package datastructures;

import java.util.ArrayList;

public class MinimalTheorieSet extends ArrayList<MinimalTheorie> {

    private ArrayList<MinimalTheorie> theories;
    private ArrayList<MinimalTheorie> singles;
    private ArrayList<MinimalTheorie> epis;
    private ArrayList<MinimalTheorie> chains;

    public MinimalTheorieSet() {
	theories = new ArrayList<MinimalTheorie>();
	singles = new ArrayList<MinimalTheorie>();
	epis = new ArrayList<MinimalTheorie>();
	chains = new ArrayList<MinimalTheorie>();
    }

    @Override
    public boolean add(MinimalTheorie theorie) {
	distributMT(theorie);
	return theories.add(theorie);
    }

    @Override
    public int size() {
	return theories.size();
    }

    @Override
    public MinimalTheorie get(int arg0) {
	return theories.get(arg0);
    }

    @Override
    public MinimalTheorie remove(int arg0) {
	return theories.remove(arg0);
    }

    // Helpers

    private void distributMT(MinimalTheorie theorie) {

    }

    private boolean isSignle(MinimalTheorie theorie) {
	return false;
    }

    private boolean isEpi(MinimalTheorie theorie) {
	return false;
    }

    private boolean isChain(MinimalTheorie theorie) {
	return false;
    }

}
