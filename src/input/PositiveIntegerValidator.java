package input;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/** Simple validator class to work alongside JCommander tool and JCommanderInput class.
 * Will ensure that a given value is a valid positive integer.
 * 
 * @author Michael Camara
 *
 */
public class PositiveIntegerValidator implements IParameterValidator {

	/** Ensures that the given value is a valid positive integer
	 * @param name Name of the object being tested
	 * @param value Value of the object being tested
	 */
	public void validate(String name, String value) throws ParameterException {
		Integer n = 0;
		
		// Test if value is an integer
		try {
			n = Integer.parseInt(value);
		}
		catch(NumberFormatException e) {
			throw new ParameterException("Parameter " + name + " should be a numeric value (found \"" + value + "\").");
		}
		
		// Test if the integer is positive
		if(n < 0) {
			throw new ParameterException("Parameter " + name + " should be non-negative (found \"" + value +"\").");		
		}
	}
}