package datastructures.cna;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <pcg.unibe.ch@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

import java.util.Comparator;

public class CNAListComparator implements Comparator<CNAList> {

    @Override
    public int compare(CNAList list1, CNAList list2) {
	int counter = 0;
	int index = -1;
	for (int col = 0; col < list1.size(); col++) {
	    if (!list1.get(col).equals(list2.get(col))) {
		index = col;
		counter++;
	    }
	}
	if (counter > 1)
	    return -1;
	else if (counter == 1) {
	    return index;
	}
	return -1;
    }
}