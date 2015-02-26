package models;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NewGroupModel implements Initializable {
	
	@FXML private TextField nameField;
	@FXML private TextArea descriptionField;
	@FXML private ComboBox<Person> personSearchField;
	@FXML private ComboBox<String> groupSearchField;
	@FXML private TableView<Person> recipientTable;
	@FXML private TableColumn<Person,String> uidColumn;
	@FXML private TableColumn<Person,String> nameColumn;
	@FXML 
	private Button cancel;
	
	private EventHandler<KeyEvent> cancelKeyPress = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0){
			if(arg0.getCode().equals(KeyCode.ENTER)){
				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.close();
			}
		}
	};
	
	public void initialize(URL arg0, ResourceBundle arg1){
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
