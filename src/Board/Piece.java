package Board;
import java.awt.Color;

/** 
 * Represents a piece on the board
 */
public class Piece {
    private Player owner; //who owns the piece
    private Position pos; //where the piece is on the board
    private boolean inMill;

    /**
     * Class constructor. starting on a position
     */
    public Piece(Position pos, Player owner){
        this.pos = pos;
        this.owner = owner;
    }

    /**
     * Class constructor, no initial position
     */
    public Piece(Player owner){
        this.owner = owner;
    }

    /**
     * Gets the colour of the piece
     * @return Java color object
     */
    public Color getColour(){
        return owner.getColour();
    }

    /**
     * Gets the owner of the piece
     * @return Player owner of the piece
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * Sets the position of the piece
     * @param pos position to set, can be null to remove the piece from the board
     */
    public void setPosition(Position pos){
        if (this.pos != null) {
            this.pos.setPiece(null);
        }

        this.pos = pos;
    }

    public boolean isInMill() {
        return inMill;
    }
    
    /** 
     * Gets the position of the piece
     * @return Position
     */
    public Position getPosition(){
        return pos;
    }
}
