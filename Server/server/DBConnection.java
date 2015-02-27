package server;

import java.sql.*;

public abstract class DBConnection {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no/boyebn_calendar";
	static final String USER = "boyebn_fellespro";
	static final String PASS = "#3RosaRever";

	static Connection connection = null;
	static Statement statement = null;
	static ResultSet result = null;
	
	protected static void initConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		
		Class.forName(JDBC_DRIVER).newInstance();
		connection = DriverManager.getConnection(DB_URL, USER, PASS);
		statement = connection.createStatement();
		
	}
	
	protected static void closeConnection() throws SQLException {
		if(connection != null)
			connection.close();
	}
	
}
