package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class NotificationSuper {
	
	protected LocalDate date;
	protected LocalTime time;
	protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	protected static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
	
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
