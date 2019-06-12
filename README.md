cell society
====

This project implements a cellular automata simulator.

Names: Jorge Raad, Michael Zhang, Daniel Kingsbury

### Timeline

Start Date: 1/26/19

Finish Date: 2/11/19

Hours Spent: 40-60 hours each

### Primary Roles
Simulation - Daniel Kingsbury

Configuration - Jorge Raad

Visualization - Michael Zhang


### Resources Used
StackOverflow
Oracle

### Running the Program

Main class: Main

Data files needed: at least on data file for each Simulation type. From there, you can use sliders to customize.

Interesting data files: we have location, probability, and quota XML configuration files for the first five simulations. For the last two, you can simply load the probability XML files and change the sliders.

Features implemented:
* All simulations except Foraging Ants and RPS.
* Implemented all 3 grid shape types.
* Allow for both immediate and non-immediate neighbors to be called according to which simulation is run.
* Allows for 3 different XML file configuration types.
* Sliders to control cell percentages and probabilities that affect simulations.
* Allows user to change grid type, display or remove grid outlines, control speed.
* Allows user to cycle through the different cell types by clicking on the cell desired to be changed.
* Displays graph of the populations of the cells in the simulation.
* Button that allows new window to open up with separate simulation. 

Assumptions or Simplifications: The default file "spreadingfire_rectangle_36x36.xml" is assumed to always work.

Known Bugs: Langton Loop crashes when it touches the edge. Simulations are pretty slow and lag with the graph. When graph is commented out, simulations begin to lag when set to top speed or when grid goes towards 50x50-100x100.

Extra credit:


### Notes
* NOTE: the graph results in significantly slower simulations. Using Ctr + F to comment out every line that has the word “graph” results in a quicker simulation.

####Document what errors you specifically check for in your README
Missing data field
* Whenever there is a missing data field, the simulations use the default value for the data field which is set at the top of the file.
Invalid Simulation type, Invalid grid type, Invalid generator type
* Each one of these results in an exception thrown as well as a printed statement indicating the root of the problem.
__
* Most of these errors would result in NullPointer Exceptions. Since all of these are down when creating a new simulation when trying to open an XML file, the process fo updating the simulation is within a try-catch in RunSimulation, so even if there is an error unaccounted for, the program would not crash since it would be caught. If the exception is caught, the current simulation is kept.

#### if you have other ideas about how to initialize the grid feel free to use that as well and document it in your README
We have several XML files that place the cells according to specified location, probabilities, and “quotas.” We did not introduce a completely new way, but we did allow for simulations to be generated according to probability straight from the sliders in the GUI.

#### there are many UI decisions to be made here, such as: do you put them all in one window or separate (you control the arrangement or does the user)? do they need to be the same simulation type (makes graphing both easier)? can they be run independently or does pause stop all running simulations? etc., — document your decisions in your README
We chose to put simulations in different windows. This seemed like the only logical choice since (1) they each have corresponding graphs and sliders which take up a lot of room and (2) Since one may want to compare simulations simultaneously, it helps to have them in different windows. Organizationally, it is also better because it keeps the different simulations apart. They are on different roots in different scenes in different stages so it is very easy to manage. They can be any simulation type and they can run independently.  This was easy to do since they were in different stages.

### Impressions
Overall, a very fun project. We were fortunate to have a cooperative team, with each member full taking care of what they were assigned. The introduction of inheritance prior to the project was ESSENTIAL. Overall, the class material did help with the issues faced within the project. We found that planning was very important, likely saving us a lot of implementation reversals in the future. We planned everything out for around 6-7 total hours before writing any code, a great decision.