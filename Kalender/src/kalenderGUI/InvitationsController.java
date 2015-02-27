package kalenderGUI;

import models.Event;
import models.Group;
import models.Invitation;
import models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	private ObservableList<Invitation> invitationTableList = FXCollections.observableArrayList();
	
	@FXML
	private void Initialize(){
		invitationTableList.add(new Invitation(new Person("Mats","Egedal",1234),new Person("Mats","Egedal",1234), new Group()));
		invitationTableList.add(new Invitation(new Person("Boye","Data",1236),new Person("John","Smith",1237), new Event()));
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
		tagColumn.setCellValueFactory(cellData -> cellData.getValue().getTagProperty());
		showInvitationDetails(null);
		invitationTable.getSelectionModel().selectedItemProperty().addListener(
				(observable,oldValue,newValue) -> showInvitationDetails(newValue));
		invitationTable.setItems(invitationTableList);
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
