package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Event {
	
	private StringProperty name;
	private LocalDate date;
	private LocalTime timeFrom;
	private LocalTime timeTo;
	private StringProperty desc;
	private ObservableList<Person> attending = FXCollections.observableArrayList();
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public Event(String name,LocalDate date,LocalTime timeFrom,LocalTime timeTo,String description,Person creator){
		this.name = new SimpleStringProperty(name);
		this.date = date;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.desc = new SimpleStringProperty(description);
		attending.add(creator);
	}
	
	
	public String getName(){
		return name.get();
	}
	public StringProperty getNameProperty(){
		return name;
	}
	
	
	public String getDescription(){
		return desc.get();
	}
	public StringProperty getDescriptionProperty(){
		return desc;
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
	
	
	public LocalTime getTimeFrom(){
		return timeFrom;
	}
	public String getTimeFromAsString(){
		return timeFrom.format(TIME_FORMATTER);
	}
	public StringProperty getTimeFromProperty(){
		return new SimpleStringProperty(timeFrom.format(TIME_FORMATTER));
	}
	
	
	public LocalTime getTimeTo(){
		return timeTo;
	}
	public String getTimeToAsString(){
		return timeTo.format(TIME_FORMATTER);
	}
	public StringProperty getTimeToProperty(){
		return new SimpleStringProperty(timeTo.format(TIME_FORMATTER));
	}
	
	
	public Boolean isMember(Person person){
		if(attending.contains(person)){
			return true;
		}else{
			return false;
		}
	}
	public void addMember(Person person){
		attending.add(person);
	}
	public void removeMember(Person person){
		if(this.isMember(person)){
			attending.remove(person);
		}
	}
}
