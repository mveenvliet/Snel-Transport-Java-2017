package RouteBerekeningTests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import CIMSOLUTIONS.Database.MySqlDB;
import CIMSOLUTIONS.SnelTransport.CalculateRoute.DeletePrevious;

public class TestCleanDatabase extends MySqlDB{

	@Test
	public void test() {
		
		
		String date = "2017-10-10";
		new DeletePrevious(date);
		
		String sqlStatement = "SELECT * FROM databasesneltransport.routelist WHERE deliveryDate = '" + date + "'";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Statement myStmt;
			myStmt = MyCon.createStatement();
			ResultSet myRs = myStmt.executeQuery(sqlStatement);
			assertFalse(myRs.next());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
