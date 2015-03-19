package controllere;

import models.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kalenderGUI.NotificationMain;

public class NotificationController {
	
	
	@FXML
	private TableView<Notification> notificationList;
	@FXML
	private TableColumn<Notification,String> nidColumn;
	@FXML
	private TableColumn<Notification,String> descColumn;
	@FXML
	private TableColumn<Notification,String> timeColumn;
	
	

	private NotificationMain mainApp;
	
	
	@FXML
	private void initialize(){
		nidColumn.setCellValueFactory(cellData -> cellData.getValue().getNidStringProperty());
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());
		descColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
	}
	
	public void showData(){
		notificationList.setItems(this.mainApp.getNotifications());
	}
	
	@FXML
	private void handleOK(){
		
	}
	
	@FXML
	private void handleClose(){
		this.mainApp.close();
	}
	
	public void setMainApp(NotificationMain mainApp){
		this.mainApp = mainApp;
	}
}
