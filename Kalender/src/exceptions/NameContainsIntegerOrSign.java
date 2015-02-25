package exceptions;

public class NameContainsIntegerOrSign extends Exception {
	public NameContainsIntegerOrSign(){}
	
	public NameContainsIntegerOrSign(String message){
		super(message);
	}
}
