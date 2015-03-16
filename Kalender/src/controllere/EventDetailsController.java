package controllere;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import requests.GetEventRequest;
import connection.ServerConnection;
import models.Group;
import models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EventDetailsController {

	@FXML private Text nameText;
	@FXML private Text dateText;
	@FXML private Text timeText;
	@FXML private Text roomText;
	@FXML private Text descriptionText;
	@FXML private TableView<Person> recipientTable;
	@FXML private TableColumn<Person,String> nameColumn;
	@FXML private TableColumn<Person,String> usernameColumn;
	@FXML private TableView<Group> groupTable;
	@FXML private TableColumn<Group,String> groupColumn;
	@FXML private Button cancel;
	@FXML private Button decline;

	private int eventId = 12;
	private ServerConnection sc;
	private ObservableList<Group> groupList = FXCollections.observableArrayList();
	private HashMap<String,Group> groupKeyList = new HashMap<String,Group>();
	private ObservableList<Person> peopleList = FXCollections.observableArrayList();
	private HashMap<String,Person> personKeyList = new HashMap<String,Person>();
	

	//	public EventDetailsController(int eid) {
	//		this.eventId = eid;
	//	}
	@FXML
	private void initialize(){
		usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsernameProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
		recipientTable.setItems(peopleList);
		groupColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		groupTable.setItems(groupList);
		try{
			JSONArray response1 = null;
			Iterator itr = response1.iterator();
			while(itr.hasNext()) {
				JSONObject person;
				person = (JSONObject) itr.next();
				Person p = new Person(person.get("firstname").toString(),person.get("lastname").toString(),person.get("username").toString(), Integer.parseInt(person.get("uid").toString()));
				peopleList.add(p);
				personKeyList.put(p.toString(), p);
			
			}
			
			JSONArray response2 = null;
			Iterator itr1 = response2.iterator();
			while(itr1.hasNext()){
				JSONObject group;
				group = (JSONObject) itr1.next();
				Group g = new Group(Integer.parseInt(group.get("gid").toString()), group.get("name").toString());
				groupList.add(g);
				groupKeyList.put(g.getName(), g);
			}
			
			
			sc = new ServerConnection("78.91.47.218", 54321);
			GetEventRequest getEvent = new GetEventRequest();
			getEvent.setId(this.eventId);
			JSONArray response = sc.sendRequest(getEvent);
			JSONObject app = (JSONObject) response.get(0);
			System.out.println(response);
			String time = app.get("starttime").toString().substring(0,5) + " - " + app.get("endtime").toString().substring(0,5);
			timeText.setText(time);
			dateText.setText(app.get("eventdate").toString());
			if(app.get("name") != null){
				nameText.setText(app.get("name").toString());
			}
			else{
				nameText.setText("Arrangementet har ikke navn");
			}
			if(app.get("description") != null){
				descriptionText.setText(app.get("description").toString());
			}
			else{
				descriptionText.setText("Arrangementet har ikke beskrivelse");
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	@FXML
	private void handleDecline(){
		
	}

	@FXML
	private void handleClose(){
		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.hide();
		stage.close();
	}

}
