package cells;

import javafx.scene.paint.Paint;
import java.util.Map;


public class Cell {
    protected int myRow;
    protected int myColumn;
    protected Paint myColor;

    /**
     * Assigns row and column to cell. Also assigns a color that is to be used on the cell
     */
    public Cell(int row, int column, Paint c){
        myRow = row;
        myColumn = column;
        myColor = c;
    }

    /**
     * Static method that creates a new cell according to a given String
     * @param cellType - type fo cell desired
     * @param dataValues - Map containing values to be used when creating cell is read from location-based XML file.
     * @return initialized Cell
     */
    public static Cell createNewCell(String cellType, Map<String, String> dataValues){
        switch (cellType) {
            case AgentCell.DATA_TYPE :
                return new AgentCell(dataValues);
            case EmptyCell.DATA_TYPE :
                return new EmptyCell(dataValues);
            case FishCell.DATA_TYPE :
                return new FishCell(dataValues);
            case SharkCell.DATA_TYPE :
                return new SharkCell(dataValues);
            case StateChangeCell.DATA_TYPE :
                return new StateChangeCell(dataValues);
            case LangdonCell.DATA_TYPE :
                //return new LangdonCell(dataValues);
                return new SugarPatch(dataValues);
            case SugarPatch.DATA_TYPE :
                return new SugarPatch(dataValues);
        }
        throw new RuntimeException("No such kind of Cell.");
    }

    /**
     * Swaps the row and columns of the current cell with the specified cell.
     * @param cell
     */
    public void swapPosition(Cell cell){
        int tempRow = cell.myRow;
        int tempCol = cell.myColumn;
        cell.myRow = this.myRow;
        cell.myColumn = this.myColumn;
        this.myRow = tempRow;
        this.myColumn = tempCol;
    }


    public void setNegativePosition() {
        this.myRow = -1;
        this.myColumn = -1;
        return;
    }

    /**
     * Returns the row of a cell.
     * @return row index
     */
    public int getRow(){
        return myRow;
    }

    /**
     * Returns the column of a cell.
     * @return column index
     */
    public int getColumn(){
        return myColumn;
    }

    /**
     * Sets the row of the cell to the specified int
     * @param row - new row
     */
    public void setRow(int row){
        myRow=row;
    }

    /**
     * Sets the column of the cell to the specified int
     * @param col - new column
     */
    public void setColumn(int col){
        myColumn=col;
    }

    /**
     * Returns the Paint used by the cell. This is used by Visualization to set the color of the cell.
     * @return Paint
     */
    public Paint getMyColor() {
        return myColor;
    }

}
