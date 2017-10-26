package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.util.List;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	@ResponseBody
	public String placeOrder(@RequestParam MultiValueMap<String, String> params) {
		Order order = new Order();

		System.out.println("order Test:\n");
		System.out.println(params);
		System.out.println(params.values());
		System.out.println(params.get("customerNumber").toString().substring(1,
				params.get("customerNumber").toString().length() - 1));
		order.getCustomer().setCustomerNumber(Integer.parseInt(params.get("customerNumber").toString().substring(1,
				params.get("customerNumber").toString().length() - 1)));
		System.out.println(
				params.get("companyName").toString().substring(1, params.get("companyName").toString().length() - 1));
		order.getCustomer().getCompany().setName(
				params.get("companyName").toString().substring(1, params.get("companyName").toString().length() - 1));
		System.out.println(params.get("city").toString().substring(1, params.get("city").toString().length() - 1));
		order.getCustomer().getAddress()
				.setCity(params.get("city").toString().substring(1, params.get("city").toString().length() - 1));
		System.out.println(params.get("street").toString().substring(1, params.get("street").toString().length() - 1));
		order.getCustomer().getAddress()
				.setStreet(params.get("street").toString().substring(1, params.get("street").toString().length() - 1));
		System.out.println(
				params.get("houseNumber").toString().substring(1, params.get("houseNumber").toString().length() - 1));
		order.getCustomer().getAddress().setHouseNumber(
				params.get("houseNumber").toString().substring(1, params.get("houseNumber").toString().length() - 1));
		System.out.println(
				params.get("postalcode").toString().substring(1, params.get("postalcode").toString().length() - 1));
		order.getCustomer().getAddress().setPostalCode(
				params.get("postalcode").toString().substring(1, params.get("postalcode").toString().length() - 1));
		System.out.println(
				params.get("deliveryDate").toString().substring(1, params.get("deliveryDate").toString().length() - 1));
		order.setDeliveryDate(
				params.get("deliveryDate").toString().substring(1, params.get("deliveryDate").toString().length() - 1));
		int i = 0;
		while (params.get("products[" + i + "][productNumber]") != null
				&& !params.get("products[" + i + "][productNumber]").toString().isEmpty()) {
			Product product = new Product();
			System.out.println("index: " + i);
			System.out.println(params.get("products[" + i + "][productNumber]").toString().substring(1,
					params.get("products[" + i + "][productNumber]").toString().length() - 1));
			product.setProductNumber(params.get("products[" + i + "][productNumber]").toString().substring(1,
					params.get("products[" + i + "][productNumber]").toString().length() - 1));
			System.out.println(params.get("products[" + i + "][discription]").toString().substring(1,
					params.get("products[" + i + "][discription]").toString().length() - 1));
			product.setName(params.get("products[" + i + "][discription]").toString().substring(1,
					params.get("products[" + i + "][discription]").toString().length() - 1));
			System.out.println(params.get("products[" + i + "][type]").toString().substring(1,
					params.get("products[" + i + "][type]").toString().length() - 1));
			product.setTypeString(params.get("products[" + i + "][type]").toString().substring(1,
					params.get("products[" + i + "][type]").toString().length() - 1));
			System.out.println(Integer.parseInt(params.get("products[" + i + "][amount]").toString().substring(1,
					params.get("products[" + i + "][amount]").toString().length() - 1)));
			product.setAmount(Integer.parseInt(params.get("products[" + i + "][amount]").toString().substring(1,
					params.get("products[" + i + "][amount]").toString().length() - 1)));
			System.out.println(Double.parseDouble(params.get("products[" + i + "][price]").toString().substring(1, params.get("products[" + i + "][price]").toString().length()-1)));
			product.setPrice(Double.parseDouble(params.get("products[" + i + "][price]").toString().substring(1, params.get("products[" + i + "][price]").toString().length()-1)));
			order.getOrderLineList().add(product);
			i++;
		}
		order.printValues();
		PlaceOrder placeOrder = new PlaceOrder(order);
		return placeOrder.writeOrderToDatabase();

	}

}
