package RouteBerekeningTests;

import static org.junit.Assert.*;

import org.junit.Test;

import CIMSOLUTIONS.SnelTransport.CalculateRoute.DeletePrevious;

public class TestCleanDatabase {

	@Test
	public void test() {
		
		String date = "2017-10-21";
		DeletePrevious cleanUp = new DeletePrevious(date);
		fail("Not yet implemented");
	}

}
