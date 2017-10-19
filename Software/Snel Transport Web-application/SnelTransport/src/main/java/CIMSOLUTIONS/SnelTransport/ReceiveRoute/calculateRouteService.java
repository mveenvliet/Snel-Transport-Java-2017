package CIMSOLUTIONS.SnelTransport.ReceiveRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import CIMSOLUTIONS.Database.*;

public class calculateRouteService extends MySqlDB {

	private String switchDDMMYYYYtoYYYYMMDD(String date) {
		
		String[] parts = date.split("-");	
		return parts[2] + parts[1]  + parts[0];
		
	}
	
	public List<String> basicFunction(String date) {

		System.out.println(date);
		List<String> plates = getStringFromDatabase(
				getPlatesFromDateSQL(
				switchDDMMYYYYtoYYYYMMDD(date)), "licencePlate");

		return plates;
	}
	
	private List<String> getStringFromDatabase(String sqlStatement, String tableHeader) {
		
		List<String> list = new ArrayList<>();

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Statement myStmt = MyCon.createStatement();
			ResultSet myRs = myStmt.executeQuery(sqlStatement);
			while (myRs.next()) {
				list.add(myRs.getString(tableHeader));
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private String getPlatesFromDateSQL(String date) {
		String sql = "SELECT licencePlate FROM databasesneltransport.trucklist WHERE idTruck IN (SELECT idTruck FROM databasesneltransport.routelist WHERE deliveryDate = '" + date + "')";
		
		return sql;
		
	}
}

	// Functions:
	//	input: date
	//	get all orders for that date
	//	get all locations corresponding to those orders
	//	(1. run Christophides Algorithm to check if the route can be done)
	//	(2. divide the number of nodes)
	//	(	Rerun 1 -> 2 untill 1 is true)
	//	(3. check number of nodes each subset)
	//	(4.	blossem shrink if the number of nodes is more than 8)	
	//	run bruteforce TSP
	//	(5.	regrow your blossems)
	//	store route in the database
	//	send route (and number of trucs) back to the user
	
	
