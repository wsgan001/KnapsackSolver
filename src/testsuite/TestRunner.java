package testsuite;

import java.io.IOException;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**  TestRunner uses JUnit to execute a suite of tests that are pulled from the
 * PerformanceTest and CorrectnessTest classes.
 * 
 * @author Michael Camara
 *
 */
public class TestRunner {

	public static void main(String[] args) throws IOException {

		// Run full test suite for correctness and performance
		Result r = JUnitCore.runClasses(CorrectnessTest.class, PerformanceTest.class);

		// Indicate overall results of testing 
		System.out.println("Total tests run: " + r.getRunCount());
		System.out.println("Number of successes: " + (r.getRunCount() - r.getFailureCount()));
		System.out.println("Number of failures: " + r.getFailureCount());

		// List all failures that have been generated 
		for (Failure failure : r.getFailures()) {
			System.out.println("\t" + failure.toString());
		}
	}
}