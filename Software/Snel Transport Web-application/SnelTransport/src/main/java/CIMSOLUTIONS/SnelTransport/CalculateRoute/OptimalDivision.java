package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.util.ArrayList;
import java.util.List;

public class OptimalDivision {
	
	private int nrOfTrucks = 0;
	private int totalTime = 0;
	private List<String> routePerTruck;
	private List<Integer> timePerTruck;
	private List<String> timeWaypointsPerTruck;
	
	void updateOptimum(List<String> newRoutePerTruck, List<Integer> newTimePerTruck, List<String> newTimeWaypointsPerTruck) {
		
		int newNrOfTrucks = newRoutePerTruck.size();
		
		int newTotalTime = 0;
		for (int time:newTimePerTruck) {
			newTotalTime += time;
		}
		
		if (newNrOfTrucks < this.nrOfTrucks || (this.nrOfTrucks == 0)) {
			this.nrOfTrucks = newNrOfTrucks;
			this.totalTime = newTotalTime;
			this.routePerTruck = new ArrayList<String>(newRoutePerTruck);
			this.timePerTruck = new ArrayList<Integer>(newTimePerTruck);
			this.timeWaypointsPerTruck = new ArrayList<String>(newTimeWaypointsPerTruck);
		} else if ((newNrOfTrucks == this.nrOfTrucks) && (totalTime < this.totalTime)){
			this.nrOfTrucks = newNrOfTrucks;
			this.totalTime = newTotalTime;
			this.routePerTruck = new ArrayList<String>(newRoutePerTruck);
			this.timePerTruck = new ArrayList<Integer>(newTimePerTruck);
			this.timeWaypointsPerTruck = new ArrayList<String>(newTimeWaypointsPerTruck);
		}
	}
	
	public void viewOptimum() {
		System.out.println("nrOfTrucks: " + nrOfTrucks);
		System.out.println("totalTime:  " +  totalTime + " seconds");
		for (int iter = 0 ; iter < nrOfTrucks ; iter++) {
			System.out.println(
					"route: " + routePerTruck.get(iter) + 
					" time: " + timePerTruck.get(iter) + 
					" waypoints: " + timeWaypointsPerTruck.get(iter));
		}
	}
	public int getNrOfTrucks() {
		return nrOfTrucks;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public List<String> getRoutePerTruck() {
		return routePerTruck;
	}

	public List<Integer> getTimePerTruck() {
		return timePerTruck;
	}

	public List<String> getTimeWaypointsPerTruck() {
		return timeWaypointsPerTruck;
	}

	
 
}
