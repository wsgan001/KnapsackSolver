package input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import planner.Requirement;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

/** JCommanderInput handles all user input via the JCommander tool using command line arguments.
 * Various optional tags are included to give the user control over the desired input and output
 * of the system.  Further, it will convert an indicated input file into a list of valid
 * Requirement objects, which will be used in all subsequent steps of the system.
 * 
 * @author Michael Camara
 *
 */
public class JCommanderInput {

	// Allow the user to specify which file will contain the list of requirements
	// NOTE: The requirements list should be formated with a header on the first line, followed by requirements
	//		 in the format: "RequirementName,IntegerCost,IntegerBenefit" separated by new lines
	@Parameter(names = {"-i", "-input"}, description = "Enter the .csv file containing the list of requirements with their associated costs and benefits.  "
			+ "The format of this file should be \"RequirementName,IntegerCost,IntegerBenefit\" with a unique requirement on each line.  "
			+ "The first line of this file is assumed to be a header and will not be read.")
	private File inputFile = new File("input.csv");
	
	// Allow the user to specify an output file
	@Parameter(names = {"-o", "-output"}, description = "Enter the desired file name where output data will be sent.  E.g. \"output.csv\"")
	private File outputFile = new File("output.csv");

	// Allow user to enter a single fixed cost (integer)
	@Parameter(names = {"-f", "-fixed"}, description = "Enter the fixed cost threshold, which should be a positive integer.", validateWith = PositiveIntegerValidator.class)
	private int fixed = 0;
	
	// Allow user to force use of the greedy algorithm regardless of input size
	@Parameter(names = {"-g", "-greedy"}, description = "Force the system to use a greedy selection algorithm instead of a dynamic one. "
			+ "Faster run time, less memory usage, but potentially poorer profit maximization")
	private boolean forceGreedy = false;
	
	// Allow user to toggle verbose mode
	@Parameter(names = {"-v", "-verbose"}, description = "Show extended statistical information")
	private boolean verbose = false;
	
	// Allow user to toggle timer
	@Parameter(names = {"-t", "-timer"}, description = "Show performance information")
	private boolean showTimer = false;
	
	// Allow user to show help menu with parameter usage information
	@Parameter(names = {"-h", "-help"}, description = "Shows parameter usage information")
	private boolean help = false;

	// Store list of Requirement objects that contain cost and benefit information
	private ArrayList<Requirement> requirements;

	// The total number of requirements identified in the input file (regardless of profit)
	private int totalNumRequirements;

	/** This method uses a scanner to parse the indicated input file.  The input file needs to be
	 * in the following format to comply with this parsing:
	 * First line: Header comments (e.g. "Requirements, Costs, Benefits"
	 * Second and subsequent lines: RequirementName,IntegerCost,IntegerBenefit
	 * 
	 * Note the first line (a header) is omitted from parsing.  Further, each line is considered its own
	 * separate requirement.  Finally, the three comma-separated-values should not have any space between
	 * them, and the cost and benefit must be valid positive integers.
	 * 
	 * After successful parsing, a Requirement object is created for each item on the list that generates
	 * a profit.  Non-profit generating requirements are excluded, as they will not help maximize profit
	 * during subsequent selection via the Optimizer class.
	 * 
	 * @return The list of Requirement objects taken from the input file that are valid and generate a profit
	 */
	public ArrayList<Requirement> getRequirements() {
		
		// Announce that requirement list is being created
		System.out.println("Creating list of requirements...");
	
		// Create initially empty list to hold parsed requirements
		requirements = new ArrayList<Requirement>();
	
		// Create scanner for reading the input file
		Scanner scan;
		try {
			scan = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			throw new ParameterException("Cannot find the indicated file in the current directory.");
		}
	
		// Do not consider the first line, which should be a descriptive header
		scan.nextLine();
	
		// Otherwise iterate through every subsequent line
		while(scan.hasNext()) {
			String reqRaw = scan.nextLine();
			String[] reqSplit = reqRaw.split(",");			
	
			// Validate each part of the requirement to ensure it is correct
			Requirement req = validateParameters(reqSplit);
	
			// Only add this requirement if it generates a profit
			if(req.getPerceivedProfit() > 0)
				requirements.add(req);
	
			// Increment the total number of requirements (regardless of profit-generating ability)
			totalNumRequirements++;
		}
	
		scan.close();
	
		return requirements;
	}

