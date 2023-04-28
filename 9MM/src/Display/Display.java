package Display;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import Board.Board;

/**
 * Manages the display
 */
public class Display extends JFrame{

    private int boardPadding = 200; //padding to each side of the board 
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
   
    public Display(){
        super("9 Mans Morris");
        
        //Frame settings
        setSize(frameWidth,frameHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setResizable(false);

        //Display the window
        setWindowSize();
        pack();
        setSize(new Dimension(frameWidth, frameHeight));
        setVisible(true);
        setLayout(null); //try setLayout(new GridLayout()); if bored
    }

    /**
     * Creates the game board. Not done at initialisation because variables need to be set later
     */
    public void createGameBoard(Board board){

        //Work out values for the spacing
        int minSize = Math.min(frameWidth, frameHeight);
        int effectiveSize = minSize - boardPadding * 2;
        int gap = effectiveSize/6;
        int slotSize = (minSize-boardPadding)/20; //(Accessibility feature)

        //Create layers
        layeredPaneBackground = new Background(boardPadding, gap, slotSize);
        layeredPaneSlots = new ButtonDisplay(boardPadding, gap, slotSize, this);
        layeredPaneHighlights = new SelectionHighlights(boardPadding, gap, slotSize);
    
        //Add layers to the frame
        add(layeredPaneSlots);
        add(layeredPaneBackground);
        add(layeredPaneHighlights);

        updateDisplay(board);
        
        //Set display sizes
        layeredPaneSlots.setPreferredSize(new Dimension(frameWidth, frameHeight));
        size = layeredPaneSlots.getPreferredSize();
        layeredPaneBackground.setSize(size);
        layeredPaneSlots.setSize(size);
        layeredPaneHighlights.setSize(size);
    }

    /**
     * Updates the display 
     * @param board the current state of the board
     */
    public void updateDisplay(Board board){

        layeredPaneSlots.createButtonDisplay(board.gePositions(), buttonLocations);

        layeredPaneHighlights.removeAllHighlights();

        repaint(); //A repaint is not normally triggered otherwise. There would be ghosting with the highlights
    }

    /**
     * Highlight an available location the selected piece can move 
     * @param availableLocations the locations available
     * @param playerColour Colour of the player who's turn it is, for the correct highlight colour
     */
    public void displayPossibleMoves(ArrayList<int[]> availableLocations, Color playerColour){ //eventually ArrayList<int[]> availableLocations -> as an input

        Color highLightcolour = new Color(
            playerColour.getRed(), 
            playerColour.getGreen(),
            playerColour.getBlue(),
            playerColour.getAlpha()*2/5
        );

        layeredPaneHighlights.addHighlights(availableLocations, highLightcolour);
     }

    /**
     * Set window size of game relative to display
     */
    private void setWindowSize(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Height and Width calculated relative to the screen so the 
        // game is roughly the same size on most screens
        frameHeight = (int)size.getHeight()*5/6;
        frameWidth = frameHeight*13/14;
    }
}
