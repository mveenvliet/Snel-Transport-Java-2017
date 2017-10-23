package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import CIMSOLUTIONS.Database.MySqlDB;

public class AddressList extends MySqlDB{

	
	private List<Address> resultSet = new ArrayList<>();
	
	
	
	public void setAddressesFromDatabase(String sqlStatement) {
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
	
	public String urlOfAllLocations() {
		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
		for (int iter = 0 ; iter < this.resultSet.size() ; iter++) {
			url += resultSet.get(iter).getUrlPartition();
			if (iter + 1 != resultSet.size()) {
				url += "|";
			}
		}
		url += "&destinations=";
		for (int iter = 0 ; iter < resultSet.size() ; iter++) {
			url += resultSet.get(iter).getUrlPartition();
			if (iter + 1 != resultSet.size()) {
				url += "|";
			}
		}
		// TO DO:
		//	Er staat hier nog een hardcoded googlemaps api
		url += "&mode=driving&language=en-EN&key=AIzaSyANJR6Knc1KAoc_OrdnGxrBmtehcWXr30o";
		
		return url;
	}
	
	public List<Address> getListOfAddresses(){
		return resultSet;
	}
	
	public List<String> getListOfAddressStrings(){
		List<String> listOfAddresses = new ArrayList<>();
		for(Address address:resultSet) {
			listOfAddresses.add(address.getStreet() + " " + address.getHouseNumber() + ", " + address.getPostalcode() + ", " + address.getCity());
		}
		
		return listOfAddresses;
		
	}
}
