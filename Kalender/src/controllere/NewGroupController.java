package controllere;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import requests.GetUserRequest;
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
import javafx.util.Callback;
import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class NewGroupController {

	@FXML private TextField nameField;
	@FXML private TextField spacesField;
	@FXML private TextArea descriptionField;
	@FXML private ComboBox<String> personSearchField;
	@FXML private ComboBox<String> groupSearchField;
	@FXML private TableView<String> recipientTable;
	@FXML private TableColumn<String,String> uidColumn;
	@FXML private TableColumn<String,String> nameColumn;
	@FXML private Button cancel;
	@FXML private ListView<String> recipientList;

	private ObservableList<String> personTableList = FXCollections.observableArrayList();
	private ObservableList<String> usernameList = FXCollections.observableArrayList();
	private ObservableList<String> nameList = FXCollections.observableArrayList();

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

	@FXML private void initialize(){
		//uidColumn.setCellValueFactory(cellData -> cellData.getValue().getUidStringProperty());
//		nameColumn.setCellValueFactory();
		//personTableList.add("dritt");
		recipientTable.setItems(personTableList);

		try {
			ServerConnection SC = new ServerConnection("78.91.49.227", 5432);
			GetUserRequest getUser = new GetUserRequest();
			JSONArray response = (JSONArray) SC.sendRequest(getUser);
			System.out.println(response.toJSONString());
			//System.out.println(arr.toJSONString());
			Iterator itr = response.iterator();
			JSONParser parser = new JSONParser();
			int i = 0;
			while(itr.hasNext()) {
				JSONObject person;
				try {
					person = (JSONObject) parser.parse(itr.next().toString());
					nameList.add(person.get("lastname") + ", " + person.get("firstname"));
					usernameList.add(person.get("username") + "");
					System.out.println(person.toJSONString());
					//					System.out.println(usernames.get(i));
					i++;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			this.personSearchField.setItems(nameList);
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
		if(personSearchField.getSelectionModel().getSelectedItem() != null){
			System.out.println(personSearchField.getSelectionModel().getSelectedItem());
			System.out.println(nameList.indexOf(personSearchField.getSelectionModel().getSelectedItem()));
			System.out.println(recipientTable.isVisible());
			personTableList.add(personSearchField.getSelectionModel().getSelectedItem());
			nameList.remove(personSearchField.getSelectionModel().getSelectedItem());
			System.out.println(personTableList.get(0));
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
}



