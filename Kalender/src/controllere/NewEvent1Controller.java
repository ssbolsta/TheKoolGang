package controllere;

import java.time.LocalTime;

import models.Person;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewEvent1Controller {
	
	@FXML private TextField nameField;
	@FXML private TextField spacesField;
	@FXML private TextArea descriptionField;
	@FXML private DatePicker dateField;
	@FXML private ComboBox<LocalTime> fromTime;
	@FXML private ComboBox<LocalTime> toTime;
	@FXML private ComboBox<String> personSearchField;
	@FXML private ComboBox<String> groupSearchField;
	@FXML private TableView<Person> recipientTable;
	@FXML private TableColumn<Person,Integer> uidColumn;
	@FXML private TableColumn<Person,String> nameColumn;
	
	
	public NewEvent1Controller(){
	}
	
	@FXML private void initialize(){
		
	}
	
	@FXML
	private  void handleLeggTilPerson(){
		
	}
	
}
