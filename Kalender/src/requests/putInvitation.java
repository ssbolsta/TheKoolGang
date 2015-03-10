package requests;

import org.json.simple.JSONObject;

public class putInvitation implements Request {

	private int InvitationID = -1;
	private int eventID = -1;
	private int brukerID = -1;
	private String description = "";
	
	public String toString(){
		
		JSONObject main = new JSONObject();
		JSONObject content = new JSONObject();
		
		main.put("request", "invitation");
		main.put("type", "put");
		content.put("eventId", eventID);
		content.put("brukerID", brukerID);
		content.put("description", description);
		main.put("content",  content);
		
		return main.toJSONString();
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public int getBrukerID() {
		return brukerID;
	}

	public void setBrukerID(int brukerID) {
		this.brukerID = brukerID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
