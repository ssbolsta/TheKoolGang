package server;

import org.json.simple.JSONObject;



public class RequestHandler  {

	
		//Identifiserer hva requesten dreier seg om 
	public void identifyQuery(){
		JSONObject jobj = ClientConnection.getJSONObject();
		String request = (String) jobj.get("Request");
		
		switch(request) {
		case "user":
			if (jobj.get("type") == "get"){
				usergetRequest();	
				break;
			}
			elif jobj.
		case 
		
		}
		
		
	}
	
	
	
}
