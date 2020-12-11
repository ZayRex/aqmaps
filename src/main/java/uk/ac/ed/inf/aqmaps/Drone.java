package uk.ac.ed.inf.aqmaps;

import java.util.ArrayList;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;

/**
 * 
 * represents the drone that will carry out the moving and reading
 *
 */
public class Drone {
	
	private Node currentNode; //the node which the drone is currently at
	private ArrayList<Coordinates> pathTravelled;
	private int charge; // the number of moves left in the drone
	private int stepsTravelled;
	private String activityLog;
	
	/**
	 * initialises the drone at the starting node initPoint
	 * @param initNode
	 */
	public Drone(Node initNode) {
		stepsTravelled = 0;
		activityLog = "";
		currentNode = initNode;
		charge = GlobalVariables.DRONE_MAX_MOVES;
		pathTravelled = new ArrayList<Coordinates>();
		pathTravelled.add(initNode.getCoords());
	}
	/**
	 * gets the nuber of steps a drone travelled 
	 * @return stepsTravelled
	 */
	public int getStepsTravelled() {
		return stepsTravelled;
	}
	
	public String getActivityLog() {
		// modify activityLog (removing the last "\n")
		if(activityLog!="") {
		return activityLog.substring(0, activityLog.length()-1);
		}
		return "";
	}
	
	/**
	 * operates the drone with the given initial optimised tour
	 * @param tour
	 * @return activity log of each move the drone did and each sensor it read.
	 */
	public void operate(Tour tour) {
		
		System.out.println("operating A* search..");
		var path = tour.getPath();
		
		// operate on a tour of all sensors
		for (var i=1; i<path.size(); i++) {
			if (charge>0)
				navigateTo(path.get(i));
			else break;
		}
		// return to starting point
		if(charge>0) {
			navigateTo(path.get(0));
		}
		System.out.println("DONE!\nsteps travelled " + stepsTravelled);				
	}
	
	/**
	 * reads the given sensor (set it as visited).
	 * @param sensor
	 */
	public void read(Node sensor) {
		sensor.setVisited(true);
	}
	
	/**
	 * moves the drone a single step to the given node.
	 * @param goal to move to.
	 */
	public void moveTo(Node goal) {
		currentNode = goal;
		pathTravelled.add(goal.getCoords());
		charge --;
		stepsTravelled ++;
		goal.setVisited(true);
	}
	
	/**
	 * operates A* search from a node which the drone is currently at to a given goal node
	 * then navigate the drone using the path resulted from A* search.
	 * @param goal
	 */
	public void navigateTo(Node goal) {
		
		var aStarPath = AStarAlg.findPath(currentNode, goal);
		
		// move the drone to each node in A* path as long as it has charge in it.
		for(var i=0; i<aStarPath.size(); i++) {
			var nextNode = aStarPath.get(i);
			var currentNode = this.currentNode;
			if(charge>0) {
				moveTo(nextNode);
				if(i == aStarPath.size()-1 && goal instanceof Sensor) {
					record(currentNode, nextNode, ((Sensor)goal).getLocation());
				}
				else {
					record(currentNode, nextNode, "null");
				}	
			}
			
		}
		// set the goal node as visited (reads the sensor if its there).
		read(goal);
	}
	
	/**
	 * records current coordinates, the direction in which the drone is heading, the next coordinates
	 * and the location of the sensor it read, if it's there.
	 * @param nextNode
	 * @param sensorLocation
	 */
	public void record(Node currentNode, Node nextNode, String sensorLocation) {
		
		var currentCoords = currentNode.getCoords();
		var nextCoords = nextNode.getCoords();
		var direction = currentCoords.directionTo(nextCoords);
		
		activityLog += stepsTravelled + "," +
				currentCoords.getLng() + "," + currentCoords.getLat() + "," +
				direction + "," + 
				nextCoords.getLng() + "," + nextCoords.getLat() + "," +
				sensorLocation + "\n";		
	}
	
	/**
	 * converts the path which the drone travelled to a MapBox feature of a lineString.
	 * @return LineString feature.
	 */
	public Feature pathToMapboxFeature() {
		
		var points = new ArrayList<Point>();
		
		for(var pos : pathTravelled) {
			
			var point = Point.fromLngLat(pos.getLng(), pos.getLat());
			points.add(point);
			
		}
		
		var line = (Geometry)LineString.fromLngLats(points);
		var feature = Feature.fromGeometry(line);
		
		return feature;
	}

}
