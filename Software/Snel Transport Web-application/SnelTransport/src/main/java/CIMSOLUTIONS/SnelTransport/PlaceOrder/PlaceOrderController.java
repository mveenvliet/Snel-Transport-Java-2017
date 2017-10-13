package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PlaceOrderController {
	int customerNumber;
	String companyName;
	String firstname;
	String lastname;
	
	@RequestMapping(value="/postCustomerController", method=RequestMethod.POST)
	public String postCustomer(@RequestBody Customer customer){
		
		System.out.println("customerNumber: " + customer.getCustomerNumber());
		System.out.println("companyName: " + customer.getCompanyName());
		System.out.println("firstName: " + customer.getFirstname());
		System.out.println("lastName: " + customer.getLastname());
		
		return "Sucessful!";
	}

}
