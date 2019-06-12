# DESIGN

## High level design goals

To describe the high level design of our program, I will outline the flow from class to class. `Main` simply sets up the animation timeline handles the generation of multiple windows. Then, it calls `RunSimulation`, which displays the non-simulation components (such as buttons, sliders, etc.) as well as defining their mechanisms (what happens when a button is pressed). `RunSimulation` receives a new instance of a `Simulation` from `XMLParser`, which parses the XML file to create a new type of simulation. `RunSimulation` holds this `Simulation` instance and creates `Grid`, `Visualization`, and `Graph` instances based on this type of `Simulation`. Therefore,
`RunSimulation` is the "controller" class that receives the new simulation type from the parsed XML file, then manages all these other components necessary to make the simulation work.

Our goal is to utilize open/closed principles to make our code maintainable.


## Adding new features
The way we implemented most of our new features was by adding a method to the Simulation superclass and overriding it in its subclasses when necessary. For example, for the click-to-change feature, each subclass of the Simulation has its own createQueueOfCellChoices() method. While this is pretty to do, we realized it would be a better design choice to make the simulation classes more closed. It would be possible to create separate feature classes that retrieve the necessary information from the simulation classes.

### Adding new simulation

To add a new simulation to the project, the first step would be to create the appropriate XML file. Then, in the `createSimulation()` method of the `Simulation` class, we must add a new case in the switch statement that handles this new simulation, in the form of the below:

```java
    case GameOfLifeSimulation.DATA_TYPE:
        return new GameOfLifeSimulation(dataValues, cells);
```

Naturally, a subclass of `Simulation` must be made to describe the rules of this simulation. This constructor of this subclass must populate itself with the correct parameters and its corresponding values to seed this new simulation with the right starting conditions. If there are new front-end features that are required to change these parameters (beyond sliders), then it would need to be implemented separately in `RunSimulation`, which holds majority of the front-end code. Similarly, new `Cell` subclasses must be created specific to this simulation. They could either be `StateChangeCell` or simply inherit from `Cell` depending on the simulation. Sometimes, it makes more sense to implement cells as stand-alone objects, but sometimes, it makes more sense to make them change states. If this simulation requires a new type of grid, then a subclass of `Grid` must be made and its new neighbor rules specified.

In sum, we must code up the new rules, create its cells, create a new grid type (if needed), and make it possible to instantiate by making a new subclass of `Simulation`. However, because all these components are separated, we must make sure all the components are in place to render this new simulation.




## Major design choices

### Cell type

Our current design of cell types split cells into two categories. Cells are either subclasses of `Cell`, or simply a `StateChangingCell`. The former has the ability to store information, whereas the latter does not.

An alternate design that was proposed was to have all `Cell` subclasses, and not have `StateChangingCell` at all.

The pros of this alternate design:
* All cells are subclasses of `Cell`, so they are analogous
* Easier integration with rest of the code (since all these cells are effectively the same)

The cons of this alternate design:
* A lot of cells will be identical except for name
* A lot of cells do not actually store any data (besides location & color), so this would be a waste

An argument against the alternate design is that it is algorithmically more complex. Since switching a state is easier that swapping cells. However, we already have a `swapCells()` method in place, so it would not actually make a difference.

### Grid class

Our current design features a `Grid` class that keeps the grid, as well as having subclasses that define the neighbour rules.

For a while we had the `Simulation` subclasses keep the grid as a 2D array, and would communicate with `RunSimulation` by passing the 2D array back and forth. In the beginning, we thought this design was clean as there were no problems, but then we started to lose track of the 2D array as every class was keeping some form of this 2D array to themselves. But another pro for this design was that we did not have to instantiate outside of `Simulation`, since every subclass of `Simulation` just owned a grid immediately.

But, as we were implementing the extensions, it was clear that we needed a `Grid` class to mediate between `RunSimulation` and `Simulation`, and to keep the grid to itself. I strongly prefer our current design.

## Assumptions
All of our simulation types have to be initialized from one of the XML files. One major assumption that we make is that our default XML file always works. We always start the default simulation. We also check several errors such as invalid parameter values or missing values. For those, if we catch an error, we set to a default value. If any error occurs that isn't caught by these initial error-checking mechanisms, we have an outer try-catch that catches this error and stays on the current simulation.
