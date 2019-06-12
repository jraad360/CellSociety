package cells;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class StateChangeCell extends Cell{
    String myState;

    public static final String DATA_TYPE = "StateChangeCell";
//    public static final List<String> DATA_FIELDS = List.of("state");

    public static final Paint COLOR_EMPTY = Color.WHITESMOKE;
    public static final Paint COLOR_POPULATED = Color.BLACK;

    public static final Paint COLOR_FULL = Color.BLUE;
    public static final Paint COLOR_CLOSED = Color.BLACK;
    public static final Paint COLOR_OPEN = Color.ANTIQUEWHITE;

    public static final Paint COLOR_TREE = Color.GREEN;
    public static final Paint COLOR_BURNING = Color.RED;

    /**
     * Calls the superclass's constructor {@link cells.Cell#Cell(int, int, Paint)}
     * Calls superclass's constructor. Additionally sets up any other instance variables specific to cell.
     */
    public StateChangeCell(int row, int column, String state) {
        super(row, column, COLOR_EMPTY);
        myState=state;
        setColor();
    }

    private void setColor() {
        Paint p;
        if (myState.equals("POPULATED")) { p = COLOR_POPULATED; }
        else if (myState.equals("FULL")) { p = COLOR_FULL;}
        else if (myState.equals("CLOSED")) { p = COLOR_CLOSED; }
        else if (myState.equals("OPEN")) { p = COLOR_OPEN; }
        else if (myState.equals("TREE")) { p = COLOR_TREE; }
        else if (myState.equals("BURNING")) { p = COLOR_BURNING; }
        else { p = COLOR_EMPTY; }
        //TODO: possibly add exception here
        myColor = p;
    }

    /**
     * This version of the constructor is used only when instantiating a cell according to a location-based XML file.
     * Calls the superclass's constructor {@link cells.Cell#Cell(int, int, Paint)}
     * Calls superclass's constructor. Additionally sets up any other instance variables specific to cell.
     */
    public StateChangeCell(Map<String, String> dataValues){
        super(Integer.parseInt(dataValues.get("row")), Integer.parseInt(dataValues.get("column")), COLOR_EMPTY);
        myState = dataValues.get("state");
        setColor();
    }

    public void setState(String state){
        myState=state;
        setColor();
    }

    public String getState(){
        return myState;
    }

}
