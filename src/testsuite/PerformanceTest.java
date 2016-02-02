package testsuite;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import planner.Optimizer;
import planner.Requirement;

/** The PerformanceTest class contains two test cases: one for the greedy algorithm and one for the
 * dynamic algorithm.  A random list of requirements is first created, and then an Optimizer object
 * is used to run each algorithm separately.  The tests pass if they finish within a specified timeout
 * period (by default timeoutDuration = 60000, i.e. 60 seconds)
 * 
 * @author Michael Camara
 *
 */
public class PerformanceTest {

	private ArrayList<Requirement> reqs;	// List of randomly generated requirements
	private Optimizer optimizer;			// Optimizer for selecting algorithm
	
	private final int numReqs = 10000;		// The number of requirements to create
	private final int fixedCost = 10000;	// The fixed cost threshold (costs of requirements cannot exceed)
	private final int maxReqCost = 100;		// The maximum cost for a created requirement
	private final int maxReqBen = 100;		// The maximum benefit for a created requirements
	
	private final long timeoutDuration = 60000; // Allotted amount of time per test
	
	@Before
	/** Before testing begins, create the initial list of randomly generated requirements, and an
	 * optimizer using this list and the specified fixed cost. 
	 */
	public void setUp() {
		reqs = createRequirements();
		optimizer = new Optimizer(reqs, fixedCost, false);
	}
	
	/** Create a randomly generated list of requirements based on the numReqs, maxReqBen, and maxReqCost
	 * instance variables.  Note that all requirements will have a positive profit to ensure compatibility
	 * with the greedy and dynamic algorithms, which are only passed such requirements during actual
	 * runs of the program (since non-profit generating requirements are not allowed by JCommanderInput
	 * during initial parsing).  However, this should not affect the accuracy of the testing conducted here.
	 * 
	 * @return A randomly generated list of requirements
	 */
	private ArrayList<Requirement> createRequirements() {

		ArrayList<Requirement> requirements = new ArrayList<Requirement>();

		Random rand = new Random();

		for(int i = 0; i < numReqs; i++) {

			// Ensure that profit is always positive
			// NOTE: this is needed to ensure proper functioning of the algorithms, which
			// always handle positive profit requirements due to preliminary parsing by
			// JCommanderInput
			int ben = rand.nextInt(maxReqBen + 1) + maxReqCost;
			int cost = rand.nextInt(maxReqCost );
			String name = "R" + i;

			Requirement r = new Requirement(name, cost, ben);
			
			requirements.add(r);
		}

		return requirements;
	}

	@Test(timeout=timeoutDuration)  // Set timeout of 60 seconds (given speed requirement)
	/** This method tests the execution time of the greedy algorithm.  A timeout annotation
	 * is used to provide a maximum amount of time the algorithm is allowed to run for.
	 * Fails if the algorithm exceeds this timeout duration.  Passes otherwise.
	 */
	public void testGreedyPerformance() {		
		ArrayList<Requirement> chosenReqs = optimizer.useGreedy();
	}
	
	/** This method tests the execution time of the greedy algorithm.  A timeout annotation
	 * is used to provide a maximum amount of time the algorithm is allowed to run for.
	 * Fails if the algorithm exceeds this timeout duration.  Passes otherwise.
	 */
	@Test(timeout=timeoutDuration)  // Set timeout of 60 seconds (given speed requirement)
	public void testDynamicPerformance() {		
		ArrayList<Requirement> chosenReqs = optimizer.useDynamic();
	}
}