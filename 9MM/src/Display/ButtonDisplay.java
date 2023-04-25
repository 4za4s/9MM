package Display;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLayeredPane;

import Board.GameButtonClicked;
import Board.Piece;
import Board.Player;

/**
 * Creates the buttons to represent pieces/empty spaces
 */
public class ButtonDisplay  extends JLayeredPane {
    private Color defaultColour = Color.white;
    private int boardPadding;
    private int gap;
    private int slotSize;
    private Piece[][] pieceArray;
    private int[][] validLocations;
    private String noPlayerString;
    private boolean noPlayerSelectable;
    private Display display;

    private String piecePhase; //string accociated with the button eg "selectPiece"
    private Player selectablePlayer; //player which can be selected on the board


    public ButtonDisplay(int boardPadding, int gap, int slotSize, 
    Piece[][] pieceArray, int[][] validLocations, String piecePhase, Player selectablePlayer,
    String noPlayerString, boolean noPlayerSelectable, Display display){
        
        this.boardPadding = boardPadding;
        this.gap = gap;
        this.slotSize = slotSize;

        this.pieceArray = pieceArray;
        this.validLocations = validLocations;
        this.piecePhase = piecePhase; 
        this.selectablePlayer = selectablePlayer;
        this.noPlayerString = noPlayerString;
        this.noPlayerSelectable = noPlayerSelectable;
        this.display = display; 
        //TODO: maybe there is some way to reduce the amount of inputs here? 
        // Maybe make it so buttons are not recreated every time


   
        }

    

    @Override
    public void paintComponent(Graphics g){

        System.out.println("making new buttons");

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
                    Piece tempPiece = new Piece(row, column, new Player(defaultColour, "noPlayer"));
                    tempPiece.setPhase(noPlayerString);
                    makeNewButton(noPlayerSelectable,tempPiece,noPlayerString, row, column);

                
                // If there is a piece there
                } else if (pieceArray[row][column] != null){
                    //Check which player piece belongs to

                     //If piece is there create a selectable  button
                    if (selectablePlayer == pieceArray[row][column].getOwner()){
                        makeNewButton(true, pieceArray[row][column], piecePhase, row, column);

                    } else { //Create an unselectable button
                        makeNewButton(false, pieceArray[row][column], "unused", row, column);
                    }


                    nextValidLocation++;

                // There is no piece there, and it is not a valid location
                } else{

                }
            }

        }

    }

    /* make a new piece */

    /**
     * Makes a new button on the board. The board gets regularly cleared and 
     * this is run each time the board is re-rendered
     * @param selectable if the button can be selected
     * @param piece the piece this button represents
     * @param piecePhrase the phrase the piece should have - tells the board manager what to do next
     * @param row the row the button should be placed on
     * @param column the column the button should be placed on
     */
    public void makeNewButton(boolean selectable, Piece piece, String piecePhrase, int row, int column){

        //Work out what the row/column correstponds to in terms of x/y
        int y = boardPadding - slotSize / 2 + gap * row; //y is row not column
        int x = boardPadding - slotSize / 2 + gap * column;

        //Pass on info about what piece was clicked when button was clicked
        // piece.setRow(row);
        // piece.setColumn(column);
        // piece.setPhase(piecePhrase);

        Piece tempPiece = new Piece(row, column, selectablePlayer);

    
        tempPiece.setEnabled(selectable);
        tempPiece.setBounds(x,y,slotSize,slotSize);
        tempPiece.setBackground(piece.getColour());

        tempPiece.setRow(row);
        tempPiece.setColumn(column);
        tempPiece.setPhase(piecePhrase);
        
        add(tempPiece); 

        new GameButtonClicked(tempPiece,display);

    }
}
