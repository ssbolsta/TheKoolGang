package exceptions;

public class WrongLengthOfInput extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongLengthOfInput(){}

	public WrongLengthOfInput(String message){
		super(message);
	}
}
