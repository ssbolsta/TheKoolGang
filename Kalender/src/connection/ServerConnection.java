package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import requests.Request;

public class ServerConnection {
	
	private DataOutputStream out;
	private DataInputStream in;
	private Socket client;
	
	private final String SERVER_IP = "localhost";
	private final int SERVER_PORT = 5432;
	
	public ServerConnection() throws IOException {
		client = new Socket(SERVER_IP, SERVER_PORT);
		
		out = new DataOutputStream(client.getOutputStream());
		in = new DataInputStream(client.getInputStream());

	}
	
	public void close() throws IOException {
		client.close();
	}
	
	public JSONObject sendRequest(Request request) throws IOException {
        out.writeUTF(request.toString());
        JSONObject result;
        try {
            result = (JSONObject)new JSONParser().parse(in.readUTF());
        } catch (ParseException e) {
            e.printStackTrace();
            result = formatError();
        }
		return result;
	}
    
    private JSONObject formatError() {
        JSONObject error = new JSONObject();
        error.put("response", "error");
        error.put("type", "format error");
        error.put("content", "none-json response received from server");
        return error;
    }
	
}
