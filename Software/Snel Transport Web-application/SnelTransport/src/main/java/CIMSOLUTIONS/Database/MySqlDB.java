package CIMSOLUTIONS.Database;

import java.sql.*;

abstract public class MySqlDB {

	private String connectionString;
	private String user;
	private String password;
	protected Connection MyCon;

	protected MySqlDB()  {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//connectToDatabase("jdbc:mysql://172.16.1.186:3306/?user=R.Christoffers","Snel", "Transport");
			connectToDatabase("jdbc:mysql://172.16.1.186:3306","Snel", "Transport");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 
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
