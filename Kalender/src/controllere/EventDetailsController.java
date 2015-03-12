package controllere;

import models.Group;
import models.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class EventDetailsController {
	
	@FXML private Text nameText;
	@FXML private Text dateText;
	@FXML private Text timeText;
	@FXML private Text roomText;
	@FXML private Text descriptionText;
	@FXML private TableView<Person> recipientTable;
	@FXML private TableColumn<Person,String> nameColumn;
	@FXML private TableColumn<Person,String> usernameColumn;
	@FXML private TableView<Group> groupTable;
	@FXML private TableColumn<Group,String> groupColumn;
	
	
	@FXML
	private void initialize(){
		
	}
	
	@FXML
	private void handleMeldAvbud(){
		
	}
	
	@FXML
	private void handleLukk(){
		
	}
	
}
