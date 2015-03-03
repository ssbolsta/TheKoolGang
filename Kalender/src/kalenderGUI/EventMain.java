package kalenderGUI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import models.Person;
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
	private ObservableList<Person> recipientList = FXCollections.observableArrayList();
	private String name;
	private String desc;
	private LocalTime fromTime;
	private LocalTime toTime;
	private LocalDate date;
	private Integer spaces;
	
	
	public EventMain(){
		
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
		this.personList = mainApp.getPersonList();
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
	}
	
	public void createEvent(){
		
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
	
	
}
