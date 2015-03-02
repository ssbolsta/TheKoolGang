package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class NotificationSuper {
	
	protected Person recipient;
	protected LocalDate date;
	protected LocalTime time;
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public Person getRecipient(){
		return recipient;
	}
	
	public LocalDate getDate(){
		return date;
	}
	public String getDateAsString(){
		return date.format(DATE_FORMATTER);
	}
	public StringProperty getDateProperty(){
		return new SimpleStringProperty(date.format(DATE_FORMATTER));
	}
	public LocalTime getTime(){
		return time;
	}
	public String getTimeAsString(){
		return time.format(TIME_FORMATTER);
	}
	public StringProperty getTimeProperty(){
		return new SimpleStringProperty(time.format(TIME_FORMATTER));
	}
}