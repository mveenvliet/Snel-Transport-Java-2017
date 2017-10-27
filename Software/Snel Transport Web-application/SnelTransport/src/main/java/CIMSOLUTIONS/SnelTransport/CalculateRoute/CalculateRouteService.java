package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


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
	
//	private String setDefaultHomeAddressSQL(){
//		String setHomeSQL = 
//				"SELECT city, street, houseNumber, postalcode " +
//				"FROM databasesneltransport.address " +
//				"WHERE idAddress " +
//				"IN (SELECT customer_idAddress " + 
//					"FROM databasesneltransport.customer " + 
//					"WHERE customerNumber = '1')";
//		return setHomeSQL;
//	}
	
//	private String setDefaultHomeAddressSQL(int customerNumber){
//		String setHomeSQL = 
//				"SELECT city, street, houseNumber, postalcode " +
//				"FROM databasesneltransport.address " +
//				"WHERE idAddress " +
//				"IN (SELECT customer_idAddress " + 
//					"FROM databasesneltransport.customer " + 
//					"WHERE customerNumber = '" + customerNumber + "')";
//		return setHomeSQL;
//	}
	

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
		
		new DeletePrevious(date);
		
		Address source = new Address("Gouda","Zeugstraat","92","2801JD");
		AddressList allAddressesInOrderList = new AddressList();
		allAddressesInOrderList.setAddressesFromDatabase(locationsAtDatesSQL(date));
		if( allAddressesInOrderList.getNumberOfAddresses() == 0) {
			return "noOrders";
		}
		
		
		DistanceSource distanceToDepo = new DistanceSource(allAddressesInOrderList,source);
		DistanceMatrix matrix = new DistanceMatrix(allAddressesInOrderList.urlOfAllLocations(),DistanceMatrix.MinimizationParameter.TIME);
		new CorrectionMatrix(allAddressesInOrderList, matrix);

		SolveTSP shortestRoute = new SolveTSP(matrix);
		if((matrix.getHeight() == 0)||(distanceToDepo.getSizeList() == 0)) {
			return "exceededKeyQuota";
		}
		System.out.println(shortestRoute.getRoute());
		DivideRoute routeWithMinTrucks = new DivideRoute();
		routeWithMinTrucks.walkRoute(shortestRoute,  matrix, allAddressesInOrderList, distanceToDepo);
		System.out.println(routeWithMinTrucks.getOptimalRoutePerTruck());
		GetAvailableTruck setTrucks = new GetAvailableTruck(routeWithMinTrucks.getOptimalNrOfTrucks(), date);
		for (int iter = 0 ; iter < routeWithMinTrucks.getOptimalNrOfTrucks() ; iter++) {
			RouteDBObject routeToDatabase = new RouteDBObject(setTrucks.getAllTruckIds().get(iter), date, routeWithMinTrucks.getOptimalRoutePerTruck().get(iter), 
					allAddressesInOrderList.getListOfAddresses(), source, routeWithMinTrucks.getOptimalTimeWaypointsPerTruck().get(iter));
			routeToDatabase.viewRoutDBObject();
			routeToDatabase.insertRouteDB(routeToDatabase);
		}
		if (routeWithMinTrucks.getOptimalNrOfTrucks() > setTrucks.getNrOfAvailableTrucks()) {
			return "Er zijn " + (routeWithMinTrucks.getOptimalNrOfTrucks() - setTrucks.getNrOfAvailableTrucks()) + " extra vrachtwagens nodig"; 
		} else {	
			return "updatedValues";
		}
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
