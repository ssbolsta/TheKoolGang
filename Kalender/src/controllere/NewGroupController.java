package controllere;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.controlsfx.dialog.Dialogs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import requests.GetUserRequest;
import requests.PutGroupRequest;
import sun.security.krb5.SCDynamicStoreConfig;
import connection.ServerConnection;
import models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NewGroupController {

	@FXML private TextField nameField;
	@FXML private TextField spacesField;
	@FXML private TextArea descriptionField;
	@FXML private ComboBox<Person> personSearchField;
	@FXML private ComboBox<String> groupSearchField;
	@FXML private TableView<Person> recipientTable;
	@FXML private TableColumn<Person,String> uidColumn;
	@FXML private TableColumn<Person,String> nameColumn;
	@FXML private Button cancel;
	@FXML private ListView<String> recipientList;
	@FXML private Button nextButton;

	private ObservableList<Person> personTableList = FXCollections.observableArrayList();
	private ObservableList<String> usernameList = FXCollections.observableArrayList();
	private ObservableList<String> nameList = FXCollections.observableArrayList();
	private ObservableList<Person> peopleList = FXCollections.observableArrayList();
	

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

	public NewGroupController(){
	}

	private boolean inputIsValid(){
		String message = "";
		if(nameField.getText() == null || nameField.getText().length() == 0){
			message += "Du må taste inn et gyldig gruppenavn!\n";
		}if(descriptionField.getText().trim().length() == 0){
			message += "Du må legge inn en gruppebeskrivelse\n";
		}if(recipientTable.getItems().isEmpty()){
			message +="Gruppen må ha minst ett medlem!\n";
		}
		if(message==""){
			return true;
		}
		else{
			Dialogs.create().title("Ugyldige Felter").masthead("Vennligst rett ugyldige felter!").message(message).showError();
			return false;
		}
	}





	@FXML private void initialize(){
		uidColumn.setCellValueFactory(cellData -> cellData.getValue().getUidStringProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
		recipientTable.setItems(personTableList);

		try {
			ServerConnection SC = new ServerConnection("78.91.74.198", 5432);
			GetUserRequest getUser = new GetUserRequest();
			JSONArray response = (JSONArray) SC.sendRequest(getUser);
			System.out.println(response.toJSONString());
			//System.out.println(arr.toJSONString());
			Iterator itr = response.iterator();
			JSONParser parser = new JSONParser();
			int i = 0;
			while(itr.hasNext()) {
				JSONObject person;
				person = (JSONObject) itr.next();
				Person p = new Person(person.get("firstname").toString(),person.get("lastname").toString(),Integer.parseInt(person.get("uid").toString()));
				peopleList.add(p);
				nameList.add(person.get("lastname") + ", " + person.get("firstname"));
				
//					nameList.add(person.get("lastname") + ", " + person.get("firstname"));
//					usernameList.add(person.get("username") + "");
//					System.out.println(person.toJSONString());
//					//					System.out.println(usernames.get(i));
				i++;
			}
			this.personSearchField.setItems(peopleList);
			new AutoCompleteCombobox<>(this.personSearchField);


		} catch (IOException e) {
			e.printStackTrace();
		}

		new AutoCompleteCombobox<>(this.personSearchField);



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
//		if(personSearchField.getSelectionModel().getSelectedItem() != null){
//			System.out.println(personSearchField.getSelectionModel().getSelectedItem());
//			System.out.println(nameList.indexOf(personSearchField.getSelectionModel().getSelectedItem()));
//			System.out.println(recipientTable.isVisible());
//			personTableList.add(personSearchField.getSelectionModel().getSelectedItem());
//			nameList.remove(personSearchField.getSelectionModel().getSelectedItem());
//			System.out.println(personTableList.get(0));
			
		if(personSearchField.getSelectionModel().getSelectedItem() != null){
			personTableList.add(personSearchField.getSelectionModel().getSelectedItem());
			//nameList.remove(personSearchField.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	private void handleFjernPerson(){
		if(recipientTable.getSelectionModel().getSelectedItem()!= null){
			System.out.println(recipientTable.getSelectionModel().getSelectedItem());
			System.out.println("meg");
			//System.out.println(recipientTable.indexOf(personSearchField.getSelectionModel().getSelectedItem()));
			//personTableList.add(recipientTable.getSelectionModel().getSelectedItem());
			//nameList.remove(recipientTable.getSelectionModel().getSelectedItem());
		}
	}
	@FXML
	private void createGroup(){
		if(inputIsValid()){
			PutGroupRequest PGR = new PutGroupRequest();
			PGR.setName(nameField.getText());
//			PGR.setAdmin(admin);
		}
	}
}



