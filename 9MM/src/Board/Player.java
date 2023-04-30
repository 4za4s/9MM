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

    /**
     * Class constructor
     */
    public Player(Color colour, String playerName){
        this.colour = colour;
        this.playerName = playerName;

        for (int i = 0; i < 9; i++) {
            pieces.add(new Piece(this));
        }
    }

    public Color getColour(){
        return colour;
    }

    public String getName(){
        return playerName;
    }

    public int getPiecesPlaced(){
        return piecesPlaced;
    }

    public void piecePlaced(){
        piecesPlaced++;
    }

    public int getPiecesLost(){
        return piecesLost;
    }

    public void pieceLost(){
        piecesLost++;
    }

    public ArrayList<Piece> getPieces(){
        return pieces;
    }
}
