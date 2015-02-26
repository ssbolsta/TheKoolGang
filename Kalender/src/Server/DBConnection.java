package Server;

import java.sql.*;

public abstract class DBConnection {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no/boyebn_calendar";
	static final String USER = "boyebn_fellespro";
	static final String PASS = "#3RosaRever";

	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet st = null;
	static String query;

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
