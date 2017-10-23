package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateRouteController {

	@RequestMapping(value="/calcRoute/date", method=RequestMethod.GET)
	@ResponseBody

		String calculateAllRoutesAtDate(@RequestParam("date") String date) {

			CalculateRouteService calculateAllRoutesAtDate = new CalculateRouteService();
			
			return calculateAllRoutesAtDate.reCalcRoutes(date);
	}

	@RequestMapping(value="/getRouteByTruckAndDate", method=RequestMethod.GET)
	@ResponseBody
	
		List<String> getAllLocationOfTruckAtDate(@RequestParam("date") String date, @RequestParam("licencePlate") String licencePlate) {

			CalculateRouteService calculateAllRoutesAtDate = new CalculateRouteService();
			return calculateAllRoutesAtDate.getAllLocationsAtDateFromTruck(date,licencePlate);
	    }


}
