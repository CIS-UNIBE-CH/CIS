package algorithms.quadro;

/**
 * Copyright (C) <2011>
 * 
 * @author Jonas Ruef & Felix Langenegger <cis.unibe.ch@gmail.com>
 * @license GPLv3, for more informations see Readme.mdown
 */

public class QuadroException extends Exception {
    private String errorMsg;

    public QuadroException(String errorMsg) {
	this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
	return errorMsg;
    }
}
