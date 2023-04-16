package Board;
import java.awt.Color;

/** 
 * Represents a piece on the board
 */
public class Piece {

    Player owner; //who owns the piece

    int row;   
    int column;
    String piecePhrase;

    public Piece(int row, int column,Player owner){
        this.row = row;
        this.column = column;
        this.owner = owner;

    }

    
    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }


    public void setRow(int row){
        this.row = row;
    }

    public void setColumn(int column){
        this.column = column;
    }

    public Color getColour(){
        return owner.getColour();
    }

    public Player getOwner(){
        return owner;
    }

    public void setPhase(String piecePhrase){
        this.piecePhrase = piecePhrase;
    }

    public String getPhrase(){
        return piecePhrase;
    }
}
