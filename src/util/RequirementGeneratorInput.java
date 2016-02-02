package util;

import java.io.File;

import com.beust.jcommander.Parameter;

/** This class utilizes JCommander to handle command line input
 * specifically for the RequirementGenerator class.
 * 
 * @author Michael Camara
 *
 */
public class RequirementGeneratorInput {
	
	// Allow the user to specify an output file containing a randomly generated list of requirements
	@Parameter(names = {"-f", "--file"}, description = "Enter the desired file name for the generated requirements.  E.g. \"input.csv\"")
	private File generatedFile = new File("input.csv");
	
	// Specify the size of the created file (i.e. the number of requirements)
	@Parameter(names = {"-s", "--size"}, description = "Size of the generated list of requirements")
	private int size = 10000;
	
	// Specify the max value for a single benefit (randomly chosen between 0 and that number)
	@Parameter(names = {"-b", "--benefit"}, description = "Max value of a single benefit")
	private int benefit = 100;
	
	// Specify the max value for a single cost (randomly chosen between 0 and that number)
	@Parameter(names = {"-c", "--cost"}, description = "Max value of a single cost")
	private int cost = 100;
	
	// Allow user to show help menu with parameter usage information
	@Parameter(names = {"-h", "--help"}, description = "Shows parameter usage information", help = true)
	private boolean help = false;

	/**
	 * @return the generatedFile
	 */
	public File getGeneratedFile() {
		return generatedFile;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the benefit
	 */
	public int getBenefit() {
		return benefit;
	}

	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @return true if help is selected, false otherwise
	 */
	public boolean isHelp() {
		return help;
	}
	
	
}