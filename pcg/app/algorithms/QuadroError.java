package algorithms;

public class QuadroError extends Exception {
    private String errorMsg;

    public QuadroError(String errorMsg) {
	this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
	return errorMsg;
    }
}
