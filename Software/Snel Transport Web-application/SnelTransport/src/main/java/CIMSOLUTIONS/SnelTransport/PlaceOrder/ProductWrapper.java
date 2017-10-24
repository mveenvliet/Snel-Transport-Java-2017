package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public class ProductWrapper extends ArrayList<Product> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Value("products")
	private List<String> items = new ArrayList<>();
	
	
	public ProductWrapper() {
		super();
	}

	public ProductWrapper(List<String> items) {
		super();
		this.items = items;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}
}
