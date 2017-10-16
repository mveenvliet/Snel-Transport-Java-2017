package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import CIMSOLUTIONS.Database.*;

public class searchCustomerService extends MySqlDB {

	private ArrayList<Customer> resultSet = new ArrayList<>();

	searchCustomerService() {
		super();
	}

	public ArrayList<Customer> getResultSet() {
		return resultSet;
	}

	public String createQuerry(Customer c) {
		String sqlQuerry = "SELECT * FROM databasesneltransport.contact ";

		String prefix = "WHERE ";
		boolean isFirst = false;

		if (c.getCustomerNumber() != 0) {
			if (!isFirst) {
				sqlQuerry += prefix;
				isFirst = true;
			}
			sqlQuerry += "customerNumber = " + c.getCustomerNumber();
		}
		if (c.getCompanyName() != null || c.getCompanyName() == "") {
			if (!isFirst) {
				isFirst = true;
			} else {
				prefix = " AND ";
			}
			sqlQuerry += prefix + "nameCompany LIKE '%" + c.getCompanyName() + "%' ";
		}
		if (c.getFirstname() != null || c.getFirstname() == "") {
			if (!isFirst) {
				isFirst = true;
			} else {
				prefix = " AND ";
			}
			sqlQuerry += prefix + "firstName LIKE '%" + c.getFirstname() + "%' ";
		}
		if (c.getLastname() != null || c.getLastname() == "") {
			if (!isFirst) {
				isFirst = true;
			} else {
				prefix = " AND ";
			}
			sqlQuerry += prefix + "lastName LIKE '%" + c.getLastname() + "%' ";
		}
		
		System.out.println(sqlQuerry);
		return sqlQuerry;
	}

	public void lookUpCustomer(Customer c) {

		String sqlQuerry = createQuerry(c);

		try {
			Statement myStmt = MyCon.createStatement();
			ResultSet myRs = myStmt.executeQuery(sqlQuerry);

			while (myRs.next()) {
				Customer tempCustomer = new Customer();
				tempCustomer.setId(myRs.getInt("idContact"));
				tempCustomer.setCustomerNumber(myRs.getInt("customerNumber"));
				tempCustomer.setCompanyName(myRs.getString("nameCompany"));
				tempCustomer.setFirstname(myRs.getString("firstName"));
				tempCustomer.setLastname(myRs.getString("lastName"));
				tempCustomer.setCity(myRs.getString("city"));
				tempCustomer.setStreet(myRs.getString("streat"));
				tempCustomer.setHouseNumber(myRs.getString("homeNumber"));
				tempCustomer.setPostalCode(myRs.getString("postalCode"));
				tempCustomer.setPhoneNumber(myRs.getString("phoneNumber"));
				
				tempCustomer.createInfoString();
				tempCustomer.printValues();
				resultSet.add(tempCustomer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
