package kalenderGUI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import models.Group;
import models.Person;
import models.Room;
import models.RoomComparator;
import controllere.ConnectionForReal;
import controllere.NewEvent1Controller;
import controllere.NewEvent2Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EventMain extends Application {

	private AnchorPane root;
	private Stage primaryStage;
	private AgendaApplication mainApp;

	private ObservableList<Person> personList = FXCollections.observableArrayList();
	private ObservableList<Group> groupList = FXCollections.observableArrayList();
	private ObservableList<Person> recipientList = FXCollections.observableArrayList();
	private ObservableList<Room> roomList = FXCollections.observableArrayList();
	private ObservableList<Group> chosenGroupList = FXCollections.observableArrayList();

	private String name;
	private String desc;
	private LocalTime fromTime;
	private LocalTime toTime;
	private LocalDate date;
	private Integer spaces;


	@SuppressWarnings("rawtypes")
	public EventMain(){
		try{
			ConnectionForReal.setURL("http://78.91.44.241:5050/");
			ConnectionForReal.scon.login("krissvor","passord");
			JSONArray response = ConnectionForReal.scon.sendGet("users");
			Iterator itr = response.iterator();
			while(itr.hasNext()){
				JSONObject person;
				try{
					person = (JSONObject) itr.next();
					if(person.get("uid").toString().equalsIgnoreCase(Long.toString(ConnectionForReal.uid))){
						continue;
					}
					personList.add(new Person(person.get("firstname").toString(),person.get("lastname").toString(),person.get("username").toString(), Integer.parseInt(person.get("uid").toString())));

				}catch(Exception e){
					e.printStackTrace();
				}
			}

			JSONArray response1 = ConnectionForReal.scon.sendGet("groups");
			Iterator itr1 = response1.iterator();
			while(itr1.hasNext()){
				JSONObject group;
				group = (JSONObject) itr1.next();
				Group g = new Group(Integer.parseInt(group.get("gid").toString()), group.get("name").toString());
				groupList.add(g);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}



	@Override
	public void start(Stage primaryStage) {
		try{

			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("NewEvent1.fxml"));
			root = (AnchorPane) loader.load();
			NewEvent1Controller controller = loader.getController();
			controller.setMainApp(this);
			controller.showData();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.show();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public void setMainApp(AgendaApplication mainApp){
		this.mainApp = mainApp;
	}

	public void showNewEvent1(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("NewEvent1.fxml"));
			root = (AnchorPane) loader.load();
			NewEvent1Controller controller = loader.getController();
			controller.setMainApp(this);
			controller.showData();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	public void showNewEvent2(){
		roomList.removeAll(roomList);
		try{
			JSONArray response2 = ConnectionForReal.scon.sendGet("rooms/available/" + date.toString() + "/" + fromTime.getHour() +":00:00/" + toTime.getHour() + ":00:00");
			@SuppressWarnings("rawtypes")
			Iterator itr2 = response2.iterator();
			while(itr2.hasNext()){
				JSONObject room;
				room = (JSONObject) itr2.next();
				if( Integer.parseInt(room.get("capacity").toString()) < spaces){
					continue;
				}
				Room r = new Room(Integer.parseInt(room.get("rid").toString()), room.get("name").toString(), Integer.parseInt(room.get("capacity").toString()));
				roomList.add(r);
				System.out.println(r.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		roomList.sort(new RoomComparator());

		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("NewEvent2.fxml"));
			root = (AnchorPane) loader.load();
			NewEvent2Controller controller = loader.getController();
			controller.setMainApp(this);
			controller.showData();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.show();

		}catch(IOException e){
			e.printStackTrace();
		}

	}


	public void close(){
		this.primaryStage.close();
		this.mainApp.setNewEventStage(null);
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}


	public void createEvent(Room location){
		HashMap<String,String> request= new HashMap<String,String>();
		request.put("name", name);
		request.put("description", desc);
		request.put("rid",location.getRoomID().toString());
		request.put("starttime", fromTime.getHour() + ":00:00");
		request.put("endtime", toTime.getHour() + ":00:00");
		request.put("eventdate", date.toString());

		JSONObject app;

		try {
			app = (JSONObject) ConnectionForReal.scon.sendPost("events", request).get(0);
			System.out.println(app);


			request.clear();

			String s= "";

			for (Group group : chosenGroupList) {
				s += group.getGroupID() +",";
			}

			if(s.length() != 0){
				request.put("eid", app.get("eid").toString());
				request.put("groups", s.substring(0, s.length()-1));
				try {
					ConnectionForReal.scon.sendPost("events/add/groups", request);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}



			request.clear();

			s = "";

			for (Person p : recipientList) {
				s += p.getUid() + ",";
			}

			if(s.length() != 0){
				request.put("eid", app.get("eid").toString());
				request.put("users", s.substring(0, s.length() - 1));
				try {
					ConnectionForReal.scon.sendPost("events/invite/users", request);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
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


	public void setRecipientList(ObservableList<Person> recipientList){
		this.recipientList = recipientList;
	}
	public ObservableList<Person> getRecipientList(){
		return recipientList;
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
