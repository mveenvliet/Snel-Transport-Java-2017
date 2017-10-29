package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import CIMSOLUTIONS.Database.MySqlDB;

public class DeletePrevious extends MySqlDB{

	public DeletePrevious(String date){

		SimpleDateFormat sdf = new SimpleDateFormat(date);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 1);
		String nextDate = sdf.format(c.getTime());
		
		
		
		String sqlProductStatus = "UPDATE databasesneltransport.orderlist SET status = 'besteld', idRoute=NULL WHERE deliveryDate = '"
				+ date + "';";
		String sqlRoutes = "DELETE FROM databasesneltransport.routelist WHERE deliveryDate ='" + date + "'; ";
		String sqlExtraTrucks = "DELETE FROM databasesneltransport.trucklist WHERE availableFrom ='" + date
				+ "' AND notAvailableFrom = '" + nextDate + "' AND licenceplate LIKE 'Extra truck%'; ";		
		
		updateDatabase(sqlProductStatus);
		updateDatabase(sqlRoutes);
		updateDatabase(sqlExtraTrucks);

	}
	private void updateDatabase(String sqlStatement) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Statement myStmt = MyCon.createStatement();
			myStmt.executeUpdate(sqlStatement);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}	
}
