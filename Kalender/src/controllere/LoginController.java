package controllere;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kalenderGUI.AgendaApplication;
import kalenderGUI.LoginMain;

public class LoginController {

	@FXML Text errorMessage;
	@FXML Button cancel;
	private String username = "bruker";
	private String password = "passord";

	public void login(String pass, String user){
		try{
			Validation.validateUsername(user);
			if(user.trim().equals(username)&&pass.equals(password)){
				Stage newStage = new Stage();
				AgendaApplication kalender = new AgendaApplication();

				kalender.start(newStage);
				cancel.fire();



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
