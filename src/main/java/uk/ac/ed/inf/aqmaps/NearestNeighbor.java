package uk.ac.ed.inf.aqmaps;

import java.util.ArrayList;

/**
 * 
 * contains methods that uses Nearest Neighbor algorithm.
 *
 */
public class NearestNeighbor {
	
	/**
	 * finds a tour for TSP problem using nearest neighbor algorithm.
	 * @param initPoint
	 * @param sensors
	 * @return tour
	 */
	public static Tour findPath(Node initPoint, ArrayList<Sensor> sensors) {
		
		var tour = new Tour();
		var nodes = new ArrayList<Node>(sensors);
		tour.addPoint(initPoint);
		var currentPoint = initPoint;
		var nextPoint = nodes.get(0);
		
		while(!nodes.isEmpty()) {
			var minDistance = Double.POSITIVE_INFINITY;
			var index = -1;
			
			// find the closest node to current node
			for (var i=0; i<nodes.size(); i++) {
				var possiblePoint = nodes.get(i);
				var distance = Util.euclideanDistance(currentPoint.getCoords(), possiblePoint.getCoords());
				if (distance < minDistance) {
					minDistance = distance;
					nextPoint = possiblePoint;
					index = i;
				}
			}
			// add the closest node to tour and make it the best node
			tour.addPoint(nextPoint);
			nodes.remove(index);
			currentPoint = nextPoint;
		}
		tour.calcCost();
		
		return tour;
	}

}
