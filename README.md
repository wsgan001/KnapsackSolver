# KnapsackSolver Manual

## Description
KnapsackSolver is designed to provide a heuristic solution to the 0/1 knapsack problem.  This approach is specifically modeled after the "next release" problem for software developers.  Given a list of requirements, each of which have a specific monetary cost and benefit, developers need to decide which requirements to work on while staying within a certain fixed cost budget.  KnapsackSolver allows the user to specify such a list of requirements, and it will calculate the most optimal combination of requirements that should be chosen.  The requirements chosen by KnapsackSolver should have the maximum possible profit out of any combination while also staying within a specified fixed cost.

## How to Use KnapsackSolver
1. Open a terminal window (Linux/OS X) or command prompt (Windows) in the default `KnapsackSolver` directory.
2. Type `ant compile` to build the system.  Visit the [Apache Ant](http://ant.apache.org/) website for information on downloading and installing this dependency.
3. Add a comma-separated-value file (.csv) to this directory, or edit the existing `input.csv` file.
	This file should contain all of your requirements in the following format:
	
	Line 1: A descriptive header.  E.g. "Requirements, Cost, Benefit"
	
	Line 2+: Enter a new requirement on each line using the format
	
	`RequirementName,IntegerCost,IntegerBenefit`
	
	Note: The cost and benefit must be valid, non-negative integer values.
4. To run ReleasePlanner, use the following syntax:

	`java -cp bin:lib/* Menu [options]`
	
5. You can fill the [options] section with a variety of tags (only `-f, (--fixed)` is required)

	  Usage: Menu [options]
	  Options:
	  * -f, --fixed
	       Enter the fixed cost threshold, which should be a positive integer.
	       Default: 0
	    -g, --greedy
	       Force the system to use a greedy selection algorithm instead of a dynamic
	       one. Faster run time, less memory usage, but potentially poorer profit
	       maximization
	       Default: false
	    -h, --help
	       Shows parameter usage information
	       Default: false
	    -i, --input
	       Enter the .csv file containing the list of requirements with their
	       associated costs and benefits.  The format of this file should be
	       "RequirementName,IntegerCost,IntegerBenefit" with a unique requirement on each line.  The first line of this file is
	       assumed to be a header and will not be read.
	       Default: input.csv
	    -o, --output
	       Enter the desired file name where output data will be sent.  E.g.
	       "output.csv"
	       Default: output.csv
	    -t, --timer
	       Show performance information
	       Default: false
	    -v, --verbose
	       Show extended statistical information
	       Default: false
6. For example: 

	`java -cp bin:lib/* solver.Menu -f 2000 -v -i "requirements.csv" -o "chosen.csv"`

    This will run KnapsackSolver using a fixed cost of $2000 and verbose mode on.  The initial list of requirements will be taken from the "requirements.csv" file, and the chosen requirements will be output to the "chosen.csv" file. 

## How to Run Tests
1. Open a terminal window (Linux/OS X) or command prompt (Windows) in the default `KnapsackSolver` directory.
2. Type the following in the command line:
 
	`java -cp bin:lib/* testsuite.TestRunner`
3.  Four tests for correctness and performance will be executed.  Any failures will be listed in the console output. 

## Collaborators
- Michael Camara @michaeljcamara (implemention, testing, user documentation)
- Gary Miller @millerg2 (design)
- Elizabeth Person @e-person (requirements analysis)
- Gregory Kapfhammer @gkapfham (professor, mentor)
