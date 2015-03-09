package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Notification extends NotificationSuper{
	
	private String description;
	
	public Notification(String description,String date,String time){
		this.description = description;
		try{
			this.date = (LocalDate) DATE_FORMATTER.parse(date,LocalDate::from);
			this.time = (LocalTime) TIME_FORMATTER.parse(time,LocalTime::from);
			
		}catch(DateTimeParseException e){
			e.printStackTrace();
		}
	}
	public String getDescription(){
		return description;
	}
}
