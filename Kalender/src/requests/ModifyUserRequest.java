package requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * 	"request" : "group",
	"type" : "put",
	"content" : {
		"name" : "<name>",
		"admin" : <pid>,
		"member_of" : <gid>
	}
 */

public class ModifyUserRequest implements Request {

	
	 private String name = "";
	 private int admin = -1;
	 private int member_of = -1;
	

	 
	 public String toString(){

    	 JSONObject main = new JSONObject();
         JSONObject content = new JSONObject();
         
         content.put("name", name);
         content.put("admin", -1);
         content.put("member_of", -1);
 
         main.put("request", "user");
         main.put("type", "modify");
         main.put("content", content);
         
         return main.toJSONString();
         
	 }



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getAdmin() {
		return admin;
	}



	public void setAdmin(int admin) {
		this.admin = admin;
	}



	public int getMember_of() {
		return member_of;
	}



	public void setMember_of(int member_of) {
		this.member_of = member_of;
	}
	 
	 
	 
}
