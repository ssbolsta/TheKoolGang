package controllere;

import models.Invitation;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class InvitationsController {
	
	@FXML private Text tag;
	@FXML private Text date;
	@FXML private Text invitedBy;
	@FXML private Text desc;
	@FXML TableView<Invitation> invitationTable;
	@FXML TableColumn<Invitation,String> dateColumn;
	@FXML TableColumn<Invitation,String> tagColumn;
	
	@FXML
	private void Initialize(){
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
		tagColumn.setCellValueFactory(cellData -> cellData.getValue().getTagProperty());
		showInvitationDetails(null);
		invitationTable.getSelectionModel().selectedItemProperty().addListener(
				(observable,oldValue,newValue) -> showInvitationDetails(newValue));
		
	}
	
	private void showInvitationDetails(Invitation invitation){
		if(invitation != null){
			tag.setText(invitation.getTag());
			date.setText(invitation.getDate().toString());
			invitedBy.setText(invitation.getSenderName());
		}else{
			tag.setText("");
			date.setText("");
			invitedBy.setText("");
			desc.setText("");
		}
	}
}
