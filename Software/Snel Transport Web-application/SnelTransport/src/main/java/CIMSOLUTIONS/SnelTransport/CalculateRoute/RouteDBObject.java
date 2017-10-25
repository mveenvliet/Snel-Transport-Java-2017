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
	
	
	RouteDBObject(int idTruck, String date, String route, List<Address> addresses, Address home, String times){
		this.setIdTruck(idTruck);
		this.setDate(date);
		this.setRoute(route, addresses, home);
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

	public void setRoute(String route, List<Address> addresses, Address home) {

		String routeToStore = "";
		int idAddress;
		String sql = 
				"SELECT idAddress " + 
				"FROM databasesneltransport.address " + 
				"WHERE (city, street, housenumber) = ('" + 
					home.getCity() + "', '" + 
					home.getStreet() + "', '" +
					home.getHouseNumber() + "')";
		int idHome = getIntDatabase(sql, "idAddress");
		routeToStore += idHome;
		for (int iter = 0; iter < route.length(); iter++) {
			int index = route.charAt(iter) - 'a';
			sql = 
					"SELECT idAddress " + 
					"FROM databasesneltransport.address " + 
					"WHERE (city, street, housenumber) = ('" +
						addresses.get(index).getCity() + "', '" + 
						addresses.get(index).getStreet() + "', '" +
						addresses.get(index).getHouseNumber() + "')";
			idAddress = getIntDatabase(sql, "idAddress");
			routeToStore += "<" + idAddress;
		}
		routeToStore += "<" + idHome;
		this.route = routeToStore;
	}

	public String getTimeWaypoints() {
		return timeWaypoints;
	}
	public void setTimeWaypoints(String timeBetweenWaypoints) {
		this.timeWaypoints = timeBetweenWaypoints;
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
					"AND deliverydate = '" + routeObject.date + "'), " +
					"status = 'wordt berzorgd' " +
						"WHERE (idAddress) " +
						"IN ('" + route.replaceAll("<","','") + "') AND deliverydate = '" +date + "'";
		System.out.println(sqlOrderlist);
		storeRouteToDatabase(sqlOrderlist);
	}
	
	public void viewRoutDBObject() {
		System.out.println("TruckId: " + this.idTruck);
		System.out.println("Date:    " + this.date);
		System.out.println(this.route);
		System.out.println(this.timeWaypoints);
	}
	
	
}
