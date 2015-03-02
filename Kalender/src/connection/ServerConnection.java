package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection {
	
	private DataOutputStream out;
	private DataInputStream in;
	private String result;
	private Socket client;
	
	private final String SERVER_IP = "78.91.50.146";
	private final int SERVER_PORT = 5432;
	
	public ServerConnection() throws IOException {
		client = new Socket(SERVER_IP, SERVER_PORT);
		
		out = new DataOutputStream(client.getOutputStream());
		in = new DataInputStream(client.getInputStream());
		
		result = null;
	}
	
	public void close() throws IOException {
		client.close();
	}
	
	public String sendRequest(String request) throws IOException {
		out.writeUTF(request);
		result = in.readUTF();
		return result;
	}
	
	
	
}
