package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import Board.Position;
import Game.Game;

/**
 * Creates the buttons to represent pieces/empty spaces
 */
public class ButtonDisplay  extends JLayeredPane {
    private Color defaultColour = Color.white;
    private ArrayList<Position> buttons = new ArrayList<Position>();

    int boardPadding; 
    int gap; 
    int slotSize;
 
    /**
     * Creates the buttons on the display
     * @param game game handles what happens when the button is pressed
     * @param positions positions are the buttons since you click on positions and pieces
     * @param buttonLocations possible locations for a button, each location will get a button
     * @param boardPadding padding around the board
     * @param gap gap between each button
     * @param slotSize size of each button
     */
    public void createButtonDisplay(Game game, ArrayList<Position> positions, int[][] buttonLocations,  Dimension size){

        setBoardElementsSize(size);

        int index = 0;
        //Loop throught each location and add appropriate button
        for (Position pos : positions) {
            if (pos.getPiece() != null) {
                Color colour = pos.getPiece().getColour();
                int[] loc = buttonLocations[index];
                makeNewButton(game, pos, colour, loc);
            }
            else {
                int[] loc = buttonLocations[index];
                makeNewButton(game, pos, defaultColour, loc);
            }
            index++;
        }
    } 

    /**
     * Makes a new button on the board. The board gets regularly cleared and 
     * this is run each time the board is re-rendered
     * @param game game handles what happens when the button is pressed
     * @param pos positions are the buttons since you click on positions and pieces
     * @param colour initial colour of the button, colout is changed based on the piece on it or default
     * @param loc location of the button on the board, based on a grid [row, column]
     * @param boardPadding padding around the board
     * @param gap gap between each button
     * @param slotSize size of each button
     */
    public void makeNewButton(Game game, Position pos, Color colour, int[] loc){

        //buttons are placed on a grid, so the location is used to calculate the x and y position on the display
        int y = boardPadding - slotSize / 2 + gap * loc[0];
        int x = boardPadding - slotSize / 2 + gap * loc[1];

        //Put piece on board
        pos.setBounds(x,y,slotSize,slotSize);
        pos.setBackground(colour);

        //creates a new listener for each position that will call the buttonPressed method in the game class
        pos.addActionListener(e -> game.buttonPressed(pos));

        add(pos); 

        buttons.add(pos);
        pos.setRowColumn(loc[0], loc[1]);

    }

    //TODO: this is duplicate code
    private void setBoardElementsSize(Dimension size){
        int width = (int) size.getWidth();
        int height = (int) size.getHeight();

        int minDim = Math.min(width, height);
 
        boardPadding = (minDim * 8 ) / 40;
        gap = (minDim - boardPadding * 2) / 6;
        slotSize = (minDim - boardPadding) / 20;

    }

    public void resizeDisplay(Dimension size) {

        //Redo board size variables

        //Go through each button

        // For each button a new position for it based on its row/colmn 

        setBoardElementsSize(size);


        int row;
        int column;
        int x;
        int y;

        for (Position but : buttons){
            row = but.getRow();
            column = but.getColumn();

            y = boardPadding - slotSize / 2 + gap * row;
            x = boardPadding - slotSize / 2 + gap * column;
    
            //Put piece on board
            but.setBounds(x,y,slotSize,slotSize);

        }
    }
}
