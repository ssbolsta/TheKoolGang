package models;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Invitation extends NotificationSuper{
	
	private final StringProperty tag;
	private Person sender;
	private Event event;
	private Group group;
	
	public Invitation(Person recipient, Person sentBy, Event event){
		this.date = LocalDate.now();
		this.recipient = recipient;
		this.sender = sentBy;
		this.event = event;
		this.tag = new SimpleStringProperty(sender.getFirstName() + " " + sender.getLastName() + " har invitert deg til å delta på en arrangement.");
	}
	public Invitation(Person recipient, Person sentBy, Group group){
		this.date = LocalDate.now();
		this.recipient = recipient;
		this.sender = sentBy;
		this.group = group;
		this.tag = new SimpleStringProperty(sender.getFirstName() + " " + sender.getLastName() + " har invitert deg til en gruppe.");
	}
	
	public Event getEvent(){
		return event;
	}
	public Group getGroup(){
		return group;
	}
	public Person getSender(){
		return sender;
	}
	public String getSenderName(){
		return sender.getFirstName() + " " + sender.getLastName();
	}
	public String getDesc(){
		String desc = "";
		return desc;
	}
	public String getTag(){
		return tag.get();
	}
	public StringProperty getTagProperty(){
		return tag;
	}
}
