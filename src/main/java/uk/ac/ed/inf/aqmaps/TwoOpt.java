package uk.ac.ed.inf.aqmaps;

import java.util.ArrayList;

/**
 * 
 * contains mehods that related to to Two-Opt algorithm
 *
 */
public class TwoOpt {
	
	/**
	 * optimises given tour using TWO-OPT algorithm
	 * @param tour
	 */
	public static void optimise(Tour tour){
		
		System.out.println("Performing TWO-OPT optimisiation..");
		var path  = tour.getPath();
		var improve = 0;
	    var iteration = 0;
	    var swap = 0;

		// swap all possible combinations between any two swapable points.
	    // keep the swaps with improvements.
	    // keep repeating as long as you find improvements 
	    // or until reaching 1000 iterations with no improvements 
		while(iteration < 1000) {
			
			var shortestDist = tour.getCost();

			for(var i=1; i<path.size()-1; i++) {
				for(var j=i+1; j<path.size(); j++) {
					
					var possiblePath = swap(path, i, j );
	                swap++;
	                
	                var possibleTour = new Tour(possiblePath);
	                
					var newDist = possibleTour.getCost();
						
					if(newDist<shortestDist) {
						improve ++;
						shortestDist = newDist;
						path = possiblePath;
						tour.setPath(path);
						tour.setCost(shortestDist);
						iteration = 0;
						
					}
				}
			}
			iteration ++;
		}
		System.out.println("TWO-OPT swaps made: " + swap);
        System.out.println("TWO-OPT improvements made: " + improve);
        
	}
	
	/**
	 * swap two nodes in a given path
	 * @param path
	 * @param node i
	 * @param node j
	 * @return the tour after swapping nodes
	 */
	private static ArrayList<Node> swap(ArrayList<Node> path, int i, int j) {
		
		var newPath = new ArrayList<Node>();
		
		//add the nodes between start and k to the path
		for(var k=0; k<=i-1; k++) {
			newPath.add(path.get(k));
		}
		
		// reverse the nodes between i and j and add them to the path
		var dec = 0;
		for(var k=i; k<=j; k++) {
			newPath.add(path.get(j-dec));
			dec++;
		}
		
		// add the nodes between j and the end to the path
		for(var k=j+1; k<path.size(); k++) {
			newPath.add(path.get(k));
		}
		
		return newPath;
	}

}
