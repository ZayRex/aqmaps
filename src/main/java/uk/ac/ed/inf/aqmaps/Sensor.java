package uk.ac.ed.inf.aqmaps;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.Point;

/**
 * 
 * represents a sensor that can be read
 *
 */
public class Sensor extends Node {

	private String location;
	private double battery;
	private String reading;
	
	public Sensor(Coordinates coords) {
		super(coords);
		
	}
	
	/**
	 * get's Sensor w3wLocation
	 * @return String W3WLocation
	 *
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * get the battery level remaining in the drone
	 * @return
	 */
	public double getBattery() {
		return battery;
	}
	
	/**
	 * returns sensor's reading
	 * @return reading
	 */
	public String getReading() {
		return reading;
	}
	
	/**
	 * gets a symbol for sensor reading visualisation
	 * @return String of the symbol
	 */
	public String getSymbol() {
		
		if(battery < 10) 
			return "cross";
		
		else {			
			var reading = Double.parseDouble(this.reading);
			
			if(reading >= 0 && reading < 128)
				return "lighthouse";
			else if(reading >= 128 && reading < 256)
				return "danger";
			else
				throw new IllegalArgumentException("The Sensor has no reading");
		}
		
	}
	/**
	 * represents the Sensor as a string
	 */
	@Override
	public String toString() {
		return "Sensor [location=" + location + ", battery=" + battery + ", reading=" + reading + "]";
	}
	
	/**
	 * checks whether the sensor is visited and converts it to MapBox feature as appropriate.
	 * @return marker feature of the sensor.
	 */
	public Feature toMapboxFeature() {
		
		var coords = Parser.parseCoords(location);
		var point = (Geometry)Point.fromLngLat(coords.getLng(), coords.getLat());
		var feature = Feature.fromGeometry(point);
		var rgbStr = "#aaaaaa"; // gray, default colour for non-visited sensors.
		
		feature.addStringProperty("marker-size", "medium");
		feature.addStringProperty("location", location);
		
		//if the sensor is visited then get its reading RGB and add symbol property
		if(getVisited()) {
			rgbStr = Util.getRGBStr(reading, battery);
			feature.addStringProperty("marker-symbol", getSymbol());
		}
		feature.addStringProperty("marker-color", rgbStr);
		feature.addStringProperty("rgb-string", rgbStr);
		
		return feature;
	}
	
	
	
}
