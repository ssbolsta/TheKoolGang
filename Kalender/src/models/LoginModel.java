package models;

import java.net.URL;
import java.util.ResourceBundle;
import controllere.LoginController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class LoginModel  extends LoginController  implements Initializable  {

	@FXML
	private Button login;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML 
	private Button cancel;
	
	//Event handlers

	private EventHandler<KeyEvent> loginKeyPress = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0){
			if(arg0.getCode().equals(KeyCode.ENTER)){
				login(password.getText(), username.getText());
			}
		}
	};
	
	private EventHandler<KeyEvent> cancelKeyPress = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0){
			if(arg0.getCode().equals(KeyCode.ENTER)){
				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.close();
			}
		}
	};


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

		login.setOnKeyPressed(loginKeyPress);
		username.setOnKeyPressed(loginKeyPress);
		password.setOnKeyPressed(loginKeyPress);
		cancel.setOnKeyPressed(cancelKeyPress);

		cancel.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.close();
			}
		});
	}
}
