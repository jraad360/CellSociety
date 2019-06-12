package simulations;

import cells.AgentCell;
import cells.Cell;
import cells.LangdonCell;
import cells.StateChangeCell;
import cells.SugarPatch;
import grids.Grid;
import grids.HexagonalGrid;
import grids.RectangularGrid;
import grids.TriangularGrid;

import java.util.*;

public abstract class Simulation {
    protected Grid myGrid;
    protected List<Cell> myCellList = new ArrayList<Cell>();
    protected ArrayList<Cell> myTakenSpots=new ArrayList<>();
    protected Map<String, String> myDataValues;
    protected Map<String, String> mySliderInfo;
    protected List<String> mySpecialSliders;
    protected Queue<Cell> myCellChoices;
    protected static final int ROW_DEFAULT = 36;
    protected static final int COL_DEFAULT = 36;

    /**
     * This enum contains the bounds used for all the sliders created in RunSimulation in instance variables called
     * "min" and "max"
     */
    public enum Bounds{
        rows(1, 200),
        columns(1,200),
        speed (1, 30),
        satisfaction (0, 1),
        spreadRate(0,1),
        growthRate(0,1),
        lightningRate(0,1),
        startEnergy(1,100),
        sharkReproductionMax(1,100),
        fishReproductionMax(1,100),
        energyGain(1,10),
        populatedRate(0,1),
        openRate(0,1),
        blueRate(0,1),
        redRate(0,1),
        treeRate(0,1),
        burningRate(0,1),
        fishRate(0,1),
        sharkRate(0,1),
        agentRate(0,1),
        sugarGrowthRate(1,4),
        growthInterval(1,10);

        private double min;
        private double max;

        /**
         * @param {double} min - minimum value desired for slider
         * @param {double} max - maximum value desired for slider
         */
        Bounds(double min, double max){
            this.min = min;
            this.max = max;
        }

        /**
         *Returns minimum values for requested slider.
         * @return min
         */
        public double getMin(){ return min; }

        /**
         *Returns maximum values for requested slider.
         * @return max
         */
        public double getMax(){ return max; }
    }

    /**
     * This constructor is only called when reading XML file that contains location for each individual Cell.
     * Sets the simulation's myDataValues to the incoming Map. Initializes its cell Grid according to the row and column
     * data fields in myDataValues and then fills it in according to the order of Cells in the cells maps.
     * @param dataValues - the Map which should contain information such as grid type, rows, etc.
     * @param cells - the Map which should contain all the initialized cells to put into the Grid
     */
    public Simulation(Map<String, String> dataValues, List<Cell> cells){
        myDataValues = dataValues;
        int numRows = Integer.parseInt(dataValues.get("rows"));
        int numCols = Integer.parseInt(dataValues.get("columns"));
        myGrid = createGrid(dataValues.get("gridShape"), numRows, numCols, cells);
    }

    /**
     * Sets the simulation's myDataValues to the incoming Map. Initializes its cell Grid according to the row and column
     * data fields in myDataValues and then uses the appropriate info to generate its Grid of Cells
     * @param dataValues - the Map which should contain information such as grid type, rows, etc.
     */
    public Simulation(Map<String, String> dataValues){
        myDataValues = dataValues;
        setupGrid(dataValues.get("generatorType"));
    }

    /**
     * Static method which returns a subclass of Simulation depending on the String passed in.
     * @param simType - String representing Simulation type
     * @param dataValues - the Map which should contain information such as grid type, rows, etc.
     * @param cells - the Map which should contain all the initialized cells to put into the Grid
     * @return instance of specified Simulation
     */
    public static Simulation createNewSimulation(String simType, Map<String, String> dataValues, List<Cell> cells){
        switch (simType) {
            case SegregationSimulation.DATA_TYPE:
                return new SegregationSimulation(dataValues, cells);
            case WatorWorldSimulation.DATA_TYPE:
                return new WatorWorldSimulation(dataValues, cells);
            case PercolationSimulation.DATA_TYPE:
                return new PercolationSimulation(dataValues, cells);
            case SpreadingFireSimulation.DATA_TYPE:
                return new SpreadingFireSimulation(dataValues, cells);
            case GameOfLifeSimulation.DATA_TYPE:
                return new GameOfLifeSimulation(dataValues, cells);
        }
        System.out.println("XML file contains invalid or unspecified SimulationType.");
        throw new RuntimeException("not any kind of Simulation");
    }

