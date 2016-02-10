# KnapsackSolver Manual

## Description
KnapsackSolver is designed to provide a heuristic solution to the 0/1 knapsack problem. This approach is specifically modeled after the "next release" problem for software developers. Given a list of requirements, each of which has a specific monetary cost and benefit, developers need to decide on which requirements to complete while staying within a certain fixed cost. KnapsackSolver allows the user to specify such a list of requirements, and it will calculate the most optimal combination of requirements that should be chosen while also staying within a specified fixed cost.

Two approaches to this problem are utilized by KnapsackSolver. The first uses a simple, greedy algorithm.  This approach is fast and requires little memory overhead, but may not yield the most optimal result for large numbers of requirements.  The second uses a more sophisticated, dynamic algorithm.  This will always yield the most optimal result, yet it requires more significant memory overhead, and will tend to run slower than the greedy algorithm.  In order to give the user the best result possible in the shortest amount of time, KnapsackSolver will automatically switch between the greedy and dynamic algorithms, depending on the size of the requirement list it is given.  If the list is small enough, it will use the dynamic algorithm to ensure the best result.  If the list is too large, it will switch to the greedy algorithm to ensure that the JVM does not exceed its maximum alloted heap size.

## Tutorial

### Compiling
KnapsackSolver is built using [Apache Ant](http://ant.apache.org).  After installing Ant, simply use the following command in the default `~/KnapsackSolver` directory to build the system:

	ant compile

### Creating An Input File
KnapsackSolver reads input from a comma-separated-value (`.csv`) file that you can either create yourself, or randomly generate through a provided method described below.

###### Randomly Generate Input
You can generate a large list of randomly generated requirements through the RequirementGenerator class.  While in the `~/KnapsackSolver` directory, use the following syntax:

	java -cp bin:lib/* util.RequirementGenerator [options]

The `[options]` section can be replaced with any of the following tags:

```
-b, --benefit
   Max value of a single benefit
   Default: 100
-c, --cost
   Max value of a single cost
   Default: 100
-f, --file
   Enter the desired file name for the generated requirements.  E.g.
   "input.csv"
   Default: input.csv
-h, --help
   Shows parameter usage information
   Default: false
-s, --size
   Size of the generated list of requirements
   Default: 10000
```

###### Create Your Own Input
Add a `.csv` to the `~/KnapsackSolver` directory, or edit the existing `input.csv` file.  This file should contain all of your requirements in the following format:
	
Line 1: A descriptive header.  E.g. "Requirements, Cost, Benefit"
	
Line 2+: Enter a new requirement on each line using the format
	
	RequirementName,IntegerCost,IntegerBenefit
	
*Note: The cost and benefit must be valid, non-negative integer values.*

### Running KnapsackSolver

1. Execution of KnapsackSolver is handled through the Menu class.  To use it, type the following in the `~/KnapsackSolver` directory:

	`java -cp bin:lib/* solver.Menu [options]`
	
2. You can fill the `[options]` section with a variety of tags (only `-f`, `--fixed`` is required)

```
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
```
	       
3. For example: 

	`java -cp bin:lib/* solver.Menu -f 2000 -v -i "requirements.csv" -o "chosen.csv"`

    This will run KnapsackSolver using a fixed cost of $2000 with verbose mode on.  The initial list of requirements will be taken from the "requirements.csv" file, and the chosen requirements will be output to the "chosen.csv" file. 

### Running The Test Suite
Type the following in the command line from the `/~KnapsackSolver` directory:
 
	`java -cp bin:lib/* testsuite.TestRunner`

Four tests for correctness and performance will be executed.  Any failures will be listed in the console output.
	
## Contributors
- Michael Camara [@michaeljcamara](https://github.com/michaeljcamara) (implemention, testing, user documentation)
- Gary Miller [@millerg2](https://github.com/millerg2) (design)
- Elizabeth Person [@e-person](https://github.com/e-person) (requirements analysis)
- Gregory Kapfhammer [@gkapfham](https://github.com/gkapfham) (professor, mentor)