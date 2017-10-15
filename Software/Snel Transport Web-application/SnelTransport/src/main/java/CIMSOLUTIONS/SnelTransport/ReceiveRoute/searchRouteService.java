package CIMSOLUTIONS.SnelTransport.ReceiveRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import CIMSOLUTIONS.Database.*;

public class searchRouteService extends MySqlDB {

	// Search for the earlier computed route from the database
	private ArrayList<String> locationList = new ArrayList<>();
	
	searchRouteService() {
		super();
	}


	public ArrayList<String> getLocationList() {
		return locationList;
	}
	
	private String lookUpRouteString(String truckName) {

		String sqlQuerry = "SELECT * FROM databasesneltransport.Routes WHERE wagennaam = " + truckName;

		try {
			Statement myStmt = MyCon.createStatement();
			return myStmt.executeQuery(sqlQuerry).getString("route");

		} catch (SQLException e) {
			e.printStackTrace();
			return "Invalid truckId request";
		}
	}

		
	public void lookUpRouteList(String truckName) {

		String routeString = lookUpRouteString(truckName);

		
		String sqlQuerry = "SELECT * FROM databasesneltransport.addressen WHERE locationID = ";
		
		try {
			for (String locationId : routeString.split(">")) {
				// TO DO:
				//	Check deze naamgeving
				Statement myStmt = MyCon.createStatement();
				ResultSet myRs = myStmt.executeQuery(sqlQuerry+locationId);
				
				
				
				String location = 
						myRs.getString("straat") + " " + 
						myRs.getString("huisnnumer") + ", " +
						myRs.getString("postcode") + ", " +
						myRs.getString("stad");
				locationList.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
