package Board;
import javax.swing.JButton;

/* like a normal button, but with a way to save some attributes */
public class GameButton extends JButton{

    private int rowPos; //column of the button
    private int columnPos; //column of the button
    private String type = "SelectPiece"; //what the button is used for


    public GameButton(String type, int rowPos, int columnPos){

        super(); //run the super stuff

        this.rowPos = rowPos;
        this.columnPos = columnPos;
        this.type = type;

        
    }

    public int getRowPos(){
        return rowPos;
    }

    public int getColumnPos(){
        return columnPos;
    }
    
    public String getType(){
        return type;
    }
}
