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
	
	@RequestMapping(value="/RouteBepaling")
    public String goToRouteBepaling(){
		System.out.println("function called");
        return "RouteBepaling";
    }
}

