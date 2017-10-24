package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import CIMSOLUTIONS.Database.MySqlDB;

public class CalculateRouteService extends MySqlDB {
	
	private String getStringFromDatabase(String sqlStatement, String tableHeader) {
		
		String result = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Statement myStmt = MyCon.createStatement();
			ResultSet myRs = myStmt.executeQuery(sqlStatement);
			while (myRs.next()) {
				result = myRs.getString(tableHeader);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String setDefaultHomeAddressSQL(){
		String setHomeSQL = 
				"SELECT city, street, houseNumber, postalcode " +
				"FROM databasesneltransport.address " +
				"WHERE idAddress " +
				"IN (SELECT customer_idAddress " + 
					"FROM databasesneltransport.customer " + 
					"WHERE customerNumber = '1')";
		return setHomeSQL;
	}
	
	private String setDefaultHomeAddressSQL(int customerNumber){
		String setHomeSQL = 
				"SELECT city, street, houseNumber, postalcode " +
				"FROM databasesneltransport.address " +
				"WHERE idAddress " +
				"IN (SELECT customer_idAddress " + 
					"FROM databasesneltransport.customer " + 
					"WHERE customerNumber = '" + customerNumber + "')";
		return setHomeSQL;
	}
	

	private String switchDDMMYYYYtoYYYYMMDD(String date) {
		String[] parts = date.split("-");
		return parts[2] + "-" + parts[1] + "-" + parts[0];
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
	
	
	String routeAtDateFromTruckSQL(String date, String licencePlate) {
		String sql = 
				"SELECT route " +
				"FROM databasesneltransport.routeList " +
				"WHERE idTruck = (" +
					"SELECT idTruck " +
					"FROM databasesneltransport.trucklist " +
					"WHERE licenceplate = '" + licencePlate + "')" +
				" AND deliverydate = '" + date + "'";
		return sql;
	}
	
	
	String locationFromLocationIdSQL(int locationId) {
		String sql = 
				"SELECT city, street, houseNumber, postalcode " +
						"FROM databasesneltransport.address " +
						"WHERE idAddress = '" + locationId + "'";
				return sql;
	}


	public String reCalcRoutes(String badFormatDate) {
		
		String date = switchDDMMYYYYtoYYYYMMDD(badFormatDate);
		AddressList allAddressesInOrderList = new AddressList();
		allAddressesInOrderList.setAddressesFromDatabase(setDefaultHomeAddressSQL());
		allAddressesInOrderList.setAddressesFromDatabase(locationsAtDatesSQL(date));
		System.out.println(allAddressesInOrderList.getListOfAddresses().size());
		if( allAddressesInOrderList.getListOfAddresses().size() == 1) {
			return "noOrders";
		}
		DistanceMatrix matrix = new DistanceMatrix(allAddressesInOrderList.urlOfAllLocations(),DistanceMatrix.MinimizationParameter.TIME);
		//matrix.viewMatrix();
		SolveTSP shortestRoute = new SolveTSP(matrix);
		Vector<Integer> times = shortestRoute.getTimesRoute(matrix);
		// \/ has to work for multiple routes
		RouteDBObject routeToDatebase = new RouteDBObject(1, date, shortestRoute.getRoute(), allAddressesInOrderList.getListOfAddresses(), times);
		routeToDatebase.insertRouteDB(routeToDatebase);		
		return "updatedValues";
	}
	
	
	public List<String> getAllLocationsAtDateFromTruck(String badFormatDate, String licencePlate){
		String date = switchDDMMYYYYtoYYYYMMDD(badFormatDate);
		
		String route = getStringFromDatabase(routeAtDateFromTruckSQL(date,licencePlate), "route");
		AddressList allAddressesInRoute = new AddressList();
		for(String locationId : route.split("<")) {
			allAddressesInRoute.setAddressesFromDatabase(locationFromLocationIdSQL(Integer.parseInt(locationId)));
		}
		return allAddressesInRoute.getListOfAddressStrings();
	}
	
}
