package exceptions;

public class NameContainsIntegerOrSign extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameContainsIntegerOrSign(){}
	
	public NameContainsIntegerOrSign(String message){
		super(message);
	}
}
