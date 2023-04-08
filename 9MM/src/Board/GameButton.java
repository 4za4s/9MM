package Board;
import javax.swing.JButton;

/* like a normal button, but with a way to save some attributes */
public class GameButton extends JButton{

    private int rowPos; //column of the button
    private int columnPos; //column of the button


    public GameButton(int rowPos, int columnPos){

        super(); //run the super stuff

        this.rowPos = rowPos;
        this.columnPos = columnPos;

        
    }

    public int getRowPos(){
        return rowPos;
    }

    public int getColumnPos(){
        return columnPos;
    }
    
}
