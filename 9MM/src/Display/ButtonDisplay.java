package Display;

import java.awt.Color;
import java.awt.event.ActionListener;

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
    private Display display;



    public ButtonDisplay(int boardPadding, int gap, int slotSize, Display display){
        this.boardPadding = boardPadding;
        this.gap = gap;
        this.slotSize = slotSize;
        this.display = display; 
        }

 
    public void createButtonDisplay(Piece[][] pieceArray, String piecePhase, Player selectablePlayer,
    String noPlayerString, boolean noPlayerSelectable){
        removeAll(); //Remove all previous buttons

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
                    makeNewButton(noPlayerSelectable,tempPiece,noPlayerString, row, column, selectablePlayer);

                
                // If there is a piece there
                } else if (pieceArray[row][column] != null){
                    //Check which player piece belongs to

                     //If piece is there create a selectable  button
                    if (selectablePlayer == pieceArray[row][column].getOwner()){
                        makeNewButton(true, pieceArray[row][column], piecePhase, row, column,selectablePlayer);

                    } else { //Create an unselectable button
                        makeNewButton(false, pieceArray[row][column], "unused", row, column,selectablePlayer);
                    }


                    nextValidLocation++;

                // There is no piece there, and it is not a valid location
                } else {

                }
            }
        }
    }

    /**
     * Makes a new button on the board. The board gets regularly cleared and 
     * this is run each time the board is re-rendered
     * @param selectable if the button can be selected
     * @param piece the piece this button represents
     * @param piecePhrase the phrase the piece should have - tells the board manager what to do next
     * @param row the row the button should be placed on
     * @param column the column the button should be placed on
     */
    public void makeNewButton(boolean selectable, Piece piece, String piecePhrase, int row, int column, Player selectablePlayer){

        //Work out what the row/column correstponds to in terms of x/y
        int y = boardPadding - slotSize / 2 + gap * row; //y is row not column
        int x = boardPadding - slotSize / 2 + gap * column;

        //Put piece on board
        piece.setEnabled(selectable);
        piece.setBounds(x,y,slotSize,slotSize);
        piece.setBackground(piece.getColour());

        piece.setRow(row);
        piece.setColumn(column);
        piece.setPhase(piecePhrase);

        //Remmve old action listeners
        for (ActionListener al : piece.getActionListeners()){

            piece.removeActionListener(al);

        }

        //Add a new listener
        new GameButtonClicked(piece,display);
        
        add(piece); 

    }
}
