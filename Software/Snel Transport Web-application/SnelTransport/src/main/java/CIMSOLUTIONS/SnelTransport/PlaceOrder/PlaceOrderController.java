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
	
//	@RequestMapping(value="/postCustomerController", method=RequestMethod.GET)
//	@ResponseBody
//	public List<Customer> getAllCustomers(@RequestParam("customerNumber") int customerNumber, @RequestParam("companyName") String companyName, @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname){
//		
////		searchCustomerService searchCustomer = new searchCustomerService();
//		Customer customer = new Customer(customerNumber, companyName, firstname, lastname);
////		searchCustomer.lookUpCustomer(customer);
////		return searchCustomer.getResultSet();
////
////		Customer klant1 = new Customer(1, "BedrijfA", "A", "Appel");
//		Customer klant2 = new Customer(2, "BedrijfB", "B", "Banaan");
//		return asList(customer, klant2);
//	}
	
	@RequestMapping(value="/searchCustomer", method=RequestMethod.GET)
	@ResponseBody
	public List<Customer> getAllCustomers(@RequestParam(value = "customerNumber", required=false) int customerNumber, @RequestParam("companyName") String companyName, @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname){
		
		searchCustomerService searchCustomer = new searchCustomerService();
		Customer customer = new Customer(customerNumber, companyName, firstname, lastname);
		searchCustomer.createQuerry(customer);
		searchCustomer.lookUpCustomer(customer);
		return searchCustomer.getResultSet();
//
////		Customer klant1 = new Customer(1, "BedrijfA", "A", "Appel");
//		Customer klant2 = new Customer(2, "BedrijfB", "B", "Banaan");
//		return asList(customer, klant2);
	}

    @RequestMapping(value="/customer", method=RequestMethod.GET)
    @ResponseBody
    public Customer getOneCustomer(@RequestParam("id") int customerId) {
        // Search Customer by id
        return new Customer(3, "BedrijfC", "C", "Clementine");
    }
    
    @RequestMapping(value="/custom", method=RequestMethod.GET)
    @ResponseBody
    public Customer getCustomer(@RequestParam("customerNumber") int customerId) {
        // Search Customer by id
        return new Customer(3, "BedrijfC", "C", "Clementine");
    }
    
    @RequestMapping(value="/custo", method=RequestMethod.GET)
    @ResponseBody
    public Customer getCustomer(@RequestParam("companyName") String companyName) {
        // Search Customer by id
        return new Customer(3, "BedrijfC", "C", "Clementine");
    }
}
