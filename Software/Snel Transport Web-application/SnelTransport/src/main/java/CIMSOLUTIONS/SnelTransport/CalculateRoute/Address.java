package CIMSOLUTIONS.SnelTransport.CalculateRoute;

public class Address {

	private String city;
	private String street;
	private String houseNumber;
	private String postalcode;
	private String urlPartition;
	private int minutesLoadTime = 30;
	
	public Address(){
		
	}
	public Address(String city, String street, String houseNumber, String postalcode){
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
		this.postalcode = postalcode;
		setUrlPartition();
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
	public void setHouseNumber(String houdNumber) {
		this.houseNumber = houdNumber;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getUrlPartition() {
		return urlPartition;
	}
	public void setUrlPartition() {
		this.urlPartition = this.street.replaceAll(" ",  "%20") + "+" +
							this.houseNumber + "+" + 
							this.city.replaceAll(" ", "%20") + "+" + 
							this.postalcode.replaceAll(" ", "%20"); 
	}
	public int getMinutesLoadTime() {
		return minutesLoadTime;
	}
	public void setMinutesLoadTime(int minutesLoadTime) {
		this.minutesLoadTime = minutesLoadTime;
	}
	
	public void showAddress() {
		System.out.println("city:            " + city);
		System.out.println("street:          " + street);
		System.out.println("houseNumber:     " + houseNumber);
		System.out.println("postalcode:      " + postalcode);
		System.out.println("urlPartition:    " + urlPartition);
		System.out.println("minutesLoadTime: " + minutesLoadTime);
		

	}
	
}
