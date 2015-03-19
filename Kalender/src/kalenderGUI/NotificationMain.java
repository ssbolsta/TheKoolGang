package kalenderGUI;

import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import models.Invitation;
import models.Notification;
import controllere.ConnectionForReal;
import controllere.NotificationController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NotificationMain extends Application{

	private Stage primaryStage;
	private AnchorPane root;
	
	private ObservableList<Notification> notifications = FXCollections.observableArrayList();
	
	
	public NotificationMain(){
		try {
			JSONArray response = ConnectionForReal.scon.sendGet("notifications");
			Iterator itr = response.iterator();
			while(itr.hasNext()){
				JSONObject invitation;
				try{
					invitation = (JSONObject) itr.next();
					notifications.add( new Notification(invitation.get("nid").toString(), invitation.get("uid").toString(), invitation.get("description").toString(),invitation.get("notificationtime").toString()));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void start(Stage primaryStage){
		try{
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(EventMain.class.getResource("Notifications.fxml"));
			root = (AnchorPane) loader.load();
			NotificationController controller = loader.getController();
			controller.setMainApp(this);
			controller.showData();
			this.primaryStage.setScene(new Scene(root));
			this.primaryStage.setResizable(false);
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
	
	public ObservableList<Notification> getNotifications(){
		return notifications;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}
