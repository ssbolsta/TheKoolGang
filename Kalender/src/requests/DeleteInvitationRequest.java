package requests;

import org.json.simple.JSONObject;

public class DeleteInvitationRequest implements Request {

	private int id;
	
	public String toString(){
		JSONObject main = new JSONObject();
		JSONObject content = new JSONObject();
		
		main.put("request", "invitation");
		main.put("type", "delete");
		content.put("id", id);
		main.put("content", content);
		
		return main.toJSONString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
