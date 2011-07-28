package algorithms.cna;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <pcg.unibe.ch@gmail.com>
 * @license GPLv3, see Readme.mdown
 */

public class NecException extends Exception {
    private String errorMsg;

    public NecException(String errorMsg) {
	this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
	return errorMsg;
    }
}
