package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	
	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty username;
	private final IntegerProperty uid;
	
	public Person(){
		this(null,null,null,(Integer) null);
	}
	
	public Person( String firstName, String lastName, String username, int uid){
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.username = new SimpleStringProperty(username);
		this.uid = new SimpleIntegerProperty(uid);
	}
	public String getFirstName(){
		return firstName.get();
	}
	public String getLastName(){
		return lastName.get();
	}
	public int getUID(){
		return uid.get();
	}
	public void setUID(int uid){
		this.uid.set(uid);
	}
	public String getUsername(){
		return username.get();
	}
	public void setFirstName(String firstName){
		this.firstName.set(firstName);
	}
	public void setLastName(String lastName){
		this.lastName.set(lastName);
	}
	public void setUsername(String username){
		this.username.set(username);
	}
	public StringProperty getFirstNameProperty(){
		return firstName;
	}
	public StringProperty getLastNameProperty(){
		return lastName;
	}
	public StringProperty getUsernameProperty(){
		return username;
	}
	public StringProperty getUidStringProperty(){
		return new SimpleStringProperty("#"+String.valueOf(uid.get()));
	}
	public StringProperty getFullNameProperty(){
		return new SimpleStringProperty(lastName.get() + ", " + firstName.get());
	}
	
	public String toString(){
		return lastName.get() + ", " + firstName.get() + username.get();
	}
}
