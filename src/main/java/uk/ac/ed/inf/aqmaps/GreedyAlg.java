package uk.ac.ed.inf.aqmaps;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * contains methods that uses Greedy algorithm
 *
 */
public class GreedyAlg {
	
	/**
	 * Select the best K neighbors with top K hScores in a greedy manner.
	 * @param all surrounding valid neighbors
	 * @param k
	 * @return List of the top k neighbors
	 */
	public static List<AStarNode> selectBestKNeighbors(List<AStarNode> neighbors, int kNeighbors) {
		
		var neighborsCopy = new ArrayList<AStarNode>(neighbors);
		var distances =  new ArrayList<Double>();
		var bestKNeighbors = new ArrayList<AStarNode>();
		
		

		for(var n: neighborsCopy) {
			distances.add(n.getHScore());
		}
		var k = 0;
		while(k<kNeighbors) {
			var idx = 0;
			
			//find the node with shortest distance to goal node (hScore)
			var minDistance = Double.POSITIVE_INFINITY;
			for(var i=0; i<distances.size(); i++) {
				if(distances.get(i)<minDistance) {
					minDistance = distances.get(i);
					idx = i;
				}
			}
			// add node with shortest distance to bestKNeighbors
			// and delete it with its distance from bestKNeighbors and neighborsCopy respectively.
			bestKNeighbors.add(neighborsCopy.get(idx));
			neighborsCopy.remove(idx);
			distances.remove(idx);
			k++;
		}
		return bestKNeighbors;
	}
}
