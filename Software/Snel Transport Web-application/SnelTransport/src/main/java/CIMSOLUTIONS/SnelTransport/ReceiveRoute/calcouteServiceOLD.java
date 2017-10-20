package CIMSOLUTIONS.SnelTransport.ReceiveRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import CIMSOLUTIONS.Database.*;


public class calcouteServiceOLD extends MySqlDB {

	// Search for the earlier computed route from the database
	private ArrayList<String> locationList = new ArrayList<>();
	private ArrayList<String> truckList = new ArrayList<>();
	
	calcouteServiceOLD() {
		super();
	}

	public void getTrucksByDate(String Date) {
		
		
		try {
			Statement myStmt = MyCon.createStatement();
			ResultSet myRsIdTrucks = myStmt.executeQuery(
					"SELECT idTruck " +
					"FROM databasesneltransport.routelist " +
					"WHERE deliveryDate = '" + Date + "')");
			
			ResultSet myRSLicencePlateTrucks = myStmt.executeQuery(
					"SELECT licencePlate " +
					"FROM databasesneltransport.trucklist " +
					"WHERE idTruck " +
					"IN" + myRsIdTrucks.getString("idTruck") + "')");
			while (myRSLicencePlateTrucks.next()) {
				truckList.add(myRSLicencePlateTrucks.getString("licencePlate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> getLocationList() {
		return locationList;
	}
	
	private String lookUpRouteString(String truckName) {

		String sqlQuerry = "SELECT route FROM databasesneltransport.routeList WHERE wagennaam = " + truckName;

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
