package kalenderGUI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import requests.ModifyEventRequest;
import models.Group;
import models.Person;
import models.Room;
import connection.ServerConnection;
import controllere.ConnectionForReal;
import controllere.EditEvent1Controller;
import controllere.EditEvent2Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EditEventMain extends Application{

	private AnchorPane root;
	private Stage primaryStage;

	private ObservableList<Person> personList = FXCollections.observableArrayList();
	private ObservableList<Group> groupList = FXCollections.observableArrayList();
	private ObservableList<Person> recipientList = FXCollections.observableArrayList();
	private ObservableList<Room> roomList = FXCollections.observableArrayList();
	private ObservableList<Group> chosenGroupList = FXCollections.observableArrayList();
	private ObservableList<Person> addedPersonList = FXCollections.observableArrayList();
	private ObservableList<Person> removedPersonList = FXCollections.observableArrayList();
	private ObservableList<Group> addedGroupList = FXCollections.observableArrayList();
	private ObservableList<Group> removedGroupList = FXCollections.observableArrayList();

	private String name;
	private String desc;
	private LocalTime fromTime;
	private LocalTime toTime;
	private LocalDate date;
	private Integer spaces;
	private ServerConnection sc;
	private int eid = 13;
	private JSONObject app;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		ConnectionForReal.setURL("http://78.91.46.78:5050/");
		try {
			ConnectionForReal.scon.login("krissvor","passord");
			app = (JSONObject) ConnectionForReal.scon.sendGet("events/eid/" + eid).get(0);
			System.out.println(app.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		showEditEvent1();

	}

	public void showEditEvent1(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EditEventMain.class.getResource("EditEvent1.fxml"));
			root = (AnchorPane) loader.load();
			EditEvent1Controller controller = loader.getController();
			controller.setMainApp(this);



			//			Henter alle brukere

			JSONArray response = ConnectionForReal.scon.sendGet("users");
			Iterator itr = response.iterator();
			while(itr.hasNext()) {
				JSONObject person;
				person = (JSONObject) itr.next();
				Person p = new Person(person.get("firstname").toString(),person.get("lastname").toString(),person.get("username").toString(), Integer.parseInt(person.get("uid").toString()));
				personList.add(p);
			}
			System.out.println(personList.toString());



			//			Henter alle brukere som er med p� arrangementet

			JSONArray response1 = ConnectionForReal.scon.sendGet("users/participantof/" + eid);
			Iterator itr1 = response1.iterator();
			while(itr1.hasNext()) {
				JSONObject person;
				person = (JSONObject) itr1.next();
				int uid = Integer.parseInt(person.get("uid").toString());
				for(Person p : personList){
					if(p.getUid() == uid){
						recipientList.add(new Person(person.get("firstname").toString(),person.get("lastname").toString(),person.get("username").toString(), Integer.parseInt(person.get("uid").toString())));
						personList.remove(p);
						break;
					}
				}
			}


			//			Henter alle grupper

			JSONArray response3 = ConnectionForReal.scon.sendGet("groups");
			Iterator itr3 = response3.iterator();
			while(itr3.hasNext()){
				JSONObject group;
				group = (JSONObject) itr3.next();
				Group g = new Group(Integer.parseInt(group.get("gid").toString()), group.get("name").toString());
				groupList.add(g);
			}

			//			Henter alle grupper som er med p� arrangementet
			JSONArray response2 = ConnectionForReal.scon.sendGet("groups/participantof/" + eid);
			Iterator itr2 = response2.iterator();
			while(itr2.hasNext()){
				JSONObject group;
				group = (JSONObject) itr2.next();
				int gid = Integer.parseInt(group.get("gid").toString());
				for(Group g : groupList){
					System.out.println("1");
					if(g.getGroupID() == gid){
						chosenGroupList.add(new Group(Integer.parseInt(group.get("gid").toString()), group.get("name").toString()));
						groupList.remove(g);
						break;
					}
				}
			}

			controller.showData();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.show();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	public void showEditEvent2(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("EditEvent2.fxml"));
			root = (AnchorPane) loader.load();
			EditEvent2Controller controller = loader.getController();
			controller.setMainApp(this);

			try {
				JSONArray response4 = ConnectionForReal.scon.sendGet("rooms");
				Iterator itr4 = response4.iterator();
				while (itr4.hasNext()){
					JSONObject room;
					room = (JSONObject) itr4.next();
					Room r = new Room(Integer.parseInt(room.get("rid").toString()), room.get("name").toString(), Integer.parseInt(room.get("capacity").toString()));
					roomList.add(r);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}


			controller.showData();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.show();

		}catch(IOException e){
			e.printStackTrace();
		}

	}

	public void editEvent(Room room){


		ArrayList<Integer> persons = new ArrayList<Integer>();
		ArrayList<Integer> groups = new ArrayList<Integer>();
		ArrayList<Integer> removedGroup = new ArrayList<Integer>();
		ArrayList<Integer> removedPerson = new ArrayList<Integer>();

		for (Person person:recipientList){
			persons.add(person.getUid());
		}
		
		for (Person person:removedPersonList){
			removedPerson.add(person.getUid());
		}

		for(Group group:chosenGroupList){
			groups.add(group.getGroupID());
		}

		for(Group group:removedGroupList){
			removedGroup.add(group.getGroupID());
		}

		HashMap<String, String > eventValue = new HashMap<String, String>();
		eventValue.put("name", this.name);
		eventValue.put("eid", Integer.toString(eid));

		// first remove, then add. Groups, then people
		
		HashMap<String, String> removedGroupsValues = new HashMap<String, String>();
		removedGroupsValues.put("eid", Integer.toString(eid));
		removedGroupsValues.put("groups", joinArrayList(removedGroup, ","));
		
		HashMap<String, String> removedUsersValues = new HashMap<String, String>();
		removedUsersValues.put("eid", Integer.toString(eid));
		removedUsersValues.put("users", joinArrayList(removedPerson, ","));
		
		HashMap<String, String> groupsValues = new HashMap<String, String>();
		groupsValues.put("eid", Integer.toString(eid));
		groupsValues.put("groups", joinArrayList(groups, ","));
		
		HashMap<String, String> userValues = new HashMap<String, String>();
		userValues.put("eid", Integer.toString(eid));
		userValues.put("users", joinArrayList(persons, ","));
		//		userValues.put("groups", joinArrayList(groups, ","));


		// Add users
		try {
			ConnectionForReal.scon.sendPost("events/remove/groups/" , removedGroupsValues);
			ConnectionForReal.scon.sendPost("events/remove/users/" , removedUsersValues);
			
			ConnectionForReal.scon.sendPost("events/add/users/", userValues);
			ConnectionForReal.scon.sendPut("events/add/groups", groupsValues);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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


	public void close(){
		this.primaryStage.close();
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage(){
		return this.primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void setSpaces(Integer spaces){
		this.spaces = spaces;
	}
	public Integer getSpaces(){
		return spaces;
	}

	public JSONObject getAppointment(){
		return app;
	}


	public void setRecipientList(ObservableList<Person> recipientList){
		this.recipientList = recipientList;
	}
	public ObservableList<Person> getRecipientList(){
		return recipientList;
	}
	
	public void setRemovedPerson(ObservableList<Person> removedList){
		this.removedPersonList = removedList;
	}
	public ObservableList<Person> getRemovedPerson(){
		return this.removedPersonList;
	}
	public void setAddedPerson(ObservableList<Person> addedList){
		this.addedPersonList = addedList;
	}
	public ObservableList<Person> getAddedPerson(){
		return this.addedPersonList;
	}
	public void setRemovedGroup(ObservableList<Group> removedList){
		this.removedGroupList = removedList;
	}
	public ObservableList<Group> getRemovedGroup(){
		return this.removedGroupList;
	}
	public void setAddedGroup(ObservableList<Group> addedList){
		this.addedGroupList = addedList;
	}
	public ObservableList<Group> getAddedGroup(){
		return this.addedGroupList;
	}


	public void setPersonList(ObservableList<Person> personList){
		this.personList = personList;
	}
	public ObservableList<Person> getPersonList(){
		return personList;
	}
	public ObservableList<Group> getGroupList(){
		return groupList;
	}

	public ObservableList<Group> getChosenGroupList(){
		return chosenGroupList;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}


	public LocalTime getFromTime() {
		return fromTime;
	}
	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}


	public LocalTime getToTime() {
		return toTime;
	}
	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
	}


	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

	public ObservableList<Room>	getRoomList(){
		return roomList;
	}



}
