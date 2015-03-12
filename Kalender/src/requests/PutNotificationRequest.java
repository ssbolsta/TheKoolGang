package requests;

import org.json.simple.JSONObject;

/*
 * Godt mulig vi ikke trenger denne, men jeg skriver den i tilfellet
 */

public class PutNotificationRequest implements Request {

	int notificationID = -1;
	int eventID = -1;
	int brukerID = -1;
	int invitationID = -1;

	public String toString() {
		JSONObject main = new JSONObject();
		JSONObject content = new JSONObject();

		main.put("request", "notification");
		main.put("type", "put");
		content.put("notificationID", notificationID);
		content.put("eventID", eventID);
		content.put("brukerID", brukerID);
		content.put("notificationID", notifcationID);
		main.put("content", content);

		return main.toJSONString();
	}

	public int getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
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

	public int getInvitationID() {
		return invitationID;
	}

	public void setInvitationID(int invitationID) {
		this.invitationID = invitationID;
	}
}
