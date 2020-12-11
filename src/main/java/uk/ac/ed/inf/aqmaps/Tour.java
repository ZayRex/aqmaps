package uk.ac.ed.inf.aqmaps;

import java.util.ArrayList;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;

/**
 * 
 * represents a high-level tour in a given map
 *
 */
public class Tour {
	private ArrayList<Node> path;
	private double cost;
	
	/**
	 * initalises a new tour with an empty path
	 */
	public Tour() {
		path = new ArrayList<Node>();
	}
	
	/**
	 * create a tour from a list of nodes
	 * @param path
	 */
	public Tour(ArrayList<Node> path) {
		this.path = path;
		cost = calcCost();
	}
	
	/**
	 * gets the tour's path
	 * @return
	 */
	public ArrayList<Node> getPath(){
		return path;
	}
	
	/**
	 * set's the path of the Tour with the given parameter
	 * @param path
	 */
	public void setPath(ArrayList<Node> path){
		this.path = path;
	}
	
	/**
	 * add a node to the tour's path
	 * @param node
	 */
	public void addPoint(Node node) {
		path.add(node);
	}
	
	/**
	 * returns the tour's cost
	 * @return cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * sets the tours cost with the given parameter
	 * @param cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	/**
	 * calculates the cost of the tour(the total distance of the tour)
	 * @return the cost
	 */
	public Double calcCost() {
		var sum = 0.0;
		for(var i=0; i<path.size()-1; i++) {
			
			sum += Util.euclideanDistance(path.get(i).getCoords(), path.get(i+1).getCoords());
		}
		cost = sum + Util.euclideanDistance(path.get(path.size()-1).getCoords(), path.get(0).getCoords());
		return cost;
	}
	
	/**
	 * converts tour's path to MapBox LineString feature.
	 * @return LineString feature;
	 */
	public Feature toMapboxFeature() {
		var points = new ArrayList<Point>();
		for(var node : path) {
			
			var point = Point.fromLngLat(node.getCoords().getLng(), node.getCoords().getLat());
			points.add(point);
			
		}
		
		var line = (Geometry)LineString.fromLngLats(points);
		
		var feature = Feature.fromGeometry(line);
		
		return feature;
	}
	
	
	
}
