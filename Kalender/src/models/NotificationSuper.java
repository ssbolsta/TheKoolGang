package models;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class NotificationSuper {
	
	protected Person recipient;
	protected LocalDate date;
	
	public Person getRecipient(){
		return recipient;
	}
	
	public LocalDate getDate(){
		return date;
	}
	public StringProperty getDateProperty(){
		return new SimpleStringProperty(date.toString());
	}
}
