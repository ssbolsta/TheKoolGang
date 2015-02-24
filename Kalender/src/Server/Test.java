package Server;

import java.sql.*;

public class Test {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DB_URL = "jdbc:mysql://boye.priv.no";
	
	static final String USER = "root";
	static final String PASS = "#3RosaRever";
	
	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			stmt = conn.createStatement();
			String sql = "SELECT * FROM kalenderbase.users";
			
			
		}
		catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		}
		
	}
}
