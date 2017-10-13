package CIMSOLUTIONS.Database;

import java.sql.*;

abstract public class MySqlDB {

	private String connectionString;
	private String user;
	private String password;
	protected Connection MyCon;

	protected MySqlDB() {
		connectToDatabase("jdbc:mysql://localhost/databasesneltransport", "Java2017", "Java2017");
	}
	
	void connectToDatabase(String connectionString, String user, String password) {
		this.connectionString = connectionString;
		this.user = user;
		this.password = password;
		try {
			MyCon = DriverManager.getConnection(this.connectionString, this.user, this.password);
			System.out.println("Connected To Database.");
		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}
}
