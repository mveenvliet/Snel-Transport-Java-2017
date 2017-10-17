package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Arrays.asList;

@RestController
public class PlaceOrderController {

	int customerNumber;
	String companyName;
	String firstname;
	String lastname;
	
	@RequestMapping(value="/postCustomerController", method=RequestMethod.GET)
	@ResponseBody
	public List<Customer> postCustomer(){
		
//		searchCustomerService searchCustomer = new searchCustomerService();
//		searchCustomer.lookUpCustomer(customer);
//		return searchCustomer.getResultSet().get(0).getInfo();

		Customer klant1 = new Customer(1, "BedrijfA", "A", "Appel");
		Customer klant2 = new Customer(2, "BedrijfB", "B", "Banaan");
		return asList(klant1, klant2);
	}

}
