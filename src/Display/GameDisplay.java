package Display;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPopupMenu;

import Board.Board;
import Board.Player;
import Board.Position;
import Game.Game;

/**
 * Manages the display
 */
public class GameDisplay extends AbstractDisplay {
    private Game game;
    private int boardPadding = 200; //padding to each side of the board
    private final int minSize = 700; //minimum size the board can display as 
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

    private ButtonDisplay buttonDisplay; //button layer for game
    private SelectionHighlights layeredPaneHighlights; //highlight layer for game 
    private Background layeredPaneBackground; //background layer for game
    PieceCounter leftPieceCounter;
    PieceCounter rightPieceCounter;

   
    /**
     * Class constructor
     */
    public GameDisplay(int Height, int Width, Game game, Window window){
        super(Height, Width, window);
        this.game = game;
    }

    /**
     * Creates the game board. Not done at initialisation because variables need to be set later
     */
    @Override
    public void createDisplay(){
        //Work out values for the spacing

        //TODO remove this
        int minSize = Math.min(window.getHeight(), window.getWidth());
        int effectiveSize = minSize - boardPadding * 2;
        int gap = effectiveSize/6;
        int slotSize = (minSize-boardPadding)/20; //(Accessibility feature)

        size = new Dimension(window.getSize());

        //Create layers
        layeredPaneBackground = new Background(size);
        buttonDisplay = new ButtonDisplay();
        layeredPaneHighlights = new SelectionHighlights(size);
        
        Board board = game.getBoard();
        buttonDisplay.createButtonDisplay(game, board.getPositions(), buttonLocations, size);
    
        //Add layers to the frame
        window.getContentPane().removeAll(); //TODO: shouldn't need this
        window.add(buttonDisplay);
        window.add(layeredPaneBackground);
        window.add(layeredPaneHighlights);
        
        
        //Set display sizes
        buttonDisplay.setPreferredSize(new Dimension(window.getHeight(), window.getWidth()));
        size = buttonDisplay.getPreferredSize();
        layeredPaneBackground.setSize(size);
        buttonDisplay.setSize(size);
        layeredPaneHighlights.setSize(size);

        window.repaint();
    }

    /**
     * Updates the display 
     */
    @Override
    public void updateDisplay(){
        Board board = game.getBoard();
        for (Position pos : board.getPositions()) {
            if (pos.getPiece() != null) {
                pos.setBackground(pos.getPiece().getColour());
            }
            else {
                pos.setBackground(Color.white);
            }
        }
       window.repaint();
    }

    public void Win(String winner){
        JPopupMenu win = new JPopupMenu(winner);
        window.add(win);
    }

    /**
     * Highlight an available location the selected piece can move 
     * @param possibleMoves the locations available
     * @param playerColour Colour of the player who's turn it is, for the correct highlight colour
     */
    public void displayPossibleMoveHighlights(ArrayList<Position> possibleMoves, Color playerColour){
        Color highlightcolour = new Color(
            playerColour.getRed(), 
            playerColour.getGreen(),
            playerColour.getBlue(),
            playerColour.getAlpha()*2/5
        );

        layeredPaneHighlights.addHighlights(possibleMoves, highlightcolour);
    }

    /**
     * Remove all highlights, calls the removeHighlights method in the highlight layer
     */
    public void removeHighlights(){
        layeredPaneHighlights.removeHighlights();
    }

    /** Adds a player counter for each player */
    public void AddPlayerCounter(ArrayList<Player> players){
        Player currentPlayer = players.get(0);

        leftPieceCounter = new PieceCounter(currentPlayer, true, size);
        leftPieceCounter.setSize(1000,1000); //TODO: set this properly later


        currentPlayer = players.get(1);
        rightPieceCounter = new PieceCounter(currentPlayer, false, size);
        rightPieceCounter.setSize(1000,1000); //TODO: set this properly later


        window.add(leftPieceCounter);
        window.add(rightPieceCounter);
    }

    @Override
    public void resizeDisplay(Dimension size) {
        buttonDisplay.resizeDisplay(size);
        layeredPaneHighlights.resizeDisplay(size); 
        layeredPaneBackground.resizeDisplay(size); 
        leftPieceCounter.resizeDisplay(size);
        rightPieceCounter.resizeDisplay(size);
    }
}

