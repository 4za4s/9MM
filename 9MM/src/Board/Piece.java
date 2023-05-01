package Board;
import java.awt.Color;

/** 
 * Represents a piece on the board
 */
public class Piece {
    private Player owner; //who owns the piece
    private Position pos; //where the piece is on the board

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
        this.pos = pos;

        //if the piece is removed from the board, the owner loses a piece
        if (pos == null){
            owner.pieceLost();
        }
        //updates the position to let it know the piece is no longer here
        if (pos != null && pos.getPiece() != this) {
            pos.setPiece(this);
        }
    }
    
    /** 
     * @return Position
     */
    public Position getPosition(){
        return pos;
    }
}