    /**
     * Static method which returns a subclass of Simulation depending on the String passed in.
     * @param simType - String representing Simulation type
     * @param dataValues - the Map which should contain information such as grid type, rows, etc.
     * @return instance of specified Simulation
     */
    public static Simulation createNewSimulation(String simType, Map<String, String> dataValues){
        switch (simType) {
            case SegregationSimulation.DATA_TYPE:
                return new SegregationSimulation(dataValues);
            case WatorWorldSimulation.DATA_TYPE:
                return new WatorWorldSimulation(dataValues);
            case PercolationSimulation.DATA_TYPE:
                return new PercolationSimulation(dataValues);
            case SpreadingFireSimulation.DATA_TYPE:
                return new SpreadingFireSimulation(dataValues);
            case GameOfLifeSimulation.DATA_TYPE:
                return new GameOfLifeSimulation(dataValues);
            case SugarScapeSimulation.DATA_TYPE:
                return new SugarScapeSimulation(dataValues);
            case LangdonLoopSimulation.DATA_TYPE:
                return new LangdonLoopSimulation(dataValues);
        }
        System.out.println("XML file contains invalid or unspecified SimulationType.");
        throw new RuntimeException("not any kind of Simulation");
    }

    /**
     * Returns myDataValues. The instance variable myDataValues is a map with
     * strings representing a simulation's data fields as the keys and the corresponding numbers as the values (as
     * Strings). This is used to initialize the sliders to be on the values specified within the XML files.
     * @return Map<String, String> myDataValues
     */
    public Map<String, String> getMyDataValues(){
        return myDataValues;
    }

    /**
     * Returns Map containing the data fields and corresponding values of numbers that should be changed according to
     * the sliders. This map is a subset of the information contained in myDataValues
     * @return mySliderInfo
     */
    public Map<String, String> getMySliderInfo(){
        return mySliderInfo;
    }

    /**
     * Returns List containing the data fields that, when changed by the sliders, should result in the creation of a new
     * Simulation instance to replace the current simulation in RunSimulation.
     * @return List of data fields that, when changed, should result in the replacing of the current simulation
     */
    public List<String> getMySpecialSliders(){
        return mySpecialSliders;
    }

    /**
     * Returns Grid which contains all the Cells
     * @return
     */
    public Grid getMyGrid() {
        return myGrid;
    }

    /**
     * When given a double representing the probability of an event occurring, the method randomly evaluates whether or
     * not an event should occur.
     * @param probability - double representing probability of event
     * @return boolean representing
     */
    protected boolean evaluateOdds(double probability){
        double rand = Math.random();
        return (rand <= probability);
    }

    protected Grid createGrid(String gridShape, int numRows, int numCols, List<Cell> cells){
        Grid grid;
        switch(gridShape){
            case "RectangularGrid":
                grid = new RectangularGrid(numRows, numCols, cells);
                break;
            case "TriangularGrid":
                grid = new TriangularGrid(numRows, numCols, cells);
                break;
            case "HexagonalGrid":
                grid = new HexagonalGrid(numRows, numCols, cells);
                break;
            default:
                System.out.println("XML file contains invalid or unspecified Grid Type.");
                throw new RuntimeException("No such grid type.");
        }
        return grid;
    }

    /**
     * Updates the instance variable myDataValues defined within each Simulation subclass to match the given map.
     * It also updates all the parameters within a Simulation to match the values within the given map. This is always
     * called from within carryOutApply. The map passed is always created using the values from mySliders.
     */
    public void updateParameters(){
        for(String s : myDataValues.keySet()){
            if(mySliderInfo.containsKey(s)) {
                mySliderInfo.put(s, myDataValues.get(s));
            }
        }
    }

    protected void setupSliderInfo(){
        mySliderInfo = new LinkedHashMap<>();
        mySpecialSliders = new ArrayList<>();
        mySpecialSliders.add("rows");
        mySpecialSliders.add("columns");
        mySliderInfo.put("rows", myDataValues.get("rows"));
        mySliderInfo.put("columns", myDataValues.get("columns"));
        mySliderInfo.put("speed", myDataValues.get("speed"));
    }

    protected void addSliderInfo(String field){
        if(!myDataValues.containsKey(field)){
            myDataValues.put(field, "0");
        }
        mySliderInfo.put(field, myDataValues.get(field));
        mySpecialSliders.add(field);
    }

