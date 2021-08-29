package db.bk.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	
	// Get connection with the database
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		String username="root";
		String password="123";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/bkdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", username, password);
		return connection;
	}

/* Check connection
 	public static void main(String [] args) {
		try {
			System.out.println(DBConnector.getConnection());
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
*/	
}
