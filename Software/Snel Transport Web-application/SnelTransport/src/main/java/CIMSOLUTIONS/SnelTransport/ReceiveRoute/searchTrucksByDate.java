package CIMSOLUTIONS.SnelTransport.ReceiveRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import CIMSOLUTIONS.Database.*;


public class searchTrucksByDate extends MySqlDB {


	private ArrayList<String> truckList = new ArrayList<>();

	public searchTrucksByDate() {
		super();
	}

	public void getTrucksByDate(String Date) {

		try {
			Statement myStmt = MyCon.createStatement();
			ResultSet myRSLicencePlateTrucks = myStmt.executeQuery(
					"SELECT licencePlate " + 
					"FROM databasesneltransport.trucklist " + 
					"WHERE idTruck " +
					"IN (SELECT idTruck " + 
						"FROM databasesneltransport.routelist "  + 
						"WHERE deliveryDate = " + Date + "')");

			while (myRSLicencePlateTrucks.next()) {
				truckList.add(myRSLicencePlateTrucks.getString("licencePlate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}