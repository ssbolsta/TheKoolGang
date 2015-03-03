package exceptions;

public class IntegerOnlyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntegerOnlyException(){}
	
	public IntegerOnlyException(String message){
		super(message);
	}

}