	/** Analyze each component of a requirement taken from the input file to ensure it is valid.
	 * Specifically, ensures that all three components are present (requirement name,  cost, and benefit);
	 * the cost and benefit are valid integers; and the cost and benefit are non-negative. If it is valid,
	 * then create a new requirement object with this information.
	 * 
	 * @param reqSplit An array containing string representations of the requirement name reqSplit[0],
	 * cost reqSplit[1], and benefit reqSplit[2] 
	 * @return A Requirement object containing a name and non-negative integer cost and benefit.
	 */
	public Requirement validateParameters(String[] reqSplit) {
	
		String name;			// Name of the requirement
		Integer cost, benefit;  // Non-negative cost and benefit of requirement
	
		// Ensure that three components are included in each reqSplit (name, cost, and benefit)
		if(reqSplit.length != 3) {
			System.out.println("Please ensure each requirement has three comma separated values:");
			System.out.println("RequirementName,IntegerCost,IntegerBenefit");
			System.out.println("Found: " );
			for(String s : reqSplit)
				System.out.print(s);
			throw new ParameterException("");
		}
	
		else {
			name = reqSplit[0];
			cost = 0;
			benefit = 0;
	
			// Ensure that both cost and benefit are valid integer values
			// Cost check:
			try {
				cost = Integer.parseInt(reqSplit[1]);
			}
			catch(NumberFormatException e) {
				throw new ParameterException("Cost parameter should be a numeric value (found \"" + cost + "\").");
			}
	
			// Benefit check:
			try {
				benefit = Integer.parseInt(reqSplit[2]);
			}
			catch(NumberFormatException e) {
				throw new ParameterException("Benefit parameter should be a numeric value (found \"" + benefit + "\").");
			}
	
			// Ensure that both cost and benefit are non-negative
			// Cost check:
			if(cost < 0) {
				throw new ParameterException("Cost parameter should be non-negative (found \"" + cost +"\").");		
			}
	
			// Benefit check:
			if(benefit < 0) {
				throw new ParameterException("Benefit parameter should be non-negative (found \"" + benefit +"\").");		
			} 
		}
	
		// If all steps have been passed, then return a Requirement object using these parameters
		return new Requirement(name, cost, benefit);
	}

	/** Return the fixed cost entered by the user
	 * 
	 * @return the fixed cost entered by the user
	 */
	public int getFixedCost() {
		return fixed;
	}
	
	/** Return the total number of requirements parsed (regardless of profit-generating ability)
	 * 
	 * @return total number of of requirements parsed (regardless of profit-generating ability)
	 */
	public int getTotalNumRequirements() {
		return totalNumRequirements;
	}

	/** Return whether verbose mode has been indicated
	 * 
	 * @return true if verbose mode has been indicated; false otherwise
	 */
	public boolean isVerbose() {
		return verbose;
	}
	
	/** Return whether user wants the performance data to be shown
	 * 
	 * @return true if user wants performance data to be shown; false otherwise
	 */
	public boolean getTimerInfo() {
		return showTimer;
	}
	
	/** Return whether the usage wants parameter usage information displayed
	 * 
	 * @return true if the user wants parameter usage information displayed;
	 * false otherwise
	 */
	public boolean getHelpInfo() {
		return help;
	}

	/** Return the user desired output file.
	 * @return the user desired output file
	 */
	public File getOutputFile() {
		return outputFile;
	}
	
	/** Return whether user wants to force the use of the greedy algorithm
	 * 
	 * @return true if the user wants to force the use of the greedy algorithm; false otherwise
	 */
	public boolean getGreedyChoice() {
		return forceGreedy;
	}
}