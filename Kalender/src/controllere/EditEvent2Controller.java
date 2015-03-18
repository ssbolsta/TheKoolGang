package controllere;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import kalenderGUI.EditEventMain;
import models.Room;

import org.controlsfx.dialog.Dialogs;
import org.json.simple.JSONObject;

public class EditEvent2Controller {
	
	
	
	@FXML
	private ComboBox<Room> roomList;
	@FXML
	private ListView<String> equipmentList;
	
	private HashMap<String,Room> roomMap = new HashMap<String,Room>();
	private EditEventMain mainApp;
	
	
	@FXML
	private void initialize(){
		
		
	}
	
	public void showData(){
//		JSONObject app = mainApp.getAppointment();
//		try {
//			JSONObject room = (JSONObject) ConnectionForReal.scon.sendGet("rooms/rid/" + app.get("rid")).get(0);
//			Room currentRoom = new Room(Integer.parseInt(room.get("rid").toString()), room.get("name").toString(), Integer.parseInt(room.get("capacity").toString()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
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
	private void handleEditEvent(){
		if(roomMap.getOrDefault(roomList.getSelectionModel().getSelectedItem(), null) != null){
			this.mainApp.editEvent(roomMap.get(roomList.getSelectionModel().getSelectedItem()));
			Dialogs.create().title("Arrangement Opprettet!").showInformation();
			this.mainApp.close();
		}else{
			Dialogs.create().title("Ugyldige Felter").masthead("Vennligst rett ugyldige felter!").message("Et rom må velges for å opprette arrangement!\n").showWarning();
		}
	}
	
	@FXML
	private void handleBack(){
		this.mainApp.showEditEvent1();
	}
	
	@FXML
	private void handleCancel(){
		this.mainApp.close();
	}
	
	public void setMainApp(EditEventMain mainApp){
		this.mainApp = mainApp;
	}
}
