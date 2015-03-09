package controllere;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.controlsfx.dialog.Dialogs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import requests.GetUserRequest;
import requests.ModifyGroupRequest;
import requests.PutGroupRequest;
import connection.ServerConnection;
import models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import kalenderGUI.EventMain;

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

	//	private ObservableList<String> usernameList = FXCollections.observableArrayList();
	//	private ObservableList<String> nameList = FXCollections.observableArrayList();
	private ObservableList<Person> chosenList = FXCollections.observableArrayList();
	private ObservableList<Person> peopleList = FXCollections.observableArrayList();
	private HashMap<String,Person> personKeyList = new HashMap<String,Person>();
	private EventMain mainApp;
	private ServerConnection sc;

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
		recipientTable.setItems(chosenList);

		try {
			sc = new ServerConnection("78.91.74.198", 5432);
			GetUserRequest getUser = new GetUserRequest();
			JSONArray response = (JSONArray) sc.sendRequest(getUser);
			System.out.println(response.toJSONString());
			//System.out.println(arr.toJSONString());
			Iterator itr = response.iterator();
//			JSONParser parser = new JSONParser();
			int i = 0;
			while(itr.hasNext()) {
				JSONObject person;
				person = (JSONObject) itr.next();
				Person p = new Person(person.get("firstname").toString(),person.get("lastname").toString(),Integer.parseInt(person.get("uid").toString()));
				peopleList.add(p);
				personKeyList.put(p.toString(), p);
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
		if(personKeyList.get(personSearchField.getSelectionModel().getSelectedItem()) != null){
			chosenList.add(personKeyList.get(personSearchField.getSelectionModel().getSelectedItem()));
			peopleList.remove(personKeyList.get(personSearchField.getSelectionModel().getSelectedItem()));
		}
	}

	@FXML
	private void handleFjernPerson(){
		if(recipientTable.getSelectionModel().getSelectedItem() != null){
			peopleList.add(recipientTable.getSelectionModel().getSelectedItem().getUID()-1,(recipientTable.getSelectionModel().getSelectedItem()));
			chosenList.remove(recipientTable.getSelectionModel().getSelectedItem());
		}
	}
	@FXML
	private void createGroup(){
		if(inputIsValid()){
			PutGroupRequest pgr = new PutGroupRequest();
			pgr.setName(nameField.getText());
			pgr.setAdmin(GlobalUserID.userID);
			ModifyGroupRequest mgr = new ModifyGroupRequest();
			for (Person person:chosenList){
				mgr.addMemeberToAdd(person.getUID());
			}
			try {
				sc.sendRequest(pgr);
				sc.sendRequest(mgr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}



