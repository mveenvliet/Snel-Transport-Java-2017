package CIMSOLUTIONS.SnelTransport.CalculateRoute;


import java.util.ArrayList;
import java.util.List;

public class DivideRoute {

	// Add new truck
	//	route per truck
	//	time per truck
	
	
	private List<Integer> totalTimePerTruck = new ArrayList<>();
	private List<String> timePerTruck = new ArrayList<>();
	private List<String> routePerTruck = new ArrayList<>();
	
	
	OptimalDivision optimum = new OptimalDivision();
	private int availableTime = 28800; 
	
	void walkRoute(SolveTSP shortestRoute, DistanceMatrix matrix, AddressList addresses, DistanceSource distanceSources) {
		
		String waypoint = "";
		String route = shortestRoute.getRoute();
		int numberOfNodes = route.length() - 1;
		for (int startPosition = 0 ; startPosition < numberOfNodes ; startPosition++) {
			for (int nodeInRoute = 0 ; nodeInRoute < numberOfNodes; ) {
				String newRoute = "" + route.charAt((startPosition + nodeInRoute)%numberOfNodes);
				
				int indexCurrentAddress = route.charAt((startPosition + nodeInRoute)%numberOfNodes) - 'a';	
				int indexNextAddress = route.charAt((startPosition + nodeInRoute + 1)%numberOfNodes) - 'a';
				
				int timePassed = distanceSources.getDistanceFromSourceToPoint(indexCurrentAddress) + 
						60*addresses.getSingleAddress(indexCurrentAddress).getMinutesLoadTime();
				waypoint = "" + Integer.toString(timePassed);
				
				int timeNextStop = timePassed +  
						60*addresses.getSingleAddress(indexNextAddress).getMinutesLoadTime() +
						matrix.getElement(indexNextAddress, indexCurrentAddress);
				
				int timeNextStopCycle = timeNextStop  +
						distanceSources.getDistanceToSourceFromPoint(indexNextAddress);
				
				while(timeNextStopCycle < availableTime) {
					timePassed = timeNextStop;
					
					newRoute += route.charAt((startPosition + nodeInRoute + 1)%numberOfNodes);
					nodeInRoute += 1;
					
					indexCurrentAddress = route.charAt((startPosition + nodeInRoute)%numberOfNodes) - 'a';
					indexNextAddress = route.charAt((startPosition + nodeInRoute + 1)%numberOfNodes) - 'a';
					
					
					waypoint += "<" + Integer.toString(timePassed);
					
					timeNextStop = timePassed + 
							60*addresses.getSingleAddress(indexNextAddress).getMinutesLoadTime() +
							matrix.getElement(indexNextAddress, indexCurrentAddress);
					
					timeNextStopCycle = timeNextStop + 
							distanceSources.getDistanceToSourceFromPoint(indexNextAddress);
				
				}
				nodeInRoute += 1;
				routePerTruck.add(newRoute);	
				totalTimePerTruck.add(timePassed + distanceSources.getDistanceToSourceFromPoint(indexNextAddress));
				timePerTruck.add(waypoint + "<" + Integer.toString(distanceSources.getDistanceToSourceFromPoint(indexNextAddress)));
			}
			optimum.updateOptimum(routePerTruck, totalTimePerTruck,timePerTruck);
			routePerTruck.clear();
			totalTimePerTruck.clear();	
		}
	}
	
	void viewOptimum() {
		optimum.viewOptimum();
	}
	
	void setAvailableTime(int seconds) {
		this.availableTime = seconds;
	}


	
	
	public int getOptimalNrOfTrucks() {
		return optimum.getNrOfTrucks();
	}

	public int getOptimalTotalTime() {
		return optimum.getTotalTime();
	}

	public List<String> getOptimalRoutePerTruck() {
		return optimum.getRoutePerTruck();
	}

	public List<Integer> getOptimalTimePerTruck() {
		return optimum.getTimePerTruck();
	}

	public List<String> getOptimalTimeWaypointsPerTruck() {
		return optimum.getTimeWaypointsPerTruck();
	}

}
