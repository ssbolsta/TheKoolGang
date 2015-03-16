package kalenderGUI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import requests.GetGroupRequest;
import requests.GetRoomRequest;
import requests.GetUserRequest;
import requests.PutEventRequest;
import models.Group;
import models.Person;
import models.Room;
import connection.ServerConnection;
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
	private ServerConnection sc;


	public EventMain(){
		try{
			sc = new ServerConnection("78.91.47.218",54321);
			GetUserRequest getUser = new GetUserRequest();
			JSONArray response = sc.sendRequest(getUser);
			@SuppressWarnings("rawtypes")
			Iterator itr = response.iterator();
			JSONParser parser = new JSONParser();
			while(itr.hasNext()){
				JSONObject person;
				try{
					person = (JSONObject) parser.parse(itr.next().toString());
					personList.add(new Person(person.get("firstname").toString(),person.get("lastname").toString(),person.get("username").toString(), Integer.parseInt(person.get("uid").toString())));

				}catch(ParseException e){
					e.printStackTrace();
				}
			}
			GetGroupRequest getGroup = new GetGroupRequest();
			JSONArray response1 = sc.sendRequest(getGroup);
			System.out.println(response1.toJSONString());
			Iterator itr1 = response1.iterator();
			while(itr1.hasNext()){
				JSONObject group;
				group = (JSONObject) itr1.next();
				Group g = new Group(Integer.parseInt(group.get("gid").toString()), group.get("name").toString());
				groupList.add(g);
			}
			GetRoomRequest getRoom = new GetRoomRequest();
			JSONArray response2 = sc.sendRequest(getRoom);
			Iterator itr2 = response2.iterator();
			while(itr2.hasNext()){
				JSONObject room;
				room = (JSONObject) itr2.next();
				Room r = new Room(Integer.parseInt(room.get("rid").toString()), room.get("name").toString(), Integer.parseInt(room.get("capacity").toString()));
				roomList.add(r);
				System.out.println(r.toString());
			}
		}catch(IOException e){
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
		}catch(IOException e){
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

		PutEventRequest putEvent = new PutEventRequest();

		String eventDate = (""+ date.getYear()+ "-" + date.getMonth()+ "-" + date.getDayOfWeek());
		String eventFromTime = ( "" +fromTime.getHour()+ ":00:00");
		String eventToTime = ( "" +toTime.getHour()+ ":00:00");
		int roomId = location.getRoomID();

		putEvent.setAdmin(3);
		putEvent.setDate(eventDate);
		putEvent.setDesc(desc);
		putEvent.setIn_room(roomId);
		putEvent.setName(name);
		putEvent.setTime(eventFromTime, eventToTime);

		try {
			sc.sendRequest(putEvent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
