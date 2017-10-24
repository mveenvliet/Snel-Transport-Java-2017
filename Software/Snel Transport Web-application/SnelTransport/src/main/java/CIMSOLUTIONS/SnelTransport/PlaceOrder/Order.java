package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.util.ArrayList;
import java.util.Date;

public class Order {
	
	private int orderId;
	private int orderNumber;
	private String orderDate;
	private String deliveryDate;
	private java.text.SimpleDateFormat dutchDateFormat = new java.text.SimpleDateFormat("dd-MM-yyyy");
//	private java.text.SimpleDateFormat mysqlDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
	private Customer customer = new Customer();
	private ArrayList<Product> orderLineList = new ArrayList<>();
	
	
	public Order() {
		super();
		setOrderDate();
	}


	public Order(int customerNumber, String companyName, String city, String street, String houseNumber, String postalcode, String deliveryDate, ArrayList<Product> orderLineList) {
		super();
		this.customer.setCustomerNumber(customerNumber);
		this.customer.getCompany().setName(companyName);
		this.customer.getAddress().setCity(city);
		this.customer.getAddress().setStreet(street);
		this.customer.getAddress().setHouseNumber(houseNumber);
		this.customer.getAddress().setPostalCode(postalcode);
		setOrderDate();
		this.deliveryDate = deliveryDate;
		this.orderLineList = orderLineList;
	}


	public Order(int orderId, int orderNumber, String orderDate, String deliveryDate, Customer customer,
			ArrayList<Product> orderLineList) {
		super();
		this.orderId = orderId;
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
		this.deliveryDate = deliveryDate;
		this.customer = customer;
		this.orderLineList = orderLineList;
	}


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public int getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}


	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate() {
		Date currentDate = new java.util.Date();

		orderDate = dutchDateFormat.format(currentDate);
		System.out.println(currentDate);
	}
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}


	public String getDeliveryDate() {
		return deliveryDate;
	}


	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public ArrayList<Product> getOrderLineList() {
		return orderLineList;
	}


	public void setOrderLineList(ArrayList<Product> orderLineList) {
		this.orderLineList = orderLineList;
	}
	
	public static String switchDateFormat(String date) {
		String[] parts = date.split("-");
		return parts[2] + "-" + parts[1] + "-" + parts[0];
	}
	
	public void printValues() {
		customer.printValues();
		for (Product product : orderLineList) {
			product.printValues();
		}
		System.out.println("orderId: " + orderId);
		System.out.println("orderNumber: " + orderNumber);
		System.out.println("orderDate: " + orderDate);
		System.out.println("deliveryDate: " + deliveryDate);
	}
}
