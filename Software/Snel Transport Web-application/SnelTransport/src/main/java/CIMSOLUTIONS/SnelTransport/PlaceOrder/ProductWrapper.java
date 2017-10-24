package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.util.ArrayList;
import java.util.List;

public class ProductWrapper {
	private List<Product> items = new ArrayList<>();
	
	
	public ProductWrapper(List<Product> items) {
		super();
		this.items = items;
	}

	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}
}
