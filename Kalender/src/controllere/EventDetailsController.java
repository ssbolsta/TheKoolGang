package controllere;


import java.util.HashMap;
import java.util.Iterator;

import org.controlsfx.dialog.Dialogs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

	private int eid;
	private int uid;
	private ObservableList<Group> groupList = FXCollections.observableArrayList();
	private HashMap<String,Group> groupKeyList = new HashMap<String,Group>();
	private ObservableList<Person> peopleList = FXCollections.observableArrayList();
	private HashMap<String,Person> personKeyList = new HashMap<String,Person>();



	public EventDetailsController(int eid) {
		this.eid = eid;
	}

	@FXML
	private void initialize(){
		usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getUsernameProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
		recipientTable.setItems(peopleList);
		groupColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
		groupTable.setItems(groupList);
		eventDetails();
	}

	@SuppressWarnings("rawtypes")
	public void eventDetails(){
		try{
			
			JSONArray response1 = ConnectionForReal.scon.sendGet("users/participantof/" + eid);
			Iterator itr = response1.iterator();
			while(itr.hasNext()) {
				JSONObject person;
				person = (JSONObject) itr.next();
				Person p = new Person(person.get("firstname").toString(),person.get("lastname").toString(),person.get("username").toString(), Integer.parseInt(person.get("uid").toString()));
				peopleList.add(p);
				personKeyList.put(p.toString(), p);

			}

			JSONArray response2 = ConnectionForReal.scon.sendGet("groups/participantof/" + eid);
			Iterator itr1 = response2.iterator();
			while(itr1.hasNext()){
				JSONObject group;
				group = (JSONObject) itr1.next();
				Group g = new Group(Integer.parseInt(group.get("gid").toString()), group.get("name").toString());
				groupList.add(g);
				groupKeyList.put(g.getName(), g);
			}

			
			
			JSONArray response = ConnectionForReal.scon.sendGet("events/eid/" + eid);
			JSONObject app = (JSONObject) response.get(0);
			String time = app.get("starttime").toString().substring(0,5) + " - " + app.get("endtime").toString().substring(0,5);
			timeText.setText(time);
			dateText.setText(app.get("eventdate").toString());
			JSONArray roomResponse = ConnectionForReal.scon.sendGet("rooms/rid/" + (app.get("rid")));
			JSONObject room= (JSONObject) roomResponse.get(0);
			roomText.setText(room.get("name").toString());
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
			
			
			this.uid = Integer.parseInt(app.get("admin").toString());
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@FXML
	private void handleDecline(){
		if(this.uid != ConnectionForReal.uid){
			HashMap<String,String> hashMap = new HashMap<String,String>();
			
			hashMap.put("eid", Integer.toString(eid));
			hashMap.put("users", Long.toString(ConnectionForReal.uid));
			
			
			try{
				ConnectionForReal.scon.sendPost("events/remove/users", hashMap);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else{
			Dialogs.create().title("Advarsel").masthead("Kan ikke melde avbud på eget arrangement!").message("Dersom du ønsker å slette arrangementet, vennligst bruk slett arrangement knapp ved hovedskjerm").showWarning();
		}
		
		
		
		handleClose();
		
	}

	@FXML
	private void handleClose(){
		Stage stage = (Stage) cancel.getScene().getWindow();
		stage.hide();
		stage.close();
	}

}
