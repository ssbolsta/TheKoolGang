package controllere;

import models.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import kalenderGUI.NotificationMain;

public class NotificationController {
	
	
	@FXML
	private ListView<Notification> notificationList;
	
	private ObservableList<Notification> notifications = FXCollections.observableArrayList();
	private NotificationMain mainApp;
	
	
	@FXML
	private void initialize(){
		notificationList.setItems(notifications);
		notificationList.getSelectionModel().select(null);
	}
	
	
	@FXML
	private void handleDelete(){
		if(notificationList.getSelectionModel().getSelectedItem() != null){
			notifications.remove(notificationList.getSelectionModel().getSelectedItem());			
		}
	}
	
	@FXML
	private void handleClose(){
		this.mainApp.close();
	}
	
	public void setMainApp(NotificationMain mainApp){
		this.mainApp = mainApp;
	}
}
