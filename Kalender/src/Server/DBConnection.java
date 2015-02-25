package Server;

import java.sql.*;

public abstract class DBConnection {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no/boyebn_calendar";
	static final String USER = "Boyebn_fellespro";
	static final String PASS = "Boyebn_fellespro";

	Connection conn = null;
	Statement stmt = null;
	ResultSet st = null;
	String query;

/* void viewTable(Connection con, String dbname) {

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Tilkoblingen fungerte");

		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
*/
}
