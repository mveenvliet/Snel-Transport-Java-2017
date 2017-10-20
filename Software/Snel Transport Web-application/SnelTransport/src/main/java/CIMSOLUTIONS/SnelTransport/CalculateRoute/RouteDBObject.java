package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import CIMSOLUTIONS.Database.MySqlDB;

public class RouteDBObject extends MySqlDB{
	
	private int idTruck;
	private String date;
	private String route;
	private String timeWaypoints;
	
	
	RouteDBObject(int idTruck, String date, String route, List<Address> addresses, List<Integer> times){
		this.setIdTruck(idTruck);
		this.setDate(date);
		this.setRoute(route, addresses);
		this.setTimeWaypoints(times);	
	}
	
	public int getIdTruck() {
		return idTruck;
	}
	public void setIdTruck(int idTruck) {
		this.idTruck = idTruck;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route, List<Address> addresses) {
		String shortRoute = "";
		int idAddress = 0;
		for (int iter = 0 ; iter < addresses.size() ; iter++) {
			String sql = 
					"SELECT idAddress " + 
					"FROM databasesneltransport.address" +
					" WHERE (city, street, housenumber) = ('" +
					addresses.get(iter).getCity() + "', '" +
					addresses.get(iter).getStreet() + "', '" +
					addresses.get(iter).getHouseNumber() + "')";

			idAddress = getIntDatabase(sql, "idAddress");
			if (iter == 0) {
				shortRoute += idAddress;
			} else {
				shortRoute += "<" + idAddress; 
			}
		}
		this.route = shortRoute;
	}
	public String getTimeWaypoints() {
		return timeWaypoints;
	}
	public void setTimeWaypoints(List<Integer> timeBetweenWaypoints) {
		
		String concatenated = timeBetweenWaypoints.get(0).toString();
		for(int iter = 1; iter<timeBetweenWaypoints.size();iter++) {
			concatenated += "<" + timeBetweenWaypoints.get(iter).toString();
		}
		this.timeWaypoints = concatenated;
	}

	private void storeRouteToDatabase(String sqlStatement) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Statement myStmt = MyCon.createStatement();
			myStmt.executeUpdate(sqlStatement);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int getIntDatabase(String sqlStatement, String argument) {
		int resultAsInt = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Statement myStmt = MyCon.createStatement();
			ResultSet rs = myStmt.executeQuery(sqlStatement);
			if (rs.next()) {
				resultAsInt = rs.getInt(argument);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return resultAsInt;
	}
	
	public void insertRouteDB(RouteDBObject routeObject) {
		String sqlRoutelist = "INSERT INTO databasesneltransport.routelist (idTruck, deliveryDate, route, timeWaypoints) VALUES ('"+
				routeObject.idTruck + "', '" + 
				routeObject.date + "', '" + 
				routeObject.route  + "', '" + 
				routeObject.timeWaypoints + "')";
		storeRouteToDatabase(sqlRoutelist);
		String sqlOrderlist = 
				"UPDATE databasesneltransport.orderList " + 
				"SET idRoute = (" +
					"SELECT idRouteList " +
					"FROM databasesneltransport.routelist " +
					"WHERE route = '" + routeObject.route + "' " + 
					"AND deliverydate = '" + routeObject.date + "') " + 
						"WHERE (idAddress) " +
						"IN ('" + route.replaceAll("<","','") + "')";
		storeRouteToDatabase(sqlOrderlist);

	}
	
	public void viewRoutDBObject() {
		System.out.println("TruckId: " + this.idTruck);
		System.out.println("Date:    " + this.date);
		System.out.println(this.route);
		System.out.println(this.timeWaypoints);
	}
}
