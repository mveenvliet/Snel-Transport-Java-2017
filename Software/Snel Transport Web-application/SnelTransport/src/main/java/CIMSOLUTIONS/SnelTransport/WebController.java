package CIMSOLUTIONS.SnelTransport;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {
	@RequestMapping(value="/",method = RequestMethod.GET)
    public String homepage(){
		System.out.println("homepage was called");
        return "index";
    }
	
	@RequestMapping(value="/routeBepaling")
    public String goToRouteBepaling(){
		System.out.println("routeBepaling was called");
        return "routeBepaling";
    }
	
	@RequestMapping(value="/customerManagement")
    public String goTocustomerManagement(){
		System.out.println("Klantmanagement was called");
        return "customerManagement";
    }
	
	@RequestMapping(value="/productManagement")
    public String goToProductManagement(){
		System.out.println("ProductManagement was called");
        return "ProductManagement";
    }
	
	@RequestMapping(value="/showOrders")
    public String goToshowOrders(){
		System.out.println("showOrders was called");
        return "showOrders";
    }
	
	@RequestMapping(value="/truckManagement")
    public String goTotruckManagement(){
		System.out.println("truckManagement was called");
        return "truckManagement";
    }
	
	
	
}

