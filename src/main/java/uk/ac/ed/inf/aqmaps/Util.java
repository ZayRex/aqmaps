package uk.ac.ed.inf.aqmaps;

/**
 * 
 * contains some utility methods that can be used anywhere in the project
 *
 */
public class Util {
	
	/**
	 * calculates the Euclidean distance between two points (sets of coordinates).
	 * @param point1
	 * @param point2
	 * @return distance
	 */
	public static Double euclideanDistance(Coordinates point1, Coordinates point2) {
		
		return Math.sqrt(Math.pow(point2.getLng() - point1.getLng(),2) + 
				Math.pow(point2.getLat() - point1.getLat(), 2));
	}
	
	/**
	 * gets RGB string given its correlated sensor reading and battery level.
	 * @param readingStr
	 * @param battery
	 * @return
	 */
	public static String getRGBStr(String readingStr, double battery) {
			
			//if the reading is invalid then return black
			if(battery < 10 || readingStr == "null" || readingStr == "NaN") 
				return "#000000";//Black
			
			else {			
				var reading = Double.parseDouble(readingStr);
				
				if(reading >= 0 && reading < 32)
					return "#00ff00";//Green
				else if(reading >= 32 && reading < 64)
					return "#40ff00";//Medium Green
				else if(reading >= 64 && reading < 96)
					return "#80ff00";//Light Gold
				else if(reading >= 96 && reading < 128)
					return "#c0ff00";//Lime Gold
				else if(reading >= 128 && reading < 160)
					return "#ffc000";//Gold
				else if(reading >= 160 && reading < 192)
					return "#ff8000";//Orange
				else if(reading >= 192 && reading < 224)
					return "#ff4000";//Red/Orange
				else if(reading >= 224 && reading < 256)
					return "#ff0000";//Red
				else
					System.out.println(" Sensor is faulty/has no reading");
					return "#000000";//Black
			}	
		}
	

}
