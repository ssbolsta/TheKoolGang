package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Room {
	
	private StringProperty name;
	private IntegerProperty capacity;
	private ObservableList<String> equipment = FXCollections.observableArrayList();
	private Integer roomID;
	
	
	public Room(Integer roomID,String name,Integer capacity, String equipment){
		this.roomID = roomID;
		this.name = new SimpleStringProperty(name);
		this.capacity = new SimpleIntegerProperty(capacity);
		for (String string : equipment.split(",")) {
			this.equipment.add(string);
		}
	}
	
	
	public Integer getRoomID(){
		return roomID;
	}
	
	public String getName(){
		return name.get();
	}
	public StringProperty getNameProperty(){
		return name;
	}
	
	public Integer getCapacity(){
		return capacity.get();
	}
	public IntegerProperty getCapacityProperty(){
		return capacity;
	}
	
	public ObservableList<String> getEquipment(){
		return this.equipment;
	}
	
	public String toString(){
		return this.getName() + " (kapasitet:" + this.getCapacity().toString() + ")";
	}
}
