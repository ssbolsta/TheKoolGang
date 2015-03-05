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
	
	private String SERVER_IP = "localhost";
	private int SERVER_PORT = 5432;
	
	public ServerConnection() throws IOException {
		client = new Socket(SERVER_IP, SERVER_PORT);
		
		out = new DataOutputStream(client.getOutputStream());
		in = new DataInputStream(client.getInputStream());

	}

    public ServerConnection(String ip, int port) throws IOException {
        
        SERVER_IP = ip;
        SERVER_PORT = port;
        
        client = new Socket(SERVER_IP, SERVER_PORT);

        out = new DataOutputStream(client.getOutputStream());
        in = new DataInputStream(client.getInputStream());

    }
	
	public void close() throws IOException {
		client.close();
	}
	
	public JSONObject sendRequest(Request request) throws IOException {
        out.writeUTF(request.toString());
        String result = in.readUTF();
        JSONObject result_json;
        try {
            result_json = (JSONObject)new JSONParser().parse(result);
        } catch (ParseException e) {
            System.err.println(result);
            result_json = formatError();
        }
		return result_json;
	}
    
    private JSONObject formatError() {
        JSONObject error = new JSONObject();
        error.put("response", "error");
        error.put("type", "format error");
        error.put("content", "none-json response received from server");
        return error;
    }
	
}
