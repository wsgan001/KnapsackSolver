package solver;

import input.JCommanderInput;

import java.io.IOException;
import java.util.ArrayList;

import output.Output;
import util.Timer;

import com.beust.jcommander.JCommander;

/** Program execution begins here.  This system is meant to serve as a "next release solver," which will help
 * determine which list of requirements should be included in the next release of a system given some threshold
 * fixed cost, which the cumulative cost of the chosen requirements cannot exceed.
 * 
 * The list of requirements is read from an external file through JCommanderInput ("input.csv" by default, 
 * but this can be changed through command line arguments--see JCommanderInput for details).  Once all of the
 * requirements have been parsed, they are sent to the Optimizer class, which automatically determines the most
 * optimal algorithm to use given the memory space available.  Two algorithms are possible: a greedy algorithm,
 * which works in O(n) time and space where n = number of requirements, and a dynamic algorithm which works 
 * in O(n*k) time and space, where k is the fixed cost.  The dynamic algorithm will tend to generate a more 
 * optimal solution (i.e. a list of requirements that maximizes the amount of profit, while staying within 
 * fixed cost), but tends to use more memory overhead to do so.  The Optimizer will estimate the amount of
 * memory needed, and always choose the dynamic algorithm if there is enough space; otherwise the greedy
 * algorithm is used.
 * 
 * Once the optimal list of requirements is chosen, the list is sent to the Output class which prints it to
 * an external output file (output.csv by default) and displays it in the console window.
 *   
 * @author Michael Camara
 */
public class Menu {

	public static void main(String[] args) throws IOException {
		
		// Start timer for input
		Timer.inputStart();
		
		// Instantiate JCommander components for accepting user-input
		JCommanderInput input = new JCommanderInput();
		
		// Retrieve and interpret command line arguments
		JCommander jcommand = new JCommander(input, args);
		
		// If user has specified the "--help" tag, show help message and stop program execution
		if(input.getHelpInfo() == true) {
			jcommand.setProgramName("01KnapsackSolver");
			jcommand.usage();
			return;
		}
		
		// Store list of requirements containing cost and benefit information
		// NOTE: All requirements in this list have a profit > 0
		ArrayList<Requirement> requirements = input.getRequirements();
		
		// Store fixed cost, indicating maximum cumulative cost for chosen requirements
		int fixedCost = input.getFixedCost();
		
		// Start timer for optimal requirement selection
		Timer.selectionStart();
		
		// Determine which combination of requirements yield the maximal profit
		Optimizer optimizer = new Optimizer(requirements, fixedCost, input.getGreedyChoice());
		ArrayList<Requirement> chosenReqs = optimizer.optimize();
		
		// Start timer for output creation
		Timer.outputStart();
		
		// Create Output object for displaying results
		Output o = new Output(requirements, fixedCost, input);
		o.output(chosenReqs, optimizer.getChosenAlgorithm());
	}
}