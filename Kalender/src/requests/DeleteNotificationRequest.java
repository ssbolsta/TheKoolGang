package requests;

import org.json.simple.JSONObject;

public class DeleteNotificationRequest implements Request {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private int id = -1;
	
	public String toString(){
		JSONObject main = new JSONObject();
		JSONObject content = new JSONObject();
		
		main.put("request", "notifcation");
		main.put("type", "delete");
		
		content.put("id", id);
		main.put("content", content);
		
		return main.toJSONString();
		
	}

}
