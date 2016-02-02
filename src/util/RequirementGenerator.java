package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.beust.jcommander.JCommander;

/** This class generates a random list of requirements then creates a valid input file that
 * can be used during program execution. 
 * 
 * @author Michael Camara
 *
 */
public class RequirementGenerator {

	public static void main(String[] args) throws IOException {
		
		// Instantiate JCommander components for accepting user-input
		RequirementGeneratorInput input = new RequirementGeneratorInput();

		// Retrieve and interpret command line arguments
		JCommander jcommand = new JCommander(input, args);
		
		// If user has specified the "--help" tag, show help message and stop program execution
		if(input.isHelp() == true) {
			jcommand.setProgramName("RequirementGenerator");
			jcommand.usage();
			return;
		}
		
		// Create corresponding File and FileWriter for output
		File file = input.getGeneratedFile();
		FileWriter writer = new FileWriter(file, false);
		
		// Create a simple, 1 line header for the file
		String header = "Requirement,Cost,Benefit";
		writer.write(header);
		
		// Record parameters entered by user
		int numReqs = input.getSize();
		int maxCost = input.getCost();
		int maxBenefit = input.getBenefit();
		
		// Use a random number generator for values
		Random rand = new Random();
		
		System.out.println("Start");
		
		// Create numReqs number of requirements with random positive costs and benefits
		for(int i = 0, benefit = 0, cost = 0; i < numReqs; i++) {	
			String name = "R" + i;
			benefit = rand.nextInt(maxBenefit);
			cost = rand.nextInt(maxCost);
			
			String requirement = System.lineSeparator() + name + "," + cost + "," + benefit;
			writer.write(requirement);
		}
				
		writer.close();
		System.out.println("Finish");
	}
}