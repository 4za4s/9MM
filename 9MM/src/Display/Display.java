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

    int boardPadding; //padding to each side of the board 
    int effectiveSize; //how mich room is left  in game (frame minus padding)
    int gap; // distance between concentric squares
    
    Frame frame;

    int frameWidth;
    int frameHeight;
    int slotSize = 50; // height/width of a button
    Dimension size; //so all board parts are created the same size
    int[][] validLocations;


    private ButtonDisplay layeredPaneSlots;
    private SelectionHighlights layeredPaneHighlights;
    private Background layeredPaneBackground;
    
    Board board; //the game board this is displaying
    ArrayList<GameButton> buttonArray = new ArrayList<GameButton>(); // array for storing the buttons
    Piece[][] pieceArray = new Piece[7][7];

    Color defaultColour = Color.white;
   

    public Display(int boardPadding, Board board, Frame frame){
        this.frame = frame;
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
        slotSize = minSize/20; //(Accessibility feature)


        //Create board
        setLayout(null); //try setLayout(new GridLayout()); if bored

  
    
        //Create and set up the background layered pane.
        layeredPaneBackground = new Background(boardPadding, gap, slotSize);

        //Add layer to the frame
        add(layeredPaneBackground);

        String piecePhase = "unused";
        Player unusedPlayer = new Player(defaultColour);
        updateDisplay(pieceArray,unusedPlayer,piecePhase, "unusedString",false);

        

        layeredPaneBackground.setSize(size);

    }

    /**
     * Updates the display 
     * @param pieceArray where all of the pieces are
     * @param selectablePlayer player whose pieces can be clicked on
     * @param piecePhase what to tell the board when a piece is clicked
     * @param noPlayerString what to tell the board if an empty slot is clicked
     * @param noPlayerSelectable can an empty slot be selected
     */
    public void updateDisplay(Piece[][] pieceArray, Player selectablePlayer,String piecePhase, String  noPlayerString, boolean noPlayerSelectable){
        //Get the new locatiosn of all of the pieces
        this.pieceArray = pieceArray;

   

        layeredPaneSlots = new ButtonDisplay(boardPadding, gap, slotSize, pieceArray, validLocations, piecePhase, selectablePlayer, noPlayerString , noPlayerSelectable, this);
        layeredPaneSlots.setPreferredSize(new Dimension(frameWidth, frameHeight)); //TODO: update above input

        //Remove everything from the frame
        this.removeAll();
        //Add all of the new stuff back to the frame
        add(layeredPaneBackground); //TODO: not optimal to use removeAll because then I need to add this back every time. Was unable to just remove the buttons because too dumb
        add(layeredPaneSlots);

        size = layeredPaneSlots.getPreferredSize();

        layeredPaneSlots.setSize(size);

    
    }




    /**
     * Highlight an available location the selected piece can move 
     * @param availableLocations the locations available
     */
    public void displayAvailableLocation(ArrayList<int[]> availableLocations){ //eventually ArrayList<int[]> availableLocations -> as an input


        layeredPaneHighlights = new SelectionHighlights(boardPadding, gap, slotSize, availableLocations);

        add(layeredPaneHighlights);


        layeredPaneHighlights.setSize(size);

        // repaint();
        // revalidate();


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
