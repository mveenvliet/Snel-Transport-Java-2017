package RouteBerekeningTests;

import static org.junit.Assert.*;


import org.junit.Test;

import CIMSOLUTIONS.SnelTransport.CalculateRoute.Address;
import CIMSOLUTIONS.SnelTransport.CalculateRoute.AddressList;
import CIMSOLUTIONS.SnelTransport.CalculateRoute.CorrectionMatrix;
import CIMSOLUTIONS.SnelTransport.CalculateRoute.DistanceMatrix;
import CIMSOLUTIONS.SnelTransport.CalculateRoute.DistanceSource;
import CIMSOLUTIONS.SnelTransport.CalculateRoute.SolveTSP;


public class TestCalcRouteFlow {

	@Test
	public void test() {
		
		Address source = new Address("Gouda","Zeugstraat","92","2801JD");
		System.out.println("stage past");
		Address address1 = new Address("Assen", "Koopmansplein", "1", "9401EL");
		Address address2 = new Address("Groningen", "Herestraat", "64", "9711LK");
		Address address3 = new Address("Leeuwarden", "Nieuwestad", "126", "8911DA");
		Address address4 = new Address("Amsterdam", "Haarlemmerplein", "10", "1013HS");
		 
		System.out.println("stage past");
		AddressList allAddressesInOrderList = new AddressList();
		
		System.out.println("stage past");
		allAddressesInOrderList.addAddress(address1);
		allAddressesInOrderList.addAddress(address2);
		allAddressesInOrderList.addAddress(address3);
		allAddressesInOrderList.addAddress(address4);
		System.out.println("stage past");
		DistanceSource distanceToDepo = new DistanceSource(allAddressesInOrderList,source);
		DistanceMatrix matrix = new DistanceMatrix(allAddressesInOrderList.urlOfAllLocations(),DistanceMatrix.MinimizationParameter.TIME);
		new CorrectionMatrix(allAddressesInOrderList, matrix);

		SolveTSP shortestRoute = new SolveTSP(matrix);
		if((matrix.getHeight() == 0)||(distanceToDepo.getSizeList() == 0)) {
			System.out.println("exceededKeyQuota");
		}
		
		assertTrue(shortestRoute.getRoute().equals("abcd") || shortestRoute.getRoute().equals("dcba")); 
		


	}
}
