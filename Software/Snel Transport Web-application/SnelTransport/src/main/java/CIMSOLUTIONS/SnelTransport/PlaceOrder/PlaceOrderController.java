package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import static java.util.Arrays.asList;

@RestController
public class PlaceOrderController {

	int customerNumber;
	String companyName;
	String firstname;
	String lastname;

	@RequestMapping(value = "/searchCustomer", method = RequestMethod.GET)
	@ResponseBody
	public List<Customer> getAllCustomers(@RequestParam(value = "customerNumber", required = false) int customerNumber,
			@RequestParam("companyName") String companyName, @RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname) {

		searchCustomerService searchCustomer = new searchCustomerService();
		Customer customer = new Customer(customerNumber, companyName, firstname, lastname);
		searchCustomer.createQuerry(customer);
		searchCustomer.lookUpCustomer(customer);
		return searchCustomer.getResultSet();
	}

	@RequestMapping(value = "/searchProduct", method = RequestMethod.GET)
	@ResponseBody
	public List<Product> getAllProducts(@RequestParam(value = "productNumber", required = false) String productNumber,
			@RequestParam("productName") String productName, @RequestParam("productType") String productType) {
		searchProductService searchProduct = new searchProductService();
		Product product = new Product(productNumber, productName, productType);
		searchProduct.lookUpProduct(product);
		for (Product p : searchProduct.getResultSet()) {
			p.printValues();
		}

		return searchProduct.getResultSet();
	}

}
