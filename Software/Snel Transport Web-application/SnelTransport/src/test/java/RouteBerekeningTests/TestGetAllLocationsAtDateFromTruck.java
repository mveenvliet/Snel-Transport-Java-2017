package RouteBerekeningTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import CIMSOLUTIONS.SnelTransport.CalculateRoute.CalculateRouteService;

public class TestGetAllLocationsAtDateFromTruck {

	@Test
	public void test() {
		
		CalculateRouteService Test = new CalculateRouteService();
		List<String> locations = Test.getAllLocationsAtDateFromTruck("21-10-2017","01ABC0");
		for(int iter = 0 ; iter<locations.size();iter++) {
			System.out.println(locations.get(iter));
		}
		assertTrue(locations.size() != 0);
	}

}
