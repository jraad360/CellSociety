package cells;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.List;
import java.util.Map;

public class AgentCell extends Cell {
    private String myType;
    public static final Paint COLOR_AGENT_RED = Color.RED;
    public static final Paint COLOR_AGENT_BLUE = Color.BLUE;
    public static final String DATA_TYPE = "AgentCell";
//    public static final List<String> DATA_FIELDS = List.of(
//            "race");

    /**
     * Calls the superclass's constructor {@link cells.Cell#Cell(int, int, Paint)}
     * Calls superclass's constructor. Additionally sets up any other instance variables specific to cell.
     */
    public AgentCell(int row, int column, String myType){
        super(row, column, COLOR_AGENT_RED);
        this.myType = myType;
        setImage();
    }

    /**
     * This version of the constructor is used only when instantiating a cell according to a location-based XML file.
     * Calls the superclass's constructor {@link cells.Cell#Cell(int, int, Paint)}
     * Calls superclass's constructor. Additionally sets up any other instance variables specific to cell.
     */
    public AgentCell(Map<String, String> dataValues){
        super(Integer.parseInt(dataValues.get("row")), Integer.parseInt(dataValues.get("column")), COLOR_AGENT_RED);
        this.myType = dataValues.get("race");
        setImage();
    }

    private void setImage() {
        if (myType.equals("RED")) { myColor=COLOR_AGENT_RED;}
        else if (myType.equals("BLUE")) { myColor=COLOR_AGENT_BLUE;}
    }

    /**
     * @return String representing the "race" of the AgentCell
     */
    public String getType(){
        return myType;
    }

    /**
     * Calculates and returns the percentage of neighbors who are of the same race.
     * @param neighbors - List containing a list of the neighboring Cells
     * @return the percentage of its neighbors who are of the same "race"
     */
    public double calculatePercentage(List<Cell> neighbors){
        int sameType = 0;
        int differentType = 0;
        for(Cell cell : neighbors){
            if (cell instanceof AgentCell && ((AgentCell) cell).getType().equals(this.getType())) {
                sameType++;
            } else if (cell instanceof AgentCell && !((AgentCell) cell).getType().equals(this.getType())) {
                differentType++;
            }

        }
        return (double) sameType/(sameType+differentType);
    }


}
