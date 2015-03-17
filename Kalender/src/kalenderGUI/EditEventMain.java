package kalenderGUI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import requests.ModifyEventRequest;
import models.Group;
import models.Person;
import models.Room;
import connection.ServerConnection;
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
	
	private String name;
	private String desc;
	private LocalTime fromTime;
	private LocalTime toTime;
	private LocalDate date;
	private Integer spaces;
	private ServerConnection sc;
	
//	public EditEventMain(int EventID){
//		
//	}
	
	@Override
	public void start(Stage primaryStage) {
		try{
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EditEventMain.class.getResource("EditEvent1.fxml"));
			root = (AnchorPane) loader.load();
			EditEvent1Controller controller = loader.getController();
			controller.setMainApp(this);
			controller.showData();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	
	public void showEditEvent1(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("EditEvent1.fxml"));
			root = (AnchorPane) loader.load();
			EditEvent1Controller controller = loader.getController();
			controller.setMainApp(this);
			controller.showData();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.show();
		}catch(IOException e){
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
			controller.showData();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.show();

		}catch(IOException e){
			e.printStackTrace();
		}

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
