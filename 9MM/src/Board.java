import javax.swing.JButton;
import javax.swing.JFrame;

/*This is the board where all of the pices go on  */ 


public class Board {

    JButton[] slots;
    JFrame frame;

    

    /* this is called whenever a button is pressed. In future it will do actual stuff.*/
    public void buttonClicked(int rowPos, int columnPos){
        System.out.println("Button was pressed. Row: " + rowPos + " Column: " + columnPos );

    }
}





//has a list of of slots
//each slot has a piece



//need piece class