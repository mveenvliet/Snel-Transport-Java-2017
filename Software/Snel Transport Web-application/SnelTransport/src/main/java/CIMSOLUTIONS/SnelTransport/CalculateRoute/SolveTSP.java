package CIMSOLUTIONS.SnelTransport.CalculateRoute;

import java.util.Vector;

public class SolveTSP {

	/*
	 * This bruteforces the Metric Traveling Salesman Problem. The adjencency matrix
	 * is not symmetric. First make the initial route a b c ... a, then test all others and
	 * compare the dur Later optimization ideas: change 2D matrix to 1D array
	 * (reduces the number of loops)
	 */

	private int shortestDistance;
	private String route = "";
	private int iterCounter = 0;//(nrOfNodes - 1)!

	void ComputeBFmTSP(DistanceMatrix matrix) {
		
		this.iterCounter = fact(matrix.getHeight() - 1);
		recursivemTSP(matrix, "", route);
		route = route + 'a';
	}

	private int fact(int n) {
		if (n == 0 || n == 1) {
			return 1;
		} else {
			return fact(n - 1) * n;
		}
	}
	
	private void recursivemTSP(DistanceMatrix matrix, String firstHalve, String secondHalve) {

		int nrNodesSecondHalve = secondHalve.length();

		if (iterCounter != 0) {
			if (nrNodesSecondHalve == 0) {
				iterCounter -= 1;
				int distanceCycle = distanceRoute(matrix, firstHalve, matrix.getWidth()) + distanceRoute(matrix,
						firstHalve.substring(matrix.getWidth() - 1, matrix.getWidth()) + firstHalve.substring(0, 1), 2);
				if (distanceCycle < shortestDistance) {
					route = firstHalve;
					shortestDistance = distanceCycle;
				}

			} else {
				for (int nodeSecondHalve = 0; nodeSecondHalve < nrNodesSecondHalve; nodeSecondHalve++) {
					recursivemTSP(matrix, firstHalve + secondHalve.charAt(nodeSecondHalve),
							secondHalve.substring(0, nodeSecondHalve)
									+ secondHalve.substring(nodeSecondHalve + 1, nrNodesSecondHalve));
				}
			}
		}

	}

	int distanceRoute(DistanceMatrix matrix, String route, int nrOfNodes) {

		// Distance route =/= distance cycle!
		int sum = 0;
		char src = route.charAt(0);
		char dest;

		for (int node = 1; node < nrOfNodes; node++) {
			dest = route.charAt(node);

			sum += matrix.getElement(src - 'a', dest - 'a');
			src = dest;

		}
		return sum;

	}

	public SolveTSP(DistanceMatrix matrix){		
		for (int yIter = 0 ; yIter < matrix.getHeight() ; yIter++){
			for (int xIter = 0 ; xIter < matrix.getWidth() ; xIter++){
				if(xIter == yIter) {
					if(matrix.getElement(xIter, yIter) != 0) {
						throw new IllegalArgumentException("The distance from A->A should be zero");
					}
				} else if(matrix.getElement(xIter, yIter) == 0) {
					throw new IllegalArgumentException("The distance from A->B cant be zero, unless A = B");
				}
			}
		}
		
		for(char xIter = 'a'; xIter < 'a' + matrix.getWidth() ; xIter++) {				
			route = route + xIter;
		}
		String wayBack 		= (char)(matrix.getWidth() + 'a' - 1) + "a";
		shortestDistance 	= distanceRoute(matrix, route,matrix.getWidth()) + distanceRoute(matrix, wayBack,2);
		
		ComputeBFmTSP(matrix);
		
	}

	public String getRoute() {
		return route;
	}

	public int getShortestDistance() {
		return shortestDistance;
	}

	public Vector<Integer> getTimesRoute(DistanceMatrix matrix){
		Vector<Integer> times = new Vector<>();
		
		
		for(int iter = 1; iter <route.length() ; iter++) {
			char src = route.charAt(iter-1);
			char dst = route.charAt(iter);
			times.add(matrix.getElement(dst - 'a', src - 'a'));
		}
		
		return times;
	}
}
