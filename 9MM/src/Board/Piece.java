package Board;
import java.awt.Color;

import javax.swing.JButton;

/** 
 * Represents a piece on the board
 */
public class Piece {

    private Player owner; //who owns the piece
    private Position pos; //where the piece is on the board
    private String piecePhase; //what is represented as happening when the piece is selected

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

    public void setPhase(String piecePhrase){
        this.piecePhase = piecePhrase;
    }

    public String getPhase(){
        return piecePhase;
    }
}
