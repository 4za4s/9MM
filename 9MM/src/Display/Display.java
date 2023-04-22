package Display;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;

import Board.Board;
import Board.GameButton;
import Board.Piece;
import Board.Player;

/**
 * Manages the display
 */
public class Display extends JPanel{

    private int boardPadding; //padding to each side of the board 
    private int effectiveSize; //how mich room is left  in game (frame minus padding)
    private int gap; // distance between concentric squares
    private int frameWidth; //width of the frame (frame = where everything is rendered)
    private int frameHeight; //height of the frame
    private int slotSize = 50; // height/width of a button
    private Dimension size; //so all board parts are created the same size
    private int[][] validLocations; //list of all valid locations a piece can be. Used to know where to place empty slots and pieces


    private ButtonDisplay layeredPaneSlots; //button layer for game
    private SelectionHighlights layeredPaneHighlights; //highlight layer for game 
    private Background layeredPaneBackground; //background layer for game
    
    private Board board; //the game board this is displaying
    private Piece[][] pieceArray = new Piece[7][7]; //array for storing piece info

    private Color defaultColour = Color.white; //default colour of game buttons
   
    public Display(int boardPadding, Board board, Frame frame){
        this.boardPadding = boardPadding;
        this.board = board;
        this.frameWidth = frame.getWidth();
        this.frameHeight = frame.getHeight();
    }

    /**
     * Creates the game board. Not done at initialisation because variables need to be set later
     */
    public void createGameBoard(){

  
        //Work out values for the spacing
        
        int minSize = Math.min(frameWidth, frameHeight);

        effectiveSize = minSize - boardPadding * 2;
        gap = effectiveSize/6;


        //make button size depend on board size
        slotSize = (minSize-boardPadding)/20; //(Accessibility feature)


        //Create board
        setLayout(null); //try setLayout(new GridLayout()); if bored

  
    
        //Create and set up the background layered pane.
        layeredPaneBackground = new Background(boardPadding, gap, slotSize);

        //Add layer to the frame
        add(layeredPaneBackground);

        String piecePhase = "unused";
        Player unusedPlayer = new Player(defaultColour, "noPlayer");
        updateDisplay(pieceArray,unusedPlayer,piecePhase, "unusedString",false,"");

        

        layeredPaneBackground.setSize(size);

    }

    /**
     * Updates the display 
     * @param pieceArray where all of the pieces are
     * @param selectablePlayer player whose pieces can be clicked on
     * @param piecePhase what to tell the board when a piece is clicked
     * @param noPlayerString what to tell the board if an empty slot is clicked
     * @param noPlayerSelectable can an empty slot be selected
     * @param turnText the text to display telling user what to do
     */
    public void updateDisplay(Piece[][] pieceArray, Player selectablePlayer,String piecePhase, String  noPlayerString, boolean noPlayerSelectable, String turnText){
        //Get the new locatiosn of all of the pieces
        this.pieceArray = pieceArray;

   

        layeredPaneSlots = new ButtonDisplay(boardPadding, gap, slotSize, pieceArray, validLocations, piecePhase, selectablePlayer, noPlayerString , noPlayerSelectable, this);
        layeredPaneSlots.setPreferredSize(new Dimension(frameWidth, frameHeight)); //TODO: update above input

        //Remove everything from the frame
        this.removeAll();
        //Add all of the new stuff back to the frame
        add(layeredPaneBackground); //TODO: not optimal to use removeAll because then I need to add this back every time. Was unable to just remove the buttons because too dumb
        add(layeredPaneSlots);

        // add(new TurnText(turnText,boardPadding,gap,frameWidth, frameHeight));


        size = layeredPaneSlots.getPreferredSize();

        layeredPaneSlots.setSize(size); //TODO: put this inside the class

    
    }




    /**
     * Highlight an available location the selected piece can move 
     * @param availableLocations the locations available
     */
    public void displayAvailableLocation(ArrayList<int[]> availableLocations){ //eventually ArrayList<int[]> availableLocations -> as an input


        layeredPaneHighlights = new SelectionHighlights(boardPadding, gap, slotSize, availableLocations);

        add(layeredPaneHighlights);


        layeredPaneHighlights.setSize(size);


        }

        /**
         * Tells the board a button has been clicked
         * @param gameButton the button that was clicked
         */
        public void buttonClicked(GameButton gameButton){
            board.buttonClicked(gameButton.getPiece());

        }

        /**
         * Sets the locations that a piece can be on
         * @param validLocations the locations
         */
        public void setValidLocations(int[][] validLocations){
            this.validLocations = validLocations;
        }

 }
