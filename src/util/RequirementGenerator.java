package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/** This class generates a random list of requirements then creates a valid input file that
 * can be used during program execution. 
 * 
 * @author Michael Camara
 *
 */
public class RequirementGenerator {

	public static void main(String[] args) throws IOException {
		
		// Indicate the name of the input file that will be created
		String fileName = "input.csv";
		
		// Indicate the number of requirements to create
		final int NUM_REQS = 10000;
		
		// Create corresponding File and FileWriter for output
		File file = new File(fileName);
		FileWriter writer = new FileWriter(file, false);
		
		// Use a random number generator for values
		Random rand = new Random();
		
		// Create a simple, 1 line header for the file
		String header = "Requirement,Cost,Benefit";
		writer.write(header);
		
		System.out.println("Start");
		
		// Create NUM_REQS number of requirements with random positive costs and benefits
		for(int i = 0; i < NUM_REQS; i++) {	
			String name = "R" + i;
			int benefit = rand.nextInt(101);
			int cost = rand.nextInt(101);
			
			String requirement = "\n" + name + "," + cost + "," + benefit;
			writer.write(requirement);// + System.lineSeparator());
		}
				
		writer.close();
		System.out.println("Finish");
	}
}