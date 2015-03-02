package connection;

import java.io.IOException;

public class Test {
	
	public static void main(String[] args) {
		try {
			
			ServerConnection sc = new ServerConnection();
			
			sc.sendRequest("hello");
			
			sc.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