    /**
     * Sets up grid according to specified generation type
     * @param generationType - String representing the method used to generate Grid, either by probability or quota
     */
    public void setupGrid(String generationType){
        switch(myDataValues.get("generatorType")){
            case "probability":
                myGrid = setupGridByProb();
                break;
            case "quota":
                myGrid = setupGridByQuota();
                break;
            default:
                throw new RuntimeException("No such generationType");
        }
    }

    protected List<Cell> initializeCellList(boolean randomize){
        List<Cell> list=new ArrayList<Cell>();
        for(int i = 0; i < myGrid.getHeight(); i++) { // i = row number
            for (int j = 0; j < myGrid.getWidth(); j++) { // j = column number
                list.add(myGrid.getCell(i, j));
            }
        }
        if (randomize) Collections.shuffle(list);
        return list;
    }

    /**
     * Updates and returns myGrid by updating the cell's positions according to the simulation's rules and then
     * returning the result of getNewGrid(myCellList). This should be called by the RunSimulation class once within the
     * step function.
     * @return updated myGrid
     */
    public abstract Grid advanceSimulation();

    /**
     * Creates a cyclic queue that allows for iteration that selects the next cell to place after a cell is clicked
     */
    public abstract void createQueueOfCellChoices();

    protected abstract Grid setupGridByProb();

    protected List<Cell> getTypedNeighbors(Cell cell, String type, List<Cell> neighbors) {
        List<Cell> specificNeighbors=new ArrayList<>();
        for(Cell neighbor: neighbors){
            if ((neighbor instanceof StateChangeCell) && ((StateChangeCell) neighbor).getState().equals(type)){
                specificNeighbors.add(neighbor);
            }
            else if ((neighbor instanceof LangdonCell) && ((LangdonCell) neighbor).getState().equals(type)){
                specificNeighbors.add(neighbor);
            }
        }
        return specificNeighbors;
    }

    protected Cell move(List<Cell> movable_spots, Cell current){
        Cell newLocation;
        if (movable_spots.size()==0) return current;
        else {
            Random rand = new Random();
            newLocation = movable_spots.get(rand.nextInt(movable_spots.size()));
        }
        return newLocation;
    }

    /**
     * Finds the next cell to be rendered on the grid after a click
     * @param current
     * @return next cell
     */
    public Cell getNextCell(Cell current) {
        Queue<Cell> q = myCellChoices;
        int num = q.size();
        if (num == 0) return null;
        while (num >= 0) {
            Cell candidate = q.poll();
            if ( (!(current instanceof StateChangeCell))
                    && (!(current instanceof AgentCell))
                    && (!(current instanceof SugarPatch))
                    && current.getClass().equals(candidate.getClass())) {
                q.add(candidate);
                return q.peek();
            }
            else if (current instanceof StateChangeCell && candidate instanceof StateChangeCell
                    && ((StateChangeCell) candidate).getState().equals(((StateChangeCell) current).getState())) {
                q.add(candidate);
                return q.peek();
            }
            else if (current instanceof AgentCell && candidate instanceof AgentCell
                    && ((AgentCell) candidate).getType().equals(((AgentCell) current).getType())) {
                q.add(candidate);
                return q.peek();
            }
            else if (current instanceof SugarPatch && candidate instanceof SugarPatch
                    && ((SugarPatch) current).getSugar() == ((SugarPatch) candidate).getSugar()
                    && ((SugarPatch) current).hasAgent() == ((SugarPatch) candidate).hasAgent()) {
                q.add(candidate);
                System.out.println("FOUND");
                return q.peek();
            }
            else {
                q.add(candidate);
            }
            num--;
        }
        return null;
    }

    /**
     * Returns the list containing the Cells that will be used to update the Grid
     * @return
     */
    public List<Cell> getMyCellList() {
        return myCellList;
    }

    protected abstract Grid setupGridByQuota();

    /**
     * Used when creating a new Simulation instance to replace a simulation of the same type within RunSimulation.
     * @return String representing the type of Simulation. Used in order to
     */
    public abstract String getSimType();

    protected double readInValue(String dataField, double defaultValue){
        try{
            return Double.parseDouble(myDataValues.get(dataField));
        }
        catch(NullPointerException e){
            myDataValues.put(dataField, Double.toString(defaultValue));
            System.out.printf("%s not found in XML. Using default value %f instead \n", dataField, defaultValue);
            return defaultValue;
        }
    }
}
