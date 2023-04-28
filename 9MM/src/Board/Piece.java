package Board;
import java.awt.Color;

/** 
 * Represents a piece on the board
 */
public class Piece {
    private Player owner; //who owns the piece
    private Position pos; //where the piece is on the board

    //start with initial position
    public Piece(Position pos, Player owner){
        this.pos = pos;
        this.owner = owner;
    }

    //start without a position
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
        if (pos.getPiece() != this) {
            pos.setPiece(this);
        }
    }

    public Position getPosition(){
        return pos;
    }
}
