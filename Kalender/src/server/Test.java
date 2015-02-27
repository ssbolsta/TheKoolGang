package server;

import java.sql.SQLException;

public class Test extends DBConnection{
	
	public static void main(String[] args) {
		try {
			
			initConnection();
			query("SELECT * FROM users ORDER BY uid DESC LIMIT 5");
			
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static void query(String query) throws SQLException {
		result = statement.executeQuery(query);
		
		System.out.println(String.format("%-20s%-20s%-20s%-20s%-20s", "uid", "firstname" , "lastname", "username", "password"));
		System.out.println("");
		
		while (result.next()) {
			int uid = result.getInt("uid");
			String firstname = result.getString("firstname");
			String lastname = result.getString("lastname");
			String username = result.getString("username");
			String password = result.getString("password");
			
			System.out.println(String.format("%-20d%-20s%-20s%-20s%-20s", uid, firstname, lastname, username, password));
		}
		
	}

}
