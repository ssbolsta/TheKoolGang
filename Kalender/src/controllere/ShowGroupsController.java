package controllere;


import models.Invitation;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kalenderGUI.InvitationsMain;
import kalenderGUI.ShowGroups;


public class ShowGroupsController {

	@FXML
	private TableView<Invitation> invitationTable;
	@FXML
	private TableColumn<Invitation,String> iidColumn;
	@FXML
	private TableColumn<Invitation,String> descColumn;

	private ShowGroups mainApp;


	@FXML
	private void initialize(){
		iidColumn.setCellValueFactory(CellData -> CellData.getValue().getIidStringProperty());
		descColumn.setCellValueFactory(CellData -> CellData.getValue().getDescriptionProperty());

	}

	public void showData(){
		invitationTable.setItems(mainApp.getInvitationList());
	}


	public void setMainApp(ShowGroups showGroups){
		mainApp = showGroups;
	}


	@FXML
	private void handleAccept(){
		if(invitationTable.getSelectionModel().getSelectedItem() != null){
			this.mainApp.acceptInvitation(invitationTable.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	private void handleDecline(){
		if(invitationTable.getSelectionModel().getSelectedItem() != null){
			this.mainApp.declineInvitation(invitationTable.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	private void handleCancel(){
		this.mainApp.close();
	}
}
