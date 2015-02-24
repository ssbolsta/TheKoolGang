package controllere;

public class LoginController {
	private String password = "niggaboy";
	private String username = "sappis";
	
	
	protected void login(String pass, String user){
		try{
			Validation.validateUsername(user);
			if(user.equals(username)&&pass.equals(password)){
				System.out.println("Du har logget inn, fliiink");
			}
			else{
				System.out.println("Brukernavn og/eller passord er feil.");
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
