package Display;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLayeredPane;

import Board.GameButton;
import Board.GameButtonClicked;
import Board.Piece;
import Board.Player;

/* Creates the buttons to represent pieces/empty spaces */
public class ButtonDisplay  extends JLayeredPane {
    Color defaultColour = Color.white;
    int boardPadding;
    int gap;
    int slotSize;
    Piece[][] pieceArray;
    int[][] validLocations;
    String noPlayerString;
    boolean noPlayerSelectable;
    Display display;

    String piecePhases[]; //these two arrays  have corrsponding locations
    Player[] selectablePlayers;


    public ButtonDisplay(int boardPadding, int gap, int slotSize, 
    Piece[][] pieceArray, int[][] validLocations, String[] piecePhases, Player[] selectablePlayers,
    String noPlayerString, boolean noPlayerSelectable, Display display){
        
        this.boardPadding = boardPadding;
        this.gap = gap;
        this.slotSize = slotSize;

        this.pieceArray = pieceArray;
        this.validLocations = validLocations;
        this.piecePhases = piecePhases; 
        this.selectablePlayers = selectablePlayers;
        this.noPlayerString = noPlayerString;
        this.noPlayerSelectable = noPlayerSelectable;
        this.display = display; //TODO: maybe there is some way to reduce the amount of inputs here?


   
        }

    

    @Override
    //This is run by default
    public void paintComponent(Graphics g){
        // int[][]buttonsLocations =   new int[][]{{0,0},{0,3},{0,6}, //{row,column}
        //                                         {1,1},{1,3},{1,5},
        //                                         {2,2},{2,3},{2,4},
        //                                         {3,0},{3,1},{3,2},{3,4},{3,5},{3,6},
        //                                         {4,2},{4,3},{4,4},
        //                                         {5,1},{5,3},{5,5},
        //                                         {6,0},{6,3},{6,6}};

        int nextValidLocation = 0; //For checking the validLocations array to save time complexity when seeing if a 
        // location is valid

        //Loop throught each location and add appropriate button
        for (int row = 0; row < pieceArray.length; row++){
            for (int column = 0; column < pieceArray[0].length; column++){

                //If no piece there but it is a valid location
                if (pieceArray[row][column] == null  
                && row == validLocations[nextValidLocation][0] 
                && column == validLocations[nextValidLocation][1] 
                ){

                    nextValidLocation++;

                    // Create a non-player button - ie empty slot
                    Piece tempPiece = new Piece(row, column, new Player(defaultColour));
                    tempPiece.setPhase(noPlayerString);
                    makeNewButton(noPlayerSelectable,tempPiece,noPlayerString, row, column);

                
                // If there is a piece there
                } else if (pieceArray[row][column] != null){
                    //Check which player piece belongs to

                    for (int i = 0; i < selectablePlayers.length; i++) {
                        //If piece is there create a selectablw  button
                        if (selectablePlayers[i] == pieceArray[row][column].getOwner()){
                            makeNewButton(true, pieceArray[row][column], piecePhases[i], row, column);



                        } else {//Create an unselectable button
                            makeNewButton(false, pieceArray[row][column], piecePhases[i], row, column);

                        }
                    }
                    nextValidLocation++;

                // There is no piece there, and it is not a valid location
                } else{
                    //

                }
            }

        }

    }

    /* make a new piece */
    public void makeNewButton(boolean selectable, Piece piece, String piecePhrase, int row, int column){

        //Work out what the row/column correstponds to in terms of x/y
        int y = boardPadding - slotSize / 2 + gap * row; //y is row not column
        int x = boardPadding - slotSize / 2 + gap * column;

        //Pass on info about what piece was clicked when button was clicked
        piece.setRow(row); //TODO, this might be unnecessary
        piece.setColumn(column);
        piece.setPhase(piecePhrase);

        GameButton tempButtonVar = new GameButton(piece);
        tempButtonVar.setEnabled(selectable);
        tempButtonVar.setBounds(x,y,slotSize,slotSize);
        tempButtonVar.setBackground(piece.getColour());
        
        add(tempButtonVar); 

        new GameButtonClicked(tempButtonVar,display);

    }
}
