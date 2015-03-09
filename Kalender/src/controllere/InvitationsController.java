package controllere;

import java.time.LocalDate;
import java.time.LocalTime;

import models.Event;
import models.Group;
import models.Invitation;
import models.Person;
import models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InvitationsController {
	
	@FXML
	private Text eventLabel;
	@FXML
	private Text eventDesc;
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
	
	@FXML private Button close;
	
	private ObservableList<Invitation> invitationTableList = FXCollections.observableArrayList();
	
	private EventHandler<KeyEvent> closeKeyPressed = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0){
			if(arg0.getCode().equals(KeyCode.ENTER)){
				Stage stage = (Stage) close.getScene().getWindow();
				stage.hide();
				stage.close();
			}
		}
	};
	
	@FXML
	private void initialize(){
		invitationTableList.add(new Invitation(new Person("Mats","Egedal",1234), new Group(1,"Balletak",new Person("hore","faen",4567))));
		invitationTableList.add(new Invitation(new Person("John","Smith",1237), new Event("Harem",LocalDate.of(2015, 3,25 ),LocalTime.of(12, 0),LocalTime.of(14, 0),"Vi skal holde et stort harem på jobben. Bar eå komme, blir masse kos!",new Room(5,"Knøllerom",50,"Dildoer(30)"))));
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
				eventLabel.setText("");
				eventDesc.setText("");
				desc.setText("Du har mottatt en forespørsel om å bli med i gruppen " + invitation.getGroup().getName() + ". Trykk på knappen for å akseptere/avslå.");
			}else{
				eventLabel.setText("Arrangement beskrivelse:");
				desc.setText("Du har blitt invitert til " + invitation.getEvent().getName() + " den " + invitation.getEvent().getDateAsString() + " klokken "
						+ invitation.getEvent().getTimeFromAsString() + " til " + invitation.getEvent().getTimeToAsString() + ". Trykk på knappen for å akseptere/avslå.");
				eventDesc.setText(invitation.getEvent().getDescription());
			}
		}else{
			tag.setText("");
			date.setText("");
			time.setText("");
			desc.setText("");
			eventDesc.setText("");
		}
		
		close.setOnKeyPressed(closeKeyPressed);
		close.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				Stage stage =(Stage) close.getScene().getWindow();
				stage.hide();
				stage.close();
			}
			
		});
	}
}
