package kalenderGUI;

import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import models.Group;
import models.GroupComparator;
import models.Person;
import controllere.ConnectionForReal;
import controllere.ShowGroupsController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShowGroupsMain extends Application{

	private Stage primaryStage;
	private AnchorPane root;
	private ObservableList<Group> groupList = FXCollections.observableArrayList();
	private ObservableList<Person> members = FXCollections.observableArrayList();
	private ObservableList<Person> nonMembers = FXCollections.observableArrayList();

	public ShowGroupsMain(){
		try {
			JSONArray response = ConnectionForReal.scon.sendGet("groups/user");
			@SuppressWarnings("rawtypes")
			Iterator itr = response.iterator();
			while(itr.hasNext()){ 
				JSONObject group;
				try{
					group = (JSONObject) itr.next();
					groupList.add( new Group( Integer.parseInt(group.get("gid").toString()), group.get("name").toString()));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			groupList.sort(new GroupComparator());
			
		} catch (Exception e) {
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


	public ObservableList<Group> getGroups(){
		return groupList;
	}

	public ObservableList<Person> getMembers(){
		return members;
	}
	
	public ObservableList<Person> getNonMembers(){
		return nonMembers;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try{
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("ShowGroups.fxml"));
			root = (AnchorPane) loader.load();
			ShowGroupsController controller = loader.getController();
			controller.setMainApp(this);
			controller.showData();
			this.primaryStage.setTitle("Gruppe vindu");
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.show();
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
