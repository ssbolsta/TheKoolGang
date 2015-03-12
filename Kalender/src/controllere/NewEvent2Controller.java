package controllere;

import java.util.HashMap;
import java.util.List;

import org.controlsfx.dialog.Dialogs;

import models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import kalenderGUI.EventMain;

public class NewEvent2Controller {
	
	@FXML
	private ComboBox<Room> roomList;
	@FXML
	private ListView<String> equipmentList;
	
	private HashMap<String,Room> roomMap = new HashMap<String,Room>();
	private ObservableList<Room> roomListCap = FXCollections.observableArrayList();
	private EventMain mainApp;
	
	
	public NewEvent2Controller(){
	}
	
	@FXML
	private void initialize(){
	}
	
	public void showData(){
		int spaces = mainApp.getSpaces();
		int spacesAuto = 0;
		Room auto = null;
		for (Room room : this.mainApp.getRoomList()) {
			roomMap.put(room.toString(), room);
			if(room.getCapacity() >= spaces && room.getCapacity() < spacesAuto){
				auto = room;
			}else if(room.getCapacity() >= spaces && auto == null){
				spacesAuto = room.getCapacity();
				auto = room;
			}
		}
		if(auto != null){
			this.roomList.getSelectionModel().select(auto);
		}
		
		roomList.setItems(this.mainApp.getRoomList());
		new AutoCompleteCombobox<>(this.roomList);
	}
	
	
	@FXML
	private void handleSelection(){
		if(roomMap.containsValue(roomList.getSelectionModel().getSelectedItem())){
			equipmentList.setItems(roomMap.get(roomList.getSelectionModel().getSelectedItem().toString()).getEquipment());
		}else{
			equipmentList.setItems(null);
		}
	}
	
	@FXML
	private void handleCreateEvent(){
		if(roomMap.getOrDefault(roomList.getSelectionModel().getSelectedItem(), null) != null){
			this.mainApp.createEvent(roomMap.get(roomList.getSelectionModel().getSelectedItem()));
			Dialogs.create().title("Arrangement Opprettet!").showInformation();
			this.mainApp.close();
		}else{
			Dialogs.create().title("Ugyldige Felter").masthead("Vennligst rett ugyldige felter!").message("Et rom må velges for å opprette arrangement!\n").showWarning();
		}
	}
	
	@FXML
	private void handleBack(){
		this.mainApp.showNewEvent1();
	}
	
	@FXML
	private void handleCancel(){
		this.mainApp.close();
	}
	
	public void setMainApp(EventMain mainApp){
		this.mainApp = mainApp;
	}
}
