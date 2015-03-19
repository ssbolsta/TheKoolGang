package controllere;


import models.Group;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kalenderGUI.ShowGroupsMain;


public class ShowGroupsController {

	@FXML
	private TableView<Group> groupTable;
	@FXML
	private TableColumn<Group,String> gidColumn;
	@FXML
	private TableColumn<Group,String> nameColumn;
	@FXML
	private ListView<String> membersList;
	
	
	private ShowGroupsMain mainApp;


	@FXML
	private void initialize(){
		gidColumn.setCellValueFactory(cellData -> cellData.getValue().getGidStringProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

	}

	public void showData(){
		groupTable.setItems(this.mainApp.getGroups());
		groupTable.getSelectionModel().clearSelection();
	}


	public void setMainApp(ShowGroupsMain showGroups){
		mainApp = showGroups;
	}


	@FXML
	private void handleCancel(){
		this.mainApp.close();
	}
	
	@FXML
	private void handleMeldUt(){
		
	}
	
}
