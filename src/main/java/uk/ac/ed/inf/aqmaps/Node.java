package uk.ac.ed.inf.aqmaps;

/**
 * 
 * represents a node that can be used in paths and algorithms.
 *
 */
public class Node {

	private Coordinates coords;
	private boolean visited;
	
	/**
	 * creates a node using the given coordinates
	 * @param coords
	 */
	public Node(Coordinates coords) {
		this.coords = coords;
	}
	
	/**
	 * get node's coordinates
	 * @return coordinates
	 */
	public Coordinates getCoords() {
		
		return coords;
	}
	
	/**
	 * returns True if the Node is visited and No otherwise
	 * @return boolean
	 */
	public boolean getVisited() {
		
		return visited;
	}
	
	/**
	 * sets node's coordinates with the given parameter
	 * @param coords
	 */
	public void setCoords(Coordinates coords) {
		
		 this.coords = coords;
	}
	
	/**
	 * set visited value to the paramater passed
	 * @param visited
	 */
	public void setVisited(boolean visited) {
		
		this.visited = visited;
	}
	/**
	 * represent the node as a string
	 */
	@Override
	public String toString() {
		return "Node [coords=" + coords + ", visited=" + visited + "]";
	}

	/**
	 * checks equality between this node and the parameter node
	 */
	@Override
	public boolean equals(Object node) {
		
		return getCoords().getLat() == ((Node)node).getCoords().getLat()
				&& getCoords().getLng() == ((Node)node).getCoords().getLng();
	}
	
	

}
