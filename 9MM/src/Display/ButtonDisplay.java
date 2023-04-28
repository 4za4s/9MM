package Display;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;

import Board.GameButtonClicked;
import Board.Piece;
import Board.Player;
import Board.Position;

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

 
    public void createButtonDisplay(Position[] positions, int[][] buttonLocations){
        removeAll(); //Remove all previous buttons

        //Loop throught each location and add appropriate button
        for (Position pos : positions) {
            if (pos.getPiece() != null) {
                makeNewButton(pos, pos.getPiece().getColour());
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
    public void makeNewButton(Position pos, Color colour){
        //Put piece on board
        pos.setEnabled(selectable);
        pos.setBounds(x,y,slotSize,slotSize);
        pos.setBackground(piece.getColour());

        pos.setRow(row);
        pos.setColumn(column);
        pos.setPhase(piecePhrase);

        //Remmve old action listeners
        for (ActionListener al : pos.getActionListeners()){
            pos.removeActionListener(al);

        }

        //Add a new listener
        new GameButtonClicked(pos, display);

                
        add(pos); 

    }

}
