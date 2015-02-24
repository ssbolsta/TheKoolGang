package conrollere;

public class UserController {

	public void searchUserByLastName(String lastName){
		try{
			validateName(lastName);
		}
		catch(Exception e){
			System.out.println(e);

		}
	}

	private void validateName(String name) throws Exception{
		name.trim();
		char[] chars = name.toCharArray();

		for (char c : chars) {
			if(!Character.isLetter(c)) {
				throw new Exception ("Navn kan ikke inneholde tall");
			}
		}
	}

	public void searchUserByPnr(String pnr){
		try{
			validatePnr(pnr);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void validatePnr(String pnr)throws Exception{
		pnr.trim();
		try{
			Long.parseLong(pnr);
		}
		catch(Exception e){
			System.out.println("Personnummer må bestå av elleve tall");
		}
		if(pnr.length()!= 11){
			throw new Exception ("Feil lengde");
		}
	}
}

