package controllere;


import models.Invitation;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import kalenderGUI.InvitationsMain;


public class InvitationsController {
	
	@FXML 
	private TableView<Invitation> invitationTable;
	@FXML 
	private TableColumn<Invitation,String> iidColumn;
	@FXML
	private TableColumn<Invitation,String> descColumn;
	
	private InvitationsMain mainApp;
	
	
	@FXML
	private void initialize(){
		iidColumn.setCellValueFactory(CellData -> CellData.getValue().getIidStringProperty());
		descColumn.setCellValueFactory(CellData -> CellData.getValue().getDescriptionProperty());
		
	}
	
	public void showData(){
		invitationTable.setItems(mainApp.getInvitationList());		
	}
	
	
	public void setMainApp(InvitationsMain main){
		mainApp = main;
	}
	
	
	@FXML
	private void handleAccept(){
		this.mainApp.acceptInvitation(invitationTable.getSelectionModel().getSelectedItem());
	}
	
	@FXML 
	private void handleDecline(){
		this.mainApp.declineInvitation(invitationTable.getSelectionModel().getSelectedItem());
	}
	
	@FXML
	private void handleCancel(){
		this.mainApp.close();
	}
}
