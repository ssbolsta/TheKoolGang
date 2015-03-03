package controllere;

import java.time.LocalTime;
import java.util.HashMap;

import models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import kalenderGUI.EventMain;

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
	@FXML private Button cancel;
	
	private ObservableList<Person> personTableList = FXCollections.observableArrayList();
	private ObservableList<Person> nameList = FXCollections.observableArrayList();
	private ObservableList<LocalTime> timeFromList = FXCollections.observableArrayList();
	private ObservableList<LocalTime> timeToList = FXCollections.observableArrayList();
	private HashMap<String,Person> personList = new HashMap<String,Person>();
	private EventMain mainApp;
	
	
	private EventHandler<KeyEvent> cancelKeyPress = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0){
			if(arg0.getCode().equals(KeyCode.ENTER)){
				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.hide();
				stage.close();
			}
		}
	};
	
	public NewEvent1Controller(){
	}
	
	@FXML private void initialize(){
		uidColumn.setCellValueFactory(cellData -> cellData.getValue().getUidStringProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
		recipientTable.setItems(personTableList);
		nameList.add( new Person("Mats","Egedal",1234));
		nameList.add( new Person("Kristian","B�",1235));
		nameList.add( new Person("Boye","Data",1236));
		nameList.add( new Person("John","Smith",1237));
		nameList.add( new Person("Jim","Jiminy",1238));
		for (Person person : nameList) {
			personList.put(person.toString(), person);
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
		new AutoCompleteCombobox<>(this.personSearchField);
		new AutoCompleteCombobox<>(this.groupSearchField);
		
		cancel.setOnKeyPressed(cancelKeyPress);

		cancel.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.hide();
				stage.close();
			}
		});
		
	}
	
	@FXML
	private  void handleLeggTilPerson(){
		if(personList.get(personSearchField.getSelectionModel().getSelectedItem()) != null){
			personTableList.add(personList.get(personSearchField.getSelectionModel().getSelectedItem()));
			nameList.remove(personList.get(personSearchField.getSelectionModel().getSelectedItem()));
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
	
	@FXML
	private void handleClose(){
		this.mainApp.getPrimaryStage().close();
	}
	
	@FXML
	private void handleNext(){
		this.mainApp.showNewEvent2();
	}
	
	public void setMainApp(EventMain mainApp){
		this.mainApp = mainApp;
	}
}
