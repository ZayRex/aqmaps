package uk.ac.ed.inf.aqmaps;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Polygon;

/**
 * 
 * represents a building in no fly zone
 *
 */
public class Building {
	
	private List<Coordinates> vertices;
	
	/**
	 * create building from MapBox feature
	 * @param buildingFeature
	 */
	public Building(Feature buildingFeature) {
		
		vertices = new ArrayList<Coordinates>();
		var geometry = (Polygon) buildingFeature.geometry();
		var points = geometry.coordinates().get(0);
		
		for (var i=0; i<points.size(); i++) {
			
			var point = points.get(i);
			var coords = new Coordinates(point.longitude(), point.latitude());
			
			vertices.add(coords);
		}
		
	}
	
	/**
	 * checks if the passed line intersects with any of the building's edges.
	 * @param line
	 * @return True if it intersects and false otherwise.
	 */
	public boolean isIntersecting(Line2D line) {
		
		for(var i=0; i<vertices.size()-1; i++) {
			
			var vertex1 = vertices.get(i);
			var vertex2 = vertices.get(i+1);
			var edge = new Line2D.Double(vertex1.getLng(), vertex1.getLat(),
					vertex2.getLng(), vertex2.getLat());
			
			if(edge.intersectsLine(line))
				return true;
		}
		return false;
	}
	

}
