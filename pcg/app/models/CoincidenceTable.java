package models;

import datastructures.cna.CNATable;
import datastructures.mt.MinimalTheory;

public class CoincidenceTable {
    private MinimalTheory theory;
    private CNATable coincidence;
    
    public CoincidenceTable(MinimalTheory theory){
	this.theory = theory;
	coincidence = new CNATable();
	
	fillUpTableBinary();
    }

    private void fillUpTableBinary() {
	
	
    }

}
