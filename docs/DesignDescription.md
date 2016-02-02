# Team 5 Lab 6 Design

+ Gary Miller
+ Michael Camara
+ Elizabeth Person

##Class Structure
The Planner is set up by using a jar file named Planner.jar to compile the classes within the system. The system in total has 11 classes spread across 5 directories that are related to the Planner's main method, input, output, testing, and utilities. Each of the classes in the system are organized in this fashion because it is much clearer to see what its purpose is.

###Input
The input directory includes two classes:

+ JCommanderInput.java
+ PositiveIntegerValidator.java

JCommanderInput.java is a class the parses the command line arguments and checks them for correctness. If one of the requirements does not meet a correctness criteria, an error is given, otherwise the list of requirements is read from the input file and initialized into an ArrayList.

PostiveIntegerValidator.java checks to make sure that all the values given are in fact positive integers. This is important because a requirement can't have a negative cost or benefit because if, for example there was a negative benefit value, that in fact would be a cost and vice versa. If a requirement is found to have a negative value, an error will be given.

###Util
The util directory includes two classes:

+ RequirementGenerator.java
+ Timer.java

RequirementGenerator.java is a class that randomly generates a list of requirements with their associated costs and benefits.

Timer.java is class that measures the time it took to perform a certain task. This is used throughout the system to measure various performance timings.

###Planner Directory
The planner directory contains most of the main function classes including:

+ PlannerMain.java
+ Requirement.java
+ Optimzer.java
+ RequirementComparator.java

PlannerMain.java is simply the main method and makes the program actually run. It makes several method calls to the other classes and utilizes their functions. The overall flow of the program is as follows, first the system takes in or randomly generates input, next it saves the input into an ArrayList, after that the preferred optimization algorithm is chosen, then it runs the preferred algorithm which determines the optimal bundle, and finally the chosen bundle is outputted. 

Requirements.java is a class that defines a requirement object. It contains a series of "get" and "set" methods to retrieve or set the values of its parameters including:

+ name
+ cost
+ benefit
+ average cost
+ average benefit
+ actual profit
+ perceived profit

Perceived profit is a way for the system to prioritize the importance of including a requirement by scaling the profit relative to importance. This was included to make the system more realistic in situations where there may be an essential requirement to implement, but it is more beneficial to implement an optional requirement.

Optimzer.java is the class that actually runs the algorithms. It first starts by determining which algorithm to utilize, dynamic or greedy. This is done by estimating the memory usage needed to try each combination of requirements. This estimation is made by:

	expected memory usage = 4[(fixed cost +1)(number of requirements)]

This value must be multiplied by 4 because each requirement will take of 4 bytes of space. After determining the expected usage, the program finds the total memory allocated and the maximum memory that can be used. Finally a ratio is taken using the formula:

	ratio = (max memory - allocated memory - expected usage) / max memory

If this ratio is below .02 the system opts to use the greedy algorithm, otherwise the dynamic algorithm is used. The dynamic algorithm is the 0-1 approach to the knapsack problem, meaning that a requirement can only be taken or not. It then determines which bundle to select by overlapping several sub problems recursively until the maximum value is found. If the dynamic algorithm is expected to take up too much space the greedy algorithm is selected. The greedy algorithm simply sorts the requirements in the order of most profitable to least profitable. It then iterates through the list of requirements and selects the most profitable ones until the cost of requirements is equal or as close to equal as possible to the fixed cost without exceeding the fixed cost.

RequirementComparator.java is a class which sorts the most profitable requirements in the case that the greedy algorithm is selected. This simply compares each requirement by their profit ratio and sorts the list of requirements, such that the most profitable requirement in at index 0 and the least profitable requirement is at index of size-.

###Output
The output directory contains only a single class:

+ Output.java

This class is used to simply print the chosen requirements onto the console, as well as performance statistics of the system. The output statistics this class prints are:

+ Algorithm chosen
+ Number of original requirements
+ Number of profit generating requirements
+ Number of non-profit generating requirements
+ Number of optimal requirements
+ Fixed Cost
+ Total cost of chosen requirements
+ Actual Profit
+ Perceived Profit
+ Elapsed time

###Testsuite
This is a directory that tests that correctness of the system and includes three classes:

+ CorrectnessTest.java
+ PerformanceTest.java
+ myTestRunner.java

CorrectnessTest.java is a test class that checks the correctness of the algorithm implemented. This is conducted by first determining all possible combinations of requirements, identifying the mathematical maximum and comparing that to the result of executing the dynamic and greedy algorithms. The test case will fail if these two values are not equal.

PerformanceTest.java is a test class that checks the performance of the system. This test has a case for each of the algorithms implemented, dynamic and greedy. A time out duration, equal to 60 seconds, is given to mark the failure of the test case. A random list of requirements with costs and benefits is generated in both cases and the elapsed time of execution is measured. The test passes if it takes less than 60 seconds to execute, and fails if it takes 60 seconds or more to execute.

myTestRunner.java is a  main method that executes the tests cases.
