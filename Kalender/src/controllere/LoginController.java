package controllere;

import org.json.simple.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kalenderGUI.AgendaApplication;

public class LoginController {

	@FXML Text errorMessage;
	@FXML Button cancel;


	public void login(String pass, String username){
		try{
			Validation.validateUsername(username);
			if(ConnectionForReal.scon.login(username, pass)){
				JSONObject user = (JSONObject) ConnectionForReal.scon.me().get(0);
				ConnectionForReal.name = user.get("firstname") +" " + user.get("lastname");
				ConnectionForReal.uid = (long) user.get("uid");
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
