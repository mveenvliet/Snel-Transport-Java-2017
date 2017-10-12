package CIMSOLUTIONS.SnelTransport.PlaceOrder;

public class Customer {
	private String firstname;
	private String lastname;
	private String companyName;
	private int customerNumber;
	
	public Customer(){}
	
	public Customer(String firstname, String lastname){
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	
	public Customer(int customerNumber, String companyName, String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.companyName = companyName;
		this.customerNumber = customerNumber;
	}

	// firstname
	public String getFirstname() {
		return firstname;
		
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	// lastname
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}
	
}