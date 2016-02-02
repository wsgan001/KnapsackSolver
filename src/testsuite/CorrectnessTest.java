package testsuite;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import planner.Optimizer;
import planner.Requirement;

/** The CorrectnessTest includes two core test cases: one for the greedy algorithm used by Optimizer, and one for the
 * dynamic algorithm.  It is designed to assess the correctness of each algorithm, meaning whether or not
 * the algorithm generates a list of requirements that stays within fixed cost and also generates the maximum
 * profit possible out of all combinations of requirements.
 * 
 * A random list of requirements is first generated, and then each test yields a list of
 * chosen requirements.  These chosen requirements are then compared to the requirements chosen by the
 * useBruteForce() method, which meticulously creates every possible combination of requirements based on the
 * initial list.  This list of all combinations is iterated through to yield the combination that has the
 * highest profit while staying within fixed cost.  If the algorithm chosen by the greedy or dynamic algorithm has
 * a profit and cost equal to these values, then the test passes; otherwise it fails.
 * 
 * @author Michael Camara
 * @author stackoverflow.com user "alfasin" created the original brute force method, which Michael Camara
 * modified to fit this context.
 *
 */
public class CorrectnessTest {

	private final int numReqs = 15;		// The number of requirements to create
	private final int fixedCost = 5000; // The fixed cost threshold (costs of requirements cannot exceed)
	private int maxReqCost = 100;		// The maximum cost for a created requirement
	private int maxReqBen = 100;		// The maximum benefit for a created requirements
	private int expectedCost;			// The expected total cost for the most optimal set of requirements
	private int expectedProfit;			// The expected total cost for the most optimal set of requirements
	
	// The initial set of requirements (randomly generated)
	private ArrayList<Requirement> originalReqs;
	
	// Use Optimizer with the originalReqs to call the desired greedy or dynamic algorithm
	private Optimizer optimizer;
	
	// Contain all possible combinations of requirements
	private Map<Integer,List<LinkedList<Requirement>>> powerSet = new HashMap<Integer,List<LinkedList<Requirement>>>();
	
	// The list of requirements that yield the best profit while staying within fixedCost
	private LinkedList<Requirement> expectedChosenReqs;	

