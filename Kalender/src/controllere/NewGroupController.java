package controllere;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.controlsfx.dialog.Dialogs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import requests.GetGroupRequest;
import requests.GetUserRequest;
import requests.ModifyGroupRequest;
import requests.PutGroupRequest;
import connection.ServerConnection;
import models.Group;
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
	@FXML private ComboBox<Group> groupSearchField;
	@FXML private TableView<Person> recipientTable;
	@FXML private TableView<Group> groupTable;
	@FXML private TableColumn<Group,String> groupColumn;
	@FXML private TableColumn<Person,String> usernameColumn;
	@FXML private TableColumn<Person,String> nameColumn;
	@FXML private ListView<String> recipientList;
	@FXML private Button cancel;
	@FXML private Button nextButton;

	private ObservableList<Group> groupList = FXCollections.observableArrayList();
	private ObservableList<Group> chosenGroupList = FXCollections.observableArrayList();
	private HashMap<String,Group> groupKeyList = new HashMap<String,Group>();
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
			message += "Du m� taste inn et gyldig gruppenavn!\n";
		}
		for(Group group:groupList){
			if(nameField.getText().equals(group.getName())){
				message += "Det er allerede en gruppe med det navnet";
			}
		}
		if(descriptionField.getText().trim().length() == 0){
			message += "Du m� legge inn en gruppebeskrivelse\n";
		}if(recipientTable.getItems().isEmpty()&& groupTable.getItems().isEmpty()){
			message +="Gruppen m� ha minst ett medlem!\n";
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
		usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsernameProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
		recipientTable.setItems(chosenList);
		groupColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		groupTable.setItems(chosenGroupList);


		try {
			sc = new ServerConnection("78.91.51.221", 54321);
			GetUserRequest getUser = new GetUserRequest();
			JSONArray response = (JSONArray) sc.sendRequest(getUser);
			Iterator itr = response.iterator();
			while(itr.hasNext()) {
				JSONObject person;
				person = (JSONObject) itr.next();
				Person p = new Person(person.get("firstname").toString(),person.get("lastname").toString(),person.get("username").toString(), Integer.parseInt(person.get("uid").toString()));
				peopleList.add(p);
				personKeyList.put(p.toString(), p);
			}

			GetGroupRequest getGroup = new GetGroupRequest();
			JSONArray response2 = sc.sendRequest(getGroup);
			System.out.println(response2.toJSONString());
			Iterator itr1 = response2.iterator();
			while(itr1.hasNext()){
				JSONObject group;
				group = (JSONObject) itr1.next();
				Group g = new Group(Integer.parseInt(group.get("gid").toString()), group.get("name").toString());
				groupList.add(g);
				groupKeyList.put(g.getName(), g);
			}

			this.personSearchField.setItems(peopleList);
			this.groupSearchField.setItems(groupList);
			new AutoCompleteCombobox<>(this.groupSearchField);
			new AutoCompleteCombobox<>(this.personSearchField);


		} catch (IOException e) {
			e.printStackTrace();
		}




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
			peopleList.add((recipientTable.getSelectionModel().getSelectedItem()));
			chosenList.remove(recipientTable.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	private void handleAddGroup(){
		if(groupKeyList.get(groupSearchField.getSelectionModel().getSelectedItem()) != null){
			chosenGroupList.add(groupKeyList.get(groupSearchField.getSelectionModel().getSelectedItem()));
			groupList.remove(groupKeyList.get(groupSearchField.getSelectionModel().getSelectedItem()));
		}
	}

	@FXML
	private void handleRemoveGroup(){
		if(groupKeyList.get(groupSearchField.getSelectionModel().getSelectedItem()) != null){
			groupList.add(groupKeyList.get(groupSearchField.getSelectionModel().getSelectedItem()));
			chosenGroupList.remove(groupKeyList.get(groupSearchField.getSelectionModel().getSelectedItem()));
		}
	}

	@FXML
	private void createGroup(){
		if(inputIsValid()){
			PutGroupRequest pgr = new PutGroupRequest();
			pgr.setName(nameField.getText());
			pgr.setAdmin(GlobalUserID.userID);
			GetGroupRequest ggr = new GetGroupRequest();
			ggr.setName(nameField.getText());
			ModifyGroupRequest mgr = new ModifyGroupRequest();
			for (Person person:chosenList){
				mgr.addMemeberToAdd(person.getUid());
			}
			for(Group group:chosenGroupList){
				mgr.addGroupToAdd(group.getGroupID());
			}
			try {
				sc.sendRequest(pgr);
				JSONObject jobj = (JSONObject) sc.sendRequest(ggr).get(0);
				int groupID = Integer.parseInt(jobj.get("gid").toString());
				mgr.setID(groupID);
				sc.sendRequest(mgr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Dialogs.create().title("Ny gruppe er laget").masthead("Ny gruppe").message("Du har laget en ny gruppe").showError();
			Stage stage = (Stage) cancel.getScene().getWindow();
			stage.hide();
			stage.close();
		}
	}
}



