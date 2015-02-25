package exceptions;

public class WrongLengthOfInput extends Exception {
	public WrongLengthOfInput(){}

	public WrongLengthOfInput(String message){
		super(message);
	}
}
