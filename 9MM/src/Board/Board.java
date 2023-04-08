package Board;
import javax.swing.JButton;
import javax.swing.JFrame;

import Display.Display;

import java.util.ArrayList;


/*This is the board where all of the pices go on  */ 


public class Board {


    Display panel; 
    JButton[] slots;
    JFrame frame;
    ArrayList<Piece> pieceArray = new ArrayList<Piece>(); // array for storing the buttons


    public Board(){
        
    }

    



    /* Create a piece and adds it to the piece array. Then updates the board with it */
    public void createPiece(int row, int column, Player owner){
        Piece tempPieceVar = new Piece(row, column, owner);
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

    /* restricts which pieces the user can click on. Only allows for the pieces bellonging to the player given*/
    public void restrictPieceAccessToOnly(Player player){


        //work out which location have the pieces of the player

        ArrayList<int[]> playersPieceLocs = new ArrayList<int[]>();



        for (Piece piece : pieceArray ){
            if (piece.getOwner() == player) {
                int[] pieceLoc = {piece.getRow(),piece.getColumn() };
                playersPieceLocs.add(pieceLoc);   

            }

        }



        panel.deselectOtherButtons(playersPieceLocs);

        //send these locations off to Display. Display will manage deselecting every other location
        
    }
}



