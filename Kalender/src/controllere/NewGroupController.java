package controllere;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.controlsfx.dialog.Dialogs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import models.Group;
import models.GroupComparator;
import models.Person;
import models.PersonComparator;
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
		}
		for(Group group:groupList){
			if(nameField.getText().equals(group.getName())){
				message += "Det er allerede en gruppe med det navnet";
			}
		}
		if(descriptionField.getText().trim().length() == 0){
			message += "Du må legge inn en gruppebeskrivelse\n";
		}if(recipientTable.getItems().isEmpty()&& groupTable.getItems().isEmpty()){
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





	@SuppressWarnings("rawtypes")
	@FXML private void initialize(){
		usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsernameProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
		recipientTable.setItems(chosenList);
		groupColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		groupTable.setItems(chosenGroupList);


		try {



			JSONArray response = ConnectionForReal.scon.sendGet("users");

			Iterator itr = response.iterator();
			while(itr.hasNext()) {
				JSONObject person;
				person = (JSONObject) itr.next();
				Person p = new Person(person.get("firstname").toString(),person.get("lastname").toString(),person.get("username").toString(), Integer.parseInt(person.get("uid").toString()));
				peopleList.add(p);
				personKeyList.put(p.toString(), p);
			}


			JSONArray response2;
			response2 = ConnectionForReal.scon.sendGet("groups");

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
			peopleList.sort(new PersonComparator());
			groupList.sort(new GroupComparator());
			new AutoCompleteCombobox<>(this.groupSearchField);
			new AutoCompleteCombobox<>(this.personSearchField);


		} catch (Exception e) {
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
				peopleList.sort(new PersonComparator());
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
			if(groupTable.getSelectionModel().getSelectedItem() != null){
				groupList.add(groupTable.getSelectionModel().getSelectedItem());
				chosenGroupList.remove(groupTable.getSelectionModel().getSelectedItem());
				groupList.sort(new GroupComparator());
			}
		}

		@FXML
		private void createGroup(){
			if(inputIsValid()){

				ArrayList<Integer> persons = new ArrayList<Integer>();
				ArrayList<Integer> groups = new ArrayList<Integer>();

				for (Person person:chosenList){
					persons.add(person.getUid());
				}

				for(Group group:chosenGroupList){
					groups.add(group.getGroupID());
				}
				try {

					HashMap<String, String > groupValue = new HashMap<String, String>();

					groupValue.put("name", nameField.getText());
					groupValue.put("grouptype", "null");

					// Create the group
					JSONObject group = (JSONObject) ConnectionForReal.scon.sendPost("groups", groupValue).get(0);

					HashMap<String, String> userValues = new HashMap<String, String>();
					userValues.put("users", joinArrayList(persons, ","));
					userValues.put("groups", group.get("gid").toString());

					HashMap<String, String> groupsValues = new HashMap<String, String>();
					groupsValues.put("subgroups", joinArrayList(groups, ","));
					groupsValues.put("mastergroups", group.get("gid").toString());

					// Add users
					ConnectionForReal.scon.sendPost("groups/add/users", userValues);
					// Add groups
					ConnectionForReal.scon.sendPost("groups/add/groups", groupsValues);

				} catch (Exception e) {
					e.printStackTrace();
				}
				Dialogs.create().title("Ny gruppe er laget").masthead("Ny gruppe").message("Du har laget en ny gruppe").showInformation();
				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.hide();
				stage.close();
			}
		}

		private String joinArrayList(ArrayList<Integer> r, String delimiter) {
			if(r == null || r.size() == 0 ){
				return "";
			}
			StringBuffer sb = new StringBuffer();
			int i, len = r.size() - 1;
			for (i = 0; i < len; i++){
				sb.append(r.get(i).toString() + delimiter);
			}
			return sb.toString() + r.get(i).toString();
		}
	}



