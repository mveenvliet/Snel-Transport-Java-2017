package CIMSOLUTIONS.SnelTransport.PlaceOrder;

public class Customer {
	private String firstname;
	private String lastname;
	private String companyName;
	private int customerNumber;
	private int id;
	private String city;
	private String street;
	private String houseNumber;
	private String postalCode;
	private String phoneNumber;
	private String info;
	
	public Customer(){}
	
	public Customer(int customerNumber, String companyName, String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.companyName = companyName;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
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

	public void createInfoString() {
		info = Integer.toString(customerNumber) + ": " + companyName + ", " + city + ", " + street + ", " + houseNumber + ", " + postalCode + ", " + phoneNumber; 
	}
	
	public void printValues() {
		System.out.println("id: " + id);
		System.out.println("customerNumber: " + customerNumber);
		System.out.println("companyName: " + companyName);
		System.out.println("firstname: " + firstname);
		System.out.println("lastname: " + lastname);
		System.out.println("city: " + city);
		System.out.println("street: " + street);
		System.out.println("houseNumber: " + houseNumber);
		System.out.println("postalCode: " + postalCode);
		System.out.println("phoneNumber: " + phoneNumber);
		System.out.println("info: " + info);
	}
	
}