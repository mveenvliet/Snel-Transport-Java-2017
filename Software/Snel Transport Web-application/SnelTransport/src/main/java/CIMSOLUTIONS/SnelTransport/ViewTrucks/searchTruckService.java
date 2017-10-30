package CIMSOLUTIONS.SnelTransport.ViewTrucks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import CIMSOLUTIONS.Database.MySqlDB;


public class searchTruckService extends MySqlDB {


		private ArrayList<Truck> resultSet = new ArrayList<>();

		public searchTruckService() {
			super();
		}

		public ArrayList<Truck> getResultSet() {
			return resultSet;
		}

		private String switchDDMMYYYYtoYYYYMMDD(String date) {
			
			String[] parts = date.split("-");	
			return parts[2] +  parts[1] + parts[0];
			
		}
		
		
		public String createQuerry(Truck t) {
			String sqlQuerry = 
					"SELECT * " + 
					"FROM databasesneltransport.trucklist WHERE " +
					"licencePlate LIKE '%" + t.getLicencePlate() + "%' "; 
				if (t.getAvailableFrom() != null && !t.getAvailableFrom().isEmpty())  {
					sqlQuerry += "AND (availableFrom >= '" + switchDDMMYYYYtoYYYYMMDD(t.getAvailableFrom()) + "' OR availableFrom IS NULL) ";
				}
				if (t.getNotAvailableFrom() != null && !t.getNotAvailableFrom().isEmpty())  {
					sqlQuerry += "AND (notAvailableFrom <'" + switchDDMMYYYYtoYYYYMMDD(t.getNotAvailableFrom()) + "' OR notAvailableFrom IS NULL) ";
				}
				if (t.getBrand() != null && !t.getBrand().isEmpty())  {
					sqlQuerry += "AND brand LIKE '" + t.getBrand() + "' ";
				}
				if (t.getType() != null && !t.getType().isEmpty())  {
					sqlQuerry += "AND type LIKE '" + t.getType() + "' ";
				}
				if (t.getChauffeur() != null && !t.getChauffeur().isEmpty())  {
					sqlQuerry += "AND driver LIKE '" + t.getChauffeur() + "' ";
				}
				if (t.getOwner() != null && !t.getOwner().isEmpty())  {
					sqlQuerry += "AND owner LIKE '" + t.getOwner() + "' ";
				}
				sqlQuerry += "ORDER BY licencePlate";
				
				return sqlQuerry;
		}

		public void lookUpTruck(Truck t) {

			String sqlQuerry = createQuerry(t);
			System.out.println(sqlQuerry);
			try {
				Statement myStmt = MyCon.createStatement();
				ResultSet myRs = myStmt.executeQuery(sqlQuerry);
				
				while (myRs.next()) {
					Truck tempTruck = new Truck();
				
					tempTruck.setIdTruck(myRs.getInt("idTruck"));
					tempTruck.setLicencePlate(myRs.getString("licencePlate"));
					tempTruck.setChauffeur(myRs.getString("driver"));
					tempTruck.setBrand(myRs.getString("brand"));
					tempTruck.setType(myRs.getString("type"));
					tempTruck.setOwner(myRs.getString("owner"));
					tempTruck.setAvailableFrom(myRs.getString("availableFrom"));
					tempTruck.setNotAvailableFrom(myRs.getString("notAvailableFrom"));
					

					//tempTruck.printValues();
					resultSet.add(tempTruck);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}


	
	
}
