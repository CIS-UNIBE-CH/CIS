package algorithms.cna;


public class NecException extends Exception {
    private String errorMsg;

    public NecException(String errorMsg) {
	this.errorMsg = errorMsg;
    }
    
    public NecException() {
	
    }

    @Override
    public String toString() {
	return errorMsg;
    }
}
