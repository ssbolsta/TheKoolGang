package requests;

import org.json.simple.JSONObject;

public class DeleteEventRequest {

	private int id = -1;

	public String toString(){
		
	
	JSONObject main = new JSONObject();
	JSONObject content = new JSONObject();

	content.put("id", id);
	
	main.put("request", "event");
	main.put("type", "delete");
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
