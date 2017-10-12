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
	public String postCustomer(@RequestBody int number, String cName, String fName, String lName){
		System.out.println("customerNumber: " + number);
		System.out.println("companyName: " + cName);
		System.out.println("firstName: " + fName);
		System.out.println("lastName: " + lName);
		
		return "Sucessful!";
	}

}
