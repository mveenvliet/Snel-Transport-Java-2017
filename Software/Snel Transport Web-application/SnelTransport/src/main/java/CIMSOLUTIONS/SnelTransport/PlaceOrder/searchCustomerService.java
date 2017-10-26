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

		String sqlQuerry = "SELECT customer.customerNumber, company.name, customer.firstName, customer.lastName, customer.email, customer.phoneNumber, customer.status, address.city, address.street, address.houseNumber, address.postalCode "
					+ "FROM databasesneltransport.customer, databasesneltransport.company, databasesneltransport.address "
						+ "WHERE customer_idAddress = address.idAddress AND company.idCompany = customer.idCompany ";
		
		if (c.getCustomerNumber() != 0) {
			sqlQuerry += "AND customerNumber = '" + c.getCustomerNumber() + "' ";
		}
		if (c.getCompany().getName() != null && ! c.getCompany().getName().isEmpty())  {

			sqlQuerry += "AND name LIKE '%" + c.getCompany().getName() + "%' ";
		}
		if (c.getFirstname() != null && !c.getFirstname().isEmpty()) {
			sqlQuerry += "AND firstName LIKE '%" + c.getFirstname() + "%' ";
		}
		if (c.getLastname() != null && !c.getLastname().isEmpty()) {
			sqlQuerry += "AND lastName LIKE '%" + c.getLastname() + "%' ";
		}
		if (c.getEmailAddress() != null && !c.getLastname().isEmpty()) {
			sqlQuerry += "AND emailAddress LIKE '%" + c.getEmailAddress() + "%' ";
		}
		if (c.getStatus() != null && !c.getStatus().isEmpty()) {
			sqlQuerry += "AND status = '" + c.getStatus() + "%' ";
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
				tempCustomer.setCustomerNumber(myRs.getInt("customerNumber"));
				tempCustomer.getCompany().setName(myRs.getString("name"));
				tempCustomer.setFirstname(myRs.getString("firstName"));
				tempCustomer.setLastname(myRs.getString("lastName"));
				tempCustomer.setEmailAddress(myRs.getString("email"));
				tempCustomer.setPhoneNumber(myRs.getString("phoneNumber"));
				tempCustomer.setStatus(myRs.getString("status"));
				tempCustomer.getAddress().setCity(myRs.getString("city"));
				tempCustomer.getAddress().setStreet(myRs.getString("street"));
				tempCustomer.getAddress().setHouseNumber(myRs.getString("houseNumber"));
				tempCustomer.getAddress().setPostalCode(myRs.getString("postalCode"));
				tempCustomer.createInfoString();
				tempCustomer.printValues();
				resultSet.add(tempCustomer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
