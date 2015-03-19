package models;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Notification {
	
	private StringProperty description;
	private StringProperty time;
	private int nid;
	private int uid;
	
	
	public Notification(String nid, String uid,String description, String time){
		this.description = new SimpleStringProperty(description);
		this.time = new SimpleStringProperty(time);
		this.nid = Integer.parseInt(nid);
		this.uid = Integer.parseInt(uid);
	}
	
	public String getDescription(){
		return description.get();
	}
	
	public StringProperty getDescriptionProperty(){
		return description;
	}
	
	public String getTime(){
		return time.get();
	}
	
	public StringProperty getTimeProperty(){
		return time; 
	}
	
	public int getNid(){
		return nid;
	}
	
	public StringProperty getNidStringProperty(){
		return new SimpleStringProperty(Integer.toString(nid));
	}
	
	public int getUid(){
		return uid;
	}
}
