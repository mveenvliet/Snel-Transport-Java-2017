package CIMSOLUTIONS.SnelTransport.PlaceOrder;

public class Address {
	
	private int id;
	private String city;
	private String street;
	private String houseNumber;
	private String postalCode;
	private String locationSpecification;
	
	public Address() {
		
	}
	
	public Address(int id, String city, String street, String houseNumber, String postalCode,
			String locationSpecification) {
		super();
		this.id = id;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
		this.locationSpecification = locationSpecification;
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

	public String getLocationSpecification() {
		return locationSpecification;
	}

	public void setLocationSpecification(String locationSpecification) {
		this.locationSpecification = locationSpecification;
	}
	
	
	

}
