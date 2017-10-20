package RouteBerekeningTests;

import static org.junit.Assert.*;

import org.junit.Test;

import CIMSOLUTIONS.SnelTransport.CalculateRoute.CalculateRouteService;

public class TestCalcRouteFlow {

	@Test
	public void test() {
		
		 
		CalculateRouteService Test = new CalculateRouteService();
		
		Test.calc("21-10-2017");
		assertTrue(true);
	}

}
