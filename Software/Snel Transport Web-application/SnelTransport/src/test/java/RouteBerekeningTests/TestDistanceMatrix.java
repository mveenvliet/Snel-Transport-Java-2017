package RouteBerekeningTests;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import CIMSOLUTIONS.SnelTransport.CalculateRoute.DistanceMatrix;
import CIMSOLUTIONS.SnelTransport.CalculateRoute.SolveTSP;

public class TestDistanceMatrix {

	@Test
	public void test() {
		String url =
				"https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
					"Assen+Nederland|" +
					"Maastricht+Nederland|" +
					"Arnhem+Nederland|"+
					"Groningen+Nederland|" +
					"Leeuwarden+Nederland|" +
					"Utrecht+Nederland" +
				"&destinations="+
					"Assen+Nederland|"+
					"Maastricht+Nederland|" +
					"Arnhem+Nederland|"+
					"Groningen+Nederland|"+
					"Leeuwarden+Nederland|"+ 
					"Utrecht+Nederland"+
					"&mode=driving&language=en-EN&key=AIzaSyANJR6Knc1KAoc_OrdnGxrBmtehcWXr30o";
		DistanceMatrix matrix = new DistanceMatrix(url,DistanceMatrix.MinimizationParameter.TIME);
		matrix.viewMatrix();
		

		SolveTSP shortestRoute = new SolveTSP(matrix);
		//System.out.println((shortestRoute.getRoute()));
		System.out.println("Van Assen naar Groningen: "+ matrix.getElement(1, 0));
		Vector<Integer> times = shortestRoute.getTimesRoute(matrix);
		for (int iter = 0 ; iter <times.size(); iter++) {
			System.out.println(times.get(iter));
		}
		
		fail("Not yet implemented");
	}

}
