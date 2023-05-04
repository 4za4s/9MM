package Display;

import javax.swing.JLayeredPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;

import Board.Board;
import Board.Position;
import Game.Game;

/**
 * Manages the display
 */
public class GameDisplay extends JLayeredPane{
    private Game game;
    private int boardPadding = 200; //padding to each side of the board
    private final int minSize = 700; //minimum size the board can display as 
    private int frameWidth; //width of the frame (frame = where everything is rendered)
    private int frameHeight; //height of the frame
    private Dimension size; //so all board parts are created the same size
    private int[][] buttonLocations = { 
        { 0, 0 }, { 0, 3 }, { 0, 6 },   // {row,column}
        { 1, 1 }, { 1, 3 }, { 1, 5 }, 
        { 2, 2 }, { 2, 3 }, { 2, 4 }, 
        { 3, 0 }, { 3, 1 }, { 3, 2 }, 
        { 3, 4 }, { 3, 5 }, { 3, 6 },
        { 4, 2 }, { 4, 3 }, { 4, 4 },
        { 5, 1 }, { 5, 3 }, { 5, 5 },
        { 6, 0 }, { 6, 3 }, { 6, 6 } 
    };


    private ButtonDisplay layeredPaneSlots; //button layer for game
    private SelectionHighlights layeredPaneHighlights; //highlight layer for game 
    private Background layeredPaneBackground; //background layer for game
   
    /**
     * Class constructor
     */
    public GameDisplay(Game game){
        this.game = game;

        setVisible(true);
        setLayout(null); //try setLayout(new GridLayout()); if bored
    }

    /**
     * Creates the game board. Not done at initialisation because variables need to be set later
     */
    public void createDisplay(Board board){
        //Work out values for the spacing
        int minSize = Math.min(Frame.HEIGHT, Frame.WIDTH);
        int effectiveSize = minSize - boardPadding * 2;
        int gap = effectiveSize/6;
        int slotSize = (minSize-boardPadding)/20; //(Accessibility feature)

        //Create layers
        layeredPaneBackground = new Background(boardPadding, gap, slotSize);
        layeredPaneSlots = new ButtonDisplay();
        layeredPaneHighlights = new SelectionHighlights(slotSize);

        layeredPaneSlots.createButtonDisplay(game, board.getPositions(), buttonLocations, boardPadding, gap, slotSize);
    
        //Add layers to the frame
        add(layeredPaneSlots);
        add(layeredPaneBackground);
        add(layeredPaneHighlights);
        
        //Set display sizes
        layeredPaneSlots.setPreferredSize(new Dimension(Frame.HEIGHT, Frame.WIDTH));
        size = layeredPaneSlots.getPreferredSize();
        layeredPaneBackground.setSize(size);
        layeredPaneSlots.setSize(size);
        layeredPaneHighlights.setSize(size);

        repaint();
    }

    /**
     * Updates the display 
     * @param board the current state of the board
     */
    public void updateDisplay(Board board){
        for (Position pos : board.getPositions()) {
            if (pos.getPiece() != null) {
                pos.setBackground(pos.getPiece().getColour());
            }
            else {
                pos.setBackground(Color.white);
            }
        }
        repaint();
    }

    /**
     * Highlight an available location the selected piece can move 
     * @param possibleMoves the locations available
     * @param playerColour Colour of the player who's turn it is, for the correct highlight colour
     */
    public void displayPossibleMoves(ArrayList<Position> possibleMoves, Color playerColour){
        Color highLightcolour = new Color(
            playerColour.getRed(), 
            playerColour.getGreen(),
            playerColour.getBlue(),
            playerColour.getAlpha()*2/5
        );

        layeredPaneHighlights.addHighlights(possibleMoves, highLightcolour);
    }

    /**
     * Remove all highlights, calls the removeHighlights method in the highlight layer
     */
    public void removeHighlights(){
        layeredPaneHighlights.removeHighlights();
    }
}

