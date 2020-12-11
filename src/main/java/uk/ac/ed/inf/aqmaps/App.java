package uk.ac.ed.inf.aqmaps;
/**
 * main class, controls the high-level implementation flow
 *
 */
public class App 
{
	static double startTime = System.nanoTime();
	
    public static void main( String[] args )
    {
    	    
    	
    	//labelling arguments
        var day = args[0];
        var month = args[1];
        var year = args[2];
        var lat = Double.parseDouble(args[3]);
        var lng = Double.parseDouble(args[4]);
        var port = args[6];
        
        var initPoint = new Node(new Coordinates(lng, lat));
        
        //set the network port to connect to webserver
        HttpClientHandler.setPort(port);
        
        //get json map of sensors for the specified date
        var json = HttpClientHandler.getSensors(year, month, day);
        
        //parse sensors from json
        var sensors = Parser.parseSensors(json);
        
        
        var tour = new Tour();
        tour.addPoint(initPoint);

        for(var sensor : sensors) {
        	
        	tour.addPoint(sensor);
        }
        
        tour.calcCost();
        System.out.println("Initial tour cost: " + tour.getCost());
        
        //optimse tour with TwoOpt
        TwoOpt.optimise(tour);
        System.out.println("TWO-OPT optimised tour cost: " + tour.getCost());
        
        AQMap.buildNoFlyZone();
        
        //initialise drone and operate it on the optimised tour
        var drone = new Drone(initPoint);
        drone.operate(tour);
        var activityLog = drone.getActivityLog();
        var geojson = AQMap.toMapboxFeatureCollection(sensors, drone).toJson();
        
        //produce results
        StreamIO.write(activityLog, "flightpath-"+day+"-"+month+"-"+year+".txt");
        StreamIO.write(geojson, "readings-"+day+"-"+month+"-"+year+".geojson");
        
    }
}
