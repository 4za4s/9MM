package Display;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLayeredPane;

import Board.Position;
import Game.Game;

/**
 * Creates the buttons to represent pieces/empty spaces
 */
public class ButtonDisplay extends JLayeredPane {
    private Color defaultColour = Color.white;
    private ArrayList<Position> buttons = new ArrayList<Position>();

    int gap; 
    int slotSize;
 
    /**
     * Creates the buttons on the display
     * @param game game handles what happens when the button is pressed
     * @param positions positions are the buttons since you click on positions and pieces
     * @param buttonLocations possible locations for a button, each location will get a button
     * @param gap gap between each button
     * @param slotSize size of each button
     */
    public void createButtonDisplay(Game game, ArrayList<Position> positions, int[][] buttonLocations, int gap, int slotSize) {

        //resizeDisplay(gap, slotSize);

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
        int y =  gap * loc[0];
        int x =  gap * loc[1];

        //Put piece on board
        pos.setBounds(x,y,slotSize,slotSize);
        pos.setForeground(colour);
        pos.setContentAreaFilled(false);
        pos.setBorder(new RoundedBorder(slotSize));

        //creates a new listener for each position that will call the buttonPressed method in the game class
        pos.addActionListener(e -> game.buttonPressed(pos));

        add(pos); 

        buttons.add(pos);
        pos.setRowColumn(loc[0], loc[1]);
    }

    public void resizeDisplay(int gap, int slotSize ) {

        this.gap = gap;
        this.slotSize = slotSize;

        //Redo board size variables

        //Go through each button and for each button a new position for it based on its row/colmn 

        int row;
        int column;
        int x;
        int y;

        for (Position but : buttons){
            
            row = but.getRow();
            column = but.getColumn();

            y =  gap * row;
            x =   gap * column;
    
            //Put piece on board
            but.setBounds(x,y,slotSize,slotSize);

        }
    }
}
