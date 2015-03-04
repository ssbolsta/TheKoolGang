package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import requests.Request;

public class ServerConnection {
	
	private BufferedWriter out;
	private BufferedReader in;
	private Socket client;
	
	private final String SERVER_IP = "78.91.49.79";
	private final int SERVER_PORT = 5432;
	
	public ServerConnection() throws IOException {
		client = new Socket(SERVER_IP, SERVER_PORT);
		
		out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));

	}
	
	public void close() throws IOException {
		client.close();
	}
	
	public JSONObject sendRequest(Request request) throws IOException {
		System.out.println(request.toString());
        out.write(request.toString());
        out.flush();
        String result = in.readLine();
        JSONObject result_json;
        try {
            result_json = (JSONObject)new JSONParser().parse(result);
        } catch (ParseException e) {
            System.out.println(result);
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
