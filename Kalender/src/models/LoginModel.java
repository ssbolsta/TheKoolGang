package models;

import java.net.URL;
import java.util.ResourceBundle;

import controllere.LoginController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class LoginModel  extends LoginController implements Initializable  {
	
	private LoginController lc = new LoginController();
	
	@FXML
	private Button login;
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'Untitled'.";
		assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'Untitled'.";
		
		login.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				login(password.getText(), username.getText());
				
			}
			
		});
	}
}
