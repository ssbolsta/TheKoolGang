package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Group{

	private StringProperty name;
	private IntegerProperty groupID;
	
	public Group(Integer groupID, String name){
		this.name = new SimpleStringProperty(name);
		this.groupID = new SimpleIntegerProperty(groupID);
	}
	
	public String getName(){
		return name.get();
	}
	public Integer getGroupID(){
		return groupID.get();
	}
	
	public String toString(){
		return this.name.get();
	}
	
	public StringProperty getNameProperty(){
		return this.name;
	}
	
	public StringProperty getGidStringProperty(){
		return new SimpleStringProperty(Integer.toString(getGroupID()));
	}
}
