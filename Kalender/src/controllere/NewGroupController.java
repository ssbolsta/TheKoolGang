package controllere;

	import java.util.HashMap;
	import models.Person;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	import javafx.event.ActionEvent;
	import javafx.event.EventHandler;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.ComboBox;
	import javafx.scene.control.TableColumn;
	import javafx.scene.control.TableView;
	import javafx.scene.control.TextArea;
	import javafx.scene.control.TextField;
	import javafx.scene.input.KeyCode;
	import javafx.scene.input.KeyEvent;
	import javafx.stage.Stage;
	
	public class NewGroupController{

	@FXML private TextField nameField;
	@FXML private TextField spacesField;
	@FXML private TextArea descriptionField;
	@FXML private ComboBox<Person> personSearchField;
	@FXML private ComboBox<String> groupSearchField;
	@FXML private TableView<Person> recipientTable;
	@FXML private TableColumn<Person,String> uidColumn;
	@FXML private TableColumn<Person,String> nameColumn;
	@FXML private Button cancel;

	private ObservableList<Person> personTableList = FXCollections.observableArrayList();
	private ObservableList<Person> nameList = FXCollections.observableArrayList();
	private HashMap<String,Person> personList = new HashMap<String,Person>();


	private EventHandler<KeyEvent> cancelKeyPress = new EventHandler<KeyEvent>(){
		@Override
		public void handle(KeyEvent arg0){
			if(arg0.getCode().equals(KeyCode.ENTER)){
				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.hide();
				stage.close();
			}
		}
	};

	public NewGroupController(){
	}

	@FXML private void initialize(){
		uidColumn.setCellValueFactory(cellData -> cellData.getValue().getUidStringProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getFullNameProperty());
		recipientTable.setItems(personTableList);
		nameList.add( new Person("Mats","Egedal",1234));
		nameList.add( new Person("Kristian","Bø",1235));
		nameList.add( new Person("Boye","Data",1236));
		nameList.add( new Person("John","Smith",1237));
		nameList.add( new Person("Jim","Jiminy",1238));
		for (Person person : nameList) {
			personList.put(person.toString(), person);
		}
		
		
		this.personSearchField.setItems(nameList);
		new AutoCompleteCombobox<>(this.personSearchField);

		cancel.setOnKeyPressed(cancelKeyPress);

		cancel.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) cancel.getScene().getWindow();
				stage.hide();
				stage.close();
			}
		});

	}

	@FXML
	private  void handleLeggTilPerson(){
		if(personList.get(personSearchField.getSelectionModel().getSelectedItem()) != null){
			personTableList.add(personList.get(personSearchField.getSelectionModel().getSelectedItem()));
			nameList.remove(personList.get(personSearchField.getSelectionModel().getSelectedItem()));
		}
	}

	@FXML
	private void handleFjernPerson(){
		if(recipientTable.getSelectionModel().getSelectedItem() != null){
			nameList.add(recipientTable.getSelectionModel().getSelectedItem());
			personTableList.remove(recipientTable.getSelectionModel().getSelectedItem());
		}
	}
}



