package uk.ac.ed.inf.aqmaps;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.ConnectException;

/**
 * 
 * contains all the methods that are related to Connecting to http server
 *
 */
public class HttpClientHandler {

	private static final HttpClient client = HttpClient.newHttpClient();
	private static String port;
	
	/**
	 * sets the port value with the given parameter
	 * @param p
	 */
	public static void setPort(String p) {
		port = p;
	}
	/**
	 * sends http request to webserver with the specified url.
	 * @param urlString
	 * @return json String of response's body
	 */
	public static String getFile(String urlString) {
		
		var server = "Directory Listing";	
		var request = HttpRequest.newBuilder().uri(URI.create(urlString)).build();
		HttpResponse<String> response = null;
		try {
			response = client.send(request, BodyHandlers.ofString());

		} catch (ConnectException e) {
			System.out.println("Fatal error: Unable to connect to "+server+" at port "+port+".");
			System.exit(1);
		}catch (IOException e) {
			System.out.println("IOException error: Unable to connect to "+server+" at port "+port+".");
			e.printStackTrace();
			System.exit(1);
		} catch (InterruptedException e) {
			System.out.println("The Request was interrupted");
			e.printStackTrace();
		}
		return response.body();
		
	}
	
	/**
	 * constructs a url to request a map of sensors with the provided date
	 * calls getFile() to get the response body
	 * @param year
	 * @param month
	 * @param day
	 * @return json String with response's body
	 */
	public static String getSensors(String year, String month, String day) {
		
		var urlString = "http://localhost:"+port+"/maps/"+year+"/"+month+"/"+day+"/air-quality-data.json";
		return getFile(urlString);
		
	}
	
	/**
	 * constructs a url to request No fly zone buildings
	 * calls getFile() to get the response body
	 * @return json String with response's body
	 */
	public static String getBuildings() {
		
		var urlString = "http://localhost:"+port+"/buildings/no-fly-zones.geojson";
		return getFile(urlString);
	}
	
	/**
	 * constructs a url to request correlated Coordinates of a W3W
	 * calls getFile() to get the response body
	 * @param word1
	 * @param word2
	 * @param word3
	 * @return json String of the response's body
	 */
	public static String getW3WDetails(String word1, String word2, String word3) {
		
		var urlString = "http://localhost:"+port+"/words/"+word1+"/"+word2+"/"+word3+"/details.json";
		return getFile(urlString);
	}
	
}
