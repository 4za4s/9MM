import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.ArrayList;


/*This is the board where all of the pices go on  */ 


public class Board {


    Display panel; 
    JButton[] slots;
    JFrame frame;
    ArrayList<Piece> pieceArray = new ArrayList<Piece>(); // array for storing the buttons


    public Board(){
        
    }

    

    /* this is called whenever a button is pressed. In future it will do actual stuff.*/
    public void buttonClicked(int rowPos, int columnPos){
        System.out.println("Button was pressed. Row: " + rowPos + " Column: " + columnPos );

    }

    /* Create a piece and adds it to the piece array. Then updates the board with it */
    public void createPiece(int row, int column){
        Piece tempPieceVar = new Piece(row, column);
        pieceArray.add(tempPieceVar);

        updatePiece(tempPieceVar);



    }

    public void setPanel(Display panel){
        this.panel = panel;
    }

    //updates a pieces location on the board
    public void updatePiece(Piece piece){

        //not sure how this works yet
        //TODO: stuff here?



        panel.updatePiece(piece); //make the visual changes as well

    }


    //method idea:
    // add piece- adds piece to the board, then tells display to update the board

    //move piece. Moves a piece around the board, then tells display to move it
}





//has a list of of slots
//each slot has a piece



//need piece class