# Requirements Analysis

## Summary:

We have been tasked with designing and implementing a Next Release Planner. It will be able to help software development teams decide which requirements from a list should be implemented and sent out in the next release of their software based off of the cost and monetary benefits associated with them. It will be implemented in Java, run through the linux terminal window, and tested in JCommander. 

## Input:

The program will accept the name of a text file filled with comma separated values. These values are the set of requirements R = {R1, …, Rn}, the set of monetary benefits associated with releasing each requirement B = {B1, …, Bn}, and the set of costs associated with implementing each requirement C = {C1, …, Cn}. 

## Behavior:

From the inputs entered into the command line, the program should be able to choose the items from the requirements set R that can be completed at no more than a total fixed cost C and maximize the total monetary profit that will be seen from the release of the next update. It should be able to accept and process thousands of requirements within a few minutes.

## Output:

A printed list of all of the requirements for the next release, the total cost vs the fixed cost, and the predicted monetary benefit. 