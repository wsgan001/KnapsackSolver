package planner;

import java.util.Comparator;

/** This comparator is used by the greedy algorithm in Optimizer to sort a list
 * of Requirement objects in descending order based on the ratio of perceived profit
 * to cost.
 * 
 * @author Michael Camara
 *
 */
public class RequirementComparator implements Comparator<Requirement> {
	
	/** Sort requirements in descending order based on the ratio of perceived profit to cost
	 * (i.e. index 0 = highest profit/cost ratio, index size-1 = lowest profit/cost ratio)
	 */
	public int compare(Requirement r1, Requirement r2) {
		
		// Get profit/cost ratio of requirement 1
		int profit1 = r1.getPerceivedProfit();
		double cost1 = r1.getCost();
		if(cost1 == 0) // Prevent divide-by-zero
			cost1 = 0.01;
		double ratio1 = profit1 / (double) cost1;

		// Get profit/cost ratio of requirement 2		
		int profit2 = r2.getPerceivedProfit();
		double cost2 = r2.getCost();
		if(cost2 == 0) // Prevent divide-by-zero
			cost2 = 0.01;
		double ratio2 = profit2 / (double) cost2;
		
		// Order list in descending order based on ratio
		if(ratio1 < ratio2)
			return 1;
		else if(ratio1 > ratio2)
			return -1;
		else
			return 0;
	}
}