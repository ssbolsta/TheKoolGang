package requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/*
 * "request" : "group",
	"type" : "get",
	"content" : {
		"id" : <gid>,
		"name" : "<first name>",
		"member" : <pid>,
		"participant_of" : <eventid>,
		"limit" : [<from>, <to>]
	}
 */

public class GetGroupRequest implements Request{

	 private int id = -1;
	 private int name = -1;
	 private int member = -1;
	 private int participant_of = -1;
	 private int[] limit = new int[]{0,0};
	    

	
    public String toString() {
    	
    	 JSONObject main = new JSONObject();
         JSONObject content = new JSONObject();
         JSONArray limit = new JSONArray();
         
         limit.add(this.limit[0]);
         limit.add(this.limit[1]);

         
         content.put("id", id);
         content.put("name", name);
         content.put("member", member);
         content.put("participant_of", participant_of);
         
         main.put("request", "group");
         main.put("type", "get");
         main.put("content", content);
         
         return main.toJSONString();

	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getName() {
		return name;
	}



	public void setName(int name) {
		this.name = name;
	}



	public int getMember() {
		return member;
	}



	public void setMember(int member) {
		this.member = member;
	}



	public int getParticipant_of() {
		return participant_of;
	}



	public void setParticipant_of(int participant_of) {
		this.participant_of = participant_of;
	}



	public int[] getLimit() {
		return limit;
	}



	public void setLimit(int[] limit) {
		this.limit = limit;
	}

  
    
    
}
