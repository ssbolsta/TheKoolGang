package controllere;

import java.util.HashMap;

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
	
	private ObservableList<Room> rooms = FXCollections.observableArrayList();
	private HashMap<String,Room> roomMap = new HashMap<String,Room>();
	private EventMain mainApp;
	
	
	public NewEvent2Controller(){
	}
	
	@FXML
	private void initialize(){
		rooms.add(new Room(1,"IT vest 115", 7,"Gitar(2),Ukulele(1),Piano(1),Trompet(4)"));
		rooms.add(new Room(2,"Hovedsal 2", 30,"Langbord(1),stikkontaker(36),Bord(4),Tavle(1)"));
		rooms.add(new Room(3,"Ultimate Gaming Room", 18,"Gaming PC(18),Mousemats(18),Refrigirator(4),Gaming Chairs(18)"));
		for (Room room : rooms) {
			roomMap.put(room.toString(), room);
		}
		roomList.setItems(rooms);
		new AutoCompleteCombobox<>(this.roomList);
	}
	
	public void showData(){
		int spaces = mainApp.getSpaces();
		int spacesAuto = 0;
		Room auto = null;
		for (Room room : rooms) {
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
