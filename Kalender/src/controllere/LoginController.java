package controllere;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kalenderGUI.AgendaApplication;

public class LoginController {

	@FXML Text errorMessage;
	private String username = "bruker";
	private String password = "passord";

	public void login(String pass, String user){
		try{
			Validation.validateUsername(user);
			if(user.equals(username)&&pass.equals(password)){
				Stage newStage = new Stage();
				AgendaApplication a = new AgendaApplication();
				a.start(newStage);
			}
			else{
				errorMessage.setVisible(true);


			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
