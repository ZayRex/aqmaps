package uk.ac.ed.inf.aqmaps;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 
 * represents a specialised node to use with A* algorithm
 *
 */
public class AStarNode extends Node implements Comparable<AStarNode>{
	
	private AStarNode parentNode;
	private Coordinates goalCoords;
	private double gScore; // distance travelled
	private double hScore; // euclidean distance to goal
	private double fScore; // sum of g and h
	private List<AStarNode> neighbors;
	
	/**
	 * Creates an AStarNode given its current coordinates, number of steps from start
	 * and goal coordinates
	 * @param coords
	 * @param stepsFromStart
	 * @param goalCoords
	 */
	public AStarNode(Coordinates coords, double stepsFromStart, Coordinates goalCoords) {
		super(coords);
		this.goalCoords = goalCoords;
		//gScore = stepsFromStart * GlobalVariables.DRONE_STEP_RANGE;
		hScore = Util.euclideanDistance(coords, goalCoords);
		fScore = gScore + hScore;
	}
	
	/**
	 *  returns the fScore of the node
	 * @return fScore
	 */
	public double getFScore() {
		return fScore;
	}
	
	/**
	 * returns the gScore of the node
	 * @return gScore
	 */
	public double getGScore() {
		return gScore;
	}
	
	/**
	 * returns the hScore of the node
	 * @return hScore
	 */
	public double getHScore() {
		return hScore;
	}
	
	/**
	 * set the value of gScore with the given paramater
	 * @param gScore
	 */
	public void setGScore(double gScore) {
		this.gScore = gScore;
	}
	
	/**
	 * set the parent of the node with the given AStarNode
	 * @param node
	 */
	public void setParent(AStarNode node) {
		parentNode = node;
	}
	
	/**
	 * return the node's parent
	 * @return parentNode
	 */
	public AStarNode getParent() {
		return parentNode;
	}
	
	/**
	 * return the node's neighbors
	 * @return neighbors
	 */
	public List<AStarNode> getNeighbors(){
		return neighbors;
	}
	
	/**
	 * calculates the F score
	 */
	public void calcFScore() {
		fScore = gScore + hScore;
	}
	
	/**
	 * custom comparator for A* priority queue, compares fScores
	 */
	@Override
	public int compareTo(AStarNode node) {
		// TODO Auto-generated method stub
		var remainder = fScore - node.getFScore();
		if(remainder<0)
			return -1;
		else if(remainder>0)
			return 1;
		else
			return 0;
	}
	
	/**
	 * calculates the coordinates of node's neighbor in a given direction.
	 * @param direction
	 * @return Coordinates of the requested neighbor.
	 */
	public Coordinates calcNeighborCoords(int direction) {
		var radius = GlobalVariables.DRONE_STEP_RANGE;
		var radians = direction*(Math.PI/180);
		var lng = radius*Math.cos(radians) + getCoords().getLng();
		var lat = radius*Math.sin(radians) + getCoords().getLat();
		
		return new Coordinates(lng, lat);
	}
	
	/**
	 * adds surrounding valid neighbors which are one drone-step away and in an angle multiple of 10.
	 */
	public void addNeighbors() {
		neighbors = new ArrayList<AStarNode>();
		var directions = IntStream.range(0, 360).filter(x -> x % 10 == 0).toArray();
			for (var i=0; i<directions.length; i++){
				
				var neighborCoords = calcNeighborCoords(directions[i]);
				
				// if the neighbor is within the confinement area
				// and the path to it doesn't run into noflyzone then add it
				if(AQMap.isConfined(neighborCoords) &&
	        			!AQMap.isInNoFlyZone(getCoords(), neighborCoords)) {
					
					var neighbor = new AStarNode(neighborCoords, gScore+1, goalCoords);
					neighbors.add(neighbor);
				}
			}
			//for long distances perform a greedy selection of the 3 neighbors with top 3 hScore.
			var longDistance = 4*GlobalVariables.DRONE_STEP_RANGE;
			if(hScore >= longDistance) {
				
				neighbors = GreedyAlg.selectBestKNeighbors(neighbors, 3);
			}
	}
	
	

}
