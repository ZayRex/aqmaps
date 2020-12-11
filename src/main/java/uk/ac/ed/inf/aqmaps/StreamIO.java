package uk.ac.ed.inf.aqmaps;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * 
 * contains methods that write data to external files.
 *
 */
public class StreamIO {
	
	/**
	 * writes a string to a file
	 * @param str
	 * @param filePath
	 */
	public static void write(String str, String filePath) {
		try{
		    var writer = new BufferedWriter(new FileWriter(filePath));
		    writer.write(str);
		    writer.close();
		    
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}

