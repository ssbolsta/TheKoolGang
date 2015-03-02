package connection;

import java.io.IOException;

public class Request extends Thread {
	
	private String request = null;
	private String response = null;
	private ServerConnection connection = null;
	
	public Request(ServerConnection connection) {
		this.connection = connection;
	}
	
	public Request(ServerConnection connection, String request) {
		this.connection = connection;
		this.request = request;
	}
	
	public void run() {
		try {
			response = connection.sendRequest(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

}
