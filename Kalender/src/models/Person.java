package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	
	private final StringProperty firstName;
	private final StringProperty lastName;
	private final IntegerProperty uId;
	
	public Person(){
		this(null,null,null);
	}
	
	public Person( String firstName, String lastName, Integer uId){
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.uId = new SimpleIntegerProperty(uId);
	}
	public String getFirstName(){
		return firstName.get();
	}
	public String getLastName(){
		return lastName.get();
	}
	public Integer getUID(){
		return uId.get();
	}
	public void setFirstName(String firstName){
		this.firstName.set(firstName);
	}
	public void setLastName(String lastName){
		this.lastName.set(lastName);
	}
	public void setUID(Integer uId){
		this.uId.set(uId);
	}
	public StringProperty getFirstNameProperty(){
		return firstName;
	}
	public StringProperty getLastNameProperty(){
		return lastName;
	}
	public IntegerProperty getUidProperty(){
		return uId;
	}
}
