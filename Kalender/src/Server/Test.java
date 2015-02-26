package Server;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Test extends DBConnection{
	
	public static void main(String[] args) {
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

}
