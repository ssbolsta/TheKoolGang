package connection;

import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;

public class Test {
	
	public static void main(String[] args) {
		try {
			
			ServerConnection serverConnection = new ServerConnection();
			JSONObject request = new JSONObject();
            request.put("request", "user");
            request.put("type", "get");
            HashMap<String, Object> content = new HashMap<String, Object>();
            content.put("id",45);
            request.put("content", content);
			JSONObject response = serverConnection.sendRequest(request);
            
            System.out.println(request);
            System.out.println(response);

        } catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
