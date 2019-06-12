package grids;

import cells.Cell;
import cells.EmptyCell;
import cells.StateChangeCell;
import cells.SugarPatch;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Grid {
    private Cell[][] myCellArray;
    private int myRows;
    private int myColumns;

    /**
     * Creates a grid according to the given parameters and populates it.
     * @param rows - number or rows in the Grid
     * @param columns - number of columns in the Grid
     * @param list - list of Cells the Grid is to be populated with
     */
    public Grid(int rows, int columns, List<Cell> list){
        myCellArray = getNewArray(rows, columns, list);
        myRows=rows;
        myColumns=columns;
    }

    /**
     * Creates an empty grid.
     * @param rows - number or rows in the Grid
     * @param columns - number of columns in the Grid
     */
    public Grid(int rows, int columns){
        myCellArray = new Cell[rows][columns];
        myRows=rows;
        myColumns=columns;
    }

    /**
     * Returns list of immediate neighbors. Immediate neighbors are those who share a side with a cell.
     * Depends on which kind of Grid (triangular, rectangular, or hexagonal).
     * @param cell - cell whose neighbors are desired
     * @return list of neighbor cells
     */
    public abstract List<Cell> getImmediateNeighbors(Cell cell);

    /**
     * Returns list of all neighbors. All neighbors include immediate neighbors as well as cells who share only a vertex
     * with the cell. Depends on which kind of Grid (triangular, rectangular, or hexagonal).
     * @param cell - cell whose neighbors are desired
     * @return list of neighbor cells
     */
    public abstract List<Cell> getAllNeighbors(Cell cell);

    private Cell[][] getNewArray(int rows, int columns, List<Cell> list){
        Cell[][] newArray = new Cell[rows][columns];
        for(Cell cell : list){
            newArray[cell.getRow()][cell.getColumn()] = cell;
        }
        return newArray;
    }

    /**
     * Replaces the Cells within the Grid with those given in the parameter.
     * @param list - list of cells to be placed into the Grid
     */
    public void updateGrid(List<Cell> list){
        for(Cell cell : list){
            if (cell.getRow() < 0 || cell.getColumn() < 0) {
                //System.out.println("SWAPPING ERROR DUE TO CLICK");
                return;
            }
            myCellArray[cell.getRow()][cell.getColumn()] = cell;
        }
    }

    public List<Cell> fillWithEmpty(List<Cell> myCellList){
        for(int i = 0; i < myRows; i++) { // i = row number
            for (int j = 0; j < myColumns; j++) { // j = column number
                if (myCellArray[i][j]==null) {
                    Cell empty=new EmptyCell(i, j);
                    myCellArray[i][j]=empty;
                    myCellList.add(empty);
                }
            }
        }
        return myCellList;
    }

    public void replaceCellOnWithNew(int row, int col, Cell neww) {
        myCellArray[row][col] = neww;
//        System.out.println("row/col of old cell: " + row + "|" + col);
        //System.out.println("didnt find cell to replace");
    }

    public Cell getCell(int row, int column){
        return myCellArray[row][column];
    }

    public void setCell(Cell cell, int row, int column){
        cell.setRow(row);
        cell.setColumn(column);
        myCellArray[row][column]=cell;
    }

    public void setCell(Cell cell){
        myCellArray[cell.getRow()][cell.getColumn()]=cell;
    }

    public int getHeight(){
        return myRows;
    }

    public int getWidth(){
        return myColumns;
    }

    public Cell[][] getMyCellArray(){
        return myCellArray;
    }

    public Map<Paint, Integer> getMapOfCellCount() {
        Map<Paint, Integer> m = new HashMap<>();
        for (int i = 0; i < myCellArray.length; i++) {
            for (int j = 0; j < myCellArray[i].length; j++) {
                Paint p = myCellArray[i][j].getMyColor();
                if (m == null || ! m.containsKey(p)) {
                    m.put(p, 1);
                }
                else {
                    m.put(p, m.get(p) + 1);
                }
            }
        }
        return m;
    }
}
