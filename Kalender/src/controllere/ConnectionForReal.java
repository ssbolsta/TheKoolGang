package controllere;

import connection.HTTPConnection;

public class ConnectionForReal {

	public static HTTPConnection scon;
	public static String name;
	public static long uid;

	public static void setURL(String url) {
		scon = new HTTPConnection(url);
	}

}
