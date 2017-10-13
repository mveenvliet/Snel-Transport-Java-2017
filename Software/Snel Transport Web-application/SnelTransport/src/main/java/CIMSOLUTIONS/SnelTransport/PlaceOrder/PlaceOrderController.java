package CIMSOLUTIONS.SnelTransport.PlaceOrder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PlaceOrderController {
	int customerNumber;
	String companyName;
	String firstname;
	String lastname;
	
	@RequestMapping(value="/postCustomerController", method=RequestMethod.POST)
	@ResponseBody public String postCustomer(@RequestBody Customer customer){
		
		searchCustomerService searchCustomer = new searchCustomerService();
//		List<Customer> retList= new ArrayList<Customer>();
		searchCustomer.lookUpCustomer(customer);
		System.out.println("say hello!");
		List<String> retList= new ArrayList<>();
		retList.add("test1");
		retList.add("test2");
//		retList = searchCustomer.getResultSet();
		return searchCustomer.getResultSet().get(0).getInfo();
	}

}
