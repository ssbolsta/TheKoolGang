package controllere;

public class Validation {

	static void validateName(String name) throws Exception{
		name = name.trim();
		char[] chars = name.toCharArray();

		for (char c : chars) {
			if(!Character.isLetter(c)) {
				throw new Exception ("Navn kan ikke inneholde tall");
			}
		}
	}


	static void validatePnr(String pnr)throws Exception{
		pnr = pnr.trim();
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
	
	static void validateUsername(String username) throws Exception{
		username = username.trim();
		char[] chars = username.toCharArray();

		for (char c : chars) {
			if(!Character.isLetter(c)) {
				throw new Exception ("Brukernavnet kan ikke innheolde tall");
			}
		}
	}
}
