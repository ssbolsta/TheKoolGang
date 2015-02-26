package controllere;

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
	@FXML private ComboBox<Person> personSearchField;
	@FXML private ComboBox<String> groupSearchField;
	@FXML private TableView<Person> recipientTable;
	@FXML private TableColumn<Person,String> uidColumn;
	@FXML private TableColumn<Person,String> nameColumn;
	
	private ObservableList<Person> personList = FXCollections.observableArrayList();
	private ObservableList<Person> personTableList = FXCollections.observableArrayList();
	private ObservableList<Person> nameList = FXCollections.observableArrayList();
	private ObservableList<LocalTime> timeFromList = FXCollections.observableArrayList();
	private ObservableList<LocalTime> timeToList = FXCollections.observableArrayList();
	
	
	public NewEvent1Controller(){
	}
	
	@FXML private void initialize(){
		uidColumn.setCellValueFactory(cellData -> cellData.getValue().getUidStringProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
		recipientTable.setItems(personTableList);
		personList.add( new Person("Mats","Egedal",1234));
		personList.add( new Person("Kristian","Bø",1235));
		personList.add( new Person("Boye","Data",1236));
		personList.add( new Person("John","Smith",1237));
		personList.add( new Person("Jim","Jiminy",1238));
		for (Person string : personList) {
			nameList.add(string);
		}
		for (int i = 0; i < 24; i++) {
			timeFromList.add(LocalTime.of(i, 0));
			if(i != 0){
				timeToList.add(LocalTime.of(i, 0));
			}
		}
		timeToList.add(LocalTime.of(23, 59));
		fromTime.setItems(timeFromList);
		toTime.setItems(timeToList);
		this.personSearchField.setItems(nameList);
		
	}
	
	@FXML
	private  void handleLeggTilPerson(){
		if(personSearchField.getSelectionModel().getSelectedItem() != null){
			personTableList.add(personSearchField.getSelectionModel().getSelectedItem());
			nameList.remove(personSearchField.getSelectionModel().getSelectedItem());
		}
	}
	
	@FXML
	private void handleFjernPerson(){
		if(recipientTable.getSelectionModel().getSelectedItem() != null){
			nameList.add(recipientTable.getSelectionModel().getSelectedItem());
			personTableList.remove(recipientTable.getSelectionModel().getSelectedItem());
		}
	}
	@FXML
	private void handleFromTime(){
		if(toTime.getSelectionModel().getSelectedItem() != null){
			LocalTime time = null;
			if(fromTime.getSelectionModel().getSelectedItem().isBefore(toTime.getSelectionModel().getSelectedItem())){
				time = toTime.getSelectionModel().getSelectedItem();
			}
			timeToList.removeAll(timeToList);
			int t = fromTime.getSelectionModel().getSelectedIndex();
			for (int i = t+1; i < 25; i++) {
				if(i != 24){
					timeToList.add(LocalTime.of(i, 0));
				}else{
					timeToList.add(LocalTime.of(23, 59));
				}
			}
			toTime.setItems(timeToList);
			if(time != null){
				toTime.getSelectionModel().select(time);
			}
		}
	}
}
