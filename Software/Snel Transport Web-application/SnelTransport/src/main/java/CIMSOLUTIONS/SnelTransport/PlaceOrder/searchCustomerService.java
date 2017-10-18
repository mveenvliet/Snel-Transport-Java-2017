package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import CIMSOLUTIONS.Database.*;

public class searchCustomerService extends MySqlDB {

	private ArrayList<Customer> resultSet = new ArrayList<>();

	public searchCustomerService() {
		super();
	}

	public ArrayList<Customer> getResultSet() {
		return resultSet;
	}

	public String createQuerry(Customer c) {
		String sqlQuerry = "SELECT * FROM databasesneltransport.contact, databasesneltransport.address WHERE contact.idContact = address.idContact ";

		if (c.getCustomerNumber() != 0) {
			sqlQuerry += "AND customerNumber = " + c.getCustomerNumber();
		}
		if (c.getCompanyName() != null && !c.getCompanyName().isEmpty()) {

			sqlQuerry += "AND nameCompany LIKE '%" + c.getCompanyName() + "%' ";
		}
		if (c.getFirstname() != null && !c.getFirstname().isEmpty()) {
			sqlQuerry += "AND firstName LIKE '%" + c.getFirstname() + "%' ";
		}
		if (c.getLastname() != null && !c.getLastname().isEmpty()) {
			sqlQuerry += "AND lastName LIKE '%" + c.getLastname() + "%' ";
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
				tempCustomer.setStreet(myRs.getString("street"));
				tempCustomer.setHouseNumber(myRs.getString("houseNumber"));
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
