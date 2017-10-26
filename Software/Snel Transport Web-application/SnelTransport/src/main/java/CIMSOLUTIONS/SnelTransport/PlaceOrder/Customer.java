package CIMSOLUTIONS.SnelTransport.PlaceOrder;

public class Customer {
	private int id;
	private int customerNumber;
	private String firstname;
	private String lastname;
	private String phoneNumber;
	private String emailAddress;
	private String status;
	private String info;

	private Company company = new Company();
	private Address address = new Address();

	public Customer() {
	}

	public Customer(int customerNumber, String companyName, String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.getCompany().setName(companyName);
		this.customerNumber = customerNumber;
	}
	
	public Customer(int customerNumber, String companyName, String firstname, String lastname, String emailAddress, String status) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.getCompany().setName(companyName);
		this.customerNumber = customerNumber;
		this.emailAddress = emailAddress;
		this.status = status;
	}
	
	public Customer(int customerNumber, String companyName) {
		this.getCompany().setName(companyName);
		this.customerNumber = customerNumber;
	}

	public String getFirstname() {
		return firstname;

	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;

	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void createInfoString() {
		info = Integer.toString(customerNumber) + ": " + getCompany().getName() + ", " + getAddress().getCity() + ", "
				+ getAddress().getStreet() + ", " + getAddress().getHouseNumber() + ", " + getAddress().getPostalCode();
	}

	public void printValues() {
		System.out.println("id: " + id);
		System.out.println("customerNumber: " + customerNumber);
		System.out.println("companyName: " + getCompany().getName());
		System.out.println("firstname: " + firstname);
		System.out.println("lastname: " + lastname);
		System.out.println("phoneNumber: " + phoneNumber);
		System.out.println("emailAddress: " + emailAddress);
		System.out.println("status: " + status);
		System.out.println("city: " + getAddress().getCity());
		System.out.println("street: " + getAddress().getStreet());
		System.out.println("houseNumber: " + getAddress().getHouseNumber());
		System.out.println("postalCode: " + getAddress().getPostalCode());
		System.out.println("info: " + info);
	}

}