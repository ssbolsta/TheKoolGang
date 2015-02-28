package controllere;

import java.time.LocalDate;
import java.time.LocalTime;

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
	
	@FXML 
	private Text tag;
	@FXML 
	private Text date;
	@FXML 
	private Text desc;
	@FXML
	private Text time;
	@FXML 
	private TableView<Invitation> invitationTable;
	@FXML 
	private TableColumn<Invitation,String> dateColumn;
	@FXML 
	private TableColumn<Invitation,String> tagColumn;
	@FXML
	private TableColumn<Invitation,String> timeColumn;
	
	private ObservableList<Invitation> invitationTableList = FXCollections.observableArrayList();
	
	@FXML
	private void initialize(){
		invitationTableList.add(new Invitation(new Person("Mats","Egedal",1234),new Person("Mats","Egedal",1234), new Group(1,"Balletak",new Person("hore","faen",4567))));
		invitationTableList.add(new Invitation(new Person("Boye","Data",1236),new Person("John","Smith",1237), new Event("Harem",LocalDate.of(2015, 3,25 ),LocalTime.of(12, 0),LocalTime.of(14, 0),"",new Person("hore","faen",4567))));
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
		tagColumn.setCellValueFactory(cellData -> cellData.getValue().getTagProperty());
		timeColumn.setCellValueFactory(cellData -> cellData.getValue().getTimeProperty());
		invitationTable.setItems(invitationTableList);
		showInvitationDetails(null);
		invitationTable.getSelectionModel().selectedItemProperty().addListener(
				(observable,oldValue,newValue) -> showInvitationDetails(newValue));
	}
	
	private void showInvitationDetails(Invitation invitation){
		if(invitation != null){
			tag.setText(invitation.getTag());
			date.setText(invitation.getDateAsString());
			time.setText(invitation.getTimeAsString());
			if(invitation.getGroup() != null){
				desc.setText("Du har mottatt en forespørsel om å bli med i gruppen " + invitation.getGroup().getName() + ". Trykk på knappen for å akseptere/avslå.");
			}else{
				desc.setText("Du har blitt invitert til " + invitation.getEvent().getName() + " den " + invitation.getEvent().getDateAsString() + " klokken "
						+ invitation.getEvent().getTimeFromAsString() + " til " + invitation.getEvent().getTimeToAsString() + ". Trykk på knappen for å akseptere/avslå.");
			}
		}else{
			tag.setText("");
			date.setText("");
			time.setText("");
			desc.setText("");
		}
	}
}
