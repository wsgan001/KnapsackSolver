package util;

/** Timer is a simple utility class to organize various timed events.  It stores selected times and then
 * will output the time the program spent in each broad category of processing: input, selection,
 * and output.
 *  
 * @author Michael Camara
 *
 */
public class Timer {
	
	private static long inputStartTime;			// Time when the input process began
	private static long selectionStartTime;		// Time when algorithm selection (dynamic/greedy) began
	private static long outputStartTime;		// Time when output began
	
	/** Mark the current time as the input start time
	 */
	public static void inputStart() {
		inputStartTime = System.currentTimeMillis();
	}
	
	/** Mark the current time as the selection start time
	 */
	public static void selectionStart() {
		selectionStartTime = System.currentTimeMillis();
	}
	
	/** Mark the current time as the output start time
	 */
	public static void outputStart() {
		outputStartTime = System.currentTimeMillis();
	}
	
	/** Output all performance data collected up until this point.
	 */
	public static void printTimes() {
		long currentTime = System.currentTimeMillis();
		
		System.out.println("\nInput Time = " + (selectionStartTime - inputStartTime) / 1000.0 + " seconds");
		System.out.println("Selection Time = " + (outputStartTime - selectionStartTime) / 1000.0 + " seconds");
		System.out.println("Output Time = " + (currentTime - outputStartTime) / 1000.0 + " seconds");
		System.out.println("TOTAL Program Time = " + (currentTime - inputStartTime) / 1000.0 + " seconds");
	}

}