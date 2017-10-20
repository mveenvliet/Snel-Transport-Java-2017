package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import CIMSOLUTIONS.Database.MySqlDB;

public class CalculateRouteService extends MySqlDB {
	
	
	private List<Address> resultSet = new ArrayList<>();
	
	public CalculateRouteService(){
		String setHomeSQL = 
				"SELECT city, street, houseNumber, postalcode " +
				"FROM databasesneltransport.address " +
				"WHERE idAddress " +
				"IN (SELECT customer_idAddress " + 
					"FROM databasesneltransport.customer " + 
					"WHERE customerNumber = '1')";
		getAddressesFromDatabase(setHomeSQL);
	}
	
	CalculateRouteService(int customerNumber){
		String setHomeSQL = 
				"SELECT city, street, houseNumber, postalcode " +
				"FROM databasesneltransport.address " +
				"WHERE idAddress " +
				"IN (SELECT customer_idAddress " + 
					"FROM databasesneltransport.customer " + 
					"WHERE customerNumber = '" + customerNumber + "')";
		getAddressesFromDatabase(setHomeSQL);
	}
	

	private String switchDDMMYYYYtoYYYYMMDD(String date) {
		String[] parts = date.split("-");
		return parts[2] + "-" + parts[1] + "-" + parts[0];
	}

	private void getAddressesFromDatabase(String sqlStatement) {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Statement myStmt = MyCon.createStatement();
			ResultSet myRs = myStmt.executeQuery(sqlStatement);
			while (myRs.next()) {
				Address tempAddress = new Address();
				tempAddress.setCity(myRs.getString("city"));
				tempAddress.setStreet(myRs.getString("street"));
				tempAddress.setHouseNumber(myRs.getString("houseNumber"));
				tempAddress.setPostalcode(myRs.getString("postalcode"));
				tempAddress.setUrlPartition();
				resultSet.add(tempAddress);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	
	String locationsAtDatesSQL(String date) {

		String sql = 
				"SELECT city, street, houseNumber, postalcode " +
				"FROM databasesneltransport.address " +
				"WHERE idAddress " +
				"IN (" +
					"SELECT idAddress " +
					"FROM databasesneltransport.orderlist " +
					"WHERE deliveryDate = '" + date + "')";
		return sql;
	}
	
	String urlOfAllLocations(List<Address> addresses) {
		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
		for (int iter = 0 ; iter < addresses.size() ; iter++) {
			url += addresses.get(iter).getUrlPartition();
			if (iter + 1 != addresses.size()) {
				url += "|";
			}
		}
		url += "&destinations=";
		for (int iter = 0 ; iter < addresses.size() ; iter++) {
			url += addresses.get(iter).getUrlPartition();
			if (iter + 1 != addresses.size()) {
				url += "|";
			}
		}
		// TO DO:
		//	Er staat hier nog een hardcoded googlemaps api
		url += "&mode=driving&language=en-EN&key=AIzaSyANJR6Knc1KAoc_OrdnGxrBmtehcWXr30o";
		
		return url;
	}


	public void calc(String badFormatDate) {
		
		
		String date = switchDDMMYYYYtoYYYYMMDD(badFormatDate);
		getAddressesFromDatabase(locationsAtDatesSQL(date));
		DistanceMatrix matrix = new DistanceMatrix(urlOfAllLocations(resultSet),DistanceMatrix.MinimizationParameter.TIME);
		matrix.viewMatrix();
		SolveTSP shortestRoute = new SolveTSP(matrix);
		Vector<Integer> times = shortestRoute.getTimesRoute(matrix);
		RouteDBObject routeToDatebase = new RouteDBObject(1, date, shortestRoute.getRoute(), resultSet, times);
		routeToDatebase.insertRouteDB(routeToDatebase);
		//routeToDatebase.viewRoutDBObject();
		
		
	}
	
	
}
