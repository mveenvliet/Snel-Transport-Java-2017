package CIMSOLUTIONS.SnelTransport.ReceiveRoute;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class searchController {

	@RequestMapping(value="/routeBepaling/date", method=RequestMethod.GET)
	@ResponseBody

		public List<String> getAllDates(@RequestParam("date") String date) {

		System.out.println(date);
		searchRouteService getPlateFromDate = new searchRouteService();
		//	List<String> dates = calculateRouteService.basicFucntion(date);
	     //   return  dates;
		return getPlateFromDate.basicFunction(date);
	    }
	 


}
