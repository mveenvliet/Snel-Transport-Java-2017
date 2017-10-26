package CIMSOLUTIONS.SnelTransport.ViewCustomers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import CIMSOLUTIONS.SnelTransport.PlaceOrder.Customer;
import CIMSOLUTIONS.SnelTransport.PlaceOrder.searchCustomerService;

@RestController
public class CustomerManagementController {

	int customerNumber;
	String companyName;
	String firstname;
	String lastname;

	@RequestMapping(value = "/searchSetCustomer", method = RequestMethod.GET)
	@ResponseBody
	public List<Customer> getAllCustomers(
			@RequestParam(value = "customerNumber", required = false) int customerNumber,
			@RequestParam("companyName") String companyName, 
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("emailAddress") String emailAddress) {

		searchCustomerService searchCustomer = new searchCustomerService();
		Customer customer = new Customer(customerNumber, companyName, firstName, lastName, emailAddress);
		
		searchCustomer.lookUpCustomer(customer);
		return searchCustomer.getResultSet();
	}
}