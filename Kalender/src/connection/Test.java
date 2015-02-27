package connection;

import java.io.IOException;

public class Test {
	
	public static void main(String[] args) {
		try {
			
			ServerConnection serverConnection = new ServerConnection();
			
			Request request1 = new Request(serverConnection, "Test 1");
			Request request2 = new Request(serverConnection, "Test 2");
			
			Request request3 = new Request(serverConnection);
			request3.setRequest("Test 3");
			
			request1.start();
			request2.start();
			request3.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
