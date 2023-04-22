package Board;
import java.awt.Color;

import javax.swing.JButton;

/** 
 * Represents a piece on the board
 */
public class Piece extends JButton{

    Player owner; //who owns the piece

    private int row; //row of the board the piece is on   
    private int column; //column of the board the piece is on   
    private String piecePhrase; //what is represented as happening when the piece is selected

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
