package requests;

import org.json.simple.JSONObject;

public class DeleteGroupRequest {

	private int id = -1;

	JSONObject main = new JSONObject();
	JSONObject content = new JSONObject();

	public String toString() {
		content.put("id", id);

		main.put("request", "group");
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

	public JSONObject getMain() {
		return main;
	}

	public void setMain(JSONObject main) {
		this.main = main;
	}

	public JSONObject getContent() {
		return content;
	}

	public void setContent(JSONObject content) {
		this.content = content;
	}

}