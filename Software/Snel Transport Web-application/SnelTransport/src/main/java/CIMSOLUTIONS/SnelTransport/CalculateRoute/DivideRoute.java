package CIMSOLUTIONS.SnelTransport.CalculateRoute;

public class DivideRoute {

	// Add new truck
	//	route per truck
	//	time per truck
	
	// Calculate time from route
	//	account for loading time c(e) + c(n_loading)
	//	note that c(n_loading) = 30 minutes for each n_loading in the simple case
	
	void walkRoute(SolveTSP shortestRoute, DistanceMatrix matrix, AddressList addresses) {
		int timepassed = 0;
		
		String routeLeft = shortestRoute.getRoute();
		while(!routeLeft.equals("a")) {
			timepassed += matrix.getElement(routeLeft.charAt(2)-'a',routeLeft.charAt(1)-'a')
						+ addresses.getSingleAddress(routeLeft.charAt(2)-'a').getMinutesLoadTime();
			routeLeft = routeLeft.substring(1, routeLeft.length() - 1);
			
		}
		
	}
}
