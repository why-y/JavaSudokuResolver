package gry.sample.sudoku;


public class UnresolvableException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UnresolvableException() {
		
	}
	
	public UnresolvableException(String message) {
		super(message);
	}
	
	public UnresolvableException(Throwable throwable) {
		super(throwable);
	}

	public UnresolvableException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
