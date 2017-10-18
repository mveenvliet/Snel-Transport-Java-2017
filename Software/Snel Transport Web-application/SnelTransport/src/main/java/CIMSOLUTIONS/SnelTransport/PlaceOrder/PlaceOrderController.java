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
	
	@RequestMapping(value="/postCustomerController", method=RequestMethod.GET)
	@ResponseBody
	public List<Customer> getAllCustomers(){
		
//		searchCustomerService searchCustomer = new searchCustomerService();
//		searchCustomer.lookUpCustomer(customer);
//		return searchCustomer.getResultSet().get(0).getInfo();

		Customer klant1 = new Customer(1, "BedrijfA", "A", "Appel");
		Customer klant2 = new Customer(2, "BedrijfB", "B", "Banaan");
		return asList(klant1, klant2);
	}

    @RequestMapping(value="/customer", method=RequestMethod.GET)
    @ResponseBody
    public Customer getOneCustomer(@RequestParam("id") int customerId) {
        // Search Customer by id
        return new Customer(3, "BedrijfC", "C", "Clementine");
    }
}
