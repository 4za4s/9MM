package Display;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import Board.Position;

/**
 * Creates the buttons to represent pieces/empty spaces
 */
public class ButtonDisplay  extends JLayeredPane {
    private Color defaultColour = Color.white;
 
    public void createButtonDisplay(ArrayList<Position> positions, int[][] buttonLocations, int boardPadding, int gap, int slotSize){
        int index = 0;
        //Loop throught each location and add appropriate button
        for (Position pos : positions) {
            if (pos.getPiece() != null) {
                Color colour = pos.getPiece().getColour();
                int[] loc = buttonLocations[index];
                makeNewButton(pos, colour, loc, boardPadding, gap, slotSize);
            }
            else {
                int[] loc = buttonLocations[index];
                makeNewButton(pos, defaultColour, loc, boardPadding, gap, slotSize);
            }
            index++;
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
    public void makeNewButton(Position pos, Color colour, int[] loc, int boardPadding, int gap, int slotSize){

        int y = boardPadding - slotSize / 2 + gap * loc[0];
        int x = boardPadding - slotSize / 2 + gap * loc[1];

        //Put piece on board
        pos.setBounds(x,y,slotSize,slotSize);
        pos.setBackground(colour);

        pos.addActionListener(e -> pos.buttonPressed());

        add(pos); 
    }
}
