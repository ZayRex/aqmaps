package uk.ac.ed.inf.aqmaps;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
/**
 * 
 * Represents air quality map, it contains methods that are related to the structure of the map
 * and buildings in no fly zone 
 *
 */
public class AQMap {
	
	private static List<Building> noFlyZone;
	
	/**
	 * builds No fly zone list of buildings
	 */
	public static void buildNoFlyZone() {
		noFlyZone = new ArrayList<Building>();
		var json = HttpClientHandler.getBuildings();
		var featureCollection = FeatureCollection.fromJson(json);
		var buildingsFeatures = featureCollection.features();
		for(var buildingFeature : buildingsFeatures) {
			noFlyZone.add(new Building(buildingFeature));
		}
		
	}
	
	/**
	 * produces a feature collection of drone travelled path
	 * @param sensors to
	 * @param drone
	 * @return Feature collection of NoFlyZone, sensors and drone path
	 */
	public static FeatureCollection toMapboxFeatureCollection(ArrayList<Sensor> sensors, Drone drone) {
		
		var features = new ArrayList<Feature>();
		
		//adding buildings features
		var json = HttpClientHandler.getBuildings();
		var featureCollection = FeatureCollection.fromJson(json);
		var buildingsFeatures = featureCollection.features();
		features.addAll(buildingsFeatures);
		
		
		//adding sensors features
		for(var i=0; i<sensors.size(); i++) {
			features.add(sensors.get(i).toMapboxFeature());
		}
		
		//adding flight path feature 
		features.add(drone.pathToMapboxFeature());
		
		return FeatureCollection.fromFeatures(features);
	}
	
	/**
	 * checks if the coordinates passed are within the confinement area
	 * @param coords
	 * @return true for inside and false for outside
	 */
	public static boolean isConfined(Coordinates coords) {
		
		var isLngConfined = coords.getLng() >= GlobalVariables.CONFINEMENT_NW_COORDS[0] &&
				coords.getLng() <= GlobalVariables.CONFINEMENT_NE_COORDS[0];
		
		var isLatConfined = coords.getLat() >= GlobalVariables.CONFINEMENT_SW_COORDS[1] &&
				coords.getLat() <= GlobalVariables.CONFINEMENT_NW_COORDS[1];
		
		return isLatConfined && isLngConfined;
	}
	
	/**
	 * checks whether the path between two points runs into NoflyZone
	 * @param p1
	 * @param p2
	 * @return true if it runs into any of the building and false otherwise
	 */
	public static boolean isInNoFlyZone(Coordinates p1, Coordinates p2) {
		
		var line = new Line2D.Double(p1.getLng(), p1.getLat(), 
				p2.getLng(), p2.getLat());
		for(var building : noFlyZone) {
			
			if(building.isIntersecting(line))
				return true;
			
		}
		return false;
	}

}
