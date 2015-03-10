package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person{

	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty username;

	public Person(){
		this(null,null,null);
	}

	public Person( String firstName, String lastName, String username){
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.username = new SimpleStringProperty(username);
	}
	public String getFirstName(){
		return firstName.get();
	}
	public String getLastName(){
		return lastName.get();
	}
	public String getUsername(){
		return username.get();
	}
	public StringProperty getFirstNameProperty(){
		return firstName;
	}
	public StringProperty getLastNameProperty(){
		return lastName;
	}
	public StringProperty getUidProperty(){
		return username;
	}
	public StringProperty getFullNameProperty(){
		return new SimpleStringProperty(lastName.get() + ", " + firstName.get());
	}

	public String toString(){
		return lastName.get() + ", " + firstName.get();
	}
}
