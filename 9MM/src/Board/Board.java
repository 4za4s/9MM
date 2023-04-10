package Board;
import javax.swing.JButton;
import javax.swing.JFrame;

import Display.Display;

import java.util.ArrayList;


/*This is the board where all of the pices go on  */ 


public class Board {


    Display panel; 
    JButton[] slots;
    BoardManager boardManager;
    ArrayList<Piece> pieceArray = new ArrayList<Piece>(); // array for storing the buttons
    int[] lastButtonClicked = {0,0}; //location of last button clicked. row, column


    int[][]validLocations =  new int[][]{{0,0},{0,3},{0,6},  //so later can work out it a move is valid
                                        {1,1},{1,3},{1,5},     //{row,column}
                                        {2,2},{2,3},{2,4}, //TODO: this is duplicated in Display. Something is bad
                                        {3,0},{3,1},{3,2},{3,4},{3,5},{3,6},
                                        {4,2},{4,3},{4,4},
                                        {5,1},{5,3},{5,5},
                                        {6,0},{6,3},{6,6} };


    

    public void setBoardManager(BoardManager boardManager){
        this.boardManager = boardManager;
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

    public void displayAvailableLocations(){

        //Get position {row,column} of all locations since they are all valid moves for this sprint
        ArrayList<int[]> locationsArrayList = new ArrayList<int[]>();

        for (int[] location : validLocations){
            locationsArrayList.add(location);

        }
        panel.displayAvailableLocation(locationsArrayList);
    }

    /* called when a button is clicked */
    public void buttonClicked(String type, int row, int column){
        //Tell what button was clicked
        lastButtonClicked[0] = row;
        lastButtonClicked[1] = column;

        boardManager.buttonClicked(type);


    }
}



