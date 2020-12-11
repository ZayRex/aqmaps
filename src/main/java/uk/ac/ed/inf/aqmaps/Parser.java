package uk.ac.ed.inf.aqmaps;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * 
 * contains methods that are related to parsing data form json files.
 *
 */
public class Parser {
	
	/**
	 * parse sensors from json String
	 * @param json
	 * @return List of sensors
	 */
	public static ArrayList<Sensor>  parseSensors(String json) {
		Type listType = new TypeToken<ArrayList<Sensor>>() {}.getType();
		
		ArrayList<Sensor> sensors = new Gson().fromJson(json, listType);
		
		for(var sensor : sensors) {
			sensor.setCoords(parseCoords(sensor.getLocation()));
		}
		
		return sensors;
		
		
	}
	
	/**
	 * parse Coordinates from W3W json String
	 * @param words
	 * @return w3w correlated Coordinates
	 */
	public static Coordinates parseCoords(String words) {
		
		var splitWords = words.split("[.]");
		var json = HttpClientHandler.getW3WDetails(splitWords[0], splitWords[1], splitWords[2]);
		
		var w3w = new Gson().fromJson(json, W3WLocation.class);
		return w3w.getCoords();
		
	}
	

}
