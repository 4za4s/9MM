package Board;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Represents one of the players of the game
 */
public class Player {
    private Color colour;
    private String playerName;
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    private int piecesPlaced = 0;
    private int piecesLost = 0;
    public final int maxPieces = 5;

    /**
     * Class constructor
     */
    public Player(Color colour, String playerName){
        this.colour = colour;
        this.playerName = playerName;

        for (int i = 0; i < maxPieces; i++) {
            pieces.add(new Piece(this));
        }
    }

    /**
     * Gets the colour of the player
     * @return Java color object
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Gets the name of the player
     * @return String name
     */
    public String getName() {
        return playerName;
    }

    /**
     * Gets the number of pieces the player has placed on the board
     * @return int number of pieces placed
     */
    public int getNumOfPiecesPlaced() {
        return piecesPlaced;
    }

    /**
     * Increments the number of pieces placed by one
     */
    public void piecePlaced() {
        piecesPlaced++;
    }

    /**
     * Gets the number of pieces the player has lost
     * @return int number of pieces lost
     */
    public int getNoOfPiecesLost() {
        return piecesLost;
    }

    /**
     * Decrements the number of pieces lost by one
     */
    public void pieceLost() {
        piecesLost++;
    }

    /**
     * Gets the pieces of the player
     * @return ArrayList of pieces
     */
    public ArrayList<Piece> getPieces() {
        return pieces;
    }
}
