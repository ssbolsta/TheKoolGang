package controllere;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import org.controlsfx.dialog.Dialogs;

import models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	
	private ObservableList<LocalTime> timeFromList = FXCollections.observableArrayList();
	private ObservableList<LocalTime> timeToList = FXCollections.observableArrayList();
	private HashMap<String,Person> personKeyList = new HashMap<String,Person>();
	private EventMain mainApp;
	
	public NewEvent1Controller(){
	}
	
	@FXML private void initialize(){
		uidColumn.setCellValueFactory(cellData -> cellData.getValue().getUidStringProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
		for (int i = 0; i < 24; i++) {
			timeFromList.add(LocalTime.of(i, 0));
			if(i != 0){
				timeToList.add(LocalTime.of(i, 0));
			}
		}
		timeToList.add(LocalTime.of(23, 59));
		fromTime.setItems(timeFromList);
		toTime.setItems(timeToList);
	}
	
	public void showData(){
		recipientTable.setItems(mainApp.getRecipientList());
		for (Person person : mainApp.getPersonList()) {
			personKeyList.put(person.toString(), person);
		}
		this.personSearchField.setItems(mainApp.getPersonList());
		new AutoCompleteCombobox<>(this.personSearchField);
		new AutoCompleteCombobox<>(this.groupSearchField);
		
		if(mainApp.getName() != null && mainApp.getDate() != null && mainApp.getFromTime() != null && mainApp.getToTime() != null && mainApp.getDesc() != null && mainApp.getSpaces() != null){
			nameField.setText(mainApp.getName());
			dateField.setValue(mainApp.getDate());
			fromTime.getSelectionModel().select(mainApp.getFromTime());
			toTime.getSelectionModel().select(mainApp.getToTime());
			descriptionField.setText(mainApp.getDesc());
			spacesField.setText(mainApp.getSpaces().toString());
		}
	}
	
	@FXML
	private  void handleLeggTilPerson(){
		if(personKeyList.get(personSearchField.getSelectionModel().getSelectedItem()) != null){
			mainApp.getRecipientList().add(personKeyList.get(personSearchField.getSelectionModel().getSelectedItem()));
			mainApp.getPersonList().remove(personKeyList.get(personSearchField.getSelectionModel().getSelectedItem()));
		}
	}
	
	@FXML
	private void handleFjernPerson(){
		if(recipientTable.getSelectionModel().getSelectedItem() != null){
			mainApp.getPersonList().add(recipientTable.getSelectionModel().getSelectedItem());
			mainApp.getRecipientList().remove(recipientTable.getSelectionModel().getSelectedItem());
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
		}else{
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
		}
	}
	
	
	@FXML
	private void handleNext(){
		if(inputIsValid()){
			this.mainApp.setSpaces(Integer.parseUnsignedInt(spacesField.getText()));
			this.mainApp.setName(nameField.getText());
			this.mainApp.setDate(dateField.getValue());
			this.mainApp.setFromTime(fromTime.getSelectionModel().getSelectedItem());
			this.mainApp.setToTime(toTime.getSelectionModel().getSelectedItem());
			if(descriptionField.getText() == null || descriptionField.getText().length() == 0){
				this.mainApp.setDesc("");
			}else{
				this.mainApp.setDesc(descriptionField.getText());
			}
			this.mainApp.showNewEvent2();
		}
	}
	
	
	@FXML
	private void handleClose(){
		this.mainApp.getPrimaryStage().close();
	}
	
	
	public void setMainApp(EventMain mainApp){
		this.mainApp = mainApp;
	}
	
	private boolean inputIsValid(){
		String message = "";
		if(nameField.getText() == null || nameField.getText().length() == 0){
			message += "Du må taste inn et gyldig navn!\n";
		}if(dateField.getValue() == null ){
			message += "Du må velge en dato!\n";
		}if(dateField.getValue() != null && dateField.getValue().isBefore(LocalDate.now())){
			message += "Du kan ikke velge en dato tidligere enn i dag!\n";
		}if(fromTime.getSelectionModel().getSelectedItem() == null || toTime.getSelectionModel().getSelectedItem() == null){
			message += "Du må velge et gyldig tidsrom!\n";
		}if(recipientTable.getItems().isEmpty()){
			message +="Arrangementet må ha minst 1 deltaker!\n";
		}if(spacesField.getText() == null || spacesField.getText().length() == 0){
			message += "Du taste inn antall plasser som trengs!\n";
		}else{
			try{
				Integer.parseUnsignedInt(spacesField.getText());
			}catch(NumberFormatException e){
				message += "Ikke et gyldig antall plasser!\n";
			}
		}
		
		if(message.length() == 0){
			return true;
		}else{
			Dialogs.create().title("Ugyldige Felter").masthead("Vennligst rett ugyldige felter!").message(message).showError();
			return false;
		}
	}
}
