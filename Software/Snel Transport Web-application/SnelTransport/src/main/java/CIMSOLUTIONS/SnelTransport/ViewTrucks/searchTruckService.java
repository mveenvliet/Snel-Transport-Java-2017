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

		public String createQuerry(Truck t) {
			String sqlQuerry = 
					"SELECT * " + 
					"FROM databasesneltransport.trucklist WHERE " +
					"licencePlate LIKE '%" + t.getLicencePlate() + "%' AND " +
					"chauffeur LIKE '%" + t.getChauffeur() + "%' AND " +
					"brand LIKE '%" + t.getBrand() + "%' AND " +
					"type LIKE '%" + t.getType() + "%' AND " +
					"owner LIKE '%" + t.getOwner() + "%' AND " +
					"availableFrom LIKE '%" + t.getAvailableFrom() + "%' AND " +
					"notAvailableFrom LIKE '%" + t.getNotAvailableFrom() + "%';";
			sqlQuerry += "ORDER BY licencePlate";
			return sqlQuerry;
		}

		public void lookUpTruck(Truck t) {

			String sqlQuerry = createQuerry(t);

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
