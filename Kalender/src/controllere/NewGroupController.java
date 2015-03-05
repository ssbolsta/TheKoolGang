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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
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
		//nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
		recipientTable.setItems(usernameList);
		
		try {
			ServerConnection SC = new ServerConnection();
			GetUserRequest getUser = new GetUserRequest();
			JSONObject response = SC.sendRequest(getUser);
			JSONArray arr = (JSONArray) response.get("content");
			System.out.println(arr.toJSONString());
			Iterator itr = arr.iterator();
			JSONParser parser = new JSONParser();
			int i = 0;
			while(itr.hasNext()) {
				JSONObject person;
				try {
					person = (JSONObject) parser.parse(itr.next().toString());
					nameList.add(person.get("last_name") + ", " + person.get("first_name"));
					usernameList.add(person.get("username") + "");
//					System.out.println(names.get(i));
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
			personTableList.add(personSearchField.getSelectionModel().getSelectedItem());
			nameList.remove(personSearchField.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	private void handleFjernPerson(){
		
	}
}



