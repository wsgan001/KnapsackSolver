package solver;

/** The Requirement class represents a single requirement, consisting of a name, a non-negative
 * cost, and a non-negative benefit.  An actual profit is derived from the difference between the benefit
 * and cost, and a perceived profit is derived based on a weighting scale (see constructor for details).
 * 
 * @author Michael Camara
 *
 */
public class Requirement {

	private String name;		// The string representation of this requirement
	private int benefit;		// The monetary benefit if this requirement is chosen 
	private int cost;			// The monetary cost if this requirement is chosen
	private int actualProfit;	// The difference between benefit and cost
	private int perceivedProfit;// A weighted profit, based on user-selected symbols

	/** Initialize the name, cost, and benefit of this Requirement.  An actual profit is derived from 
	 * the difference between the benefit and cost.  Further, if the first character in the name of the 
	 * Requirement contains one of the following (!,@,#), then a "perceived" profit will be generated 
	 * based on a weighting scale.  E.g. a ! symbol will cause the perceived profit to be 1.5 times the 
	 * actual profit, while a # symbol will cause the perceived profit to be 0.75 times the actual profit.
	 * If no symbols are used, then no weighting scheme is applied and perceivedProfit = actualProfit.
	 * @param name The string representation of the requirement
	 * @param cost The monetary cost of the requirement
	 * @param benefit The monetary benefit of the requirement
	 */
	public Requirement(String name, int cost, int benefit) {
		this.name = name;
		this.cost = cost;
		this.benefit = benefit;

		// Derive the absolute profit
		actualProfit = benefit - cost;

		// Generate a weighted perceived profit if first character of name is one of the following:
		switch(name.charAt(0)) {
		case '!':
			perceivedProfit = (int) (actualProfit * 1.5);
			break;
		case '@':
			perceivedProfit = (int) (actualProfit * 1.25);
			break;
		case '#':
			perceivedProfit = (int) (actualProfit * 0.75);
			break;

			// If no symbol is detected, then no weighting is applied
		default:
			perceivedProfit = actualProfit;
			break;
		}
	}

	/** Retrieve the name of the requirement
	 * 
	 * @return the name of the requirement
	 */
	public String getName() {
		return name;
	}

	/** Retrieve the benefit of the requirement
	 * 
	 * @return the benefit of the requirement
	 */
	public int getBenefit() {
		return benefit;
	}

	/** Retrieve the cost of the requirement
	 * 
	 * @return the cost of the requirement
	 */
	public int getCost() {
		return cost;
	}

	/** Retrieve the actual profit of the requirement (benefit - cost)
	 * 
	 * @return the actual profit of the requirement (benefit - cost)
	 */
	public int getActualProfit() {
		return actualProfit;
	}

	/** Retrieve the perceived profit of the requirement.  This is a weighted value of the actual
	 * profit, based on whether the first character of the requirement name is one of the 
	 * following (!,@,#).  ! indicates a high priority and greatly multiplies the actual profit,
	 * while # indicates a low priority and mildly divides the actual profit.
	 * 
	 * @return a weighted, perceived profit of the requirement
	 */
	public int getPerceivedProfit() {
		return perceivedProfit;
	}

	/** Retrieve a formatted string representation of the requirement for output purposes
	 * 
	 * @return a formatted string representation of the requirement for output purposes
	 */
	public String toString() {
		return name + "," + cost + "," + benefit;
	}
}
