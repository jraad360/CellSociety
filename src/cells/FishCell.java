package cells;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.Map;

public class FishCell extends Cell {

    public static final Paint COLOR_FISH = Color.ORANGE;
    public static final String DATA_TYPE = "FishCell";
//    public static final List<String> DATA_FIELDS = List.of("reproductionTime");
    private int myTurnsSurvived;
    private int myReproductionTime;

    /**
     * Calls the superclass's constructor {@link cells.Cell#Cell(int, int, Paint)}
     * Calls superclass's constructor. Additionally sets up any other instance variables specific to cell.
     */
    public FishCell(int row, int column, int reproductionTime) {
        super(row, column, COLOR_FISH);
        this.myTurnsSurvived=0;
        this.myReproductionTime=reproductionTime;
    }

    /**
     * This version of the constructor is used only when instantiating a cell according to a location-based XML file.
     * Calls the superclass's constructor {@link cells.Cell#Cell(int, int, Paint)}
     * Calls superclass's constructor. Additionally sets up any other instance variables specific to cell.
     */
    public FishCell(Map<String, String> dataValues){
        super(Integer.parseInt(dataValues.get("row")), Integer.parseInt(dataValues.get("column")), COLOR_FISH);
        this.myTurnsSurvived=0;
        this.myReproductionTime=Integer.parseInt(dataValues.get("reproductionTime"));
    }

    public boolean canReproduce(){
        if (this.myTurnsSurvived==this.myReproductionTime) return true;
        return false;
    }

    public void setMyTurnsSurvived(int tracker){ this.myTurnsSurvived = tracker; }

    public void updateMyTurnsSurvived(){ this.myTurnsSurvived++; }
}
