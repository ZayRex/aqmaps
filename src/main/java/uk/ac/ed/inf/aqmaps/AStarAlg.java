package uk.ac.ed.inf.aqmaps;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 * contains methods that uses A* algorithm
 *
 */
public class AStarAlg {
	
	/**
	 * Uses A* algorithm to find the shortest path between two points
	 * @param start
	 * @param goal
	 * @return the resulted path of nodes to visit
	 */
	public static List<Node> findPath(Node start, Node goal) {
		
	    // openSet is a priority queue of nodes to be expanded
		// closedSet is a list of nodes that were expanded
		var openSet = new PriorityQueue<AStarNode>();
		var closedSet = new ArrayList<Node>();
		var path = new ArrayList<Node>();
		
	    // create an AStar instance for the starting node
		var aStart = new AStarNode(start.getCoords(), 0, goal.getCoords());
		
		openSet.add(aStart);

	    while(!openSet.isEmpty()) {
	        
	    	//get the node with best fScore in priority queue
	    	var current = openSet.poll();
	    	// if node's hScore is less than reading range then the drone reached the sensor
	    	// and we recursively call nodes parents form the end point to construct the path
	    	// exclude the first node to make sure that the drone moves before it reads again
	        if (!current.equals(start) && current.getHScore() <= GlobalVariables.DRONE_READING_RANGE){
	            var temp = current;
	            while(temp.getParent()!=null) {
	            	path.add(0, temp);
	            	temp = temp.getParent();	
	            }
	            break;
	        }
	        //if we didn't reach yet we expand the node to its valid neighbors and add it to the closedSet
	        closedSet.add(current);    		
	        current.addNeighbors();
	        var neighbors = current.getNeighbors();
	        for(var neighbor: neighbors) {
	        	
	        	//if the neighbor node is in the closedSet then we skip it as we already expanded it.
	        	if(closedSet.contains(neighbor))
	        		continue;
	        	// calculate temporary gScore
	        	var tempG = current.getGScore() + GlobalVariables.DRONE_STEP_RANGE;
	        	var newPath = false;
	        	
	        	//if the neighbor is already in the openSet and the tempG the we got is better than its gScore
	        	// then we found a new path and we replace its gScore with tempG
        		if(openSet.contains(neighbor) && tempG<neighbor.getGScore()) { 
    				neighbor.setGScore(tempG);
    				newPath = true;			
        		}
        		// else if its not in the open set, then its a the first new path.
        		else if(!openSet.contains(neighbor)){
    				neighbor.setGScore(tempG);
    				newPath = true;
        		}
        		if(newPath) {
        			neighbor.calcFScore();
            		neighbor.setParent(current);
            		openSet.add(neighbor);
            		}
        	}    	
	    }
	    
	    return path;
	}
}
