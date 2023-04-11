package Board;
import Display.Display;

import java.util.ArrayList;


/*This is the board where all of the pices go on  */ 


public class Board {


    Display panel; 
    BoardManager boardManager;
    Piece[][] pieceArray = new Piece[7][7];// array for storing the pieces

    int[] lastPieceSelected = {0,0}; //location of last piece selected (button clicked). row, column
    int[] secondLastPieceSelected = {0,0}; //These are for keeping track of what piece to move where



    int[][]validLocations =  new int[][]{{0,0},{0,3},{0,6},  //so later can work out it a move is valid
                                         {1,1},{1,3},{1,5},     //{row,column}
                                         {2,2},{2,3},{2,4}, //TODO: this is duplicated in Display. Something is bad
                                         {3,0},{3,1},{3,2},{3,4},{3,5},{3,6},
                                         {4,2},{4,3},{4,4},
                                         {5,1},{5,3},{5,5},
                                         {6,0},{6,3},{6,6} };
    


    /* Create a piece and adds it to the piece array. Then updates the board with it */
    public void createPiece(int row, int column, Player owner){
        Piece tempPieceVar = new Piece(row, column, owner);
        pieceArray[row][column] = tempPieceVar;



    }



    /* restricts which pieces the user can click on. Only allows for the pieces bellonging to the player given*/
    // public void restrictPieceAccessToOnly(Player player){


    //     //work out which location have the pieces of the player

    //     ArrayList<int[]> playersPieceLocs = new ArrayList<int[]>();



    //     for (Piece piece : pieceArray ){
    //         if (piece.getOwner() == player) {
    //             int[] pieceLoc = {piece.getRow(),piece.getColumn() };
    //             playersPieceLocs.add(pieceLoc);   

    //         }

    //     }



    //     panel.deselectOtherButtons(playersPieceLocs);

    //     //send these locations off to Display. Display will manage deselecting every other location
        
    // }

    public void displayAvailableLocations(){

        //Get position {row,column} of all empty locations since they are all valid moves for this sprint
        ArrayList<int[]> locationsArrayList = new ArrayList<int[]>();

        for (int[] location : validLocations){
            if (pieceArray[location[1]][location[0]] == null){
                locationsArrayList.add(location);

            }
            

        }
        panel.displayAvailableLocation(locationsArrayList);
    }

    /* called when a button is clicked */
    public void buttonClicked(Piece piece){
        //Recordlast two buttons clicked
        secondLastPieceSelected[0] = lastPieceSelected[0];
        secondLastPieceSelected[1] = lastPieceSelected[1];

        lastPieceSelected[0] = piece.getRow();
        lastPieceSelected[1] = piece.getColumn();

        boardManager.buttonClicked(piece.getPhrase());


    }

    public void setDesiplaysValidLocations(){
        panel.setValidLocations(validLocations);
    }

    /* Note: panel is the display frame */
    public void setPanel(Display panel){
        this.panel = panel;
    }


    public void setBoardManager(BoardManager boardManager){
        this.boardManager = boardManager;
    }

    /* Moves a piece from one spot in the board to another */
    public void movePiece(Player[] selectablePlayers, String piecePhases[],String noPlayerString, boolean noPlayerSelectable){

        //Move piece 
        pieceArray[lastPieceSelected[0]][lastPieceSelected[1]] = pieceArray[secondLastPieceSelected[0]][secondLastPieceSelected[1]];
        
        pieceArray[secondLastPieceSelected[0]][secondLastPieceSelected[1]] = null;

        //Update board visually
        updatePieces(selectablePlayers,piecePhases, noPlayerString, noPlayerSelectable);
    }

    /* Rerenders the display of all of the pieces */
    public void updatePieces( Player[] selectablePlayers, String piecePhases[],String noPlayerString, boolean noPlayerSelectable){
        panel.updatePieces(pieceArray,selectablePlayers,piecePhases,noPlayerString,noPlayerSelectable);
    }

}



