package connection;


import java.io.*;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import requests.Request;

public class ServerConnection {

    private BufferedWriter out;
    private BufferedReader in;
    private Socket client;

    private String SERVER_IP = "localhost";
    private int SERVER_PORT = 5432;

    public ServerConnection() throws IOException {
        client = new Socket(SERVER_IP, SERVER_PORT);

        out = new BufferedWriter (new OutputStreamWriter(client.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));

    }

    public ServerConnection(String ip, int port) throws IOException {

        SERVER_IP = ip;
        SERVER_PORT = port;

        System.out.println(ip +  port);
        client = new Socket(SERVER_IP, SERVER_PORT);

        out = new BufferedWriter (new OutputStreamWriter(client.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));

    }

    public void close() throws IOException {
        client.close();
    }

    public JSONArray sendRequest(Request request) throws IOException {

        out.write(request.toString());
        out.flush();

        String result = in.readLine();
        JSONArray result_json;

        try {
            result_json = (JSONArray) new JSONParser().parse(result);

        } catch (ParseException e) {
            System.out.println(result);
            //result_json = formatError();
            return null;
        }

        return result_json;
    }

    @SuppressWarnings({ "unused", "unchecked" })
	private JSONArray formatError() {
        JSONArray response = new JSONArray();
        JSONObject error = new JSONObject();
        error.put("response", "error");
        error.put("type", "format error");
        error.put("content", "none-json response received from server");
        response.add(error);
        return response;
    }

}
