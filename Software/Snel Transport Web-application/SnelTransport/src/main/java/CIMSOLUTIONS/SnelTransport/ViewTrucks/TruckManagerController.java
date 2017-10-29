package CIMSOLUTIONS.SnelTransport.ViewTrucks;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TruckManagerController {


		@RequestMapping(value = "/searchSetTruck", method = RequestMethod.GET)
		@ResponseBody
		public List<Truck> getAllTrucks(
			@RequestParam("licencePlate") String licencePlate,
			@RequestParam("chauffeur") String chauffeur,
			@RequestParam("brand") String brand,
			@RequestParam("type") String type,
			@RequestParam("owner") String owner,
			@RequestParam("availableFrom") String availableFrom,
			@RequestParam("notAvailableFrom") String notAvailableFrom){
		
			
		Truck truck = new Truck(licencePlate, chauffeur, brand, type, owner, availableFrom, notAvailableFrom);
		truck.printValue();
		searchTruckService searchTruck = new searchTruckService();	
		searchTruck.lookUpTruck(truck);
		return searchTruck.getResultSet();

	}
}
