package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class ClientConnection implements Runnable{
	
	private final Socket clientSocket;
	private static JSONObject jobj; 
	
	public ClientConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	
	@Override
	public void run() {
		try {
			DataInputStream in = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
			
			String line;
			
			while(true) {
				line = in.readUTF();
				System.out.println(line);
				out.writeUTF(line.toUpperCase() + " - hilsen serveren");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
		}
	
	// Parser strengen som ble sendt tilbake til et JSONobjekt 
	private void stringToJSON(String line) throws Exception{
		try{
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(line);
			this.jobj = obj;
		}
		catch (Exception e){
			 throw new Exception("Feil ved JSON-parsing");
		}
	}
	
	public static JSONObject getJSONObject(){
		return jobj;
	}

	
	
	
	
	
	
	
	
	
	
	
}
