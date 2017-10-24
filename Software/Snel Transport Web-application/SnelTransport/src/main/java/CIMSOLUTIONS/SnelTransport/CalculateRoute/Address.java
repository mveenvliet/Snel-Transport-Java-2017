package CIMSOLUTIONS.SnelTransport.CalculateRoute;

public class Address {

	private String city;
	private String street;
	private String houseNumber;
	private String postalcode;
	private String urlPartition;
	private int minutesLoadTime;
	
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
	
	
}
