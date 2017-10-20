package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class CalculateRouteController {

	@RequestMapping(value="/calcRoute/date", method=RequestMethod.GET)
	@ResponseBody

		void calculateAllRoutesAtDate(@RequestParam("date") String date) {

			CalculateRouteService calculateAllRoutesAtDate = new CalculateRouteService();
			calculateAllRoutesAtDate.calc(date);
	    }
	 


}
