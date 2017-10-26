package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.util.ArrayList;
import java.util.List;

public class Product {

	private int id;
	private int compatmentNumber;
	private int amount;
	private double price;
	private String productNumber;
	private String name;
	private String location;
	private String status;
	private String typeString;
	private List<String> typeList = new ArrayList<>();

	public Product() {
	}

	public Product(String productNumber, String name, String type) {
		this.productNumber = productNumber;
		this.name = name;
		typeList.add(type);
	}

	public Product(String productNumber, String name, List<String> type) {
		this.productNumber = productNumber;
		this.name = name;
		typeList = type;
	}

	public Product(int id, int compatmentNumber, int amount, double price, String productNumber, String name,
			String location, String status, List<String> type) {
		super();
		this.id = id;
		this.compatmentNumber = compatmentNumber;
		this.amount = amount;
		this.price = price;
		this.productNumber = productNumber;
		this.name = name;
		this.location = location;
		this.status = status;
		typeList = type;
	}

	public Product(String productNumber, String productName, String categoryList, double productPrice, 
			int amount, String warehouse, int compartimentNumber, String productStatus) {
		this.productNumber = productNumber;
		this.name = productName;
		this.typeString = categoryList;
		this.price = productPrice;
		this.amount = amount;
		this.location = warehouse;
		this.compatmentNumber = compartimentNumber;
		this.status = productStatus;
	
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompatmentNumber() {
		return compatmentNumber;
	}

	public void setCompatmentNumber(int compatmentNumber) {
		this.compatmentNumber = compatmentNumber;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeString() {
		return typeString;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}

	public List<String> getTypeList() {
		return typeList;
	}
	
	public void addToTypeList(String type) {
		for (String string : typeList) {
			if(string.equalsIgnoreCase(type)) {
				return;
			}
		}
		typeList.add(type);
	}

	public String getTypeListString() {
		boolean first = true;
		for (String string : typeList) {
			if (first) {
				typeString = string;
				first = false;
			} else {
				typeString += ", " + string;
			}
		}

		return typeString;
	}

	public void printValues() {
		System.out.println("id: " + id);
		System.out.println("productNumber: " + productNumber);
		System.out.println("productName: " + name);
		System.out.println("price: " + price);
		System.out.println("amount: " + amount);
		System.out.println("location: " + location);
		System.out.println("compatmentNumber: " + compatmentNumber);
		System.out.println("status: " + status);
		System.out.println("types: " + getTypeList());
	}
}
