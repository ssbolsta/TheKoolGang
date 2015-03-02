package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Group{

	private StringProperty name;
	private IntegerProperty groupID;
	private ObservableList<Person> members = FXCollections.observableArrayList();
	
	
	public Group(Integer groupID, String name, Person creator){
		this.name = new SimpleStringProperty(name);
		this.groupID = new SimpleIntegerProperty(groupID);
		members.add(creator);
	}
	public String getName(){
		return name.get();
	}
	public Integer getGroupID(){
		return groupID.get();
	}
	public void setName(String name){
		this.name.set(name);
	}
	
	public Boolean isMember(Person person){
		if(members.contains(person)){
			return true;
		}else{
			return false;
		}
	}
	public void addMember(Person person){
		members.add(person);
	}
	public void removeMember(Person person){
		if(this.isMember(person)){
			members.remove(person);
		}
	}
}