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

    public Color getColour(){
        return owner.getColour();
    }

    public Player getOwner(){
        return owner;
    }

    public void setPosition(Position pos){
        this.pos = pos;
        if (pos != null && pos.getPiece() != this) {
            pos.setPiece(this);
        }
    }

    public Position getPosition(){
        return pos;
    }
}
