package CIMSOLUTIONS.SnelTransport.ViewTrucks;

public class Truck {

	private int idTruck; 
	private String licencePlate;
	private String chauffeur;
	private String brand;
	private String type;
	private String owner;
	private String availableFrom;
	private String notAvailableFrom;
	
	Truck(String licencePlate, String chauffeur, String brand, String type, String owner, String availableFrom, String notAvailableFrom){
	
		this.licencePlate = licencePlate;
		this.chauffeur = chauffeur;
		this.brand = brand;
		this.type = type;
		this.setOwner(owner);
		this.availableFrom = availableFrom;
		this.notAvailableFrom = notAvailableFrom;
		
	}
	
	public Truck() {
	}


	public String getLicencePlate() {
		return licencePlate;
	}
	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}
	public String getChauffeur() {
		return chauffeur;
	}
	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAvailableFrom() {
		return availableFrom;
	}
	public void setAvailableFrom(String availableFrom) {
		this.availableFrom = availableFrom;
	}
	public String getNotAvailableFrom() {
		return notAvailableFrom;
	}
	public void setNotAvailableFrom(String notAvailableFrom) {
		this.notAvailableFrom = notAvailableFrom;
	}



	public int getIdTruck() {
		return idTruck;
	}



	public void setIdTruck(int idTruck) {
		this.idTruck = idTruck;
	}



	public String getOwner() {
		return owner;
	}



	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public void printValue() {
		System.out.println(idTruck); 
		System.out.println(licencePlate);
		System.out.println(chauffeur);
		System.out.println(brand);
		System.out.println(type);
		System.out.println(owner);
		System.out.println(availableFrom);
		System.out.println(notAvailableFrom);
	}
	
}
