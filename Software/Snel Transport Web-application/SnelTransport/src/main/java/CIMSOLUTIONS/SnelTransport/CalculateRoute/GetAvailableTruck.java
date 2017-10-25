package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import CIMSOLUTIONS.Database.MySqlDB;

public class GetAvailableTruck extends MySqlDB{

	
	String date;
	List<Integer> idAvailableTrucks = new ArrayList<>();
	List<Integer> idExtraTrucks = new ArrayList<>();
	
	GetAvailableTruck(int requiredNrOfTrucks, String date){
		setAvailableTruckIds(date);
		setExrtaTrucks(requiredNrOfTrucks, date);
	}
	
	void setAvailableTruckIds(String date) {
		String sql = 
				"SELECT idTruck " + 
				"FROM databasesneltransport.trucklist " +
				"WHERE " +
					"((availableFrom <= '" + date + "') OR (availableFrom IS NULL)) " +
				"AND " +
					"((notAvailableFrom > '" + date + "') OR (notAvailableFrom IS NULL)) " +
				"AND owner IS NOT NULL";
		this.idAvailableTrucks = new ArrayList<Integer>(getIntListDatabase(sql,"idTruck"));
	}
			
	void setExrtaTrucks(int requiredNrOfTrucks, String date) {

		SimpleDateFormat sdf = new SimpleDateFormat(date);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 1);  
		String nextDate = sdf.format(c.getTime());

		int availableNrOfTrucks = idAvailableTrucks.size();
		if ((requiredNrOfTrucks - availableNrOfTrucks) > 0) {
			int nrExtraTrucks = requiredNrOfTrucks - availableNrOfTrucks;
			for (int iter = 0 ; iter < nrExtraTrucks ; iter++){
				String sendSql = 
						"INSERT INTO databasesneltransport.trucklist (licencePlate, availableFrom, notAvailableFrom)" +
						"VALUES ('Extra truck " + (iter + 1) + "-" + date + "', '" + date + "', '" + nextDate + "')";
				System.out.println(sendSql);
				storeToDatabase(sendSql);
			}
			String getSql = 
					"SELECT idTruck "+ 
					"FROM databasesneltransport.trucklist " +
					"WHERE " +
						"(availableFrom = '" + date + "') " +
					"AND "+
						"(notAvailableFrom = '" + nextDate + "') " +
					"AND " +
						"(licencePlate LIKE 'Extra truck%')";
			this.idExtraTrucks = new ArrayList<Integer>(getIntListDatabase(getSql,"idTruck"));
		}
	}
	
	
	private List<Integer> getIntListDatabase(String sqlStatement, String argument) {
		List<Integer> listInteger = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Statement myStmt = MyCon.createStatement();
			ResultSet rs = myStmt.executeQuery(sqlStatement);
			while (rs.next()) {
				listInteger.add(rs.getInt(argument));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return listInteger;
	}
	
	private void storeToDatabase(String sqlStatement) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Statement myStmt = MyCon.createStatement();
			myStmt.executeUpdate(sqlStatement);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> getAllTruckIds(){
		List<Integer> allIds = new ArrayList<>(this.idAvailableTrucks);
		allIds.addAll(this.idExtraTrucks);
		return allIds;
	}
	
	public int getNrOfAvailableTrucks() {
		return idAvailableTrucks.size();
	}
}
