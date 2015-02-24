package conrollere;

public class UserController {

	public void searchUserByLastName(String lastName){
		try{
			Validation.validateName(lastName);
		}
		catch(Exception e){
			System.out.println(e);

		}
	}

	

	public void searchUserByPnr(String pnr){
		try{
			Validation.validatePnr(pnr);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}

