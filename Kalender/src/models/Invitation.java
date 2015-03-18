package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Invitation{
	
	private final StringProperty description;
	private int iid;
	private int eid;
	private int uid;
	
	
	public Invitation(String iid, String eid, String uid, String description){
		this.description = new SimpleStringProperty(description);
		this.iid = Integer.parseInt(iid);
		this.eid = Integer.parseInt(eid);
		this.uid = Integer.parseInt(uid);
	}

	
	
	public StringProperty getIidStringProperty(){
		return new SimpleStringProperty(Integer.toString(iid));
	}
	
	public String getDescription(){
		return description.get();
	}
	public StringProperty getDescriptionProperty(){
		return description;
	}

	public int getUid() {
		return uid;
	}

	public int getEid() {
		return eid;
	}

	public int getIid() {
		return iid;
	}

}
