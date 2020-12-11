package uk.ac.ed.inf.aqmaps;

/**
 * 
 * represents the Coordinates in lng lat
 *
 */
public class Coordinates {
	
	private double lng;
	private double lat;
	
	/**
	 * creates Coordinates with given lng and lat
	 * @param lng
	 * @param lat
	 */
	public Coordinates(double lng, double lat) {
		this.lat = lat;
		this.lng = lng;
	}
	
	/**
	 * gets lng
	 * @return lng
	 */
	public double getLng() {
		
		return lng;
	}
	
	/**
	 * gets lat
	 * @return lat
	 */
	public double getLat() {
		
		return lat;
	}
	
	/**
	 * gets the direction from this Coordinates to goal Coordinates
	 * @param goal
	 * @return the direction (rounded angle in degrees)
	 */
	public long directionTo(Coordinates goal) {
		
		var radiansD = Math.atan2(goal.getLat() - lat, goal.getLng() - lng);
		var degreesD = Math.round(Math.toDegrees(radiansD));
		
		if(degreesD < 0) {
			degreesD += 360;
		}
		
		return degreesD;		
	}

	@Override
	public String toString() {
		return "Coordinates [lng=" + lng + ", lat=" + lat + "]";
	}

}
