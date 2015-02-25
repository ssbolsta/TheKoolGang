package kalenderGUI;

import java.time.LocalTime;

import models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	private ObservableList<Person> personList = FXCollections.observableArrayList();
	private ObservableList<String> nameList = FXCollections.observableArrayList();
	private ObservableList<LocalTime> timeList = FXCollections.observableArrayList();
	
	
	public NewEvent1Controller(){
	}
	
	@FXML private void initialize(){
		personList.add( new Person("Mats","Egedal",1234));
		personList.add( new Person("Kristian","Bø",1235));
		personList.add( new Person("Boye","Data",1236));
		personList.add( new Person("John","Smith",1237));
		personList.add( new Person("Jim","Jiminy",1238));
		for (Person string : personList) {
			nameList.add(string.toString());
		}
		for (int i = 0; i < 25; i++) {
			timeList.add(LocalTime.of(i, 0));
		}
		fromTime.setItems(timeList);
		toTime.setItems(timeList);
		this.personSearchField.setItems(nameList);
		
	}
	
	@FXML
	private  void handleLeggTilPerson(){
		
	}
	
}
