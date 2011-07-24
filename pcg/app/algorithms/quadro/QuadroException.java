package algorithms.quadro;

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
