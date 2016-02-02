package output;

import input.JCommanderInput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import planner.Requirement;
import util.Timer;

/** The Output class takes a list of Requirements and then handles how they are displayed to the user.
 * By default they are displayed in both the console and sent an output file ("output.csv").
 * 
 * @author Michael Camara
 *
 */
public class Output {

	private ArrayList<Requirement> originalReqs;  // The initial list of requirements from JCommanderInput
	private int fixedCost;						  // The fixed cost threshold
	private JCommanderInput input;				  // The object handling input
	
	/** Initialize the original requirements list, the fixed cost, and the JCommanderInput object
	 * 
	 * @param originalReqs The list of requirements parsed by JCommanderInput
	 * @param fixedCost The fixed cost threshold
	 * @param input The JCommanderInput object handling user input
	 */
	public Output(ArrayList<Requirement> originalReqs, int fixedCost, JCommanderInput input) {
		this.originalReqs = originalReqs;
		this.fixedCost = fixedCost;
		this.input = input;
	}
	
	/** This method will display selected output based on options entered by the user and parsed via
	 * JCommanderInput.  By default, all chosen requirements will be shown on the console and printed
	 * to an output file, "output.csv" unless otherwise stated.
	 * 
	 * @param chosenReqs The chosen list of profit-maximizing requirements
	 * @param chosenAlgorithm The algorithm that was selected to find chosenReqs
	 * @throws IOException if unable to create output file ("output.csv" by default)
	 */
	public void output(ArrayList<Requirement> chosenReqs, String chosenAlgorithm) throws IOException {
		
		// Create FileWriter for output file and create same header as input file
		FileWriter writer = new FileWriter(new File("output.csv"));
		String header = "Requirement,Cost,Benefit";
		writer.write(header);
		
		// Print all chosen requirements to console and to output file
		int totalPerceivedProfit = 0, totalActualProfit = 0, totalCost = 0, totalBenefit = 0;
		for(int i = 0; i < chosenReqs.size(); i++) {
			Requirement r = chosenReqs.get(i);
			
			totalPerceivedProfit += r.getPerceivedProfit();
			totalActualProfit += r.getActualProfit();
			totalCost += r.getCost();
			totalBenefit += r.getBenefit();
			
			// Print requirement to console
			System.out.println(r);
		
			// Print requirement to output file
			writer.write(System.lineSeparator() + r);
		}
		
		// Output overall statistics if verbose mode has been selected at startup
		if(input.isVerbose()) {
			System.out.println("\nAlgorithm chosen: " + chosenAlgorithm);
			System.out.println("\nNumber of original requirements: " + input.getTotalNumRequirements());
			System.out.println("Number of profit generating requirements: " + originalReqs.size());
			System.out.println("Number of non profit generating requirements: " + (input.getTotalNumRequirements() - originalReqs.size()));
			System.out.println("Number of optimal requirements chosen: " + chosenReqs.size());
			
			System.out.println("\nFixed cost = $" + fixedCost);
			System.out.println("Total cost of chosen requirements = $" + totalCost);
			System.out.println("Total benefit of chosen requirements = $" + totalBenefit);
			System.out.println("Total actual profit of chosen requirements = $" + totalActualProfit);
			System.out.println("Total perceived profit of chosen requirements = $" + totalPerceivedProfit);
		}
			
		// Print performance times if timer mode has been selected at startup
		if(input.getTimerInfo()) {
			Timer.printTimes();
		}
		
		writer.close();
	}
}