	@Before
	/** Before testing begins, create the base set of randomly generated requirements, and also create the
	 * "powerset" containing all combinations of those initial requirements, which will be used by both
	 * tests.
	 */
	public void setUp() {
		// Create a random list of requirements
		originalReqs = createRequirements();
	
		// Instantiate an optimizer using these requirements and the fixedcost threshold
		optimizer = new Optimizer(originalReqs, fixedCost, false);
	
		// Use a brute force algorithm to determine the most optimal requirements
		expectedChosenReqs = useBruteForce(originalReqs);
	
		// Tally the total actual profit and cost for these chosen requirements
		expectedProfit = 0;
		expectedCost = 0;
	
		for(Requirement r : expectedChosenReqs) {
			if(r != null) {
				expectedProfit += r.getPerceivedProfit();
				expectedCost += r.getCost();
			}
		}
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

	@Test
	/** Test whether the dynamic algorithm yields the "correct" result.  Here, "correct" indicates that
	 * the algorithm has chosen requirements that give the maximum profit possible out of all possible
	 * combinations that could be chosen while staying within the fixedCost.  To validate this, a
	 * brute force algorithm is used to generate *all* possible combinations of requirements, and then
	 * the true "best" combination of requirements is identified and compared with the combination
	 * chosen by the dynamic algorithm.
	 * 
	 * Fails if: profit from dynamic algorithm does not match profit from brute force algorithm; cost
	 * from dynamic algorithm does not match cost from brute force algorithm (which implicitly will
	 * always be less than or equal to fixedCost.
	 */
	public void testDynamicCorrectness() {

		// Use the dynamic algorithm to select the most optimal requirements
		ArrayList<Requirement> actualChosenReqs = optimizer.useDynamic();

		// Tally the total actual profit and cost for these chosen requirements
		int actualProfit = 0, actualCost = 0;
		for(Requirement r : actualChosenReqs) {
			if(r != null) {
				actualProfit += r.getPerceivedProfit();
				actualCost += r.getCost();
			}
		}

		// Assert whether the profit of requirements chosen by the dynamic algorithm
		// match the best case profit chosen by the brute-force algorithm		
		assertEquals("Profit from dynamic algorithm does not match expected profit", expectedProfit, actualProfit);

		// Assert whether the cost of requirements chosen by the dynamic algorithm
		// match the best case cost chosen by the brute-force algorithm
		assertEquals("Total cost from dynamic algorithm does not match expected cost", expectedCost, actualCost);
	}

	@Test
	/** Test whether the greedy algorithm yields the "correct" result.  Here, "correct" indicates that
	 * the algorithm has chosen requirements that give the maximum profit possible out of all possible
	 * combinations that could be chosen while staying within the fixedCost.  To validate this, a
	 * brute force algorithm is used to generate *all* possible combinations of requirements, and then
	 * the true "best" combination of requirements is identified and compared with the combination
	 * chosen by the greedy algorithm.
	 * 
	 * Fails if: profit from greedy algorithm does not match profit from brute force algorithm; cost
	 * from greedy algorithm does not match cost from brute force algorithm (which implicitly will
	 * always be less than or equal to fixedCost.
	 */
	public void testGreedyCorrectness() {

		// Use the dynamic algorithm to select the most optimal requirements
		ArrayList<Requirement> actualChosenReqs = optimizer.useGreedy();

		// Tally the total actual profit and cost for these chosen requirements
		int actualProfit = 0, actualCost = 0;
		for(Requirement r : actualChosenReqs) {
			if(r != null) {
				actualProfit += r.getPerceivedProfit();
				actualCost += r.getCost();
			}
		}

		// Assert whether the profit of requirements chosen by the greedy algorithm
		// match the best case profit chosen by the brute-force algorithm
		assertEquals("Profit from greedy algorithm does not match expected profit", expectedProfit, actualProfit);	    

		// Assert whether the cost of requirements chosen by the greedy algorithm
		// match the best case cost chosen by the brute-force algorithm
		assertEquals("Total cost from greedy algorithm does not match expected cost", expectedCost, actualCost);
	}

	/** Use a brute force algorithm, created initially by stackoverflow.com user "alfasin" and modified slightly
	 * by me (Michael Camara), to generate all possible combinations of requirements (see the makePowerSet()
	 * and collectResult() method for specifics on the algorithm).  Once all combinations have been generated,
	 * iterate through the combinations to determine the one the yields the maximum profit while having a total
	 * cost that is less than or equal to the given fixed cost.  This serves as the "expected" result to be
	 * compared with the greedy and dynamic algorithms.
	 * 
	 * **NOTE: due to the multiple recursive nature of this algorithm and large number of combinations that can
	 * be created, only relatively small requirements lists can be used within a reasonable amount of time 
	 * (a randomly selected list of 15 requirements has been hard-coded by default).  However, this technique
	 * should ensure that all possible combinations are created, which is necessary for our correctness testing.
	 * 
	 * @param reqs The list of requirements on which the algorithm is applied
	 * @return The list of all possible combinations for the original list of requirements
	 */
	private LinkedList<Requirement> useBruteForce(ArrayList<Requirement> reqs) {

		// Create powerset containing all possible combinations of requirements
		makePowerSet(reqs);

		// Store most optimal requirements (highest profit within fixedCost threshold)
		LinkedList<Requirement> expectedChosenReqs = new LinkedList<Requirement>();

		// Store max profit out of all requirements in powerset
		int maxProfit = 0;

		// Iterate through all possible combinations of requirements
		for (Integer key : powerSet.keySet()) {

			for(LinkedList<Requirement> combinationList : powerSet.get(key)) {

				int totalCost = 0;
				int totalProfit = 0;

				for(Requirement r : combinationList) {

					// Only consider non-null entries from powerset
					if(r != null) {

						// Keep running total of costs and profits for a given combination
						int cost = r.getCost();
						int profit = r.getPerceivedProfit();

						totalCost += cost;
						totalProfit += profit;
					}
				}

				//  Update the chosen requirements list if profit is highest within fixedCost threshold
				if(totalCost <= fixedCost && totalProfit > maxProfit) {
					expectedChosenReqs = combinationList;
				}
			}	  
		}

		return expectedChosenReqs;
	}

	/** First call of the recursive powerset generating method created by stackoverflow.com user "alfasin."
	 * 
	 * @param src The original list of requirements
	 * @author stackoverflow.com user "alfasin"
	 */
	private void makePowerSet(List<Requirement> src) {
		makePowerSet(new LinkedList<Requirement>(), src);
	}

	/** Recursive method created by stackoverflow.com user "alfasin" to help generate a list containing
	 * all possible combinations of some original list.  In this application, it will generate all combination
	 * of requirements from a given starting list.  See the useBruteForce() method for further details on how
	 * this implementation is used in the testing. 
	 *  
	 * @param prefix Modified list of requirements to be used during recursive calls
	 * @param src Original list of requirements
	 * @author stackoverflow.com user "alfasin"
	 */
	private void makePowerSet(LinkedList<Requirement> prefix, List<Requirement> src) {
		if (src.size() > 0) {
			prefix = new LinkedList<Requirement>(prefix); //create a copy to not modify the orig
			src = new LinkedList<Requirement>(src); //copy
			Requirement curr = src.remove(0);
			collectResult(prefix, curr);
			makePowerSet(prefix, src);
			prefix.add(curr);
			makePowerSet(prefix, src);
		}
	}

	/** Recursive method created by stackoverflow.com user "alfasin" to help generate a list containing
	 * all possible combinations of some original list.  In this application, it will generate all combination
	 * of requirements from a given starting list.  See the useBruteForce() method for further details on how
	 * this implementation is used in the testing. 
	 *  
	 * @param prefix Modified list of requirements to be used during recursive calls
	 * @param curr The current requirement to be added to each combination
	 * @author stackoverflow.com user "alfasin"
	 * 
	 */
	private void collectResult(LinkedList<Requirement> prefix, Requirement curr) {
		prefix = new LinkedList<Requirement>(prefix); //copy
		prefix.add(curr);
		List<LinkedList<Requirement>> addTo;
		if (powerSet.get(prefix.size()) == null) {
			List<LinkedList<Requirement>> newList = new LinkedList<LinkedList<Requirement>>();
			addTo = newList;
		} else {
			addTo = powerSet.get(prefix.size());
		}	    

		addTo.add(prefix);
		powerSet.put(prefix.size(), addTo);
	}
}
